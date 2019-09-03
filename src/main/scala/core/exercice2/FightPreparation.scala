package core.exercice2

import entities.exercice2.Creature
import entities.exercice2.creatures.{AstralDeva, DoubleAxeFury, GreatWyrmRedDragon, GreataxeOrc, Hound, MovanicDeva, Planetar, Slayer, Solar, Warlord, WorgsRider, YoungAdultRedDragon}
import org.apache.spark.rdd.RDD
import org.apache.spark.SparkContext
import org.apache.spark.graphx.{Edge, VertexId}

import scala.collection.mutable.ArrayBuffer

object FightPreparation {

  /**
   * Préparation des ponts et noeuds pour le combat
  **/

  def prepareFight1(sc: SparkContext): (RDD[(VertexId, (Creature))], RDD[Edge[String]]) ={
    // Je crée un tableau contenant les personnages du combat
    //Les noeuds correspondent à chaque personnage, ils sont chacun identifiés par un VertexId
    val characters: ArrayBuffer[(VertexId, Creature)] = ArrayBuffer()
    var character: Creature = new Solar(1, false)
    character.setPosition((40,100, 0))
    characters+=((1, character))
    character = new Warlord(2,true)
    character.setPosition((170,100, 0))
    characters+=((2, character))
    for(i<-3 to 6){
      var y = 70+((i-3)*20)
      character = new DoubleAxeFury(i, true)
      character.setPosition((140, y, 0))
      characters+=((i, character))
    }
    for(i<-7 to 15){
      var y = 20+((i-7)*20)
      character = new WorgsRider(i, true)
      character.setPosition((120, y, 0))
      characters+=((i,character))
    }
    // Je crée un tableau contenant les relations entre chaque personnage
    //Les ponts correspondent aux relations entre chaque noeud
    //la chaine de carractères est la realition entre les personnages (alliés ou ennemis), l'entier est la distance entre eux
    val relations: ArrayBuffer[Edge[String]] = ArrayBuffer()
    for(character1<-characters){
      for(character2<-characters){
        if(character1._2.isBad()){
          if(!character2._2.isBad()){
            relations+=Edge(character1._2.getId().toLong, character2._2.getId().toLong, "enemies")
          } else {
            relations+=Edge(character1._2.getId().toLong, character2._2.getId().toLong, "allies")
          }
        }else{
          if (!character2._2.isBad()){relations+=Edge(character1._2.getId().toLong, character2._2.getId().toLong, "allies")}
        }
      }
    }
    // Je transforme mes tableaux en RDD
    val characterNode: RDD[(VertexId, (Creature))] = sc.parallelize(characters)
    val characterEdges: RDD[Edge[String]] = sc.parallelize(relations)
    return (characterNode, characterEdges)
  }

  def prepareFight2(sc: SparkContext): (RDD[(VertexId, (Creature))], RDD[Edge[String]]) ={
    // Je crée un tableau contenant les personnages du combat
    //ALLIES
    val characters: ArrayBuffer[(VertexId, Creature)] = ArrayBuffer()
    var character: Creature = new Solar(1, false)
    character.setPosition((50,100, 0))
    characters+=((1, character))
    for(i<-2 to 3){
      var y = 35+((i-2)*40)
      character=new Planetar(i, false)
      character.setPosition((15, y, 0))
      characters+=((i, character))
    }
    for(i<-4 to 5){
      var y = 35+((i-4)*40)
      character=new MovanicDeva(i, false)
      character.setPosition((25, y, 0))
      characters+=((i, character))
    }
    for(i<-6 to 10){
      var y = 15+((i-6)*20)
      character=new AstralDeva(i,false)
      character.setPosition((35, y, 0))
      characters+=((i, character))
    }
    for(i<-11 to 110){
      var a = math.ceil((i-7)/20).toInt
      var x = (a*5)+45
      var y = 5+(((i-7)-(a*20))*5)
      character=new Hound(i, false)
      character.setPosition((x, y, 0))
      characters+=((i, character))
    }
    //ENEMIES
    character=new GreatWyrmRedDragon(111, true)
    character.setPosition((180,55, 0))
    characters+=((111, character))
    for(i<-112 to 116){
      var y =10+((i-108)*30)
      character=new YoungAdultRedDragon(i,true)
      character.setPosition((170, y, 0))
      characters+=((i, character))
    }
    for(i<-117 to 131){
      var y =15+((i-111)*5)
      character=new Slayer(i, true)
      character.setPosition((160, y, 0))
      characters+=((i, character))
    }
    for(i<-132 to 431){
      var a = math.ceil((i-127)/20).toInt
      var x = (a*5)+75
      var y = 5+(((i-127)-(a*20))*5)
      character=new GreataxeOrc(i,  true)
      character.setPosition((x,y, 0))
      characters+=((i, character))
    }
    // Je crée un tableau contenant les relations entre chaque personnage (relation, distance)
    val relations: ArrayBuffer[Edge[String]] = ArrayBuffer()
    for(character1<-characters){
      for(character2<-characters){
        if(character1._2.isBad()){
          if(!character2._2.isBad()){
            relations+=Edge(character1._2.getId().toLong, character2._2.getId().toLong, "enemies")
          } else {
            relations+=Edge(character1._2.getId().toLong, character2._2.getId().toLong, "allies")
          }
        }
      }
    }
    // Je transforme mes tableaux en RDD
    val characterNode: RDD[(VertexId, (Creature))] = sc.parallelize(characters)
    val characterEdges: RDD[Edge[String]] = sc.parallelize(relations)
    return (characterNode, characterEdges)
  }
}
