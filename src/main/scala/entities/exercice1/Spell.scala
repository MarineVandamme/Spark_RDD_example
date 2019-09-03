package entities.exercice1

import scala.collection.mutable.ArrayBuffer

case class Spell() {
  var name: String = "default spell name"
  var creatures = ArrayBuffer[String]()

  def addCreature(creature: String): Unit={
    creatures+=creature
  }
  def setName(spName : String): Unit={
    name = spName
  }
}

object Spell{

}
