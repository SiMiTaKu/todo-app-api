package json.writes

import lib.model.{Category, Todo}
import lib.model.Todo.{Importance, Status}
import lib.persistence.default.TodoRepository.EntityEmbeddedId
import play.api.libs.json._
import json.writes.WriteJsValueCategory.writeCategoryId
import ixias.util.json.JsonEnvWrites

import java.time.LocalDateTime

object WriteJsValueTodo extends JsonEnvWrites {
  case class JsValueTodoItem(
    id:          Todo.Id,
    title:       String,
    body:        String,
    category_id: Category.Id,
    state:       Status,
    importance:  Importance,
    updated_at:  LocalDateTime,
    created_at:  LocalDateTime,
  )

  implicit val writeTodoId: Writes[Todo.Id] = JsNumber(_)

  // ixias.util.json.JsonEnvWrites
  // implicit object EnumStatusWrites extends Writes[ixias.util.EnumStatus] {
  //   def writes(enum: ixias.util.EnumStatus) =
  //     JsNumber(enum.code)
  //   }
  implicit val writeStatus: Writes[Status] = EnumStatusWrites.writes(_)

  implicit val writeImportance: Writes[Importance] = EnumStatusWrites.writes(_)

  implicit val writes: Writes[JsValueTodoItem] = Json.writes[JsValueTodoItem]

  def list(todos: Seq[EntityEmbeddedId]): Seq[JsValueTodoItem] = {
    todos.map { todo => single(todo) }
  }

  def single(todo: EntityEmbeddedId): JsValueTodoItem = {
    JsValueTodoItem(
      id          = todo.v.id.getOrElse(Todo.Id(0L)),
      category_id = todo.v.category_id,
      title       = todo.v.title,
      body        = todo.v.body,
      state       = todo.v.state,
      importance  = todo.v.importance,
      updated_at  = todo.v.updatedAt,
      created_at  = todo.v.createdAt
    )
  }
}
