package models

import play.api.libs.json.Json

/**
 * Created by pzheng on 9/30/15.
 */

case class Move(pileIndex: Int, amountTaken: Int)

object Move {
  implicit val moveFormat = Json.format[Move]

  var moves = List(
    Move(1, 3),
    Move(4, 6)
  )

  def add(pileIndex: Int, amountTaken: Int) =
    moves = moves :+ Move(pileIndex, amountTaken)

  def findAll = moves
}
