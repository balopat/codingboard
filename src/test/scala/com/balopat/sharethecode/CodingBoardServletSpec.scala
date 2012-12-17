package com.balopat.sharethecode

import org.scalatra.test.specs2._
import org.eclipse.jetty.servlet.ServletHolder
import scala.xml._
import org.xmlmatchers.transform.XmlConverters._ 

class CodingBoardServletSpec extends ScalatraSpec { def is =
  "CodingBoardServlet on GET / "                     ^
    "should return status 200"                ! root200 ^
    "should contain a link to createboard"     ! rootlinkToCreateCodingBoard ^
    "should go to root page with error " +
    "if board not found"                       ! notExistentCodingBoard ^
    "should go to root page with error " +
    "if board not found"                       ! notExistentCodingBoardCodeSnippet ^
    "should go to root page with error " +
    "if codesnippet posted against " + 
    "non-existing board"                       ! postingCodeSnippetOnNonExistentCodingBoard ^
                                                end

  addServlet(new CodingBoardServlet(), "/*")

  def root200 = get("/") {
    status must_== 200
  }

  def rootlinkToCreateCodingBoard = get("/") {
    body should not contain ("href=\"/createboard\"")
  }

  def notExistentCodingBoard = get("/boards/notexistent") {
    status must_== 200 
    body should contain("CodingBoard not found")
  }

  def notExistentCodingBoardCodeSnippet = get("/boards/notexistent/codesnippet") {
    status must_== 200 
    body should contain("CodingBoard not found")
  }

  def postingCodeSnippetOnNonExistentCodingBoard = post("/boards/notexistent/post", Map("formtoken"->"atoken")) {
    status must_== 200 
    body should contain("CodingBoard not found")
  } 
}
