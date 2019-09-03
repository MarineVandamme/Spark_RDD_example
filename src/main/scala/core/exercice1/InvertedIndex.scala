package core.exercice1

import entities.exercice1.{Creature, Spell}
import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.mutable.ArrayBuffer

object InvertedIndex {

  def inverseIndex(creatures: IndexedSeq[Creature]): ArrayBuffer[Spell] ={
    val conf: SparkConf = new SparkConf().setAppName("Index").setMaster("local")
    val sc: SparkContext = new SparkContext(conf)
    //Je transforme mon arrayBuffer en RDD (map)
    val rdd = sc.parallelize(creatures.toSeq)
    val rdd1 = rdd.flatMap(creature => creature.spells.map(spell => (spell, creature.name)))
    val rdd2 = rdd1.groupByKey()
    //Je transforme mon RDD en tableau de spells
    var spells: ArrayBuffer[Spell] = new ArrayBuffer[Spell]()
    val res = rdd2.collect().foreach(tuple => {
      var spell = new Spell
      spell.setName(tuple._1)
      for(creature <- tuple._2){
        spell.addCreature(creature)
      }
      spells+=spell
    })
    return spells
  }
}
