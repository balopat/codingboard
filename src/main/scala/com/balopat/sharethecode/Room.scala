package com.balopat.sharethecode


case class Room (room: String) {

  var codeSnippets = List[CodeSnippet]()
  
  def += (codeSnippet : CodeSnippet): Room = {
    if (codeSnippets.filter( _.id.equals(codeSnippet.id)).isEmpty) 
      codeSnippets :+= codeSnippet
     this
  }

  override def toString = room
  
  def lastCodeSnippetId = if (codeSnippets.isEmpty)  "" else codeSnippets.last.id

}
