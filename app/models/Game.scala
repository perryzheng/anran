package models

import play.api.libs.json.Json

/**
 * Created by pzheng on 9/29/15.
 */
class Game(val player1Id: Long, val player2Id: Long) {
  var isPlayer1sTurn = true
  private val board = new Board

  /**
   * Makes the move and returns the amount actually being taken
   * @param pileIndex - the index in the pile
   * @param amountTaken - amount of tokens taken
   * @return the amount actually being taken
   */
  def makeMove(pileIndex: Int, amountTaken: Int): Int =
    board.makeMove(pileIndex, amountTaken)

  def getPiles = board.piles

  /**
   * Switch turn and return the next player's id
   * @return the next player's id
   */
  def switchTurn: Long = {
    isPlayer1sTurn = !isPlayer1sTurn
    if (isPlayer1sTurn)
      player1Id
    else
      player2Id
  }

  def isPlayersTurn(playerId: Long) = {
    if (isPlayer1sTurn)
      playerId == player1Id
    else
      playerId == player2Id
  }
}