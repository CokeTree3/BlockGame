// DO NOT MODIFY FOR BASIC SUBMISSION
// scalastyle:off

package engine.graphics

/** Color in red green blue, where each color value is in the range 0-255 */
case class Color(red: Float, green: Float, blue: Float, alpha: Float) {

  def linearInterpolation(l: Float, r: Float, t: Float): Float = (1 - t) * l + t * r

  def interpolate(fraction: Float, rhs: Color): Color =
    Color(linearInterpolation(red,   rhs.red,   fraction),
          linearInterpolation(green, rhs.green, fraction),
          linearInterpolation(blue,  rhs.blue,  fraction),
          linearInterpolation(alpha, rhs.alpha, fraction)
    )

  def fade(value: Float): Color = new Color(this.red, this.green, this.blue, value)
}

/** Color companion object */
object Color {

  // This is called on Color(r, g, b) (without new)
  def apply(red: Float, green: Float, blue: Float): Color = new Color(red, green, blue, 255)



  val DarkGreen: Color = Color(  0, 100,   0)
  val Black: Color     = Color(  0,   0,   0)
  val Gray: Color      = Color(100, 100, 100)
  val Red: Color       = Color(255,   0,   0)
  val White: Color     = Color(255, 255, 255)
  val LightBlue: Color = Color(173, 216, 230)
  val Orange: Color    = Color(255, 165,   0)
  val Blue: Color      = Color(  0,   0, 255)
  val Purple: Color    = Color(128,   0, 128)
  val DarkBlue: Color  = Color(  9,  14,  41)
  val DarkGrey: Color  = Color(  50, 52,  61)
  val DarkCyan: Color  = Color(  94,117, 130)

}
