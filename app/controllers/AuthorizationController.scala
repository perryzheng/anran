package controllers

import controllers.GameController._
import models.{Lobby, Room, Player}
import play.api.mvc._

/**
 * Created by pzheng on 10/1/15.
 */
object AuthorizationController extends Controller {
  def login = Action {
    println("login")
    Ok(views.html.index(List[Int](), moveForm))
  }
}

trait Secured {
  def playerId(request: RequestHeader) = request.session.get(Secured.PlayerIdParam)

  def onUnauthorized(request: RequestHeader): Result = {
    println("on unauthorized")
    Results.Redirect(routes.AuthorizationController.login)
  }

  def withPlayerId(f: => String => Request[AnyContent] => Result) = {
    Security.Authenticated(playerId, onUnauthorized) { playerId =>
      Action(request => f(playerId)(request))
    }
  }

  def withPlayerAndRoom(f: (Player, Room) => Request[AnyContent] => Result) = withPlayerId { playerId => implicit request =>
    (Player.get(playerId.toLong), Lobby.rooms.get(playerId.toLong)) match {
      case (Some(player), Some(room)) => f(player, room)(request)
      case (_, _) => onUnauthorized(request)
    }
  }
}

object Secured {
  val PlayerIdParam = "playerId"
}