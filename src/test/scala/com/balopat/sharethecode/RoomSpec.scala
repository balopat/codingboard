package com.balopat.sharethecode

import org.specs2.mutable._

class RoomSpec extends Specification {

  "A Room" should {
       "return it's name in toString" in {
          new Room("room name").toString should_==("room name")
       }

       "return empty String for "+ 
       "the lastCodeSnippet id if there are no codesnippets" in {
          new Room("room").lastCodeSnippetId should_==("")
       }

       "return the last codeSnippet id" in {
          val room = new Room("room")
          val aCodeSnippet = new CodeSnippet("id", "desc", "code", "lang", 0)
          val anotherCodeSnippet = new CodeSnippet("id2", "desc", "code", "lang", 0)
          room += aCodeSnippet += anotherCodeSnippet
          room.lastCodeSnippetId should_==("id2")
       }

       "add a codeSnippet if the id is not there already" in {
          val room = new Room("room")
          val aCodeSnippet = new CodeSnippet("id", "desc", "code", "lang", 0) 
          (room += aCodeSnippet).codeSnippets.size should_== 1
       }

       "not add a codeSnippet if resubmitted" in {
          val room = new Room("room")
          val aCodeSnippet = new CodeSnippet("id", "desc", "code", "lang", 0)
          val anotherCodeSnippet = new CodeSnippet("id", "desc", "code", "lang", 0)
          room += aCodeSnippet += anotherCodeSnippet
          room.codeSnippets.size should_==(1)
       }
  }



}
