package models

import scala.collection.mutable.ListBuffer

/**
 * Created by pzheng on 9/29/15.
 */
class Board {
  val piles = createPiles

  def createPiles = ListBuffer(3, 4, 5)

  def makeMove(pileIndex: Int, amountTaken: Int): Int = {
    def isInvalidMove(pileIndex: Int, amountTaken: Int): Boolean =
      pileIndex >= piles.size || piles(pileIndex) == 0 || amountTaken <= 0

    if (isInvalidMove(pileIndex, amountTaken))
      throw new InvalidMoveException(s"Not a valid move")

    if (amountTaken <= pileIndex) {
      piles(pileIndex) -= amountTaken
      amountTaken
    } else {
      val actualTaken = piles(pileIndex)
      piles(pileIndex) = 0
      actualTaken
    }
  }
}

case class InvalidMoveException(e: String) extends RuntimeException(e)