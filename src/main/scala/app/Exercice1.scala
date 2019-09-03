package app

import core.exercice1.Crawler.{getCreatures}
import core.exercice1.InvertedIndex.{inverseIndex}

object Exercice1 {
  def main(args: Array[String]): Unit = {
    //Crawler : Permet d'explorer les pages d'un site web pour collecter des informations
    val creatures = getCreatures()
    //Index inversé : Tansforme une liste de (a1, list(b1,b2...)) en (b1, list(a1,a3...))
    val spells = inverseIndex(creatures)
    //Affichage des résultats
    println("" +
      "********************************************************\n" +
      "***                  Résultats du crawler            ***\n" +
      "********************************************************\n")
    for (creature <- creatures){
      println(creature.name+" : ")
      for (spell<-creature.spells){
        print(spell+", ")
      }
      println('\n')
    }
    println("" +
      "********************************************************\n" +
      "***             Résultats de l'index inversé         ***\n" +
      "********************************************************\n")
    for (spell <- spells){
      println(spell.name+" : ")
      for (creature<-spell.creatures){
        print(creature+", ")
      }
      println('\n')
    }
  }
}
