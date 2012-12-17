package com.balopat.sharethecode

import scala.collection.mutable.Map
import scala.collection.immutable.List

object CodingBoards  {
  def instance = new CodingBoards() 
}

class CodingBoards {

  private val rooms = Map[String, CodingBoard]()
  private var formTokens = scala.collection.mutable.Seq[String]()

  def create(room:String) = {
    rooms += (room -> new CodingBoard(room))
  }


  def get(room: String) =  rooms(room)
  def exists(room: String) = rooms.contains(room)
  def list = rooms.keys
}

