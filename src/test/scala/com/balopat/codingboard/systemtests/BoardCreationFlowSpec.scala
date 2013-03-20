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
    textField("board").value = "Test Board"
    textField("lengthOfSessionInMinutes").value = "1"
    click on id("submit")
    pageTitle should be("CodingBoard - Test Board")
    go to homePage
    textField("filter").value should be ("")
    close()
  }
}
