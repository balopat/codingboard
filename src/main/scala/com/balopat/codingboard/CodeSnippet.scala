package com.balopat.codingboard

import com.ocpsoft.pretty.time.PrettyTime

import org.json4s.jackson.JsonMethods._
import org.json4s.JsonDSL._

case class CodeSnippet(id: String, description: String, code: String, language: String, timestamp: Long) {

  def formattedTime = new PrettyTime().format(new java.util.Date(timestamp))

  def toJSON = ("codeSnippet" ->
    ("id" -> id) ~
      ("description" -> description) ~
      ("code" -> code) ~
      ("language" -> language) ~
      ("timestamp" -> formattedTime)
    )
}
