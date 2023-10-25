package tetris.logic

import engine.random.{RandomGenerator, ScalaRandomGen}
import tetris.logic.GameLogic._


class GameLogic(val randomGen: RandomGenerator) {

  // private var gameState = GameState(this.initialBoard, generateBlock())
  val gridDims: Dimensions = Dimensions(width = DefaultWidth, height = DefaultHeight)
  private var gameState: GameState = GameState(generateBlock())

  def getCellType(p : Point): CellType = gameState.getCell(p)

  def isGameOver: Boolean = false

  def generateBlock(): Block = {
    val newTetIndex = randomGen.randomInt(9)
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

  def getBlockCells: Seq[Point] = gameState.b.l

  private def checkPlacement(block: List[Point]):Boolean = if(block.forall(p => gridDims.allPointsInside.contains(p) && getCellType(p) == Empty)) true else false

  def placeBlock(centerPoint: Point): Unit = {
    println(centerPoint)
    //currentBlock = currentBlock.moveAnchor(centerPoint)
    val blockMap = gameState.b.mapToAnchor(centerPoint)
    println(blockMap)

    if (checkPlacement(blockMap)) {
      blockMap.foreach(p => gameState = gameState.updateCell(p, FullCell))
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