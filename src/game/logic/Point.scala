package game.logic

case class Point(x : Int, y : Int) {

  def right(): Point = Point(x + 1, y)

  def left(): Point = Point(x - 1, y)

  def shiftClockwise(): Point = Point(-y, x)

  def shiftCounterClockwise(): Point = Point(y, -x)

  def moveTo(baseP: Point): Point = Point(baseP.x + this.x, baseP.y + this.y)

}