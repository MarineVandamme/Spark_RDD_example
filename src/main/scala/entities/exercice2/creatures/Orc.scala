package entities.exercice2.creatures

import entities.exercice2.{Attack, Creature}

import scala.collection.mutable.ArrayBuffer

abstract class Orc(id: Int, isEvil: Boolean) extends Creature(id, isEvil) {
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

case class WorgsRider(id: Int, isEvil: Boolean) extends Orc(id, isEvil){
  override val name: String = "Worg Rider"
  override var hp: Int = 13
  override val maxHP: Int = 13
  override var attacks: ArrayBuffer[Attack] = new ArrayBuffer[Attack]()
  attacks+=new Attack("mwk battleaxe", 5, 1, 8, 0, 3, List(6) )
  attacks+=new Attack("shortbow", 110, 1, 6, 0, 3, List(4))
  override val speedPerTurn: Int = 20
  override val armor: Int = 18
  override val regen: Int = 0
}

case class Warlord(id: Int, isEvil: Boolean) extends Orc(id, isEvil){
  override val name: String = "Warlord"
  override var hp: Int = 141
  override val maxHP: Int = 141
  override var attacks: ArrayBuffer[Attack] = new ArrayBuffer[Attack]()
  attacks+=new Attack("vicious flail", 5, 1,8,10,1 , List(20, 15, 5))
  attacks+=new Attack("mwk throwing axe", 110, 1, 6, 5, 1, List(20, 15, 5))
  override val speedPerTurn: Int = 30
  override val armor: Int = 27
  override val regen: Int = 0
}

case class DoubleAxeFury(id: Int, isEvil: Boolean) extends Orc(id, isEvil){
  override val name: String = "Half-Orc Barbarian 11"
  override var hp: Int = 142
  override val maxHP: Int = 142
  override var attacks: ArrayBuffer[Attack] = new ArrayBuffer[Attack]()
  attacks+=new Attack("orc double axe", 5, 1,8,10,3, List(19,14,9))
  attacks+=new Attack("mwk composite longbow", 110, 1, 8, 6, 3, List(16, 11, 6))
  override val speedPerTurn: Int = 40
  override val armor: Int = 17
  override val regen: Int = 3
}

case class GreataxeOrc(id: Int, isEvil: Boolean) extends Orc(id, isEvil){
  override val name: String = "Orc Barbarian 4"
  override var hp: Int = 42
  override val maxHP: Int = 42
  override var attacks: ArrayBuffer[Attack] = new ArrayBuffer[Attack]()
  attacks+=new Attack("greataxe", 5, 1,12,10,1, List(11) )
  attacks+=new Attack("mwk composite longbow", 110, 1, 6, 7, 1, List(5))
  override val speedPerTurn: Int = 30
  override val armor: Int = 15
  override val regen: Int = 0
}