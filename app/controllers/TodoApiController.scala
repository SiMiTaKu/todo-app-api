package controllers

import lib.model.{Category, Todo}
import lib.persistence.default.TodoRepository

import play.api.mvc._
import play.api.i18n.I18nSupport
import play.api.libs.json.Json

import scala.concurrent.Future.successful
import scala.concurrent.ExecutionContext.Implicits.global

import json.writes.WriteJsValueTodo
import json.reads.ReadJsValueTodo.{JsValueCreateTodo, JsValueUpdateTodo}

import javax.inject._

@Singleton
class TodoApiController @Inject()(
                                val controllerComponents: ControllerComponents
                              ) extends BaseController with I18nSupport {

  def list() = Action async{ implicit req =>
    TodoRepository.getAll().map{
      todos => Ok(Json.toJson(WriteJsValueTodo.list(todos)))
    }
  }

  def detail(id: Long) = Action async { implicit request: Request[AnyContent] =>
    TodoRepository.get(Todo.Id(id)).map{
      todo => todo match{
        case Some(result) => Ok(Json.toJson(WriteJsValueTodo.single(result)))
        case None         => NotFound
      }
    }
  }

  def remove(id: Long) = Action async { implicit request: Request[AnyContent] =>
    TodoRepository.remove(Todo.Id(id)).map {
      case Some(result) => Ok
      case None         => NotFound
    }
  }

  def add() = Action(parse.json).async { implicit req =>
    req.body
      .validate[JsValueCreateTodo]
      .fold(
        errors => {
          successful(NotFound)
        },
        todoData => {
          TodoRepository.add(
            Todo.apply(
              Category.Id(todoData.category_id),
              todoData.title,
              todoData.body
            )
          ).map(_ => Ok)
        }
      )
  }

  def update(id: Long) = Action(parse.json).async { implicit req =>
    req.body
      .validate[JsValueUpdateTodo]
        .fold(
          errors   => successful(NotFound),
          todoData => for{
                        result <- TodoRepository.get(Todo.Id(id))
                        _      <- result match {
                                    case None      => successful(NotFound)
                                    case Some(old) => TodoRepository.update(old.map(_.copy(
                                                        category_id = Category.Id(todoData.category_id),
                                                        title       = todoData.title,
                                                        body        = todoData.body,
                                                        state       = todoData.state
                                                      )))
                                  }
                      } yield {
                        result match {
                          case None => NotFound
                          case _    => Ok
                        }
                      }

        )
  }
}