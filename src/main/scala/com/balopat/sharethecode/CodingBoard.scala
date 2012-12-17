package com.balopat.sharethecode


case class CodingBoard (board: String) {

  var codeSnippets = List[CodeSnippet]()
  
  def += (codeSnippet : CodeSnippet): CodingBoard = {
    if (codeSnippets.filter( _.id.equals(codeSnippet.id)).isEmpty) 
      codeSnippets :+= codeSnippet
     this
  }

  override def toString = board
  
  def lastCodeSnippetId = if (codeSnippets.isEmpty)  "" else codeSnippets.last.id

}
