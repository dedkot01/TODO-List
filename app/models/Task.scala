package models

import play.api.libs.json._

case class Task(id: Long, title: String)

object Task {
  implicit val taskFormat = Json.format[Task]
}
