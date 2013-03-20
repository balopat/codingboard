package com.balopat.codingboard.systemtests

import org.scalatest._
import concurrent.{AsyncAssertions, Eventually}
import org.scalatest.selenium.Chrome
import org.scalatest.time.SpanSugar._

class BoardCreationFlowSpec extends FlatSpec with ShouldMatchers with Eventually with AsyncAssertions with Chrome {

  class HomePage extends Page {
    val url = "http://localhost:8080"
  }

  val homePage = new HomePage()

  def createBoard(boardName: String){
    go to homePage
    click on "goto_create_board"
    textField("board").value = boardName
    textField("lengthOfSessionInMinutes").value = "1"
    click on "submit"
  }

  "The CodingBoard homepage" should "have the correct title" in {
    go to homePage
    pageTitle should be("CodingBoard")
  }

  "The CodingBoard homepage" should "have a button to create board" in {
    click on id("goto_create_board")
    pageTitle should be("CodingBoard - Create Board")
  }

  "The CreateBoard page " should "have the correct title" in {
    go to ("http://localhost:8080/createboard")
    pageTitle should be("CodingBoard - Create Board")
  }

  "The CreateBoard page " should "lead to the Test Board when submitted and have a filter field" in {
    createBoard("Test Board")
    pageTitle should be("CodingBoard - Test Board")
    go to homePage
    textField("filter").value should be ("")
  }

  "The CodingBoard homepage" should "display only the boards matching the filter expression" in {
    createBoard("New Board 1")
    createBoard("New Board 2")
    createBoard("Different Board")
    go to ("http://localhost:8080/")
    findAll(new CssSelectorQuery("table.table.table-hover.striped td.board-name")).length shouldBe 4
    textField("filter").value = "Different"
    eventually(timeout(1 seconds), interval(5 millis)) {
      findAll(new CssSelectorQuery("table.table.table-hover.striped tr.clickable[style='display: table-row;']")).length shouldBe 1
      find(new CssSelectorQuery("table.table.table-hover.striped tr.clickable[style='display: table-row;'] td.board-name")).get.text should be ("Different Board")
    }
    textField("filter").value = ""
    findAll(new CssSelectorQuery("table.table.table-hover.striped td.board-name")).length shouldBe 4
    close()
  }

}