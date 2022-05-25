package json.writes

import lib.model.Category
import play.api.libs.json._
import lib.persistence.default.CategoryRepository.EntityEmbeddedId
import play.api.libs.functional.syntax.{toFunctionalBuilderOps, unlift}

object WriteJsValueCategory {
  case class JsValueCategoryItem(
                                id:    Category.Id,
                                name:  String,
                                slug:  String,
                                color: Short,
                                )

  implicit val writes = (
    (__ \ "id"    ).write[Long] ~
    (__ \ "name"  ).write[String] ~
    (__ \ "slug"  ).write[String] ~
    (__ \ "color" ).write[Short]
  )(unlift(JsValueCategoryItem.unapply))

  def list(categories: Seq[EntityEmbeddedId]): Seq[JsValueCategoryItem] = {
    categories.map{ category => single(category) }
  }

  def single(category: EntityEmbeddedId): JsValueCategoryItem = {
    JsValueCategoryItem(
      id    = category.v.id.getOrElse(Category.Id(0L)),
      name  = category.v.name,
      slug  = category.v.slug,
      color = category.v.color.code
    )
  }
}