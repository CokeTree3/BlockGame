package tetris.logic

case class Game3x3Square(blockList: List[Point], anchorP: Point = Point(0, 0)) extends CenterAnchorBlock(blockList, 0, anchorP) {

  def moveNext: Game3x3Square = {
    this.anchorP.x match {
      case 7 if this.anchorP.y == 7 => Game3x3Square()
      case 7 => Game3x3Square(blockList, Point(1, this.anchorP.y + 3))
      case _ => Game3x3Square(blockList, Point(this.anchorP.x + 3, this.anchorP.y))
    }
  }
}
object Game3x3Square{
  def apply() : Game3x3Square = new Game3x3Square(List[Point](Point(-1, -1), Point(-1, 0), Point(-1, 1), Point(0, -1), Point(0, 0), Point(0, 1), Point(1, -1), Point(1, 0), Point(1, 1)), Point(1, 1))
}

case class JBlock(blockList: List[Point], anchorP: Point = Point(0, 0)) extends CenterAnchorBlock(blockList, 1, anchorP) {
}
object JBlock{
  def apply() : JBlock = new JBlock(List[Point](Point(-1, -1), Point(-1, 0), Point(0, 0), Point(1, 0)))
}


case class LBlock(blockList: List[Point], anchorP: Point = Point(0, 0)) extends CenterAnchorBlock(blockList, 2, anchorP){
}
object LBlock{
  def apply() : LBlock = new LBlock(List[Point](Point(-1, 0), Point(0, 0), Point(1, 0), Point(1, -1)))
}


case class SBlock(blockList: List[Point], anchorP: Point = Point(0, 0)) extends CenterAnchorBlock(blockList, 3, anchorP) {
}
object SBlock{
  def apply() : SBlock = new SBlock(List[Point](Point(-1, 0), Point(0, 0), Point(0, -1), Point(1, -1)))
}


case class ZBlock(blockList: List[Point], anchorP: Point = Point(0, 0)) extends CenterAnchorBlock(blockList, 4, anchorP){
}
object ZBlock{
  def apply() : ZBlock = new ZBlock(List[Point](Point(-1, -1), Point(0, -1), Point(0, 0), Point(1, 0)))
}


case class TBlock(blockList: List[Point], anchorP: Point = Point(0, 0)) extends CenterAnchorBlock(blockList, 5, anchorP){
}
object TBlock{
  def apply() : TBlock = new TBlock(List[Point](Point(0, -1), Point(0, 0), Point(-1, 0), Point(1, 0)))
}

case class UBlock(blockList: List[Point], anchorP: Point = Point(0, 0)) extends CenterAnchorBlock(blockList, 6, anchorP){
}
object UBlock{
  def apply() : UBlock = new UBlock(List[Point](Point(-1, -1), Point(-1, 0), Point(0, 0), Point(1, 0), Point(1, -1)))
}


case class OBlock(blockList: List[Point], anchorP: Point = Point(0, 0)) extends CenterAnchorBlock(blockList, 7, anchorP){
}
object OBlock{
  def apply() : OBlock = new OBlock(List[Point](Point(0, -1), Point(0, 0), Point(1, -1), Point(1, 0)))
}


case class I_Block(blockList: List[Point], anchorP: Point = Point(0, 0)) extends CenterAnchorBlock(blockList, 8, anchorP){
}
object I_Block{
  def apply() : I_Block = new I_Block(List[Point](Point(-1, -1), Point(-1, 0), Point(-1, 1), Point(0, 1), Point(1, 1)))
}


case class DotBlock(blockList: List[Point], anchorP: Point = Point(0, 0)) extends CenterAnchorBlock(blockList, 9, anchorP){
}
object DotBlock{
  def apply() : DotBlock = new DotBlock(List[Point](Point(0, 0)))
}


case class I2Block(blockList: List[Point], anchorP: Point = Point(0, 0)) extends CenterAnchorBlock(blockList, 10, anchorP){
}
object I2Block{
  def apply() : I2Block = new I2Block(List[Point](Point(0, 0), Point(1, 0)))
}


case class I3Block(blockList: List[Point], anchorP: Point = Point(0, 0)) extends CenterAnchorBlock(blockList, 11, anchorP){
}
object I3Block{
  def apply() : I3Block = new I3Block(List[Point](Point(0, 0), Point(1, 0), Point(-1, 0)))
}


case class I4Block(blockList: List[Point], anchorP: Point = Point(0, 0)) extends CenterAnchorBlock(blockList, 12, anchorP){
}
object I4Block{
  def apply() : I4Block = new I4Block(List[Point](Point(0, 0), Point(1, 0), Point(-1, 0), Point(2, 0)))
}



