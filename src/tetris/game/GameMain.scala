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

  private var dispState = 0
  private var mouseActive = false
  private var gameLogic = GameLogic()
  val gridDims: Dimensions = gameLogic.gridDims
  private val heightInPixels = 800
  private val widthInPixels = heightInPixels - heightInPixels / 3
  val screenArea: Rectangle = Rectangle(Coordinate(0, 0), widthInPixels.toFloat, heightInPixels.toFloat)
  private val gameField: Rectangle = Rectangle(Coordinate((widthInPixels / 8).toFloat, (heightInPixels / 10).toFloat), (widthInPixels - (widthInPixels / 4)).toFloat)

  private var widthPerCell: Float = gameField.width / gridDims.width
  private var heightPerCell: Float = widthPerCell

  override def draw(): Unit = {
    if (dispState == 0) drawMainMenu()
    else if(dispState == 2) drawSettingsMenu()
    else if(dispState == 3) drawGameOverScreen()
    else drawGameField()
  }

  def drawSettingsMenu(): Unit = {
    setBackground(Color.DarkBlue)
    setFillColor(Color.DarkGrey)

    drawRectangle(Rectangle(Coordinate(screenArea.left + 40, screenArea.top + 40), screenArea.width - 80, screenArea.height - 80), 70f)
    setFillColor(Color.White)

    drawBtns(getBtnMap(screenArea, dispState))
  }

  def drawMainMenu(): Unit = {
    setFillColor(Color.White)
    setBackground(Color.DarkBlue)
    drawTextCentered("Game Menu", 50, Coordinate(gameField.centerX, gameField.heightThirds(1)))

    drawBtns(getBtnMap(screenArea, dispState))
  }

  def drawGameOverScreen(): Unit = {
    setFillColor(Color.LightBlue.fade(50f), 100f)
    drawRectangle(Rectangle(Coordinate(screenArea.left + 30, screenArea.top + 30), screenArea.width - 60, screenArea.height - 60), 70f)

    setFillColor(Color.Red)
    drawTextCentered("GAME OVER!", 40, Coordinate(screenArea.centerX, gameField.heightThirds(1)))
    setFillColor(Color.White)
    drawTextCentered("Score: " + gameLogic.getScore, 40, Coordinate(screenArea.centerX, gameField.heightThirds(1) + gameField.top))

    drawBtns(getBtnMap(screenArea, dispState))
  }

  def drawGameField(): Unit = {
    setBackground(Color.DarkCyan)
    widthPerCell = gameField.width / gridDims.width
    heightPerCell = widthPerCell
    setFillColor(Color.White)
    drawRectangle(gameField, 0f)

    gridDims.allPointsInside.foreach(p => drawCell(getCell(p), gameLogic.getCellType(p)))

    for (i <- 0 to gridDims.width / 3) {
      drawLine(Coordinate(gameField.widthThirds(i) + gameField.left, gameField.top), Coordinate(gameField.widthThirds(i) + gameField.left, gameField.bottom))
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

  private def drawBtns(btnMap: Map[String, Rectangle]): Unit = {
    btnMap.foreach(btn => if (isMouseOver(btn._2)) drawRectangle(btn._2.grow(1.13f), 20) else drawRectangle(btn._2, 20))
    setFillColor(Color.Black)
    btnMap.foreach(btn => drawTextCentered(btn._1, 20, Coordinate(btn._2.centerX, btn._2.centerY + 7), Color.White))
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
    if(gameLogic.isGameOver) dispState = 3
  }

  /** Method that calls handlers for different key press events.
   * You may add extra functionality for other keys here.
   * See [[event.KeyEvent]] for all defined keycodes.
   *
   * @param event The key press event to handle
   */
  override def keyPressed(event: KeyEvent): Unit = {

    event.getKeyCode match {
      case VK_ENTER => dispState = 1
      case VK_P => dispState = 0
      case VK_C => dispState = 3
      case _ => ()
    }
  }

  private def getMouseCoordinate: Coordinate = Coordinate(mouseX.toFloat, mouseY.toFloat)

  override def mouseDragged(event: MouseEvent): Unit = {
    if(dispState == 1 && getBlockArea().exists(cell => cell.contains(getMouseCoordinate))) mouseActive = true
  }

  override def mouseClicked(): Unit = {
    val mouseLoc = getMouseCoordinate
    val map = getBtnMap(screenArea, dispState)
    if(dispState == 0){
      if(map("Infinite Play").contains(mouseLoc)) dispState = 1
      else if (map("Custom Game").contains(mouseLoc)) dispState = 1
      else if (map("Settings").contains(mouseLoc)) dispState = 2
      else if (map("Quit").contains(mouseLoc)) System.exit(0)
    }
    else if(dispState == 2){
      if (map("Reset Score").contains(mouseLoc)) gameLogic.resetGame()
      else if (map("Colour Theme").contains(mouseLoc)) ???                          // TODO change theme
      else if (map("X").contains(mouseLoc)) dispState = 0
    }
    else if (dispState == 3) {
      if (map("Restart").contains(mouseLoc)) {
        dispState = 1
        gameLogic.resetGame()
      }
      else if (map("Main Menu").contains(mouseLoc)) {
        dispState = 0
        gameLogic.resetGame()
      }
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