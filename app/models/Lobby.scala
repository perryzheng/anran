package models

import scala.collection.mutable.{ListBuffer, Map}

/**
 * Created by pzheng on 9/30/15.
 */
object Lobby {
  val rooms = Map[Long, Room]()
  val availableRoomNums = ListBuffer[Long]()
  var currentRoomNum = 0L

  /**
   * Player joins the room, returns the current room
   */
  def joinRoom(player: Player): Room = {
    availableRoomNums.isEmpty match {
      case true =>
        availableRoomNums += player.id
        val currentRoom = new Room(currentRoomNum)
        currentRoom.player1 = player
        rooms(currentRoomNum) = currentRoom
        currentRoomNum = currentRoomNum + 1
        currentRoom
      case _ =>
        val roomNum = availableRoomNums.remove(0)
        rooms(roomNum).player2 = player
        rooms(roomNum)
    }
  }

  def leaveRoom(player: Player) = {
  }
}

class Room(val id: Long) {
  var player1: Player = Player(0, "")
  var player2: Player = Player(0, "")
  lazy val game: Game = new Game(player1.id, player2.id)
}