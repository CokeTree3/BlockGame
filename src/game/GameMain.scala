package game

import engine.GameBase
import engine.graphics.{Color, Coordinate, Rectangle}
import processing.core.{PApplet, PImage}
import processing.event.{KeyEvent, MouseEvent}
import game.logic.{Point => GridCoordinate, _}
import java.awt.event.KeyEvent._
import java.io.{File, PrintWriter}
import scala.io.Source
import scala.util.Random

class GameMain extends GameBase {

  private var dispState = 0
  private var imgList = List[PImage]()
  private var settingsData = readSettings()
  private var colorTheme = Map[String, Color]()

  private var mouseActive = false
  private var (notifShow, notifText) = (8.26f, "")
  private val stdGameLogic = GameLogic()
  private var customGameLogic = GameLogic(getRandomLevel(Random.nextInt(11)))
  private def getGameLogic: GameLogic = if(dispState == 1) stdGameLogic else customGameLogic

  private val heightInPixels = 800
  private val widthInPixels = heightInPixels - heightInPixels / 3
  private val screenArea: Rectangle = Rectangle(Coordinate(0, 0), widthInPixels.toFloat, heightInPixels.toFloat)
  private val gameField: Rectangle = Rectangle(Coordinate((widthInPixels / 8).toFloat, (heightInPixels / 10).toFloat), (widthInPixels - (widthInPixels / 4)).toFloat)

  private val widthPerCell: Float = gameField.width / stdGameLogic.gridDims.width
  private val heightPerCell: Float = widthPerCell

  override def draw(): Unit = {
    if (dispState == 0) drawMainMenu()
    else if(dispState == 2) drawSettingsMenu()
    else if(dispState == 3) drawGameOverScreen()
    else drawGameScene()
  }

  private def drawSettingsMenu(): Unit = {
    setBackground(colorTheme("menuBackground"))
    setFillColor(colorTheme("settingsBackground"))

    drawRectangle(Rectangle(Coordinate(screenArea.left + 40, screenArea.top + 40), screenArea.width - 80, screenArea.height - 80), 70f)
    setFillColor(colorTheme("button"))

    drawBtns(getBtnMap(screenArea, dispState), colorTheme("button"), colorTheme("buttonText"))
    drawNotif(notifText)
  }

  private def drawMainMenu(): Unit = {
    setFillColor(colorTheme("button"))
    setBackground(colorTheme("menuBackground"))
    drawTextCentered("Game Menu", 50, Coordinate(gameField.centerX, gameField.heightThirds(1)))
    drawTextCentered("Best Score: " + settingsData.head.split("= ")(1),  25, Coordinate(gameField.centerX, gameField.centerY + 20))

    drawBtns(getBtnMap(screenArea, dispState), colorTheme("button"), colorTheme("buttonText"))
  }

  private def drawGameOverScreen(): Unit = {
    setFillColor(colorTheme("gameBlock").fade(50f))
    drawRectangle(Rectangle(Coordinate(screenArea.left + 30, screenArea.top + 30), screenArea.width - 60, screenArea.height - 60), 70f)

    setFillColor(Color.Red)
    drawTextCentered("GAME OVER!", 40, Coordinate(screenArea.centerX, gameField.heightThirds(1)))
    setFillColor(colorTheme("button"))
    drawTextCentered("Score: " + getGameLogic.getScore, 40, Coordinate(screenArea.centerX, gameField.heightThirds(1) + gameField.top))

    showImage(imgList(1), getBtnMap(screenArea, dispState)("_sadFace"))
    drawBtns(getBtnMap(screenArea, dispState), colorTheme("button"), colorTheme("buttonText"))
  }

  private def drawGameScene(): Unit = {
    setBackground(colorTheme("gameBackground"))
    drawGameField()

    if(mouseActive && !getGameLogic.isGameOver)drawMovableBlock(getMouseCoordinate)
    else drawMovableBlock()

    drawTextCentered("Score: " + getGameLogic.getScore,20, Coordinate(gameField.centerX, gameField.top - 30))
    showImage(imgList.head, getBtnMap(screenArea, dispState)("_pause"))

    if(getGameLogic.isGameOver) {
      if(getGameLogic.getScore.toInt > settingsData.head.split("= ")(1).toInt) updateSettings(getGameLogic.getScore, "Score")
      dispState = 3
    }
  }

  private def drawGameField(): Unit = {
    setFillColor(colorTheme("gameEmpty"))
    drawRectangle(gameField, 0f)
    getGameLogic.gridDims.allPointsInside.foreach(p => drawCell(getCell(p), getGameLogic.getCellType(p)))

    for (i <- 0 to getGameLogic.gridDims.width / 3) {
      drawLine(Coordinate(gameField.widthThirds(i) + gameField.left, gameField.top), Coordinate(gameField.widthThirds(i) + gameField.left, gameField.bottom))
    }
    for (i <- 0 to getGameLogic.gridDims.height / 3) {
      drawLine(Coordinate(gameField.left, gameField.heightThirds(i) + gameField.top), Coordinate(gameField.right, gameField.heightThirds(i) + gameField.top))
    }

    def getCell(p: GridCoordinate): Rectangle = {
      val leftUp = Coordinate(gameField.left + p.x * widthPerCell, gameField.top + p.y * heightPerCell)
      Rectangle(leftUp, widthPerCell, heightPerCell)
    }
  }

  private def drawNotif(text: String): Unit = {
    val d = Math.pow(Math.E, notifShow - Math.E).toFloat
    if(notifShow < 8.25f) {
      setFillColor(colorTheme("notification").fade(255 - d))
      val area = Rectangle(Coordinate(screenArea.centerX - 150, screenArea.bottom - 300), 300, 40)
      drawRectangle(area, 25)

      setFillColor(colorTheme("buttonText"))
      drawTextCentered(text, 20, Coordinate(area.centerX, area.centerY + 7))

      notifShow = notifShow + 0.1f
    }
  }

