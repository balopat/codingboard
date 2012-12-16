package com.balopat.sharethecode

import scala.collection.mutable.Map
import scala.collection.immutable.List


object Rooms {

  private val rooms = Map[String, Room]()
  private var formTokens = scala.collection.mutable.Seq[String]()

  def create(room:String) = {
    rooms += (room -> new Room(room))
  }

  def update(room: String,  codeSnippet: CodeSnippet) = {
     if (!formTokens.contains(codeSnippet.id)) {
       formTokens :+= codeSnippet.id
       rooms += (room -> (
                          rooms.getOrElseUpdate(room, new Room(room)) + codeSnippet
                        )
              )
     }
     rooms.get(room).get

  }

  def get(room: String) =  rooms(room)
  def exists(room: String) = rooms.contains(room)
  def list = rooms.keys
}


case class Room (room: String) {

  var codeSnippets = List[CodeSnippet]()
  def + (codeSnippet : CodeSnippet): Room = {
     codeSnippets :+= codeSnippet
     this
  }
  override def toString = room
  def lastCodeSnippetId = if (codeSnippets.isEmpty)  "" else codeSnippets.last.id
}
