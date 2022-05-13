
/* 実装中のためコメントアウト


package json.reads


import lib.persistence.onMySQL.TodoRepository.EntityEmbeddedId

import play.api.libs.json._
import play.api.libs.json.Reads._
import play.api.libs.functional.syntax._ // Combinator syntax

class ReadJsValueTodo {
  case class JsValueTodoItem(
                              id:          Long,
                              category_id: Long,
                              title:       String,
                              body:        String,
                              state:       Short
                            )

  implicit val reads: Reads[JsValueTodoItem] = Json.reads[JsValueTodoItem]

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

 */
