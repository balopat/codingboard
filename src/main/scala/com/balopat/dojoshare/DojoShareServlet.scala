package com.balopat.dojoshare

import org.scalatra._
import scalate.ScalateSupport
import com.balopat.dojoshare.RoomEntries.get

class DojoShareServlet extends ScalatraServlet with ScalateSupport {



  get("/") {
    contentType="text/html"
    jade("index")
  }

  post("/submitroom") {
    <html>
      <body>
          Variables: {params("room")}
        </body>
      </html>
  }

  get("/rooms/:room") {

    val room = params("room")   
    contentType="text/html"
    jade("room", "counter" -> RoomEntries.get(room), "room" -> room) 
    
  }


  notFound {
    // remove content type in case it was set through an action
    contentType = null
    // Try to render a ScalateTemplate if no route matched
    findTemplate(requestPath) map { path =>
      contentType = "text/html"
      layoutTemplate(path)
    } orElse serveStaticResource() getOrElse resourceNotFound()
  }
}
