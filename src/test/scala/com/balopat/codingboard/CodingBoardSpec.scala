package com.balopat.codingboard

import org.specs2.mutable._

class CodingBoardSpec extends Specification {

  "A CodingBoard" should {
       "return it's name in toString" in {
          new CodingBoard("board name").toString should_==("board name")
       }

       "return empty String for "+ 
       "the lastCodeSnippet id if there are no codesnippets" in {
          new CodingBoard("board").lastCodeSnippetId should_==("")
       }

       "return the last codeSnippet id" in {
          val board = new CodingBoard("board")
          val aCodeSnippet = new CodeSnippet("id", "desc", "code", "lang", 0)
          val anotherCodeSnippet = new CodeSnippet("id2", "desc", "code", "lang", 0)
          board += aCodeSnippet += anotherCodeSnippet
          board.lastCodeSnippetId should_==("id2")
       }

       "add a codeSnippet if the id is not there already" in {
          val board = new CodingBoard("board")
          val aCodeSnippet = new CodeSnippet("id", "desc", "code", "lang", 0) 
          (board += aCodeSnippet).codeSnippets.size should_== 1
       }

       "not add a codeSnippet if resubmitted" in {
          val board = new CodingBoard("board")
          val aCodeSnippet = new CodeSnippet("id", "desc", "code", "lang", 0)
          val anotherCodeSnippet = new CodeSnippet("id", "desc", "code", "lang", 0)
          board += aCodeSnippet += anotherCodeSnippet
          board.codeSnippets.size should_==(1)
       }
  }



}
