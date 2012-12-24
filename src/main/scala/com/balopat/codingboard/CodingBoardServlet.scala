package com.balopat.codingboard

class CodingBoardServlet(boards: CodingBoards = CodingBoards.instance) extends CodingBoardViewHelper 
  {  

    get("/") {
       index()
    }

    post("/submitboard") {
       createAndJoinBoard(params("board"), params("lengthOfSessionInMinutes")) 
    }

    get("/boards/:board") {
       joinCodingBoard(params("board"))
    }

    get("/boards/:board/post") {
       joinCodingBoard(params("board"))
    }

    post("/boards/:board/post") {
       postSnippetToBoard(params("board"), params("formtoken"), params("description"), params("code"), params("language"))
    }

    post("/boards/:board/refresh") {
       isBoardChangedSinceLastCodeSnippet(params("board"), params("lastCodeSnippetId"))
    }

    get("/boards/:board/codesnippet") {
       postCodeSnippetTo(params("board"))
    }
    
    notFound {
     contentType = null
     findTemplate(requestPath) map { path =>
        contentType = "text/html"
        layoutTemplate(path)
     } orElse serveStaticResource() getOrElse index()
  }
}
