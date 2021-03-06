package lib.model

import ixias.model._
import ixias.util.EnumStatus
import lib.model.Todo.Status

import java.time.LocalDateTime

// ユーザーを表すモデル
//~~~~~~~~~~~~~~~~~~~~
import Todo._

case class Todo(
                 id:          Option[Id],
                 category_id: Category.Id,
                 title:       String,
                 body:        String,
                 state:       Status,
                 updatedAt:   LocalDateTime = NOW,
                 createdAt:   LocalDateTime = NOW
               ) extends EntityModel[Id]

object Todo {
  val  Id = the[Identity[Id]]
  type Id = Long @@ Todo
  type WithNoId = Entity.WithNoId [Id, Todo]
  type EmbeddedId = Entity.EmbeddedId[Id, Todo]

  sealed abstract class Status(val code: Short, val name: String) extends EnumStatus
  object Status extends EnumStatus.Of[Status] {
    case object TODO extends Status(code = 0, name = "TODO")
    case object RUN  extends Status(code = 1, name = "RUN")
    case object DONE extends Status(code = 2, name = "DONE")
  }

  def apply(category_id: Category.Id, title: String, body: String): WithNoId = {
    new Entity.WithNoId(
      new Todo(
        id          = None,
        category_id = category_id,
        title       = title,
        body        = body,
        state       = Status(0),
      )
    )
  }
}