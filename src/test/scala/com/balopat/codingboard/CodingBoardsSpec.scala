package com.balopat.codingboard

import org.specs2.mutable._

class CodingBoardsSpec extends Specification {
    

  "CodingBoards" should {
    val boards = new CodingBoards()

    "initially say that a board does not exist" in {
      boards.exists("non existent board") should beEqualTo(false)
    }

    "say the board exists after created" in {
      boards.create("testCodingBoard")
      boards.exists("testCodingBoard") should beEqualTo(true)
    }

    "can return a board after created" in {
      boards.create("testCodingBoard") 
      boards.get("testCodingBoard") must beAnInstanceOf[CodingBoard]
    }

    "returns the name of the boards" in {
      boards.create("testCodingBoard1")
      boards.create("testCodingBoard2")
      boards.create("testCodingBoard3")
      
      boards.list must contain ("testCodingBoard1", "testCodingBoard2", "testCodingBoard3")
    }

  }

}


