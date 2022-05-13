package json.writes

import play.api.libs.json._
import lib.persistence.default.CategoryRepository.EntityEmbeddedId

object WriteJsValueCategory {
  case class JsValueCategoryItem(
                                id:    Long,
                                name:  String,
                                slug:  String,
                                color: Short,
                                )

  implicit val write: Writes[JsValueCategoryItem] = Json.writes[JsValueCategoryItem]

  def list(categories: Seq[EntityEmbeddedId]): Seq[JsValueCategoryItem] = {
    categories.map{ category => single(category) }
  }

  def single(category: EntityEmbeddedId): JsValueCategoryItem = {
    JsValueCategoryItem(
      id    = category.v.id match { case Some(id) => id case None => 0},
      name  = category.v.name,
      slug  = category.v.slug,
      color = category.v.color.code
    )
  }
}