  private def drawCell(area: Rectangle, fill: CellType, rad: Float = 2f): Unit = {
    fill match {
      case Empty() => setFillColor(colorTheme("gameEmpty"))
      case _ => setFillColor(colorTheme("gameBlock"))
    }
    drawRectangle(area, rad)
    fill match {
      case DoubleCell() => drawTextCentered("2",area.height/2, Coordinate(area.centerX, area.centerY + 5))
      case TripleCell() => drawTextCentered("3",area.height/2, Coordinate(area.centerX, area.centerY + 5))
      case _ => ()
    }
  }

  private def getBlockArea(centerCoordinate: Coordinate = Coordinate(screenArea.centerX, screenArea.heightThirds(2) + 75), scale: Float = 1f): Seq[Rectangle] = {
    getGameLogic.getBlockCells.map(cell =>
      Rectangle(Coordinate((centerCoordinate.x - (widthPerCell * scale) / 2) + (widthPerCell * scale) * cell.x, (centerCoordinate.y - (heightPerCell * scale) / 2) + (heightPerCell * scale) * cell.y), widthPerCell * scale))
  }

  private def drawMovableBlock(centerCoordinate: Coordinate = Coordinate(screenArea.centerX, screenArea.heightThirds(2) + 75)): Unit = {
    getBlockArea(centerCoordinate, 1.09f).foreach(cell => drawCell(cell, FullCell(), 5f))
  }

  override def keyPressed(event: KeyEvent): Unit = {

    event.getKeyCode match {
      case VK_P => dispState = 0
      case VK_ESCAPE => System.exit(0)
      case _ => ()
    }
  }

  override def mouseDragged(event: MouseEvent): Unit = {
    if((dispState == 1 || dispState == 4) && getBlockArea().exists(cell => cell.contains(getMouseCoordinate))) mouseActive = true
  }

  override def mouseClicked(): Unit = {
    val mouseLoc = getMouseCoordinate
    val map = getBtnMap(screenArea, dispState)
    if(dispState == 0){
      if(map("Infinite Play").contains(mouseLoc)) dispState = 1
      else if (map("Custom Game").contains(mouseLoc)) dispState = 4
      else if (map("Settings").contains(mouseLoc)) dispState = 2
      else if (map("Quit").contains(mouseLoc)) System.exit(0)
    }
    else if(dispState == 1 || dispState == 4){
      if(map("_pause").contains(mouseLoc)) dispState = 0

    }
    else if(dispState == 2){
      if (map("Reset Score").contains(mouseLoc)) {
        notifText = "Score has been reset"
        updateSettings("0", "Score")
      }
      else if (map("Colour Theme").contains(mouseLoc)) {
        notifText = "Set theme "
        if (settingsData(1).split("= ")(1) == "default") updateSettings("dark", "Theme") else updateSettings("default", "Theme")
        colorTheme = getColorTheme(settingsData(1).split("= ")(1))
      }
      else if (map("Reload Level").contains(mouseLoc)) {
        notifText = "New level loaded"
        notifShow = 0f
        customGameLogic = GameLogic(getRandomLevel(Random.nextInt(3)))
      }
      else if (map("X").contains(mouseLoc)) dispState = 0
    }
    else if (dispState == 3) {
      if (map("Restart").contains(mouseLoc)) {
        dispState = 1
        getGameLogic.resetGame()
      }
      else if (map("Main Menu").contains(mouseLoc)) {
        dispState = 0
        getGameLogic.resetGame()
      }
    }
  }

  override def mouseReleased(): Unit = {
    val mousePoint = getMouseCoordinate
    if(mouseActive && gameField.contains(mousePoint)) getGameLogic.placeBlock(GridCoordinate(((mouseX - gameField.left) / widthPerCell).toInt, ((mouseY - gameField.top) / heightPerCell).toInt))
    mouseActive = false
  }

  private def updateSettings(newVal: String, cont: String): Unit = {
    val tempFile = new File("./res/tmp.ini")
    new File("./res/settings.ini").delete()

    val writer = new PrintWriter(tempFile)
    settingsData.foreach(line => if (line.contains(cont)) writer.println(line.split(" = ")(0) + " = " + newVal) else writer.println(line))
    writer.close()

    tempFile.renameTo(new File("./res/settings.ini"))
    settingsData = readSettings()
    if(cont == "Theme") notifText = notifText + newVal
    notifShow = 0f
  }

  private def getRandomLevel(count: Int): Seq[Seq[CellType]] = {
    val fields = Source.fromFile("./res/customFields.txt")
    val data = fields.getLines().slice(11 * count + 1, 11 * count + 10).toList
    data.map(row => row.map {
      case '0' => Empty()
      case '1' => FullCell()
      case '2' => DoubleCell()
      case '3' => TripleCell()
    })
  }

  private def readSettings(): List[String] = {
    val settingsFile = Source.fromFile("./res/settings.ini")
    val data = settingsFile.getLines().toList.map(_.trim)
    settingsFile.close()
    data
  }

  override def settings(): Unit = {
    pixelDensity(displayDensity())
    size(widthInPixels, heightInPixels)
  }

  override def setup(): Unit = {
    colorTheme = getColorTheme(settingsData(1).split("= ")(1))
    imgList = imgList.appended(loadImage("./res/pauseMenuImg.png"))
    imgList = imgList.appended(loadImage("./res/sadFace.png"))
    text("", 0, 0)
  }
}

object GameMain {

  def main(args:Array[String]): Unit = {
    PApplet.main("game.GameMain")
  }
}