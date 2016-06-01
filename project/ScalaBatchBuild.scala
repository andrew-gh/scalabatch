import sbt._
import Keys._

object ScalaBatchBuild extends Build {
  
  lazy val root = project.in(file(".")).aggregate(core, rest)

  lazy val core = project

  lazy val rest = project.settings(
    libraryDependencies +=  "org.scalaj" %% "scalaj-http" % "2.3.0"
  ).dependsOn(core)
  
  lazy val sample = project.dependsOn(core, rest)
}