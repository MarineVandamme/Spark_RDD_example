package app

import core.exercice2.{FightExecution, FightPreparation}
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.graphx.TripletFields

object Exercice2_fight2 {
  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setAppName("Fight").setMaster("local")
    val sc: SparkContext = new SparkContext(conf)
    val fp2 = FightPreparation.prepareFight2(sc)
    val fields2 = new TripletFields(true, true, true)
    FightExecution.fight(fp2._1, fp2._2, fields2)
  }
}
