
package json.reads

import lib.model.{Category, Todo}
import play.api.libs.json._

import ixias.util.json.JsonEnvReads
import json.reads.ReadJsValueCategory.readCategoryId

object ReadJsValueTodo extends JsonEnvReads {
  //それぞれに必要なreadを用意する必要がある。
  case class JsValueCreateTodo(
                                title:       String,
                                body:        String,
                                category_id: Category.Id,
                              )

  implicit val reads: Reads[JsValueCreateTodo]    = Json.reads[JsValueCreateTodo]

  case class JsValueUpdateTodo(
                                id:          Todo.Id,
                                title:       String,
                                body:        String,
                                category_id: Category.Id,
                                state:       Short,
                              )

  implicit val readTodoId:  Reads[Todo.Id]           = idAsNumberReads[Todo.Id]
  implicit val readsUpdate: Reads[JsValueUpdateTodo] = Json.reads[JsValueUpdateTodo]

}
