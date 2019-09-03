package core.exercice2

import entities.exercice2.Creature
import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD
import org.apache.spark.graphx._

import scala.collection.mutable.ArrayBuffer

object FightExecution {

  /**
   * Execution du combat
  **/

    var round: Int = 0

  // Je définis d'abord mes deux fonctions pour pouvoir utiliser aggregateMessage
  // sendMsg spécifie comment on envoie les messages aux sommets
  // Ici, elle permet à une créature de cibler ses ennemis
  // L'entrée est un triplet (source: srcAttr, lien: attr ,destination: dstAttr)
  // mergeMsg : Cette fonction vous donne un message A et un message B et vous demande de produire
  // un message C qui aura le même type (il faut simplement fusionner les 2)

  // Fonctions qui permettent d'infliger et/ou de recevoir des dégats
  def sendMsgDammage(triplet: EdgeContext[Creature, String, List[Int]]): Unit ={
    //La source attaque la destination --> le message envoyé est la quantité de dégats
    if (triplet.srcAttr.canAttack(triplet.dstAttr)&&triplet.attr=="enemies"){
      triplet.sendToDst((List(triplet.srcAttr.attack(triplet.dstAttr))))
    }
    if (triplet.dstAttr.canAttack(triplet.srcAttr)&&triplet.attr=="enemies"){
      while (triplet.dstAttr.canAttack(triplet.srcAttr)){
        triplet.sendToSrc(List(triplet.dstAttr.attack(triplet.srcAttr)))
      }
    }
  }
  //Je merge en renvoyant une liste d'entiers correspondant aux dégats reçus car la règle est
  // particulière pour la réception de dégats
  def mergeMsgDammage(msg1: List[Int], msg2: List[Int]): List[Int]={
      msg1:::msg2
  }
  //Fonction pour le mouvement
  def sendMsgMove(triplet: EdgeContext[Creature, String, List[Creature]]): Unit ={
    if (triplet.attr=="enemies"){
      triplet.sendToDst((List(triplet.srcAttr)))
      triplet.sendToSrc(List(triplet.dstAttr))
    }
  }
  def mergeMsgMove(msg1: List[Creature], msg2: List[Creature]): List[Creature] ={
    msg1:::msg2
  }

  def findCreatureNameById(vertexRDD: RDD[(VertexId, (Creature))], id: Int): String ={
    val creatures = vertexRDD.collect
    var res = ""
    for(creature<-creatures){
      if (creature._2.getId()==id){
        res = creature._2.name
      }
    }
    return res
  }

  def round(node: RDD[(VertexId, (Creature))],edges: RDD[Edge[String]], tripletFields: TripletFields): (RDD[(VertexId, (Creature))], RDD[Edge[String]], TripletFields)={
    val vertexRDD = node
    val edgeRDD = edges
    var graph: Graph[Creature, String] = Graph(vertexRDD, edgeRDD)
    //J'incrépente le compteur de tours
    round=round + 1
    println("*********************    ROUND "+round+"    *********************")
    //Les créatures s'attaquent
    val messagesDammage = graph.aggregateMessages[List[Int]](sendMsgDammage, mergeMsgDammage, tripletFields)
    messagesDammage.collect()
    graph=graph.joinVertices(messagesDammage){
        (_, target, dammageList)=>{
          for(dammage<-dammageList){
            target.takeDamage(dammage)
          }
          target
        }
    }

    //On nettoie le champ de bataille
    var idToRemove = List[Int]()
    for (vertice<-graph.vertices.collect){
      if (vertice._2.hp<=0){
        println(vertice._2.name+" n°"+vertice._2.getId()+" is dead")
        idToRemove=idToRemove:::List(vertice._2.getId())
      }
    }
    graph = graph.subgraph(vpred = (_, creature) =>  creature.hp > 0)
    var nwedges = graph.edges.filter(edge => {
      if(idToRemove.contains(edge.srcId)||idToRemove.contains(edge.dstId)){
        false
      }
      else{
        true
      }
    })
    graph = Graph(graph.vertices, nwedges)
    // On réinitialise les tours des attaques
    graph.vertices.foreach(_._2.initializeAttacks())
    // Les créatures se régénèrent
    graph = Graph(graph.vertices.map(v => {
      v._2.regenerate()
      v
    }), graph.edges)
    //Puis bougent
    val messagesMove = graph.aggregateMessages[List[Creature]](sendMsgMove, mergeMsgMove, tripletFields)
    messagesMove.collect()
    graph=graph.joinVertices(messagesMove){
      (_, creature, enemies)=>{
        var closestEnemy: Creature = enemies(0)
        var lowestDist: Double = 4000000
        for(enemy<-enemies){
          if(creature.calculateDistance(enemy)<lowestDist){
            lowestDist=creature.calculateDistance(enemy)
            closestEnemy=enemy
          }
        }
        println("Closest enemy of "+creature.name + " n°"+creature.getId()+" is "+closestEnemy+" with a distance of "+lowestDist)
        creature.move(closestEnemy)
        creature
      }
    }
    //Synthèse :
    for (vertice<- graph.vertices){
      println(vertice._2.name+" n°"+vertice._2.getId()+" has "+vertice._2.hp+"hp and is in position : ("+vertice._2.position._1+","+vertice._2.position._2+")")
    }
    return (graph.vertices, graph.edges, tripletFields)
  }


  var countGood:Integer=0
  var countBad:Integer=0
  var res: String=""

  def  fight(node: RDD[(VertexId, (Creature))],edges: RDD[Edge[String]], tripletFields: TripletFields): Unit = {
    var continue: Boolean = true
    var gameNode = node
    var gameEdge = edges
    while(continue){
    //for(i<-0 to 5){
      var graphParam=round(gameNode, gameEdge, tripletFields)
      gameNode=graphParam._1
      gameEdge=graphParam._2
      for(node<-gameNode){
        if(node._2.isBad()){
          countBad+=1
        }
        if(!node._2.isBad()){
          countGood+=1
        }
      }
      if (countGood==0||countBad==0){
        continue=false
        if(countGood==0){
          res="Bad team wins !!"
        }
        if(countBad==0){
          res="Good team wins !!"
        }
      }
      countBad=0
      countGood=0
    }
    println(res)
  }
}
