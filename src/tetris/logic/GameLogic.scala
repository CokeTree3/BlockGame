package tetris.logic

import engine.random.{RandomGenerator, ScalaRandomGen}
import tetris.logic.GameLogic._


class GameLogic(val randomGen: RandomGenerator) {
  val gridDims: Dimensions = Dimensions(width = DefaultWidth, height = DefaultHeight)
  private var gameState: GameState = GameState(generateBlock())

  def getCellType(p : Point): CellType = gameState.getCell(p)

  def isGameOver: Boolean = false

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

  private def checkFull():Unit = {
    var newBoard = gameState.gameBoard.map(row => if(!row.contains(Empty)) Seq.fill(gridDims.width)(Empty) else row)

    for(i <- 0 until gridDims.width) {
      if (!gameState.getColumn(i).contains(Empty)) newBoard = gameState.updateColumn(Empty, i)
    }

    gameState = gameState.updateBoard(newBoard)
  }

  def placeBlock(centerPoint: Point): Unit = {
    println(centerPoint)
    val blockMap = gameState.b.mapToAnchor(centerPoint)
    println(blockMap)

    if (checkPlacement(blockMap)) {
      blockMap.foreach(p => gameState = gameState.updateCell(p, FullCell))
      checkFull()
      gameState = GameState(gameState.gameBoard, generateBlock())
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