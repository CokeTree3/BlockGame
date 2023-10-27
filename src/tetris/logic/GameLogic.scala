package tetris.logic

import engine.random.{RandomGenerator, ScalaRandomGen}
import tetris.logic.GameLogic._


class GameLogic(val randomGen: RandomGenerator) {
  val gridDims: Dimensions = Dimensions(width = DefaultWidth, height = DefaultHeight)
  private var gameState: GameState = GameState(generateBlock())

  var isGameOver: Boolean = false

  def getCellType(p : Point): CellType = gameState.getCell(p)

  private def generateBlock(): Block = {
    def getBlock: Block = {
      val newTetIndex = randomGen.randomInt(8) + 1
      newTetIndex match {
        case 1 => JBlock()
        case 2 => LBlock()
        case 3 => SBlock()
        case 4 => ZBlock()
        case 5 => TBlock()
        case 6 => UBlock()
        case 7 => I_Block()
        case 8 => IBlock()
        case 9 => OBlock()
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

  private def checkPlacement(block: List[Point]):Boolean = if(block.forall(p => gridDims.allPointsInside.contains(p) && getCellType(p) == Empty)) true else false

  private def checkGameOver(): Boolean = {
    !gridDims.allPointsInside.exists(point => gameState.getCell(point) == Empty && checkPlacement(gameState.b.mapToAnchor(point)))
  }

  private def checkFull():Unit = {
    var tempState = GameState(gameState.gameBoard, gameState.b)
    tempState = tempState.updateBoard(gameState.gameBoard.map(row => if(!row.contains(Empty)) Seq.fill(gridDims.width)(Empty) else row))

    for(i <- 0 until gridDims.width) {
      if (!gameState.getColumn(i).contains(Empty)) tempState = tempState.updateColumn(Empty, i)
    }

    var squareArea = Game3x3Square()
    for (i <- 0 until 9) {
      val square = squareArea.mapToAnchor()
      if (square.forall(p => gameState.getCell(p) != Empty)) {
        square.foreach(p => tempState = tempState.updateCell(p, Empty))
      }
      squareArea = squareArea.moveNext
    }
    gameState = tempState
  }

  def placeBlock(centerPoint: Point): Unit = {
    val blockMap = gameState.b.mapToAnchor(centerPoint)

    if (checkPlacement(blockMap)) {
      blockMap.foreach(p => gameState = gameState.updateCell(p, FullCell))
      checkFull()
      gameState = GameState(gameState.gameBoard, generateBlock())
      isGameOver = checkGameOver()
    }
  }
}


object GameLogic {

  val DrawSizeFactor = 2.0

  private def makeEmptyBoard(gridDims : Dimensions): Seq[Seq[CellType]] = {
    val emptyLine = Seq.fill(gridDims.width)(Empty)
    Seq.fill(gridDims.height)(emptyLine)
  }

  private val DefaultWidth: Int = 9
  private val DefaultHeight: Int = 9

  def apply():GameLogic = new GameLogic(new ScalaRandomGen() )

}