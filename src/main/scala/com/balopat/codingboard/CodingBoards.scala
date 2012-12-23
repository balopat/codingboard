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

  def create(board:String, lengthOfSessionInMinutes: Integer, creationTimeInMillies: Long) = {
    boards += (board -> new CodingBoard(board, lengthOfSessionInMinutes, creationTimeInMillies))
  }


  def get(board: String) =  boards(board)
  def exists(board: String) = boards.contains(board)
  def list = boards.values
  def remove(board: String) = boards.remove(board)

  val boardNameValidations = List[(String, String => Boolean)](
        ("Board name cannot be empty", (name: String) => name == null || name.isEmpty)
        ,("Board already exists", (name: String) => exists(name))
      )
  val lengthOfSessionValidations = List[(String, String => Boolean)](
      ("Length of session cannot be empty", (lengthOfSession: String) => 
        lengthOfSession == null || lengthOfSession.isEmpty)

    )

  def validate(board: String, lengthOfSession: String) = {
    ("boardNameError" -> boardNameValidations.filter(_._2(board)).map(_._1).firstOption.getOrElse(""),
     "lengthOfSessionError" -> lengthOfSessionValidations.filter(_._2(lengthOfSession)).map(_._1).firstOption.getOrElse(""))
  }
}

