package com.balopat.codingboard

import org.specs2.mutable._

class CodingBoardsSpec extends Specification {

  val fixture = new {
     val boards = new CodingBoards()
     val lifeTimeInMinutes = 1   
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
      
      fixture.boards.list must contain ("t1", "t2", "t3")
    }
    
     def aTestCodingBoard(name: String = "testingCodingBoard")  = { 
        fixture.boards.create(name, fixture.lifeTimeInMinutes, fixture.creationTimeInMillis)
     }

  }

}


