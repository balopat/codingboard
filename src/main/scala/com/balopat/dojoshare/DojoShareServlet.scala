package com.balopat.dojoshare

import org.scalatra._
import scalate.ScalateSupport
import scala.collection.immutable.List

class DojoShareServlet extends ScalatraServlet with ScalateSupport {
  
  def joinRoom(room: String) = {
    contentType="text/html"
    if (RoomEntries.exists(room)) 
         jade("room", "entries" -> RoomEntries.get(room),  "room" -> room)
       else 
         jade("index", "rooms" -> RoomEntries.rooms, "errorMessage" -> "Room not found!")

 }

  get("/") {
    contentType="text/html"
    jade("index", "rooms" -> RoomEntries.rooms) 
  }

  post("/submitroom") {
    contentType="text/html"
    val room = params("room")
    if (!RoomEntries.exists(room)) { 
      RoomEntries.create(room)
      joinRoom(room)   
    }else {
      jade("createroom", "errorMessage" -> "A room with this name already exists!", "room" -> room)
    }
  }


  get("/rooms/:room") {
    joinRoom(params("room"))   
  }
  

  post("/rooms/:room") {
      val room = params("room") 
      if (RoomEntries.exists(room)) {  
        RoomEntries.update(room, params("formtoken"),new CodeSnippet(params("description"), params("code"), params("language"), System.currentTimeMillis)) 
        joinRoom(room)
      } else {
        contentType="text/html"
        jade("index", "rooms" -> RoomEntries.rooms, "errorMessage" -> "Room not found!") 
      }
   }

             
  get("/rooms/:room/codesnippet") {
    contentType="text/html"
    val room = params("room")    
    if (RoomEntries.exists(room)) { 
      jade("codesnippet",  "formtoken" -> java.util.UUID.randomUUID.toString,  "room" -> room) 
    } else {
      jade("index", "rooms" -> RoomEntries.rooms, "errorMessage" -> "Room not found!")
    }
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
