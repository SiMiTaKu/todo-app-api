package controllers

import lib.model.{Category}
import lib.persistence.default.CategoryRepository

import play.api.mvc.{BaseController, _}
import play.api.i18n.I18nSupport
import play.api.libs.json.Json

import scala.concurrent.ExecutionContext.Implicits.global

import javax.inject._

import json.writes.JsValueCategory

@Singleton
class CategoryApiController @Inject()(
  val controllerComponents: ControllerComponents
  ) extends BaseController with I18nSupport {

  def list() = Action async { implicit req =>
    CategoryRepository.getAll().map {
      categories => Ok(Json.toJson(JsValueCategory.list(categories)))
    }
  }

  def detail(id: Long) = Action async { implicit rew =>
    CategoryRepository.get(Category.Id(id)).map {
      case None => NotFound
      case Some(category) => Ok(Json.toJson(JsValueCategory.single(category)))
    }
  }
}
