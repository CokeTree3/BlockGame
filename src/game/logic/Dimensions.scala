package game.logic

case class Dimensions(width : Int, height : Int) {

  val allPointsInside : Seq[Point] =
    for(y <- 0 until height; x <- 0 until width)
      yield Point(x,y)
}
