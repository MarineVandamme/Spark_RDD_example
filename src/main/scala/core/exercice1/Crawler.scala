package core.exercice1

import entities.exercice1.{Creature, Spell}
import org.apache.spark.{SparkConf, SparkContext}
import org.jsoup.Jsoup

import scala.collection.mutable.ArrayBuffer

object Crawler {

  val baseUrl: String ="http://www.dxcontent.com/MDB_MonsterBlock.asp?MDBID="

  def getCreature(id: Int): Creature={
    val pageUrl=baseUrl+id
    val document = Jsoup.connect(pageUrl).get()
    var creature = new Creature()
    //Recherche du nom de la créature
    val nomCreatureElement = document.select("tbody > tr > td > b")
    var nomCreature : String = "no name"
    if (nomCreatureElement.toString().split('>')(1).split('<')(0).contains(',')){
      nomCreature = nomCreatureElement.toString().split('>')(1).split('<')(0).split(", ")(1)
    }else {
      nomCreature = nomCreatureElement.toString().split('>')(1).split('<')(0)
    }
    creature.setName(nomCreature)
    //Recherche des sorts
    val elements = document.getElementsByClass("HangIndent_2")
    if (elements.size()!=0){
      val spells = elements.get(0).toString.split('>')(1).split('<')(0).split(", ")
      for (spell <- spells) creature.addSpell(spell)
    }
    return creature
  }

  def getCreatures(): IndexedSeq[Creature]={
    var creatures = new ArrayBuffer[Creature]()
    for (i<-4 to 15){
      var creature= getCreature(i)
      creatures+=creature
    }
    return creatures
  }

}
