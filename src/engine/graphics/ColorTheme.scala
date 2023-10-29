package engine.graphics

object ColorTheme {
  val lightTheme: Map[String, Color] = Map[String, Color](
    "menuBackground" -> Color(204, 232, 240),
    "gameBackground" -> Color.White,
    "settingsBackground" -> Color( 123, 182, 199),
    "button" -> Color.White,
    "buttonText" -> Color.Black,
    "gameEmpty" -> Color.White,
    "gameBlock" -> Color(61, 177, 209),
    "notification" -> Color( 153, 212, 229)
  )

  val darkTheme: Map[String, Color] = Map[String, Color](
    "menuBackground" -> Color(9, 14, 41),
    "gameBackground" -> Color(53, 51, 92),
    "settingsBackground" -> Color(59, 55, 77),
    "button" -> Color.White,
    "buttonText" -> Color.Black,
    "gameEmpty" -> Color(32, 49, 79),
    "gameBlock" -> Color(134, 179, 194),
    "notification" -> Color(99, 95, 117)
  )
}
