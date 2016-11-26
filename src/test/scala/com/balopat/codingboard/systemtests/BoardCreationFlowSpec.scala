package com.balopat.codingboard.systemtests


import org.scalatest._
import concurrent.{AsyncAssertions, Eventually}
import org.scalatest.selenium.{Page, Chrome}
import org.scalatest.time.SpanSugar._

class BoardCreationFlowSpec extends FlatSpec with Matchers with Eventually with AsyncAssertions with Chrome {

  class HomePage extends Page {
    val url = "http://localhost:8080"
  }

  val homePage = new HomePage()

  def createBoard(boardName: String, isPrivate: Boolean = false){
    go to homePage
    click on "goto_create_board"
    textField("board").value = boardName
    textField("lengthOfSessionInMinutes").value = "2"
    if(isPrivate) checkbox("private").select()
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

  "The CreateBoard page " should "lead to the Test Board when submitted" in {
    createBoard("Test Board")
    pageTitle should be("CodingBoard - Test Board")
    currentUrl should endWith("/boards/test_board")
    find(new CssSelectorQuery("span#time-left")).get.text should (include("minutes") and include("seconds"))
  }

  "The CodingBoard homepage" should "display only the boards matching the filter expression" in {
    createBoard("New Board 1")
    createBoard("New Board 2")
    createBoard("Different Board")
    go to ("http://localhost:8080/")
    findAll(new CssSelectorQuery("table.table.table-hover.striped td.board-name")).length shouldBe 4
    textField("filter").value should be ("")
    textField("filter").value = "Different"
    eventually(timeout(1 seconds), interval(5 millis)) {
      findAll(new CssSelectorQuery("table.table.table-hover.striped tr.clickable[style='display: table-row;']")).length shouldBe 1
      find(new CssSelectorQuery("table.table.table-hover.striped tr.clickable[style='display: table-row;'] td.board-name")).get.text should be ("Different Board")
    }
    textField("filter").value = ""
    findAll(new CssSelectorQuery("table.table.table-hover.striped td.board-name")).length shouldBe 4
  }

  "The CreateBoard page " should "lead to the Test Board and show the private URL" in {
    createBoard("Private Board", true)
    find(new CssSelectorQuery("code")).get.text shouldBe "http://localhost:8080/boards/private_board"
  }

  "The CodingBoard contributors page" should "have the correct title and at least one contributor" in {
    go to ("http://localhost:8080/contributors")
    pageTitle should be("The CodingBoard Community")
    eventually(timeout(5 seconds), interval(50 millis)) {
      findAll(new CssSelectorQuery("div#contributors span")).length should be > (0)
    }
    close()
  }

}