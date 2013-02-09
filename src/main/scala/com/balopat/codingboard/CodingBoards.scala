package com.balopat.codingboard

import scala.collection.mutable.Map
import scala.collection.immutable.List
import actors.{Actor,TIMEOUT}
import Actor._

object CodingBoards  {
  def instance = new CodingBoards() 
}

class CodingBoards {

  private val boards = Map[String, CodingBoard]()

  def create(boardName:String, lengthOfSessionInMillis: Long, creationTimeInMillis: Long) = {
    val board = new CodingBoard(boardName, lengthOfSessionInMillis, creationTimeInMillis)
    boards += (board.url -> board)
    actor {
      receiveWithin(lengthOfSessionInMillis) {
        case TIMEOUT => boards.remove(board.url)
      }
    }
    board
  }

  def get(boardURL: String) =  boards(boardURL)
  def exists(boardURL: String) = boards.contains(boardURL)
  def list = boards.values
  def remove(boardURL: String) = boards.remove(boardURL)

  private val boardNameValidations = List[(String, String => Boolean)](
        ("Board name cannot be empty", (name: String) => name == null || name.isEmpty),
        ("Board already exists", (name: String) => exists(name))
      )

  private val lengthOfSessionValidations = List[(String, String => Boolean)](
      ("Length of session cannot be empty", (lengthOfSession: String) => 
        lengthOfSession == null || lengthOfSession.isEmpty),
      ("Please provide an integer value for length of session!", (lengthOfSession: String) => 
        {
          try{
              lengthOfSession.toInt
              false
          } catch {
            case _: NumberFormatException => true
          }
        }) 
    )

  def validate(board: String, lengthOfSession: String) = {
    Seq(
      "boardNameError" ->
        boardNameValidations.filter(_._2(board)).map(_._1).headOption.getOrElse(""),
      "lengthOfSessionError" ->
        lengthOfSessionValidations.filter(_._2(lengthOfSession)).map(_._1).headOption.getOrElse("")
    ).filter(!_._2.equals(""))
  }
}

