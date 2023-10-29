package tetris.logic

import engine.random.{RandomGenerator, ScalaRandomGen}
import tetris.logic.GameLogic._


class GameLogic(val randomGen: RandomGenerator, val customField: Seq[Seq[CellType]]) {
  val gridDims: Dimensions = Dimensions(width = DefaultWidth, height = DefaultHeight)
  private var gameState: GameState = GameState(customField, generateBlock())
  var isGameOver: Boolean = false

  def getCellType(p : Point): CellType = gameState.getCell(p)

  private def generateBlock(): Block = {
    def getBlock: Block = {
      val newTetIndex = randomGen.randomInt(12) + 1
      newTetIndex match {
        case 1 => JBlock()
        case 2 => LBlock()
        case 3 => SBlock()
        case 4 => ZBlock()
        case 5 => TBlock()
        case 6 => UBlock()
        case 7 => OBlock()
        case 8 => I_Block()
        case 9 => DotBlock()
        case 10 => I2Block()
        case 11 => I3Block()
        case 12 => I4Block()

      }
    }
    randomGen.randomInt(100) % 4 match {
      case 0 => getBlock
      case 1 => getBlock.rotateLeft()
      case 2 => getBlock.rotateLeft().rotateLeft()
      case 3 => getBlock.rotateRight()
    }
  }

  def getBlockCells: Seq[Point] = gameState.b.l

  def getScore: String = gameState.score.toString
  def resetGame(): Unit = {
    gameState = GameState(customField, generateBlock())
    isGameOver = false
  }

  private def checkPlacement(block: List[Point]):Boolean = if(block.forall(p => gridDims.allPointsInside.contains(p) && getCellType(p) == Empty())) true else false

  private def checkGameOver(): Boolean = {
    !gridDims.allPointsInside.exists(point => gameState.getCell(point) == Empty() && checkPlacement(gameState.b.mapToAnchor(point)))
  }

  private def checkFull():Unit = {
    var tempState = GameState(gameState.gameBoard, gameState.b, gameState.score)
    tempState = tempState.updateBoard(gameState.gameBoard.map(row => if(!row.contains(Empty())) {
        gameState = gameState.updateScore(gridDims.width)
        row.map(_.next)
    } else row))

    for(i <- 0 until gridDims.width) {
      if (!gameState.getColumn(i).contains(Empty())) {
        tempState = tempState.updateColumn(i)
        gameState = gameState.updateScore(gridDims.width)
      }
    }

    var squareArea = Game3x3Square()
    for (i <- 0 until 9) {
      val square = squareArea.mapToAnchor()
      if (square.forall(p => gameState.getCell(p) != Empty())) {
        square.foreach(p => tempState = tempState.updateCell(p, tempState.getCell(p).next))
        gameState = gameState.updateScore(gridDims.width)
      }
      squareArea = squareArea.moveNext
    }
    gameState = gameState.updateBoard(tempState.gameBoard)
  }

  def placeBlock(centerPoint: Point): Unit = {
    val blockMap = gameState.b.mapToAnchor(centerPoint)

    if (checkPlacement(blockMap)) {
      blockMap.foreach(p => gameState = gameState.updateCell(p, FullCell()))
      gameState = gameState.updateScore(blockMap.length)
      checkFull()
      gameState = gameState.updateBlock(generateBlock())
      isGameOver = checkGameOver()
    }
  }
}


object GameLogic {

  val DrawSizeFactor = 2.0

  private def makeEmptyBoard(): Seq[Seq[CellType]] = {
    val emptyLine = Seq.fill(DefaultWidth)(Empty())
    Seq.fill(DefaultHeight)(emptyLine)
  }

  private val DefaultWidth: Int = 9
  private val DefaultHeight: Int = 9

  def apply():GameLogic = new GameLogic(new ScalaRandomGen(), makeEmptyBoard())

  def apply(customBoard: Seq[Seq[CellType]]): GameLogic = new GameLogic(new ScalaRandomGen(), customBoard)

}