package tetris.logic

case class GameState(gameBoard: Seq[Seq[CellType]], b: Block) {

  def updateBoard(newBoard: Seq[Seq[CellType]]): GameState = GameState(newBoard, this.b)

  def updateCell(c: Point, value: CellType): GameState = GameState(gameBoard.updated(c.y, gameBoard(c.y).updated(c.x, value)), this.b)

  def getCell(p:Point): CellType = gameBoard(p.y)(p.x)

  def getColumn(index: Int):Seq[CellType] = for(row <- gameBoard.indices) yield gameBoard(row)(index)

  def updateColumn(value:CellType, index: Int): Seq[Seq[CellType]] = {
    var newBoard = gameBoard
    for(row <- gameBoard.indices)newBoard = newBoard.updated(row, newBoard(row).updated(index, value))
    newBoard
  }

  def getAnchor: Point = b.anchorPoint

  def changeCell(cellPoint: Point, cell:CellType): Seq[Seq[CellType]] = {
    gameBoard.updated(cellPoint.y, gameBoard(cellPoint.y).updated(cellPoint.x, cell))
  }
}

object GameState {
  def apply(b: Block): GameState = {
    val line = Seq.fill(9)(Empty)
    new GameState(Seq.fill(9)(line), b)
  }
}