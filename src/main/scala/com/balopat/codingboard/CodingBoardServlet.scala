package com.balopat.codingboard

class CodingBoardServlet(boards: CodingBoards = CodingBoards.instance) extends CodingBoardViewHelper {

  get("/") {
    index()
  }

    post("/submitboard") {
       val isPrivate = params.get("private").isDefined
       createAndJoinBoard(params("board"), params("lengthOfSessionInMinutes"), isPrivate ) 
    }

  get("/boards/:board") {
    joinCodingBoard(params("board"))
  }

  get("/createboard") {
    contentType = null
    findTemplate(requestPath) map { path =>
      contentType = "text/html"
      layoutTemplate(path)
    }
  }

  get("/boards/:board/post") {
    joinCodingBoard(params("board"))
  }

  post("/boards/:board/post") {
    postSnippetToBoard(params("board"), params("formtoken"), params("description"), params("code"), params("language"))
    redirect("/boards/" + params("board"))
  }

  post("/boards/:board/refresh") {
    isBoardChangedSinceLastCodeSnippet(params("board"), params("lastCodeSnippetId"))
  }

  get("/boards/:board/codesnippet") {
    codeSnippetFormFor(params("board"))
  }

  notFound {
    serveStaticResource() getOrElse index()
  }
}
