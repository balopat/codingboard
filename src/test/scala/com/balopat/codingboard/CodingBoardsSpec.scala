package com.balopat.codingboard

import org.specs2.mutable._
import java.util.concurrent.TimeUnit

class CodingBoardsSpec extends Specification {

  val fixture = new {
     val boards = new CodingBoards()
     val lengthOfSessionInMillis = 10000   
     val creationTimeInMillis = 1000l
     val isPrivate = false
  }


  "CodingBoards" should {

    "initially say that a board does not exist" in {
      fixture.boards.exists("non existent board") should beEqualTo(false)
    }

    "say the board exists after created" in {
      aTestCodingBoard("testCodingBoard1")
      fixture.boards.exists("testcodingboard1") should beEqualTo(true)
    }

    "can return a board after created" in {
      aTestCodingBoard("testCodingBoard2")
      fixture.boards.get("testcodingboard2") must beAnInstanceOf[CodingBoard]
    }

    "not return a board when removed" in {
      aTestCodingBoard("testCodingBoard3")
      fixture.boards.remove("testcodingboard3")
      fixture.boards.exists("testcodingboard3") should beEqualTo(false)
    }

    "returns the name of the boards" in {
      aTestCodingBoard("t1")
      aTestCodingBoard("t2")
      aTestCodingBoard("t3")
      
      fixture.boards.list.map(_.board) must contain ("t1", "t2", "t3")
    }
    
    "Private boards should not be included in 'list'" in {
      aTestCodingBoard("p1")
      aTestCodingBoard("p2",true)
      
      val boards = fixture.boards.list.map(_.board)
      
      boards must contain ("p1")
      boards must not contain ("p2")
    }
  
    "Private boards should available when ID is known" in {
      aTestCodingBoard("t2",true)
      
      fixture.boards.get("t2") must not(throwA[NoSuchElementException])
    }
  
    "not allow empty boardname" in {
      fixture.boards.validate("", "1") should beEqualTo(Seq("boardNameError"->"Board name cannot be empty"))    
    }

    "not allow already existing boardname" in {
      fixture.boards.validate("t1", "1") should beEqualTo(Seq("boardNameError"->"Board already exists"))    
    }


    "not allow empty input as session length" in {
      fixture.boards.validate("test", "") should beEqualTo(Seq("lengthOfSessionError" -> "Length of session cannot be empty"))
    }

    "only allow a number as session length" in {
      val invalidInput = "non-numeric"
      fixture.boards.validate("test", invalidInput) should beEqualTo(
            Seq("lengthOfSessionError" -> "Please provide an integer value for length of session!"))
    }

    "only allow session length <= 24 hours" in {
      fixture.boards.validate("test", TimeUnit.HOURS.toMinutes(24).toString) should beEqualTo(Seq())

      fixture.boards.validate("test", (TimeUnit.HOURS.toMinutes(24) + 1).toString) should beEqualTo(
        Seq("lengthOfSessionError" -> "Please provide a session length under 24 hours"))
    }


    "not return any error for correct values" in {
      fixture.boards.validate("some board", "1" ).isEmpty should beTrue
    }

    "remove the board after expiry" in {
      fixture.boards.create("expiring board", 100, 1000, false)
      Thread.sleep(101)
      fixture.boards.exists("expiring board") should beFalse
    }

    "coding boards instance should be available" in {
      CodingBoards.instance should not beNull
    }

    def aTestCodingBoard(name: String = "testingCodingBoard", isPrivate: Boolean = fixture.isPrivate)  = { 
        fixture.boards.create(name, fixture.lengthOfSessionInMillis, fixture.creationTimeInMillis, isPrivate)
    }

  }
}


