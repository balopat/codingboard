package com.balopat.codingboard

import org.scalatest._
import org.scalatest.selenium.Chrome

class CreateBoardPage extends FlatSpec with ShouldMatchers with Chrome {

  "The CreateBoard page " should "have the correct title" in { 
    go to ("http://localhost:8080/createboard")
    title should be ("CodingBoard - Create Board")
  }

  "The CreateBoard page " should "lead to the Test Room when submitted" in {
    go to ("http://localhost:8080/createboard") 
    textField ("board").value = "Test Board" 
    click on id("submit") 
    title should be ("CodingBoard - Test Board") 
  }

}
