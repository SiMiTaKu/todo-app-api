
package json.reads

import play.api.libs.json.{Json, Reads}

object ReadJsValueTodo {
  case class JsValueCreateTodo(
                                title:       String,
                                body:        String,
                                category_id: String, //reqを渡す時にtoLong
                              )

  implicit val reads: Reads[JsValueCreateTodo] = Json.reads[JsValueCreateTodo]
}
