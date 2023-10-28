// DO NOT MODIFY FOR BASIC SUBMISSION
// scalastyle:off

package engine

import engine.graphics.Color.Black
import engine.graphics.{Color, Coordinate, Rectangle}
import processing.core.{PApplet, PConstants}

class GameBase   extends PApplet {

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
      "Pause" -> Rectangle(Coordinate(screenArea.right - 55, screenArea.top + 20), 35))

    val gameOverButtons = Map[String, Rectangle](
      "Restart" -> Rectangle(Coordinate(screenArea.left + 60, screenArea.heightThirds(2) - 20), 200, 50),
      "Main Menu" -> Rectangle(Coordinate(screenArea.right - 260, screenArea.heightThirds(2) -20), 200, 50))

    state match {
      case 0 => menuButtons
      case 1 => gameButtons
      case 2 => settingsButtons
      case 3 => gameOverButtons
    }
  }


  def drawTextCentered(string: String, size: Float, center: Coordinate, outlineColor: Color = Black): Unit = {
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

  def setFillColor(c: Color, alpha: Float = 255f): Unit =
    fill(c.red, c.green, c.blue, alpha)

  def setBackground(c: Color): Unit =
    background(c.red, c.green, c.blue, c.alpha)

}
