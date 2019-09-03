package entities.exercice2.creatures

import entities.exercice2.{Attack, Creature}

import scala.collection.mutable.ArrayBuffer

abstract class Angel(id: Int, isEvil: Boolean) extends Creature(id, isEvil) {
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

case class Solar(id: Int, isEvil: Boolean) extends Angel(id, isEvil){
  override val name: String = "Solar"
  override var hp: Int = 363
  override val maxHP: Int = 363
  override var attacks: ArrayBuffer[Attack] = new ArrayBuffer[Attack]()
  attacks+=new Attack("dancing greatsword", 5, 3, 6, 18, 1, List(35, 30, 25, 20))
  attacks+=new Attack("composite longbow", 110, 2, 6, 14, 1, List(31, 26, 21, 16))
  override val speedPerTurn: Int = 50
  override val armor: Int = 44
  override val regen: Int = 15
}

case class Planetar(id: Int, isEvil: Boolean) extends Angel(id, isEvil){
  override val name: String = "Planetar"
  override var hp: Int = 229
  override val maxHP: Int =  229
  override var attacks: ArrayBuffer[Attack] = new ArrayBuffer[Attack]()
  attacks+=new Attack("holy greatsword", 5, 3, 6, 15, 1, List(27, 22, 17))
  override val speedPerTurn: Int = 50
  override val armor: Int = 32
  override val regen:Int = 10
}

case class MovanicDeva(id: Int, isEvil: Boolean) extends Angel(id, isEvil){
  override val name: String = "Movanic Deva"
  override var hp: Int = 126
  override val maxHP: Int =  126
  override var attacks: ArrayBuffer[Attack] = new ArrayBuffer[Attack]()
  attacks+=new Attack("flaming greatsword", 5, 2, 6, 7, 1, List(17, 12, 7))
  override val speedPerTurn: Int = 40
  override val armor: Int = 24
  override val regen:Int = 10
}

case class AstralDeva(id: Int, isEvil: Boolean) extends Angel(id, isEvil){
  override val name: String = "Astral Deva"
  override var hp: Int = 172
  override val maxHP: Int =  172
  override var attacks: ArrayBuffer[Attack] = new ArrayBuffer[Attack]()
  attacks+=new Attack("disrupting warhammer", 5, 1, 8, 14, 3, List(26, 21, 16))
  override val speedPerTurn: Int = 50
  override val armor: Int = 29
  override val regen:Int = 10
}

case class Slayer(id: Int, isEvil: Boolean) extends Angel(id, isEvil){
  override val name: String = "Slayer"
  override var hp: Int = 112
  override val maxHP: Int =  112
  override var attacks: ArrayBuffer[Attack] = new ArrayBuffer[Attack]()
  attacks+=new Attack("bane orc double axe", 5, 1, 8, 7, 3, List(21, 16, 11))
  attacks+=new Attack("mwk composite longbow", 110, 1, 8, 6, 3, List(19, 14, 9))
  override val speedPerTurn: Int = 40
  override val armor: Int = 26
  override val regen:Int = 0
}
