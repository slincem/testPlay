package controllers

import javax.inject._

import model.{Persona, PersonaRepository}
import play.api._
import play.api.libs.json.Json
import play.api.mvc._
import scala.concurrent.ExecutionContext.Implicits.global

import scala.concurrent.Future

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject()(cc: ControllerComponents, dao:PersonaRepository) extends AbstractController(cc) {

  /**
   * Create an Action to render an HTML page.
   *
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
    *
   */
  def getAll=Action.async{
    dao.all() map { per =>
      val json= Json.toJson(per)
      Ok(json).withHeaders(ACCESS_CONTROL_ALLOW_ORIGIN -> "*")
    }
  }

  def getById(id:Long)=Action.async{
    dao.byId(id) map { per =>
      val json= Json.toJson(per)
      Ok(json).withHeaders(ACCESS_CONTROL_ALLOW_ORIGIN -> "*")
    }
  }




  /*def index() = Action { implicit request: Request[AnyContent] =>
    {

      //Logger.info(dao.all().isCompleted.toString)
      Logger.info("sali")
      Ok(views.html.index())

    }

  }*/
}
