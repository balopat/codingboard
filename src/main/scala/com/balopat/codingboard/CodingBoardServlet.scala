package com.balopat.codingboard

import org.scalatra._
import scalate.ScalateSupport
import scala.collection.immutable.List
import org.scalatra.atmosphere._
import org.scalatra.json.{JValueResult, JacksonJsonSupport}
import org.json4s._
import JsonDSL._
import org.atmosphere.cpr.MetaBroadcaster

class CodingBoardServlet(boards: CodingBoards = CodingBoards.instance) extends ScalatraServlet
  with ScalateSupport with JValueResult
  with JacksonJsonSupport with SessionSupport
  {

implicit protected val jsonFormats: Formats = DefaultFormats

  def joinCodingBoard(board: String, extraAttributes: (String, Any)*) = {
    contentType="text/html"
    if (boards.exists(board))
      jade("board", ("board" -> boards.get(board) :: extraAttributes.toList).toArray: _*  )
       else
         jade("index", "boards" -> boards.list, "errorMessage" -> "CodingBoard not found!")

 }

  get("/") {
    contentType="text/html"
    jade("index", "boards" -> boards.list)
  }

  post("/submitboard") {
    contentType="text/html"
    val board = params("board")
    if (!boards.exists(board)) {
      boards.create(board)
      joinCodingBoard(board)
    }else {
      jade("createboard", "errorMessage" -> "A board with this name already exists!", "board" -> board)
    }
  }


  get("/boards/:board") {
    joinCodingBoard(params("board"))
  }

  get("/boards/:board/post") {
    joinCodingBoard(params("board"))
  }

  post("/boards/:board/post") {
      val board = params("board")
      val formToken = params("formtoken")
    whenCodingBoardExistsOtherwiseRootError(
        board, 
        { 
          val codeSnippet = 
            new CodeSnippet(formToken, 
                params("description"), 
                params("code"), 
                params("language"), 
                System.currentTimeMillis)
          boards.get(board) += codeSnippet
          joinCodingBoard(board )
        }
    )
   }

  post("/boards/:board/refresh") {
    val codeSnippets = boards.get(params("board")).codeSnippets
    if ( (!codeSnippets.isEmpty) && (!codeSnippets.last.id.equals(params("lastCodeSnippetId"))))
    {
        //TODO get all since the UUID, for now it's just the last
        compact(render(codeSnippets.last.toJSON))
    }else{
      compact(render("refresh"->"norefresh"))
    }
  }

  get("/boards/:board/codesnippet") {
    val board = params("board")
    contentType="text/html"
    whenCodingBoardExistsOtherwiseRootError(
        board, 
        jade("codesnippet", 
             "formtoken" -> java.util.UUID.randomUUID.toString,  
             "board" -> board)
    )
  }
 
  def whenCodingBoardExistsOtherwiseRootError(board:String, f: =>Any) = {
    if (boards.exists(board)) {
      f 
    } else {
      contentType="text/html"
      jade("index", "boards" -> boards.list, "errorMessage" -> "CodingBoard not found!")
    }
  }

   
  notFound {
    // remove content type in case it was set through an action
    contentType = null
    // Try to render a ScalateTemplate if no route matched
    findTemplate(requestPath) map { path =>
      contentType = "text/html"
      layoutTemplate(path)
    } orElse serveStaticResource() getOrElse resourceNotFound()
  }
}
