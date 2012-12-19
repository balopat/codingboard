package com.balopat.codingboard

import scala.collection.mutable.Map
import scala.collection.immutable.List
import scala.actors.Actor

object CodingBoards  {
  def instance = new CodingBoards() 
}

class CodingBoards {

  val boards = Map[String, CodingBoard]()
  private var formTokens = scala.collection.mutable.Seq[String]()

  def create(board:String, lifeTimeInMinutes: Integer, creationTimeInMillies: Long) = {
    boards += (board -> new CodingBoard(board, lifeTimeInMinutes, creationTimeInMillies))
  }


  def get(board: String) =  boards(board)
  def exists(board: String) = boards.contains(board)
  def list = boards.values
  def remove(board: String) = boards.remove(board)
}

