package json.writes

import play.api.libs.json._
import lib.persistence.default.CategoryRepository.EntityEmbeddedId

object JsValueCategory {
  def list(categories: Seq[EntityEmbeddedId]): Seq[JsValue] =
    categories.map{ category =>
      JsObject(Seq(
        "id"    -> JsNumber(category.v.id match { case Some(id) => id case None => 0}),
        "name"  -> JsString(category.v.name),
        "slug"  -> JsString(category.v.slug),
        "color" -> JsNumber(category.v.color.code)
      ))
    }

  def single(category: EntityEmbeddedId): JsValue =
    JsObject(Seq(
      "id" -> JsNumber(category.v.id match { case Some(id) => id case None => 0}),
      "name" -> JsString(category.v.name),
      "slug" -> JsString(category.v.slug),
      "color" -> JsNumber(category.v.color.code)
    ))
}