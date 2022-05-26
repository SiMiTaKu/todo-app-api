package json.reads

import lib.model.Category
import play.api.libs.json.{Json, Reads}

object ReadJsValueCategory{
  case class JsValueCreateCategory(
                                name:  String,
                                slug:  String,
                                color: Short
                              )
  implicit val read: Reads[JsValueCreateCategory] = Json.reads[JsValueCreateCategory]

  case class JsValueUpdateCategory(
                                    id:    Category.Id,
                                    name:  String,
                                    slug:  String,
                                    color: Short
                                  )

  implicit val readCategoryId: Reads[Category.Id]           = _.validate[Category.Id]
  implicit val readUpdate:     Reads[JsValueUpdateCategory] = Json.reads[JsValueUpdateCategory]
}