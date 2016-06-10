import sbt._
import Keys._

object ScalaBatchBuild extends Build {

  val test = "org.scalatest" %% "scalatest" % "2.2.6"
  val restLib = "org.scalaj" %% "scalaj-http" % "2.3.0"

  val commonDependencies: Seq[ModuleID] = Seq(test)
  val restDependencies: Seq[ModuleID] = Seq(restLib)

  lazy val root = project.settings(
    scalaVersion  := "2.10.6"
  ).in(file(".")).aggregate(core, rest)

  lazy val core = project.settings(libraryDependencies ++= commonDependencies)

  lazy val rest = project.settings(libraryDependencies ++= commonDependencies).
    settings(libraryDependencies ++= restDependencies).dependsOn(core)
  
  lazy val sample = project.dependsOn(core, rest)
}