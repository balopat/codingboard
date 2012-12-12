package com.balopat.dojoshare
import com.ocpsoft.pretty.time.PrettyTime

case class CodeSnippet (description: String, code: String, language: String, timestamp: Long) {
   def formattedTime = new PrettyTime().format(new java.util.Date(timestamp))
}
