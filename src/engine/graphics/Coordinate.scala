// DO NOT MODIFY FOR BASIC SUBMISSION
// scalastyle:off

package engine.graphics

case class Coordinate(x: Float, y: Float) {
  def +(rhs: Coordinate) : Coordinate = Coordinate(x + rhs.x, y + rhs.y)
  def -(rhs : Coordinate) : Coordinate = Coordinate(x - rhs.x, y - rhs.y)
}
