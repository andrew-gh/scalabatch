import sbt._
import Keys._

object ScalaBatchBuild extends Build {

  val test = "org.scalatest" %% "scalatest" % "2.2.6"
  val restLib = "org.scalaj" %% "scalaj-http" % "2.3.0"

  val commonDependencies: Seq[ModuleID] = Seq(test)
  val restDependencies: Seq[ModuleID] = Seq(restLib)

  lazy val baseSettings = Seq(
    organization := "org.scalabatch",
    version := "0.1-SNAPSHOT",
    publishMavenStyle := true
  )

//  lazy val root = project.settings(
//    scalaVersion  := "2.10.6",
//    publishArtifact in (Compile, packageBin) := false,
//    publishArtifact in (Compile, packageDoc) := false,
//    publishArtifact in (Compile, packageSrc) := false
//  ).in(file(".")).aggregate(core, rest)

  lazy val root: Project = Project(
    id        = "root",
    base      = file("."),
    aggregate = Seq(core, rest),
    settings  = Defaults.coreDefaultSettings ++ Seq(
      packagedArtifacts := Map.empty           // prevent publishing anything!
    )
  )

//  lazy val core = project.settings(libraryDependencies ++= commonDependencies)

  lazy val core = Project(
    id = "core",
    base = file("scalabatch-core"),
    settings = baseSettings ++ Seq(
      name := "scalabatch-core",
      libraryDependencies ++= commonDependencies
    )
  )

  lazy val rest = Project(
    id = "rest",
    base = file("scalabatch-rest"),
    settings = baseSettings ++ Seq(
      name := "scalabatch-rest",
      libraryDependencies ++= {
        commonDependencies ++ restDependencies
      }
    )
  ) dependsOn(core)

//  lazy val rest = project.settings(libraryDependencies ++= commonDependencies).
//    settings(libraryDependencies ++= restDependencies).dependsOn(core)
  
  lazy val sample = Project(
    id = "sample",
    base = file("scalabatch-sample"),
    settings = baseSettings ++ Seq(
      name := "scalabatch-sample",
      libraryDependencies ++= {
        commonDependencies ++ restDependencies
      }
    )
  ) dependsOn(core, rest)
}