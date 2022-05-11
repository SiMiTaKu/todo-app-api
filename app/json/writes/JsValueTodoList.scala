
package json.writes

import play.api.libs.json._
import ixias.model._
import ixias.util.EnumStatus
import lib.model.Todo.Status

import play.api.libs.functional.syntax._

import java.time.LocalDateTime

// ユーザーを表すモデル
//~~~~~~~~~~~~~~~~~~~~
import lib.model.Todo._

import lib.persistence.default.TodoRepository.EntityEmbeddedId

case class JsValueTodoListItem(
                                id:          Option[Id],
                                category_id: Long,
                                title:       String,
                                body:        String,
                                state:       Short,
                                updatedAt:   LocalDateTime = NOW,
                                createdAt:   LocalDateTime = NOW
                              ) extends EntityModel[Id]


object JsValueTodoListItem {

  def apply(todos: Seq[EntityEmbeddedId]): Seq[JsValue] =
    todos.map{todo =>
      JsObject(
        Seq(
          "id"          -> JsNumber(todo.v.id match {case Some(id) => id case None => 0}),
          "category_id" -> JsNumber(todo.v.category_id),
          "title"       -> JsString(todo.v.title),
          "body"        -> JsString(todo.v.body),
          "state"       -> JsNumber(todo.v.state.code),
        )
      )
    }
}
