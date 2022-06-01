package json.reads

import lib.model.{Category, Todo}
import play.api.libs.json._
import ixias.util.json.JsonEnvReads
import json.reads.ReadJsValueCategory.readCategoryId
import lib.model.Todo.{Importance, Status}

import java.time.LocalDateTime

object ReadJsValueTodo extends JsonEnvReads {
  //それぞれに必要なreadを用意する必要がある。
  case class JsValueCreateTodo(
    title:       String,
    body:        String,
    category_id: Category.Id,
    importance:  Importance
  )

  implicit val readTodoImportance: Reads[Importance] = enumReads(Importance)

  implicit val reads: Reads[JsValueCreateTodo] = Json.reads[JsValueCreateTodo]

  case class JsValueUpdateTodo(
    id:          Todo.Id,
    title:       String,
    body:        String,
    category_id: Category.Id,
    state:       Status,
    importance:  Importance,
    updated_at:  LocalDateTime,
    created_at:  LocalDateTime
  )

  // ixias.uril.json.JsonEnvReads
  // def idAsNumberReads[T <: ixias.model.@@[Long, _]]: Reads[T] =
  //   new Reads[T] {
  //     def reads(json: JsValue) = json match {
  //       case JsNumber(n) if n.isValidLong => {
  //         val Id = ixias.model.the[ixias.model.Identity[T]]
  //         JsSuccess(Id(n.toLong.asInstanceOf[T]))
  //       }
  //       case JsNumber(n) => JsError("error.expected.tag.long")
  //       case _           => JsError("error.expected.tag.jsnumber")
  //     }
  //   }
  implicit val readTodoId: Reads[Todo.Id] = idAsNumberReads[Todo.Id]

  //  ixias.uril.json.JsonEnvReads
  //  def enumReads[E <: ixias.util.EnumStatus](enum: ixias.util.EnumStatus.Of[E]): Reads[E] =
  //    new Reads[E] {
  //      def reads(json: JsValue) = json match {
  //        case JsNumber(n) if n.isValidShort => JsSuccess(enum(n.toShort))
  //        case JsNumber(n) => JsError("error.expected.enum.short")
  //        case _           => JsError("error.expected.enum.jsnumber")
  //      }
  //    }
  implicit val readTodoStatus: Reads[Status] = enumReads(Status)

  implicit val readsUpdate: Reads[JsValueUpdateTodo] = Json.reads[JsValueUpdateTodo]

}
