package json.writes

import lib.model.Category
import lib.model.Category.ColorMap
import play.api.libs.json._
import lib.persistence.default.CategoryRepository.EntityEmbeddedId
import ixias.util.json.JsonEnvWrites

object WriteJsValueCategory extends JsonEnvWrites {
  case class JsValueCategoryItem(
                                  id:    Category.Id,
                                  name:  String,
                                  slug:  String,
                                  color: ColorMap,
                                )

  implicit val writeCategoryId: Writes[Category.Id] = JsNumber(_)
  implicit val writeColorMap: Writes[ColorMap]      = EnumStatusWrites.writes(_)
  implicit val writes: Writes[JsValueCategoryItem]  = Json.writes[JsValueCategoryItem]


  def list(categories: Seq[EntityEmbeddedId]): Seq[JsValueCategoryItem] = {
    categories.map{ category => single(category) }
  }

  def single(category: EntityEmbeddedId): JsValueCategoryItem = {
    JsValueCategoryItem(
      id    = category.v.id.getOrElse(Category.Id(0L)),
      name  = category.v.name,
      slug  = category.v.slug,
      color = category.v.color
    )
  }
}