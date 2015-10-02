package controllers

import models.Move
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import play.api.libs.json.Json

object GameController extends Controller with Secured {
  val moveForm = Form(
    mapping(
      "pileIndex" -> number,
      "numBeans" -> number
    )(Move.apply)(Move.unapply)
  )

  def index = withPlayerAndRoom { (player, room) => implicit request =>
    Ok(views.html.index(room.game.getPiles.toList, moveForm))
  }

  def makeMove = withPlayerAndRoom { (player, room) => implicit request =>
    moveForm.bindFromRequest.fold(
      errors => BadRequest,
      move => {
        Move.add(move.pileIndex, move.amountTaken)
        try {
          val actualTaken = room.game.makeMove(move.pileIndex - 1, move.amountTaken)
          val json = Map(
            "piles" -> room.game.getPiles.toList.mkString(","),
            "pileIndex" -> move.pileIndex.toString,
            "actualTaken" -> actualTaken.toString,
            "whichPlayer" -> player.name
          )
          Ok(Json.toJson(json))
        } catch { case e: Exception =>
          BadRequest(Json.toJson("invalid move"))
        }
      }
    )
  }

  def getMoves = Action {
    val moves = Move.findAll
    Ok(Json.toJson(moves))
  }
}
