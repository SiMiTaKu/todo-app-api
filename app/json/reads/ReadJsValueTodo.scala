
package json.reads

import play.api.libs.json.{Json, Reads}

object ReadJsValueTodo {
  //それぞれに必要なreadを用意する必要がある。
  case class JsValueCreateTodo(
                                title:       String,
                                body:        String,
                                category_id: Long,
                              )

  implicit val reads: Reads[JsValueCreateTodo] = Json.reads[JsValueCreateTodo]

  case class JsValueUpdateTodo(
                                id:          Long,
                                title:       String,
                                body:        String,
                                category_id: Long,
                                state:       Short,
                              )
  implicit val readsUpdate: Reads[JsValueUpdateTodo] = Json.reads[JsValueUpdateTodo]
}
