package tetris.logic

case class GameState(gameBoard: Seq[Seq[CellType]], b: Block) {

  def updateBoard(newBoard: Seq[Seq[CellType]]): GameState = GameState(newBoard, this.b)

  def updateCell(c: Point, value: CellType): GameState = GameState(gameBoard.updated(c.y, gameBoard(c.y).updated(c.x, value)), this.b)

  def getCell(p:Point): CellType = gameBoard(p.y)(p.x)

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