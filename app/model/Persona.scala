package model

import play.api.libs.json._

case class Persona(id: Long, name: String, age: Int)

object Persona {
  implicit val personFormat = Json.format[Persona]
}
