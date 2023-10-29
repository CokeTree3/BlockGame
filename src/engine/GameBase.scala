package engine

import engine.graphics.{Color, Coordinate, Rectangle, ColorTheme}
import processing.core.{PApplet, PConstants, PImage}

class GameBase   extends PApplet {

  def getColorTheme(variation: String): Map[String, Color] = {
    variation match {
      case "default" => ColorTheme.lightTheme
      case "dark" => ColorTheme.darkTheme
    }
  }

  def getBtnMap(screenArea: Rectangle, state: Int): Map[String, Rectangle] = {
    val menuButtons = Map[String, Rectangle](
      "Infinite Play" -> Rectangle(Coordinate(screenArea.centerX - 125, screenArea.heightThirds(2) - 20), 250, 40),
      "Custom Game" -> Rectangle(Coordinate(screenArea.centerX - 125, screenArea.heightThirds(2) + 50), 250, 40),
      "Settings" -> Rectangle(Coordinate(screenArea.centerX - 125, screenArea.heightThirds(2) + 120), 120, 40),
      "Quit" -> Rectangle(Coordinate(screenArea.centerX + 5, screenArea.heightThirds(2) + 120), 115, 40))

    val settingsButtons = Map[String, Rectangle](
      "Reset Score" -> Rectangle(Coordinate(screenArea.left + 60, screenArea.top + 100), 200, 40),
      "Colour Theme" -> Rectangle(Coordinate(screenArea.left + 60, screenArea.top + 170), 200, 40),
      "X" -> Rectangle(Coordinate(screenArea.right - 95, screenArea.top + 60), 35))

    val gameButtons = Map[String, Rectangle](
      "_pause" -> Rectangle(Coordinate(screenArea.right - 55, screenArea.top + 10), 45))

    val gameOverButtons = Map[String, Rectangle](
      "Restart" -> Rectangle(Coordinate(screenArea.left + 60, screenArea.heightThirds(2) - 20), 200, 50),
      "Main Menu" -> Rectangle(Coordinate(screenArea.right - 260, screenArea.heightThirds(2) -20), 200, 50),
      "_sadFace" -> Rectangle(Coordinate(screenArea.centerX - 64, screenArea.centerY - 64), 128))

    state match {
      case 0 => menuButtons
      case 1 => gameButtons
      case 2 => settingsButtons
      case 3 => gameOverButtons
    }
  }

  def drawBtns(btnMap: Map[String, Rectangle], fillCol: Color, textCol: Color): Unit = {
    setFillColor(fillCol)
    val map = btnMap.filter(btn => !btn._1.startsWith("_"))

    map.foreach(btn => if (isMouseOver(btn._2)) drawRectangle(btn._2.grow(1.13f), 20) else drawRectangle(btn._2, 20))
    setFillColor(textCol)
    map.foreach(btn => drawTextCentered(btn._1, 20, Coordinate(btn._2.centerX, btn._2.centerY + 7), fillCol))
  }

  private def isMouseOver(area: Rectangle): Boolean = area.contains(getMouseCoordinate)

  def getMouseCoordinate: Coordinate = Coordinate(mouseX.toFloat, mouseY.toFloat)


  def drawTextCentered(string: String, size: Float, center: Coordinate, outlineColor: Color = Color.Black): Unit = {
    val (x, y) = (center.x, center.y-(size/2))
    textAlign(PConstants.CENTER, PConstants.CENTER)
    textSize(size)
    drawText(string, Coordinate(x, y), outlineColor)
  }

  private def drawText(string: String, pos: Coordinate, outlineColor: Color): Unit = {
    drawTextShadow(string, pos, outlineColor)
    text(string, pos.x, pos.y)
  }

  private def drawTextShadow(string: String, pos: Coordinate, color: Color, thickness: Float = 1): Unit = {
    pushStyle()
    setFillColor(color)
    List((1,0),(-1,0),(0,1),(0,-1)).foreach(t => {
      text(string, pos.x+(t._1*thickness), pos.y+t._2*thickness)
    })
    popStyle()
  }

  def drawLine(p1 : Coordinate, p2 : Coordinate) : Unit = {
    stroke(79, 92, 99)
    strokeWeight(2)
    line(p1.x,p1.y, p2.x,p2.y )
    strokeWeight(1)
  }

  def drawRectangle(r: Rectangle, rad: Float = 5f): Unit = {
    rect(r.left, r.top, r.width, r.height, rad)
  }

  def setFillColor(c: Color): Unit =
    fill(c.red, c.green, c.blue, c.alpha)

  def setBackground(c: Color): Unit =
    background(c.red, c.green, c.blue, c.alpha)

  def showImage(p: PImage, area:Rectangle): Unit =
    image(p, area.left, area.top, area.width, area.height)

}
