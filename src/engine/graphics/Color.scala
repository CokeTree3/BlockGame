package engine.graphics

/** Color in red green blue, where each color value is in the range 0-255 */
case class Color(red: Float, green: Float, blue: Float, alpha: Float) {

  def fade(value: Float): Color = new Color(this.red, this.green, this.blue, value)
}

object Color {

  def apply(red: Float, green: Float, blue: Float): Color = new Color(red, green, blue, 255)

  val Black: Color     = Color(  0,   0,   0)
  val Red: Color       = Color(255,   0,   0)
  val White: Color     = Color(240, 242, 241)
}
