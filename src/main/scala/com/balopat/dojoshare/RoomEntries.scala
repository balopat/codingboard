package com.balopat.dojoshare 

import scala.collection.mutable.Map

object RoomEntries {

   private val cache = Map[String, Int]() 

   def get(room: String): Int = { 
     cache += (room -> (1  + cache.getOrElseUpdate(room, 0))) 
     cache.get(room).get
} 
   
}
