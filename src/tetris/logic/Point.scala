package tetris.logic

// you can alter this file!

case class Point(x : Int, y : Int, cellFilled: Boolean = false) {

  def down(): Point = Point(x, y + 1)

  def addCell(content: Boolean): Point = {
    val s = Point(x, y, content)
    s
  }

  def right(): Point = Point(x + 1, y)

  def left(): Point = Point(x - 1, y)

  def shiftClockwise(): Point = Point(-y, x)

  def shiftCounterClockwise(): Point = Point(y, -x)

  def moveTo(baseP: Point): Point = Point(baseP.x + this.x, baseP.y + this.y)

  def isOutOfBounds(width: Int, height: Int): Boolean = {
    if(x < 0 || y < 0 || x >= width || y >= height) true else false
  }
}

object Point{
  def apply(x: Int, y: Int): Point = new Point(x, y, false)
}