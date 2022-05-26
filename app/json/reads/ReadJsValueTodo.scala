
package json.reads

import lib.model.{Category, Todo}
import play.api.libs.json._

object ReadJsValueTodo {
  //それぞれに必要なreadを用意する必要がある。
  case class JsValueCreateTodo(
                                title:       String,
                                body:        String,
                                category_id: Long,
                              )
  //implicit val readCategoryId: Reads[Category.Id] = _.validate[lib.model.Category.Id]
  //エラーになるためコメントアウト
  implicit val reads: Reads[JsValueCreateTodo]    = Json.reads[JsValueCreateTodo]

  case class JsValueUpdateTodo(
                                id:          Long,
                                title:       String,
                                body:        String,
                                category_id: Long,
                                state:       Short,
                              )


  //implicit val readTodoId:  Reads[Todo.Id]           = _.validate[lib.model.Todo.Id]
  //エラーになるためコメントアウト
  implicit val readsUpdate: Reads[JsValueUpdateTodo] = Json.reads[JsValueUpdateTodo]

}
