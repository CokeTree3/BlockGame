/*package tetris.logic

import engine.random.{RandomGenerator, ScalaRandomGen}
import tetris.logic.TetrisLogic._

/** To implement Tetris, complete the ``TODOs`` below.
 *
 * If you need additional files,
 * please also put them in the ``tetris`` package.
 */
class TetrisLogic(val randomGen: RandomGenerator,
                  val gridDims : Dimensions,
                  val initialBoard: Seq[Seq[CellType]]) {

  def this(random: RandomGenerator, gridDims : Dimensions) = this(random, gridDims, makeEmptyBoard(gridDims))

  def this() = this(new ScalaRandomGen(), DefaultDims, makeEmptyBoard(DefaultDims))

  private val spawnAnchorLocation: Point = Point((gridDims.width / 2.0).ceil.toInt -1, 1)

  private var gameState = GameState(this.initialBoard, generateTetromino())

  gameState = spawnTetromino(spawnAnchorLocation)

  def getCellType(p : Point): CellType = gameState.gameBoard(p.y)(p.x)

  def isGameOver: Boolean = gameState.gameBoard.head.length != gridDims.width

  private def generateTetromino(): Tetromino = {
    val newTetIndex = randomGen.randomInt(7)
    newTetIndex match {
      case 0 => IBlock()
      case 1 => JBlock()
      case 2 => LBlock()
      case 3 => OBlock()
      case 4 => SBlock()
      case 5 => TBlock()
      case 6 => ZBlock()
    }
  }

  private def spawnTetromino(anchorLoc: Point): GameState = {
    gameState.t.l.foreach(block => gameState = gameState.moveBlock(block, anchorLoc, gameState.t.cell))
    gameState.updateTetromino(gameState.createMovedTetromino(anchorLoc))
  }

  private def moveTetromino(moveDir: Point): GameState = {
    val tetLocation = gameState.mapTetrominoToBoard()

    if (checkMove(tetLocation, moveDir)) {
      removeOldTetromino(tetLocation)
      tetLocation.foreach(point => gameState = gameState.moveBlock(point, moveDir, gameState.t.cell))
      val newPosTet = gameState.createMovedTetromino(gameState.getAnchor().moveTo(moveDir))
      gameState.updateTetromino(newPosTet)
    } else {
      gameState
    }
  }

  private def removeOldTetromino(tList: List[Point]): Unit = {
    tList.foreach(gamePoint => gameState = gameState.updateBoard(gameState.changeCell(gamePoint, Empty)))
  }

  private def checkMove(tList: List[Point], dir: Point = Point(0, 0)): Boolean = {
    def isPartOfTetromino(point:Point): Boolean = gameState.mapTetrominoToBoard().contains(point)

    val simMove = tList.map(_.moveTo(dir))
    simMove.foreach(c => if (c.isOutOfBounds(gridDims.width, gridDims.height) || (getCellType(c) != Empty && !isPartOfTetromino(c))) return false)
    true
  }

  private def removeFullLines(): Unit = {
    gameState = gameState.updateBoard(gameState.gameBoard.filter(_.contains(Empty)))
    for(i <- 0 until (gridDims.height - gameState.gameBoard.length)){
      gameState = gameState.addLine(Seq.fill(gridDims.width)(Empty))
    }
  }

  def rotateLeft(): Unit = {
    if(!isGameOver){
      removeOldTetromino(gameState.mapTetrominoToBoard())
      val tempBoard = gameState.rotate("Left")
      if (checkMove(tempBoard.mapTetrominoToBoard(tempBoard.t))) gameState = tempBoard
      gameState = moveTetromino(Point(0, 0))
    }
  }

  def rotateRight(): Unit = {
    if(!isGameOver){
      removeOldTetromino(gameState.mapTetrominoToBoard())
      val tempBoard = gameState.rotate("Right")
      if (checkMove(tempBoard.mapTetrominoToBoard(tempBoard.t))) gameState = tempBoard
      gameState = moveTetromino(Point(0, 0))
    }
  }

  def moveLeft(): Unit = if(!isGameOver) gameState = moveTetromino(Point(-1, 0))

  def moveRight(): Unit = if(!isGameOver) gameState = moveTetromino(Point(1, 0))

  def moveDown(): Unit = {
    if(!isGameOver) {
      val prevAnchor = gameState.getAnchor()
      gameState = moveTetromino(Point(0, 1))
      if (gameState.getAnchor() == prevAnchor) {
        removeFullLines()
        gameState = gameState.updateTetromino(generateTetromino())
        gameState = spawnTetromino(spawnAnchorLocation)
      }
    }
  }

  def doHardDrop(): Unit = {
    if(!isGameOver) {
      moveDown()
      if(gameState.getAnchor() != spawnAnchorLocation) doHardDrop()
    }
  }
}


object TetrisLogic {

  val FramesPerSecond: Int = 5 // change this to speed up or slow down the game

  val DrawSizeFactor = 2.0 // increase this to make the game bigger (for high-res screens)
  // or decrease to make game smaller

  private def makeEmptyBoard(gridDims : Dimensions): Seq[Seq[CellType]] = {
    val emptyLine = Seq.fill(gridDims.width)(Empty)
    Seq.fill(gridDims.height)(emptyLine)
  }


  // These are the dimensions used when playing the game.
  // When testing the game, other dimensions are passed to
  // the constructor of GameLogic.
  //
  // DO NOT USE the variable DefaultGridDims in your code!
  //
  // Doing so will cause tests which have different dimensions to FAIL!
  //
  // In your code only use gridDims.width and gridDims.height
  // do NOT use DefaultDims.width and DefaultDims.height


  private val DefaultWidth: Int = 9
  private val NrTopInvisibleLines: Int = 4
  private val DefaultVisibleHeight: Int = 20
  private val DefaultHeight: Int = DefaultVisibleHeight + NrTopInvisibleLines
  private val DefaultDims : Dimensions = Dimensions(width = DefaultWidth, height = DefaultHeight)


  def apply() = new TetrisLogic(new ScalaRandomGen(),
    DefaultDims,
    makeEmptyBoard(DefaultDims))

}
*/