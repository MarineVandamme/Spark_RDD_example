package entities.exercice2

import scala.collection.mutable.ArrayBuffer

abstract class Creature(id: Int, isEvil: Boolean) extends Serializable {

  val name: String
  var hp: Int
  val maxHP: Int
  var attacks: ArrayBuffer[Attack]
  val speedPerTurn: Int
  val armor: Int
  val regen: Int
  val canFly: Boolean
  var position: (Int, Int, Int)
  var turn = 0

  def isBad(): Boolean ={
    return isEvil
  }

  def getId(): Int={
    return id
  }

  def getPosition(): (Int, Int, Int)={
    return position
  }
  def setPosition(pos:(Int, Int, Int)): Unit ={
    position=pos
  }

  def getMaxRange(): Int={
    var maxRange=0
    for(attack<-attacks){
      if(attack.scope>maxRange){
        maxRange=attack.scope
      }
    }
    return maxRange
  }

  // Stratégie pour choisir l'attaque : je choisis celle dont la portée est la plus courte
  // En entrée : la distance avec la cible
  def chooseAttack(distance: Int): Attack={
    var chosenAttack: Attack = null
    for(attack<-this.attacks){
      if(distance<=attack.scope){
        if(chosenAttack==null){
          chosenAttack=attack
        } else {
          if(chosenAttack.scope>attack.scope){
            chosenAttack=attack
          }
        }
      }
    }
    return chosenAttack
  }

  // inflige les dégats à la créature
  def takeDamage(power: Int): Unit ={
    if(power>(armor*0.65)){
      println(this.name + "n°"+this.id + " takes "+power+" dammages")
      hp-=power
    }
    else {println(this.name+" n°"+this.id+ " is protected by his armor")}
  }

  def calculateDistance(enemy: Creature): Int={
    val distance: Double = Math.sqrt(
      Math.pow((enemy.getPosition()._1)-(getPosition()._1), 2)+
        Math.pow((enemy.getPosition()._2)-(getPosition()._2), 2)+
        Math.pow((enemy.getPosition()._3)-(getPosition()._3), 2)
    )
    return distance.toInt
  }

  def canAttack(enemy: Creature): Boolean={
    if(calculateDistance(enemy)<=getMaxRange()&&attacks(0).list.length>turn){
      return true
    } else {
      return false
    }
  }
  // renvoie la puissance de l'attaque
  def attack(enemy: Creature): Int={
    val distance:Int = calculateDistance(enemy)
    val attack: Attack = this.chooseAttack(distance)
    var power = 0
    if(attack!=null){power = attack.calculateAttackPower(turn)}
    println(name + " " + id + " attacks " + enemy.name + " n°" + enemy.getId() + " with " + attack.name+" dealing "+power+" dammage")
    turn+=1
    return power
  }

  // Stratégie : je veux me rapproher au maximum de l'ennemi
  // En indice : la position de l'ennemi
  // Je n'ai pas traité la 3D dans cet exercice
  def move(creature: Creature): Unit ={
    val x1: Int = creature.position._1
    val x2: Int = position._1
    val y1: Int = creature.position._2
    val y2: Int = position._2
    val distance: Double = Math.sqrt(
      Math.pow(x2-x1, 2)+
        Math.pow(y2-y1, 2)
    )
    if(!canAttack(creature)){
      var newx: Int = x2
      var newy: Int = y2
      if(x2>x1){
        if(x2-speedPerTurn<x1){
          newx=x1
        } else {
          newx  = x2-speedPerTurn
        }
      }else{
        if(x2+speedPerTurn>x1){
          newx=x1
        }else{
          newx=x2-speedPerTurn
        }
      }
      if(y2>y1){
        if(y2-speedPerTurn<y1){
          newy=y1
        } else {
          newy  = y2-speedPerTurn
        }
      }else{
        if(y2+speedPerTurn>y1){
          newy=y1
        }else{
          newy=y2-speedPerTurn
        }
      }
      //Je définis les limites du terrain de combat (carré de 1000*1000)
      if(newx<0){newx=0}
      if(newy<0){newy=0}
      if (newx>1000){newx=1000}
      if (newy>1000){newy=1000}
      setPosition(newx, newy, position._3)
      println(name + " n°"+id+" moves to ("+newx+","+newy+")")
    }else{
      println("closest enemy is already targetable")
    }
  }


  def regenerate(): Unit ={
    hp+=regen
    if (hp>maxHP){
      hp=maxHP
    }else{
      if(regen!=0){
        println(name+ " n°"+id+" regenerates of "+regen+"hp")
      }
    }
  }

  def initializeAttacks(): Unit ={
    turn=0
  }
}
