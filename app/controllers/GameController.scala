package controllers

import models.{Game, Move}
import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import play.api.libs.json.Json

object GameController extends Controller {
  val moveForm = Form(
    mapping(
      "pileIndex" -> number,
      "numBeans" -> number
    )(Move.apply)(Move.unapply)
  )

  def index = Action {
    val game = new Game(1L, 2L)
    val piles = game.board.piles.toList
    Ok(views.html.index(piles, moveForm))
  }

  def makeMove = Action { implicit request =>
    val move = moveForm.bindFromRequest.get
    Move.add(move.pileIndex, move.amountTaken)
    Redirect(routes.GameController.index())
  }

  def getMoves = Action {
    val moves = Move.findAll
    Ok(Json.toJson(moves))
  }
}
