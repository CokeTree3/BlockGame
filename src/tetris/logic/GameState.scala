package tetris.logic

case class GameState(gameBoard: Seq[Seq[CellType]], b: Block, score:Int) {

  def updateBoard(newBoard: Seq[Seq[CellType]]): GameState = GameState(newBoard, this.b, this.score)

  def updateCell(c: Point, value: CellType): GameState = GameState(gameBoard.updated(c.y, gameBoard(c.y).updated(c.x, value)), this.b, this.score)

  def getCell(p:Point): CellType = gameBoard(p.y)(p.x)

  def getColumn(index: Int):Seq[CellType] = for(row <- gameBoard.indices) yield gameBoard(row)(index)

  def updateColumn(index: Int): GameState = {
    var newBoard = gameBoard
    for(row <- gameBoard.indices)newBoard = newBoard.updated(row, newBoard(row).updated(index, newBoard(row)(index).next))
    GameState(newBoard, this.b, this.score)
  }

  def updateBlock(newBlock: Block): GameState = GameState(this.gameBoard, newBlock, this.score)

  def updateScore(change: Int): GameState = GameState(this.gameBoard, this.b, this.score + change)

  def changeCell(cellPoint: Point, cell:CellType): Seq[Seq[CellType]] = {
    gameBoard.updated(cellPoint.y, gameBoard(cellPoint.y).updated(cellPoint.x, cell))
  }
}

object GameState {
  def apply(b: Block): GameState = {
    val line = Seq.fill(9)(Empty())
    new GameState(Seq.fill(9)(line), b, 0)
  }
}