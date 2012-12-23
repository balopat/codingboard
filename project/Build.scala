import sbt._
import Keys._

object CodingBoardBuild extends Build
{
  lazy val root =
    Project("root", file("."))
      .configs( PageTest )
      .settings( inConfig( PageTest )(Defaults.testSettings) : _*)
      .settings( libraryDependencies += specs )

  lazy val PageTest = config("page") extend(Test)
  lazy val specs =  "org.scalatest" %% "scalatest" % "2.0.M5" % "page"
}
