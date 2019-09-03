package entities.exercice2.creatures

import entities.exercice2.{Attack, Creature}

import scala.collection.mutable.ArrayBuffer

abstract class Archon(id: Int, isEvil: Boolean) extends Creature(id, isEvil){
  override val name: String
  override var hp: Int
  override val maxHP: Int
  override var attacks: ArrayBuffer[Attack]
  override val speedPerTurn: Int
  override val armor: Int
  override val regen: Int
  override val canFly: Boolean = false
  override var position: (Int, Int, Int)=(0,0,0)
}

case class Hound(id: Int, isEvil: Boolean) extends Archon(id, isEvil){
  override val name: String = "Hound Archon"
  override var hp: Int = 39
  override val maxHP: Int = 39
  override var attacks: ArrayBuffer[Attack] = new ArrayBuffer[Attack]()
  attacks+=new Attack("bite", 5, 1, 8, 3, 1, List(8))
  override val speedPerTurn: Int = 40
  override val armor: Int = 19
  override val regen: Int = 10
}
