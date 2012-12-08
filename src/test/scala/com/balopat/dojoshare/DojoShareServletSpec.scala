package com.balopat.dojoshare

import org.scalatra.test.specs2._

// For more on Specs2, see http://etorreborre.github.com/specs2/guide/org.specs2.guide.QuickStart.html
class DojoShareServletSpec extends ScalatraSpec { def is =
  "GET / on DojoShareServlet"                     ^
    "should return status 200"                  ! root200 ^
    "should return status 200"                  ! createroom200 ^
                                                end

  addServlet(classOf[DojoShareServlet], "/*")

  def root200 = get("/") {
    status must_== 200
  }


  def createroom200 = get("/createroom") {
    status must_== 200
  }

}
