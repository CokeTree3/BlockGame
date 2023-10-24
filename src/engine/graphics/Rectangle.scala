// DO NOT MODIFY FOR BASIC SUBMISSION
// scalastyle:off

package engine.graphics

case class Rectangle(leftUp: Coordinate, width: Float, height: Float) {
  def top: Float = leftUp.y
  def bottom: Float = top + height
  def left: Float = leftUp.x
  def leftDown: Coordinate = Coordinate(left, bottom)
  def right: Float = left + width
  def rightDown: Coordinate = Coordinate(right, bottom)
  def rightUp: Coordinate = Coordinate(right, top)
  def widthThirds(i:Int): Float = (width/3)*i
  def heightThirds(i:Int): Float = (height/3)*i
  def center: Coordinate = Coordinate(centerX, centerY)
  def centerX: Float = leftUp.x + width / 2
  def centerY: Float = leftUp.y + height / 2
  def centerUp: Coordinate = Coordinate(centerX, top)
  def centerDown: Coordinate = Coordinate(centerX, bottom)
  def centerLeft: Coordinate = Coordinate(left, centerY)
  def centerRight: Coordinate = Coordinate(right, centerY)

  def grow(factor : Float) : Rectangle = {
    val newWidth = width * factor
    val newHeight = height * factor
    val diffX = (newWidth - width) / 2
    val diffY = (newHeight - height) / 2
    Rectangle(Coordinate(left - diffX, top - diffY ), newWidth,newHeight)

  }

  def contains(p : Coordinate) : Boolean =
    p.x >= left && p.x < right && p.y >= top && p.y < bottom

  def localCoordinate(globalCoordinate : Coordinate) : Coordinate =
    globalCoordinate - leftUp
}

object Rectangle{
  def apply(leftUp: Coordinate, rightDown:Coordinate): Rectangle = {
    val width = rightDown.x - leftUp.x
    val height = rightDown.y - leftUp.y
    new Rectangle(leftUp, width, height)
  }

  def apply(leftUp: Coordinate, width: Float): Rectangle = new Rectangle(leftUp, width, width)
}