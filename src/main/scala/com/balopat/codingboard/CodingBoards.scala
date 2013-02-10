package com.balopat.codingboard

import scala.collection.mutable.Map
import scala.collection.immutable.List
import actors.{Actor,TIMEOUT}
import Actor._

object CodingBoards  {
  def instance = new CodingBoards() 
}

class CodingBoards {

  val boards = Map[String, CodingBoard]()
  private var formTokens = scala.collection.mutable.Seq[String]()

  def create(boardName:String, lengthOfSessionInMillis: Long, creationTimeInMillis: Long, isPrivate: Boolean) = {
    val board = new CodingBoard(boardName, lengthOfSessionInMillis, creationTimeInMillis, isPrivate)
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
  
  /**
   * List of all non-private boards. 
   * This means private boards can only accessed if you know their id. Could be done in controller.
   */
  def list = boards.values filter ( !_.isPrivate ) 
  
  def remove(boardURL: String) = boards.remove(boardURL)

  val boardNameValidations = List[(String, String => Boolean)](
        ("Board name cannot be empty", (name: String) => name == null || name.isEmpty)
        ,("Board already exists", (name: String) => exists(name))
      )

  val lengthOfSessionValidations = List[(String, String => Boolean)](
      ("Length of session cannot be empty", (lengthOfSession: String) => 
        lengthOfSession == null || lengthOfSession.isEmpty),
      ("Please provide an integer value for length of session!", (lengthOfSession: String) => 
        {
          try{
              lengthOfSession.toInt
              false
          }catch {
            case _ => true
          }
        }) 
    )

  def validate(board: String, lengthOfSession: String) = {
    Seq("boardNameError" -> 
      boardNameValidations.filter(_._2(board)).map(_._1).headOption.getOrElse(""),
     "lengthOfSessionError" -> 
      lengthOfSessionValidations.filter(_._2(lengthOfSession)).map(_._1).headOption.getOrElse("")).filter(!_._2.equals(""))
  }
}

