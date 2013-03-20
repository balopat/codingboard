package com.balopat.codingboard.systemtests

import org.scalatest._
import org.scalatest.selenium.Chrome
import org.scalatest.time.SpanSugar._
import concurrent.{Eventually, AsyncAssertions}

class BoardFilteringFlowSpec extends FlatSpec with ShouldMatchers with Eventually with AsyncAssertions with Chrome {

  def createBoard(boardName: String){
    go to ("http://localhost:8080/")
    click on "goto_create_board"
    textField("board").value = boardName
    textField("lengthOfSessionInMinutes").value = "1"
    click on "submit"
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