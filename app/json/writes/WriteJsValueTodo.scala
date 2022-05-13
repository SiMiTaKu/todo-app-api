package json.writes

import play.api.libs.json._
import lib.persistence.default.TodoRepository.EntityEmbeddedId

object WriteJsValueTodo {
  case class JsValueTodoItem(
                                  id:          Long,
                                  category_id: Long,
                                  title:       String,
                                  body:        String,
                                  state:       Short
                                )

  implicit val writes: Writes[JsValueTodoItem] = Json.writes[JsValueTodoItem]

  def list(todos: Seq[EntityEmbeddedId]): Seq[JsValueTodoItem] = {
    todos.map { todo => single(todo) }
  }

  def single(todo: EntityEmbeddedId): JsValueTodoItem = {
    JsValueTodoItem(
      id          = todo.v.id match { case Some(id) => id case None => 0},
      category_id = todo.v.category_id,
      title       = todo.v.title,
      body        = todo.v.body,
      state       = todo.v.state.code
    )
  }
}
