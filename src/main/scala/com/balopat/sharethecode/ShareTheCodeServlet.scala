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
  with AtmosphereSupport {
 
implicit protected val jsonFormats: Formats = DefaultFormats

  def joinRoom(room: String, extraAttributes: (String, Any)*) = {
    contentType="text/html"
    if (RoomEntries.exists(room)) 
         jade("room", ("room" -> room ::  "entries" -> RoomEntries.get(room) :: extraAttributes.toList).toArray: _*  )
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
  

  post("/rooms/:room/post") {
      val room = params("room") 
      val formToken = params("formtoken")
      if (RoomEntries.exists(room)) {  
        val codeSnippet = new CodeSnippet(params("description"), params("code"), params("language"), System.currentTimeMillis)
        RoomEntries.update(room,formToken,codeSnippet) 
        println("broadcast")
//        MetaBroadcaster.getDefault().broadcastTo("*", compact(render( "codesnippetPosted" -> (codeSnippet.toJSON ~ ("formtoken"->formToken)))) )
        joinRoom(room, "lastPostUUId"->formToken)
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

  atmosphere("/roomgateway") {
      new AtmosphereClient {
            def receive = {
                            case Connected => println("someone connected")
                      }
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
