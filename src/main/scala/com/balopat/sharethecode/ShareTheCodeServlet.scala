package com.balopat.sharethecode

import org.scalatra._
import scalate.ScalateSupport
import scala.collection.immutable.List
import org.scalatra.atmosphere._
import org.scalatra.json.{JValueResult, JacksonJsonSupport}
import org.json4s._
import JsonDSL._
import org.atmosphere.cpr.MetaBroadcaster

class ShareTheCodeServlet extends ScalatraServlet
  with ScalateSupport with JValueResult 
  with JacksonJsonSupport with SessionSupport 
  {
 
implicit protected val jsonFormats: Formats = DefaultFormats

  def joinRoom(room: String, extraAttributes: (String, Any)*) = {
    contentType="text/html"
    if (Rooms.exists(room)) 
      jade("room", ("room" -> Rooms.get(room) :: extraAttributes.toList).toArray: _*  )
       else 
         jade("index", "rooms" -> Rooms.list, "errorMessage" -> "Room not found!")

 }

  get("/") {
    contentType="text/html"
    jade("index", "rooms" -> Rooms.list) 
  }

  post("/submitroom") {
    contentType="text/html"
    val room = params("room")
    if (!Rooms.exists(room)) { 
      Rooms.create(room)
      joinRoom(room)   
    }else {
      jade("createroom", "errorMessage" -> "A room with this name already exists!", "room" -> room)
    }
  }


  get("/rooms/:room") {
    joinRoom(params("room"))   
  }
  

  post("/rooms/:room/post") {
      val room = params("room") 
      val formToken = params("formtoken")
      if (Rooms.exists(room)) {  
        val codeSnippet = new CodeSnippet(formToken, params("description"), params("code"), params("language"), System.currentTimeMillis)
        Rooms.update(room, codeSnippet) 
        println("broadcast")
        joinRoom(room )
      } else {
        contentType="text/html"
        jade("index", "rooms" -> Rooms.list, "errorMessage" -> "Room not found!") 
      }
   }

  post("/rooms/:room/refresh") {
    val codeSnippets = Rooms.get(params("room")).codeSnippets
    if ( (!codeSnippets.isEmpty) && (!codeSnippets.last.id.equals(params("lastCodeSnippetId"))))
    {
        //TODO get all since the UUID, for now it's just the last
        compact(render(codeSnippets.last.toJSON))
    }else{
      compact(render("refresh"->"norefresh"))
    }  
  }
             
  get("/rooms/:room/codesnippet") {
    contentType="text/html"
    val room = params("room")    
    if (Rooms.exists(room)) { 
      jade("codesnippet",  "formtoken" -> java.util.UUID.randomUUID.toString,  "room" -> room) 
    } else {
      jade("index", "rooms" -> Rooms.list, "errorMessage" -> "Room not found!")
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
