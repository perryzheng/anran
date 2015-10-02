package models

import scala.collection.mutable.ListBuffer


/**
 * Created by pzheng on 9/30/15.
 */
case class Player(id: Long, name: String)

object Player {
  private val players = ListBuffer[Player]()

  def apply(name: String): Player = {
    val player = Player(players.size, name)
    players += player
    player
  }

  def get(playerId: Long): Option[Player] = {
    Some(players(playerId.toInt))
  }
}