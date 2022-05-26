package json.writes

import lib.model.{Category, Todo}
import play.api.libs.json._
import lib.persistence.default.TodoRepository.EntityEmbeddedId
import play.api.libs.functional.syntax.{toFunctionalBuilderOps, unlift}
import json.writes.WriteJsValueCategory.writeCategoryId

object WriteJsValueTodo {
  case class JsValueTodoItem(
                                  id:          Todo.Id,
                                  title:       String,
                                  body:        String,
                                  category_id: Category.Id,
                                  state:       Short
                                )
  implicit val writeTodoId: Writes[Todo.Id] = JsNumber(_)

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
      state       = todo.v.state.code
    )
  }
}
