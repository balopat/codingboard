package com.balopat.codingboard.functional

import org.scalatest._
import org.scalatest.selenium.Chrome

class CreateBoardFlowSpec extends FlatSpec with ShouldMatchers with Chrome {

  "Creating a board from the home page" should "display the created board and filter field" in {
    go to ("http://localhost:8080/")
    click on "goto_create_board"
    textField("board").value = "New Board"
    textField("lengthOfSessionInMinutes").value = "10"
    click on "submit"
    go to ("http://localhost:8080/")
    textField("filter").value should be ("")
  }

}