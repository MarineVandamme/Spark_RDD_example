package app

import core.exercice2.{FightExecution, FightPreparation}
import entities.exercice2.Creature
import entities.exercice2.creatures.{DoubleAxeFury, Solar, Warlord, WorgsRider}
import org.apache.spark._
import org.apache.spark.rdd.RDD
import org.apache.spark.util.IntParam
import org.apache.spark.graphx._
import org.apache.spark.graphx.util.GraphGenerators

import scala.collection.mutable.ArrayBuffer

object Exercice2_fight1 {
  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setAppName("Fight").setMaster("local")
    val sc: SparkContext = new SparkContext(conf)
    val fp1 = FightPreparation.prepareFight1(sc)
    val fields1 = new TripletFields(true, true, true)
    FightExecution.fight(fp1._1, fp1._2, fields1)
  }
}
