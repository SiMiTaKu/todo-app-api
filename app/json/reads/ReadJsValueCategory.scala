package json.reads

import play.api.libs.json.{Json, Reads}

object ReadJsValueCategory{
  case class JsValueCreateCategory(
                                name:  String,
                                slug:  String,
                                color: Short
                              )
  implicit val read: Reads[JsValueCreateCategory] = Json.reads[JsValueCreateCategory]
}