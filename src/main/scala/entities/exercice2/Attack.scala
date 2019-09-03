package entities.exercice2

case class Attack(name: String, scope: Int, a:Int, x:Int, b:Int, c:Int, list: List[Int]) {

  /***
  Dice notation : AdX+B/xC
      A = nombre de lancés
      X = nombre de faces
      B = à ajouter
      C = nombre de fois à jouer
  Le résultat donne le nombre de PV retirés à l'ennemi
  ***/

  def calculateAttackPower(turn: Int): Int ={
    val r = scala.util.Random
    var res=0
    for (i<-1 to c){
      res += a*(r.nextInt(x+1)-1)+b
    }
    return res
  }

}

object Attack{

}
