package controllers

import lib.model.Category
import lib.model.Category.ColorMap
import lib.persistence.default.{CategoryRepository, TodoRepository}
import play.api.mvc.{BaseController, _}
import play.api.i18n.I18nSupport
import play.api.libs.json.Json

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future.successful
import json.writes.WriteJsValueCategory
import json.reads.ReadJsValueCategory.{JsValueCreateCategory, JsValueUpdateCategory}

import javax.inject._

@Singleton
class CategoryApiController @Inject()(
  val controllerComponents: ControllerComponents
  ) extends BaseController with I18nSupport {

  def list() = Action async { implicit req =>
    CategoryRepository.getAll().map {
      categories => Ok(Json.toJson(WriteJsValueCategory.list(categories)))
    }
  }

  def detail(id: Long) = Action async { implicit req =>
    CategoryRepository.get(Category.Id(id)).map {
      case None => NotFound
      case Some(category) => Ok(Json.toJson(WriteJsValueCategory.single(category)))
    }
  }

  def remove(id: Long) = Action async { implicit request: Request[AnyContent] =>
    for{
      result <- CategoryRepository.remove(Category.Id(id))
      _      <- result match {
                  case None => successful(None)
                  case _    => TodoRepository.removeMatchCategory(Category.Id(id))
                }
    } yield {
      result match {
        case None => NotFound
        case _    => Ok
      }
    }
  }

  def add() = Action ( parse.json ).async { implicit req =>
    req.body
      .validate[JsValueCreateCategory].fold(
        error    => successful(NotFound),
        category => CategoryRepository.add(
          Category.apply(
            category.name,
            category.slug,
            category.color
          )
        ).map(_    => Ok)
      )
  }

  def update(id: Long) = Action ( parse.json ).async { implicit req =>
    req.body
      .validate[JsValueUpdateCategory].fold(
        error    => successful(NotFound),
        category => for{
                      result <- CategoryRepository.get(Category.Id(id))
                      _      <- result match {
                                  case None      => successful(NotFound)
                                  case Some(old) => CategoryRepository.update(old.map(_.copy(
                                                      name  = category.name,
                                                      slug  = category.slug,
                                                      color = category.color
                                                    )))
                                }
                    }yield{
                      result match{
                        case None => NotFound
                        case _    => Ok
                      }
                    }
      )
  }
}
