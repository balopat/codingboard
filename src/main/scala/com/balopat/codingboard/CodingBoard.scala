package com.balopat.codingboard


case class CodingBoard (board: String, lifeTimeInMinutes: Integer, creationTimeInMillis: Long) {

  var codeSnippets = List[CodeSnippet]()
  
  def += (codeSnippet : CodeSnippet): CodingBoard = {
    if (codeSnippets.filter( _.id.equals(codeSnippet.id)).isEmpty) 
      codeSnippets :+= codeSnippet
     this
  }

  override def toString = board
  
  def lastCodeSnippetId = if (codeSnippets.isEmpty)  "" else codeSnippets.last.id

  def isExpired(now: Long) = lifeTimeInMinutes * 60 < timeLeftInSeconds(now)

  def timeLeftInSeconds(now: Long = System.currentTimeMillis) = {
    (lifeTimeInMinutes * 60 ) -(now - creationTimeInMillis)/1000
  }
}
