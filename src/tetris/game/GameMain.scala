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

  private var dispMainMenu = true
  private var dispSettingMenu = false
  private var mouseActive = false
  private var gameLogic = GameLogic()
  val gridDims: Dimensions = gameLogic.gridDims
  private val heightInPixels = 800
  private val widthInPixels = heightInPixels - heightInPixels / 3
  val screenArea: Rectangle = Rectangle(Coordinate(0, 0), widthInPixels.toFloat, heightInPixels.toFloat)
  val gameField: Rectangle = Rectangle(Coordinate((widthInPixels / 8).toFloat, (heightInPixels / 10).toFloat), (widthInPixels - (widthInPixels / 4)).toFloat)

  private val btnMap = Map[String, Rectangle](
    "Infinite Play" -> Rectangle(Coordinate(screenArea.centerX - 125, screenArea.heightThirds(2) - 20), 250, 40),
    "Custom Game" -> Rectangle(Coordinate(screenArea.centerX - 125, screenArea.heightThirds(2) + 50), 250, 40),
    "Settings" -> Rectangle(Coordinate(screenArea.centerX - 125, screenArea.heightThirds(2) + 120), 120, 40),
    "Quit" -> Rectangle(Coordinate(screenArea.centerX + 5, screenArea.heightThirds(2) + 120), 115, 40))

  private var widthPerCell: Float = gameField.width / gridDims.width
  private var heightPerCell: Float = widthPerCell

  override def draw(): Unit = {

    if (dispMainMenu) drawMainMenu()
    else drawGameField()
    if (gameLogic.isGameOver) drawGameOverScreen()
  }

  def drawMainMenu(): Unit = {
    setFillColor(Color.White)
    setBackground(Color.DarkBlue)
    drawTextCentered("Game Menu", 50, Coordinate(gameField.centerX, gameField.heightThirds(1)))

    btnMap.foreach(btn => if(isMouseOver(btn._2)) drawRectangle(btn._2.grow(1.13f), 20) else drawRectangle(btn._2, 20))
    setFillColor(Color.Black)
    btnMap.foreach(btn => drawTextCentered(btn._1, 20, Coordinate(btn._2.centerX, btn._2.centerY + 7), Color.White))
  }

  def drawGameOverScreen(): Unit = {
    setFillColor(Color.Red)
    drawTextCentered("GAME OVER!", 20, screenArea.center)
  }

  def drawGameField(): Unit = {
    setBackground(Color.DarkCyan)
    widthPerCell = gameField.width / gridDims.width
    heightPerCell = widthPerCell
    setFillColor(Color.White)
    drawRectangle(gameField, 0f)

    gridDims.allPointsInside.foreach(p => drawCell(getCell(p), gameLogic.getCellType(p)))

    for (i <- 0 to gridDims.width / 3) {
      drawLine(Coordinate((gameField.widthThirds(i)) + gameField.left, gameField.top), Coordinate(gameField.widthThirds(i) + gameField.left, gameField.bottom))
    }
    for (i <- 0 to gridDims.height / 3) {
      drawLine(Coordinate(gameField.left, gameField.heightThirds(i) + gameField.top), Coordinate(gameField.right, gameField.heightThirds(i) + gameField.top))
    }

    if(mouseActive && !gameLogic.isGameOver)drawMovableBlock(getMouseCoordinate)
    else drawMovableBlock()

    def getCell(p: GridCoordinate): Rectangle = {
      val leftUp = Coordinate(gameField.left + p.x * widthPerCell, gameField.top + p.y * heightPerCell)
      Rectangle(leftUp, widthPerCell, heightPerCell)
    }

    drawTextCentered("Score: " + gameLogic.getScore,20, Coordinate(gameField.centerX, gameField.top - 30))
  }

  private def drawCell(area: Rectangle, fill: CellType, rad: Float = 2f): Unit = {
    if (fill != Empty) setFillColor(Color.LightBlue) else setFillColor(Color.White)
    drawRectangle(area, rad)
  }

  private def getBlockArea(centerCoordinate: Coordinate = Coordinate(screenArea.centerX, screenArea.heightThirds(2) + 75)): Seq[Rectangle] = {
    gameLogic.getBlockCells.map(cell => Rectangle(Coordinate((centerCoordinate.x - widthPerCell / 2) + widthPerCell * cell.x, (centerCoordinate.y - heightPerCell / 2) + heightPerCell * cell.y), widthPerCell, heightPerCell))
  }

  private def drawMovableBlock(centerCoordinate: Coordinate = Coordinate(screenArea.centerX, screenArea.heightThirds(2) + 75)): Unit = {
    val s = getBlockArea(centerCoordinate)
    if(s.exists(isMouseOver)){
      widthPerCell = gameField.width * 1.09f / gridDims.width
      heightPerCell = widthPerCell
    }
    getBlockArea(centerCoordinate).foreach(cell => drawCell(cell, FullCell, 5f))
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

  private def getMouseCoordinate: Coordinate = Coordinate(mouseX.toFloat, mouseY.toFloat)

  override def mouseDragged(event: MouseEvent): Unit = {
    if(!dispMainMenu && getBlockArea().exists(cell => cell.contains(getMouseCoordinate))) mouseActive = true
  }

  override def mouseClicked(): Unit = {
    if(dispMainMenu){
      val mouseLoc = getMouseCoordinate
      if(btnMap("Infinite Play").contains(mouseLoc)) dispMainMenu = false
      else if (btnMap("Custom Game").contains(mouseLoc)) dispMainMenu = false
      else if (btnMap("Settings").contains(mouseLoc)) dispSettingMenu = true
      else if (btnMap("Quit").contains(mouseLoc)) System.exit(0)
    }
  }

  private def isMouseOver(area: Rectangle): Boolean = area.contains(getMouseCoordinate)

  override def mouseReleased(): Unit = {
    val mousePoint = getMouseCoordinate
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

  def main(args:Array[String]): Unit = {
    PApplet.main("tetris.game.GameMain")
  }

}