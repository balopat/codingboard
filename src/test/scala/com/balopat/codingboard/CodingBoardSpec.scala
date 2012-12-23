package com.balopat.codingboard

import org.specs2.mutable._

class CodingBoardSpec extends Specification {

  "A CodingBoard" should {
       "return it's name in toString" in {
         aBoard.toString should_==("board")
       }

       "return empty String for "+ 
       "the lastCodeSnippet id if there are no codesnippets" in {
          aBoard.lastCodeSnippetId should_==("")
       }

       "return the last codeSnippet id" in {
          val board = aBoard
          val aCodeSnippet = new CodeSnippet("id", "desc", "code", "lang", 0)
          val anotherCodeSnippet = new CodeSnippet("id2", "desc", "code", "lang", 0)
          (board += 
            aCodeSnippet += 
            anotherCodeSnippet).lastCodeSnippetId should_==("id2")
       }

       "add a codeSnippet if the id is not there already" in {
          val board = aBoard
          val aCodeSnippet = new CodeSnippet("id", "desc", "code", "lang", 0) 
          (board += aCodeSnippet).codeSnippets.size should_== 1
       }

       "not add a codeSnippet if resubmitted" in {
          val board = aBoard 
          val aCodeSnippet = new CodeSnippet("id", "desc", "code", "lang", 0)
          val anotherCodeSnippet = new CodeSnippet("id", "desc", "code", "lang", 0)
          board += aCodeSnippet += anotherCodeSnippet
          board.codeSnippets.size should_==(1)
       }

      "says 300 seconds left when lifeTimeInMillis + creationTimeInMillis - now = 300000" in {
          aBoard.timeLeftInSeconds(201000) should beEqualTo(300)
      }

       def aBoard() = {
         val lifeTimeInMinutes =  500000
         val creationTimeInMillis = 1000
         new CodingBoard("board", lifeTimeInMinutes, creationTimeInMillis)
       }


  }



}
