package com.balopat.codingboard

import org.scalatra._
import scalate.ScalateSupport
import org.scalatra.json.{JValueResult, JacksonJsonSupport}
import org.json4s._
import JsonDSL._

class CodingBoardViewHelper(boards: CodingBoards = CodingBoards.instance) extends ScalatraServlet with ScalateSupport with JValueResult
with JacksonJsonSupport {
  implicit protected val jsonFormats: Formats = DefaultFormats

  def postSnippetToBoard(board: String, formToken: String, description: String, code: String, language: String) = {
    whenCodingBoardExistsOtherwiseErrorOnHomePage(
    board, {
      val codeSnippet =
        new CodeSnippet(formToken,
          description,
          code,
          language,
          System.currentTimeMillis)
      boards.get(board) += codeSnippet
    }
    )
  }

  def isBoardChangedSinceLastCodeSnippet(board: String, lastCodeSnippetId: String) = {
    if (boardChanged(board, lastCodeSnippetId)) {
      compact(render(codeSnippets(board).last.toJSON))
    } else {
      compact(render("refresh" -> "norefresh"))
    }
  }

  def boardChanged(board: String, lastCodeSnippetId: String) = {
    (!codeSnippets(board).isEmpty) && (!codeSnippets(board).last.id.equals(lastCodeSnippetId))
  }

  def codeSnippets(board: String) = boards.get(board).codeSnippets

  def codeSnippetFormFor(board: String) = {
    contentType = "text/html"
    whenCodingBoardExistsOtherwiseErrorOnHomePage(
      board,
      jade("codesnippet",
        "formtoken" -> java.util.UUID.randomUUID.toString,
        "board" -> board)
    )
  }

  def createAndJoinBoard(board: String, lengthOfSession: String) = {
    val validationErrors = boards.validate(board, lengthOfSession)
    if (!validationErrors.isEmpty) {
      contentType = "text/html"
      jade("createboard", (validationErrors :+ ("board" -> board) :+ ("lengthOfSession" -> lengthOfSession)).toArray: _*)
    } else {
      joinCodingBoard(boards.create(board, lengthOfSession.toInt * 60000, System.currentTimeMillis).url)
    }
  }

  def joinCodingBoard(boardURL: String, extraAttributes: (String, Any)*) = {
    contentType = "text/html"
    whenCodingBoardExistsOtherwiseErrorOnHomePage(
      boardURL,
      jade("board", ("board" -> boards.get(boardURL) :: extraAttributes.toList).toArray: _*)
    )
  }

  def whenCodingBoardExistsOtherwiseErrorOnHomePage(boardURL: String, boardExistsPage: => Any) = {
    if (boards.exists(boardURL)) {
      boardExistsPage
    } else {
      index("errorMessage" -> "CodingBoard not found!")
    }
  }

  def index(extraAttributes: (String, Any)*) = {
    contentType = "text/html"
    jade("index", ("boards" -> boards.list :: extraAttributes.toList).toArray: _*)
  }
}
