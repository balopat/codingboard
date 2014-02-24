import com.typesafe.sbt.SbtStartScript

seq(SbtStartScript.startScriptForClassesSettings: _*)

organization := "com.balopat"

name := "CodingBoard"

version := "0.1.0-SNAPSHOT"

scalaVersion := "2.9.2"

seq(webSettings :_*)

seq(ScctPlugin.instrumentSettings : _*)

classpathTypes ~= (_ + "orbit")

libraryDependencies ++= Seq(
"org.scalatra" % "scalatra-json" % "2.2.0-RC3",
"org.json4s"   %% "json4s-jackson" % "3.0.0",
"org.eclipse.jetty" % "jetty-websocket" % "8.1.7.v20120910" % "container",
"org.scalatra" % "scalatra" % "2.2.0-RC3",
"org.scalatra" % "scalatra-scalate" % "2.2.0-RC3",
"org.scalatra" % "scalatra-specs2" % "2.2.0-RC3" % "test",
"ch.qos.logback" % "logback-classic" % "1.0.6" % "runtime",
"org.eclipse.jetty" % "jetty-webapp" % "8.1.7.v20120910" % "compile;container",
"org.eclipse.jetty.orbit" % "javax.servlet" % "3.0.0.v201112011016" % "container;provided;test" artifacts (Artifact("javax.servlet", "jar", "jar")),
"org.fusesource.scalamd" % "scalamd" % "1.5",
"com.ocpsoft" % "ocpsoft-pretty-time" % "1.0.7",
"org.xmlmatchers" % "xml-matchers" % "0.10" % "test",
"org.seleniumhq.selenium" % "selenium-java" % "2.24.1" % "test",
"org.seleniumhq.selenium" % "selenium-firefox-driver" % "2.24.1" % "test",
"org.scalatest" %% "scalatest" % "2.0.M5" % "test",
  "com.samebug.notifier" % "samebug-notifier" % "1.0.0")


parallelExecution in Test := false

testOptions in PageTest := Seq(Tests.Filter(s => s.endsWith("FlowSpec")))

testOptions in Test := Seq(Tests.Filter(s => !s.endsWith("FlowSpec")))

resolvers += "Sonatype OSS Snapshots" at "http://oss.sonatype.org/content/repositories/snapshots/"
