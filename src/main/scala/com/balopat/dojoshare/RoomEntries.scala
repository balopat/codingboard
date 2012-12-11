package com.balopat.dojoshare 

import scala.collection.mutable.Map
import scala.collection.immutable.List

object RoomEntries {

  private val cache = Map[String, List[CodeSnippet]]() 

  def create(room:String) = {
    cache += (room -> List[CodeSnippet]())
  }

  def update(room: String, codeSnippet: CodeSnippet) = { 
     cache += (room -> ( 
                          cache.getOrElseUpdate(room, List[CodeSnippet]()) :+ codeSnippet
                        )
              ) 
     cache.get(room).get
  } 

  def get(room: String) =  cache(room) 
  def exists(room: String) = cache.contains(room)
  def rooms = cache.keys
}
