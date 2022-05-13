package controllers

import lib.model.Todo
import lib.persistence.default.TodoRepository

import play.api.mvc._
import play.api.i18n.I18nSupport
import play.api.libs.json.Json

import scala.concurrent.ExecutionContext.Implicits.global

import javax.inject._

import json.writes.JsValueTodo

@Singleton
class TodoApiController @Inject()(
                                val controllerComponents: ControllerComponents
                              ) extends BaseController with I18nSupport {

  def list() = Action async{ implicit req =>
    TodoRepository.getAll().map{
      todos => Ok(Json.toJson(JsValueTodo.list(todos)))
    }
  }

  def detail(id: Long) = Action async { implicit request: Request[AnyContent] =>
    TodoRepository.get(Todo.Id(id)).map{
      todo => todo match{
        case Some(result) => Ok(Json.toJson(JsValueTodo.single(result)))
        case None         => NotFound
      }
    }
  }
}