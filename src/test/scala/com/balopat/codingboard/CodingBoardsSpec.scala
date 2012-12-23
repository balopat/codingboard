package com.balopat.codingboard

import org.specs2.mutable._

class CodingBoardsSpec extends Specification {

  val fixture = new {
     val boards = new CodingBoards()
     val lengthOfSessionInMinutes = 1   
     val creationTimeInMillis: Long = 1000
  } 


  "CodingBoards" should {

    "initially say that a board does not exist" in {
      fixture.boards.exists("non existent board") should beEqualTo(false)
    }

    "say the board exists after created" in {
      aTestCodingBoard("testCodingBoard")
      fixture.boards.exists("testCodingBoard") should beEqualTo(true)
    }

    "can return a board after created" in {
      aTestCodingBoard("testCodingBoard")
      fixture.boards.get("testCodingBoard") must beAnInstanceOf[CodingBoard]
    }

    "returns the name of the boards" in {
      aTestCodingBoard("t1")
      aTestCodingBoard("t2")
      aTestCodingBoard("t3")
      
      fixture.boards.list.map(_.board) must contain ("t1", "t2", "t3")
    }
  
    "not allow empty boardname" in {
      fixture.boards.validate("", "1") should beEqualTo("boardNameError"->"Board name cannot be empty", "lengthOfSessionError" -> "")    
    }
    "not allow empty lengthOfSession" in {
      fixture.boards.validate("test", "") should beEqualTo("boardNameError"->"", "lengthOfSessionError" -> "Length of session cannot be empty")    
    }


     def aTestCodingBoard(name: String = "testingCodingBoard")  = { 
        fixture.boards.create(name, fixture.lengthOfSessionInMinutes, fixture.creationTimeInMillis)
     }

     def cleanUpBoards() = {
       fixture.boards.list.foreach ( b => {
         fixture.boards.remove(b.board)
       })
     }

  }

}


