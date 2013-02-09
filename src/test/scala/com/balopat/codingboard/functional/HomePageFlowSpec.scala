package com.balopat.codingboard.functional

import org.scalatest._
import org.scalatest.selenium.Chrome

class HomePageFlowSpec extends FlatSpec with ShouldMatchers with Chrome {

  "The CodingBoard homepage" should "have the correct title" in {
    go to ("http://localhost:8080/")
    pageTitle should be ("CodingBoard")
  }

  "The CodingBoard homepage" should "have a button to create board" in {
    go to ("http://localhost:8080/")
    click on id("goto_create_board")
    pageTitle should be ("CodingBoard - Create Board")
  }

}