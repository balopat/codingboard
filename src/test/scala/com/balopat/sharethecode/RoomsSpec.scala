package com.balopat.sharethecode

import org.specs2.mutable._

class RoomsSpec extends Specification {
    
  "Rooms" should {
    "initially say that a room does not exist" in {
      Rooms.exists("non existent room") should beEqualTo(false)
    }

    "say the room exists after created" in {
      Rooms.create("testRoom")
      Rooms.exists("testRoom") should beEqualTo(true)
    }

    "can return a room after created" in {
      Rooms.create("testRoom") 
      Rooms.get("testRoom") must beAnInstanceOf[Room]
    }

    "returns the name of the rooms" in {
      Rooms.create("testRoom1")
      Rooms.create("testRoom2")
      Rooms.create("testRoom3")
      
      Rooms.list must contain ("testRoom1", "testRoom2", "testRoom3")
    }

  }

}


