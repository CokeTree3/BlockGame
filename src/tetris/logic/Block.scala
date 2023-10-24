package tetris.logic

abstract class Block(val l: List[Point], val blockType: Int, val anchorPoint: Point = Point(0, 0)) {
  def rotateLeft(): Block
  def rotateRight(): Block

  def createNewTet(l: List[Point], anchor: Point): Block = {
    blockType match {
      case 1 => JBlock(l, anchor)
      case 2 => LBlock(l, anchor)
      case 3 => SBlock(l, anchor)
      case 4 => ZBlock(l, anchor)
      case 5 => TBlock(l, anchor)
      case 6 => UBlock(l, anchor)
      case 7 => I_Block(l, anchor)
      case 8 => IBlock(l, anchor)
      case 9 => OBlock(l, anchor)
    }
  }
}

class CenterAnchorBlock(l: List[Point], blockType: Int, anchorPoint: Point) extends Block(l, blockType, anchorPoint){
  def rotateLeft(): Block = {
    val lNew = l.map(p => p.shiftCounterClockwise())
    createNewTet(lNew, this.anchorPoint)
  }

  def rotateRight(): Block = {
    val lNew = l.map(p => p.shiftClockwise())
    createNewTet(lNew, this.anchorPoint)
  }

}

class OffsetAnchorBlock(l: List[Point], blockType: Int, anchorPoint: Point) extends Block(l, blockType, anchorPoint){
  def rotateLeft(): Block = {
    val lNew = l.map(p => p.shiftCounterClockwise().down())
    createNewTet(lNew, this.anchorPoint)
  }

  def rotateRight(): Block = {
    val lNew = l.map(p => p.shiftClockwise().right())
    createNewTet(lNew, this.anchorPoint)
  }
}
