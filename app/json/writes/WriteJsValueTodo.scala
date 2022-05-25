package json.writes

import lib.model.{Category, Todo}
import play.api.libs.json._
import lib.persistence.default.TodoRepository.EntityEmbeddedId
import play.api.libs.functional.syntax.{toFunctionalBuilderOps, unlift}

object WriteJsValueTodo {
  case class JsValueTodoItem(
                                  id:          Todo.Id,
                                  category_id: Category.Id,
                                  title:       String,
                                  body:        String,
                                  state:       Short
                                )

  implicit val writes = (
    (__ \ "id"         ).write[Long] ~
    (__ \ "category_id").write[Long] ~
    (__ \ "title"      ).write[String] ~
    (__ \ "body"       ).write[String] ~
    (__ \ "state"      ).write[Short]
  )(unlift(JsValueTodoItem.unapply))

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
