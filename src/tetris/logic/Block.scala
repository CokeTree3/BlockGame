package tetris.logic

abstract class Block(val l: List[Point], val blockType: Int, val anchorPoint: Point = Point(0, 0)) {
  def rotateLeft(): Block
  def rotateRight(): Block

  def moveAnchor(anchor: Point, l: List[Point] = this.l): Block = {
    blockType match {
      case 1 => JBlock(l, anchor)
      case 2 => LBlock(l, anchor)
      case 3 => SBlock(l, anchor)
      case 4 => ZBlock(l, anchor)
      case 5 => TBlock(l, anchor)
      case 6 => UBlock(l, anchor)
      case 7 => OBlock(l, anchor)
      case 8 => I_Block(l, anchor)
      case 9 => DotBlock(l, anchor)
      case 10 => I2Block(l, anchor)
      case 11 => I3Block(l, anchor)
      case 12 => I4Block(l, anchor)

    }
  }

  def mapToAnchor(newAnchor: Point = this.anchorPoint): List[Point] = l.map(p => p.moveTo(newAnchor))
}

class CenterAnchorBlock(l: List[Point], blockType: Int, anchorPoint: Point) extends Block(l, blockType, anchorPoint){
  def rotateLeft(): Block = {
    val lNew = l.map(p => p.shiftCounterClockwise())
    moveAnchor(this.anchorPoint, lNew)
  }

  def rotateRight(): Block = {
    val lNew = l.map(p => p.shiftClockwise())
    moveAnchor(this.anchorPoint, lNew)
  }

}

class OffsetAnchorBlock(l: List[Point], blockType: Int, anchorPoint: Point) extends Block(l, blockType, anchorPoint){
  def rotateLeft(): Block = {
    val lNew = l.map(p => p.shiftCounterClockwise().down())
    moveAnchor(this.anchorPoint, lNew)
  }

  def rotateRight(): Block = {
    val lNew = l.map(p => p.shiftClockwise().right())
    moveAnchor(this.anchorPoint, lNew)
  }
}
