package tetris.logic

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

case class I_Block(blockList: List[Point], anchorP: Point = Point(0, 0)) extends CenterAnchorBlock(blockList, 7, anchorP){
}
object I_Block{
  def apply() : I_Block = new I_Block(List[Point](Point(-1, -1), Point(-1, 0), Point(-1, 1), Point(0, 1), Point(1, 1)))
}


case class IBlock(blockList: List[Point], anchorP: Point = Point(0, 0)) extends OffsetAnchorBlock(blockList, 8, anchorP){
}
object IBlock{
  def apply() : IBlock = new IBlock(List[Point](Point(-1, 0), Point(0, 0), Point(1, 0), Point(2, 0)))
}


case class OBlock(blockList: List[Point], anchorP: Point = Point(0, 0)) extends OffsetAnchorBlock(blockList, 9, anchorP){
}
object OBlock{
  def apply() : OBlock = new OBlock(List[Point](Point(0, -1), Point(0, 0), Point(1, -1), Point(1, 0)))
}
