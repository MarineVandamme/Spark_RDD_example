package entities.exercice2.creatures

import entities.exercice2.{Attack, Creature}

import scala.collection.mutable.ArrayBuffer

abstract class Dragon(id: Int, isEvil: Boolean) extends Creature(id, isEvil) {
  override val name: String
  override var hp: Int
  override val maxHP: Int
  override var attacks: ArrayBuffer[Attack]
  override val speedPerTurn: Int
  override val armor: Int
  override val regen: Int
  override val canFly: Boolean = true
  override var position: (Int, Int, Int)=(0,0,0)
}

case class GreatWyrmRedDragon(id: Int, isEvil: Boolean) extends Dragon(id, isEvil){
  override val name: String = "Great Wyrm Red Dragon"
  override var hp: Int = 449
  override val maxHP: Int = 449
  override var attacks: ArrayBuffer[Attack] = new ArrayBuffer[Attack]()
  attacks+=new Attack("bite", 5, 4, 8, 24, 1, List(37))
  override val speedPerTurn: Int = 40
  override val armor: Int = 39
  override val regen: Int = 20
}

case class YoungAdultRedDragon(id: Int, isEvil: Boolean) extends Dragon(id, isEvil){
  override val name: String = "Young Adult Red Dragonn"
  override var hp: Int = 172
  override val maxHP: Int = 172
  override var attacks: ArrayBuffer[Attack] = new ArrayBuffer[Attack]()
  attacks+=new Attack("bite", 5, 2, 8, 13, 1, List(22))
  override val speedPerTurn: Int = 40
  override val armor: Int = 26
  override val regen: Int = 5
}