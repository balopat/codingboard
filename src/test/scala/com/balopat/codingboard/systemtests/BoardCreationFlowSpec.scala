package com.balopat.codingboard.systemtests

import org.scalatest._
import org.scalatest.selenium.Chrome

class BoardCreationFlowSpec extends FlatSpec with ShouldMatchers with Chrome {

  class HomePage extends Page {
    val url = "http://localhost:8080"
  }

  val homePage = new HomePage()

  "The CodingBoard homepage" should "have the correct title" in {
    go to homePage
    title should be("CodingBoard")
  }

  "The CodingBoard homepage" should "have a button to create board" in {
    click on id("goto_create_board")
    title should be("CodingBoard - Create Board")
  }

  "The CreateBoard page " should "have the correct title" in {
    go to ("http://localhost:8080/createboard")
    title should be("CodingBoard - Create Board")
  }

  "The CreateBoard page " should "lead to the Test Board when submitted" in {
    textField("board").value = "Test Board"
    textField("lengthOfSessionInMinutes").value = "2"
    click on id("submit")
    title should be("CodingBoard - Test Board")
    close()
  }
}
