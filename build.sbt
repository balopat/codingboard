import com.typesafe.sbt.SbtStartScript

seq(SbtStartScript.startScriptForClassesSettings: _*)

organization := "com.balopat"

name := "Dojo Share"

version := "0.1.0-SNAPSHOT"

scalaVersion := "2.9.2"

seq(webSettings :_*)

classpathTypes ~= (_ + "orbit")

libraryDependencies ++= Seq(
 "org.scalatra" % "scalatra-atmosphere" % "2.2.0-SNAPSHOT",
 "org.scalatra" % "scalatra-json" % "2.2.0-SNAPSHOT",
 "org.json4s"   %% "json4s-jackson" % "3.0.0",
 "org.eclipse.jetty" % "jetty-websocket" % "8.1.7.v20120910" % "container",
  "org.scalatra" % "scalatra" % "2.2.0-SNAPSHOT",
  "org.scalatra" % "scalatra-scalate" % "2.2.0-SNAPSHOT",
  "org.scalatra" % "scalatra-specs2" % "2.2.0-SNAPSHOT" % "test",
  "ch.qos.logback" % "logback-classic" % "1.0.6" % "runtime",
  "org.eclipse.jetty" % "jetty-webapp" % "8.1.7.v20120910" % "compile;container",
  "org.eclipse.jetty.orbit" % "javax.servlet" % "3.0.0.v201112011016" % "container;provided;test" artifacts (Artifact("javax.servlet",
  "jar", "jar")),
  "org.fusesource.scalamd" % "scalamd" % "1.5",
  "com.ocpsoft" % "ocpsoft-pretty-time" % "1.0.7")

resolvers += "Sonatype OSS Snapshots" at "http://oss.sonatype.org/content/repositories/snapshots/"
