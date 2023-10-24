package tetris.logic

case class GameState(gameBoard: Seq[Seq[CellType]], t: Block) {

  def updateBoard(newBoard: Seq[Seq[CellType]]): GameState = GameState(newBoard, t)


  def getAnchor(): Point = t.anchorPoint

  def changeCell(cellPoint: Point, cell:CellType): Seq[Seq[CellType]] = {
    gameBoard.updated(cellPoint.y, gameBoard(cellPoint.y).updated(cellPoint.x, cell))
  }
}