// DO NOT MODIFY FOR BASIC SUBMISSION
// scalastyle:off

package engine

import engine.graphics.Color.Black
import engine.graphics.{Color, Coordinate, Rectangle}
import processing.core.{PApplet, PConstants}

class GameBase   extends PApplet {

  // ===Processing Wrappers & Abstractions===


  def drawTextCentered(string: String, size: Float, center: Coordinate): Unit = {
    val (x, y) = (center.x, center.y-(size/2))
    textAlign(PConstants.CENTER, PConstants.CENTER)
    textSize(size)
    drawText(string, Coordinate(x, y))
  }

  def drawText(string: String, pos: Coordinate, withShadow: Boolean = true): Unit = {
    if (withShadow) drawTextShadow(string, pos)
    text(string, pos.x, pos.y)
  }

  /** Quick hack for legibility on different backgrounds */
  def drawTextShadow(string: String, pos: Coordinate, color: Color = Black, thickness: Float = 1): Unit = {
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

  def drawRectangle(r: Rectangle): Unit = {
    rect(r.left, r.top, r.width, r.height)
  }

  def setFillColor(c: Color): Unit =
    fill(c.red, c.green, c.blue, c.alpha)

  def setBackground(c: Color): Unit =
    background(c.red, c.green, c.blue, c.alpha)

}
