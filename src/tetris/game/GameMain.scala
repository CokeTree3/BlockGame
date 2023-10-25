// DO NOT MODIFY FOR BASIC SUBMISSION


package tetris.game

import java.awt.event
import java.awt.event.KeyEvent._
import engine.GameBase
import engine.graphics.{Color, Coordinate, Rectangle}
import processing.core.{PApplet, PConstants}
import processing._
import processing.event.{KeyEvent, MouseEvent}
import tetris.logic._
import tetris.game.GameMain._
import tetris.logic.{Point => GridCoordinate}

class GameMain extends GameBase {


  var dispMainMenu: Boolean = true
  var mouseActive: Boolean = false
  var gameLogic: GameLogic = GameLogic()
  val gridDims: Dimensions = gameLogic.gridDims
  val heightInPixels: Int = 800 //(HeightCellInPixels * gridDims.height).ceil.toInt
  val widthInPixels: Int = heightInPixels - heightInPixels / 3 //(WidthCellInPixels * gridDims.width).ceil.toInt
  val screenArea: Rectangle = Rectangle(Coordinate(0, 0), widthInPixels.toFloat, heightInPixels.toFloat)
  val gameField: Rectangle = Rectangle(Coordinate((widthInPixels / 8).toFloat, (heightInPixels / 10).toFloat), (widthInPixels - (widthInPixels / 4)).toFloat)

  val widthPerCell: Float = gameField.width / gridDims.width
  val heightPerCell: Float = gameField.height / gridDims.height

  override def draw(): Unit = {
    if (dispMainMenu) drawMainMenu()
    else drawGameField()
    if (gameLogic.isGameOver) drawGameOverScreen()
  }

  def drawMainMenu(): Unit = {
    setFillColor(Color.White)
    setBackground(Color.DarkBlue)
    drawTextCentered("Game Menu", 50, screenArea.center)
    drawTextCentered("^", 20, gameField.leftUp)
    drawTextCentered("<", 20, gameField.leftDown)
    drawTextCentered("^", 20, gameField.rightUp)
    drawTextCentered(">", 20, gameField.rightDown)
  }

  def drawGameOverScreen(): Unit = {
    setFillColor(Color.Red)
    drawTextCentered("GAME OVER!", 20, screenArea.center)
  }

  def drawGameField(): Unit = {
    setBackground(Color.DarkCyan)

    gridDims.allPointsInside.foreach(p => drawCell(getCell(p), gameLogic.getCellType(p)))

    for (i <- 0 to gridDims.width / 3) {
      drawLine(Coordinate((gameField.widthThirds(i)) + gameField.left, gameField.top), Coordinate(gameField.widthThirds(i) + gameField.left, gameField.bottom))
    }
    for (i <- 0 to gridDims.height / 3) {
      drawLine(Coordinate(gameField.left, gameField.heightThirds(i) + gameField.top), Coordinate(gameField.right, gameField.heightThirds(i) + gameField.top))
    }

    if(mouseActive)drawMovableBlock(Coordinate(mouseX.toFloat, mouseY.toFloat))
    else drawMovableBlock()

    def getCell(p: GridCoordinate): Rectangle = {
      val leftUp = Coordinate(gameField.left + p.x * widthPerCell, gameField.top + p.y * heightPerCell)
      Rectangle(leftUp, widthPerCell, heightPerCell)
    }
  }

  private def drawCell(area: Rectangle, fill: CellType): Unit = {
    if (fill != Empty) setFillColor(Color.LightBlue) else setFillColor(Color.White)
    drawRectangle(area)
  }

  private def getBlockArea(centerCoordinate: Coordinate = Coordinate(screenArea.centerX, screenArea.bottom - 100)): Seq[Rectangle] = {
    gameLogic.getBlockCells.map(cell => Rectangle(Coordinate((centerCoordinate.x - widthPerCell / 2) + widthPerCell * cell.x, (centerCoordinate.y - heightPerCell / 2) + heightPerCell * cell.y), widthPerCell, heightPerCell))
  }

  private def drawMovableBlock(centerCoordinate: Coordinate = Coordinate(screenArea.centerX, screenArea.bottom - 100)): Unit = {
    val s = getBlockArea(centerCoordinate)
    s.foreach(cell => drawCell(cell, FullCell))
  }

  /** Method that calls handlers for different key press events.
   * You may add extra functionality for other keys here.
   * See [[event.KeyEvent]] for all defined keycodes.
   *
   * @param event The key press event to handle
   */
  override def keyPressed(event: KeyEvent): Unit = {

    event.getKeyCode match {
      case VK_ENTER => dispMainMenu = false
      case VK_P => dispMainMenu = true
      case _ => ()
    }
  }

  override def mouseDragged(event: MouseEvent): Unit = {
    if(getBlockArea().exists(cell => cell.contains(Coordinate(mouseX.toFloat, mouseY.toFloat)))) mouseActive = true
  }

  override def mouseReleased(): Unit = {
    val mousePoint = Coordinate(mouseX.toFloat, mouseY.toFloat)
    if(mouseActive && gameField.contains(mousePoint)) gameLogic.placeBlock(Point(((mouseX - gameField.left) / (gameField.width/gridDims.width)).toInt, ((mouseY - gameField.top) / (gameField.height / gridDims.height)).toInt))
    mouseActive = false
  }

  override def settings(): Unit = {
    pixelDensity(displayDensity())
    size(widthInPixels, heightInPixels)
  }

  override def setup(): Unit = {
    // Fonts are loaded lazily, so when we call text()
    // for the first time, there is significant lag.
    // This prevents it from happening during gameplay.
    text("", 0, 0)
  }
}

object GameMain {


  val WidthCellInPixels: Double = 15 * GameLogic.DrawSizeFactor
  val HeightCellInPixels: Double = WidthCellInPixels

  def main(args:Array[String]): Unit = {
    PApplet.main("tetris.game.GameMain")
  }

}