package tetris.logic

abstract class CellType(){
  def next: CellType
}
case class FullCell()  extends CellType {def next:CellType = Empty()}

case class Empty()   extends CellType {def next:CellType = Empty()}

case class DoubleCell() extends CellType {def next:CellType = FullCell()}

case class TripleCell() extends CellType {def next:CellType = DoubleCell()}
