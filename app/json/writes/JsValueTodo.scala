package json.writes

import play.api.libs.json._
import lib.persistence.default.TodoRepository.EntityEmbeddedId

object JsValueTodo {
  def list(todos: Seq[EntityEmbeddedId]): Seq[JsValue] =
    todos.map{todo =>
      JsObject(Seq(
          "id"          -> JsNumber(todo.v.id match {case Some(id) => id case None => 0}),
          "category_id" -> JsNumber(todo.v.category_id),
          "title"       -> JsString(todo.v.title),
          "body"        -> JsString(todo.v.body),
          "state"       -> JsNumber(todo.v.state.code),
      ))
    }

  def single(todo: EntityEmbeddedId): JsValue =
    JsObject(Seq(
      "id"          -> JsNumber(todo.v.id match {case Some(id) => id case None => 0}),
      "category_id" -> JsNumber(todo.v.category_id),
      "title"       -> JsString(todo.v.title),
      "body"        -> JsString(todo.v.body),
      "state"       -> JsNumber(todo.v.state.code),
    ))

}
