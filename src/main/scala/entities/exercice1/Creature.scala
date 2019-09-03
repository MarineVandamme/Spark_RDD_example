package entities.exercice1

import scala.collection.mutable.ArrayBuffer

case class Creature(){

  var name: String = "default creature name"
  var spells = ArrayBuffer[String]()

  def addSpell(spell : String): Unit={
    spells+=spell
  }
  def setName(crName : String): Unit={
    name = crName
  }

}

object Creature{

}
