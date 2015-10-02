package controllers

import models.{Lobby, Player}
import play.api._
import play.api.data._
import play.api.data.Forms._
import play.api.libs.json.Json
import play.api.mvc._

/**
 * Created by pzheng on 9/30/15.
 */
object RoomsController extends Controller with Secured {
  val playerForm = Form("name"-> nonEmptyText)

  def join: Action[AnyContent] = Action { implicit request =>
    playerForm.bindFromRequest.fold(
      errors => BadRequest,
      name => {
        val room = Lobby.joinRoom(Player(name))
        Redirect(routes.GameController.index()).withSession(
          Secured.PlayerIdParam -> room.id.toString
        )
      }
    )
  }

  def getRoom = withPlayerAndRoom { (player, room) => implicit request =>
    val json = Map(
      "currentPlayerId" -> player.id.toString,
      "player1Id" -> room.player1.id.toString,
      "player1Name" -> room.player1.name,
      "player2Id" -> room.player2.id.toString,
      "player2Name" -> room.player2.name,
      "piles" -> room.game.getPiles.toList.mkString(",")
    )
    Ok(Json.toJson(json))
  }
}
