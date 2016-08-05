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
    publishMavenStyle := true,
    publishTo := {
      val nexus = "https://oss.sonatype.org/"
      if (isSnapshot.value)
        Some("snapshots" at nexus + "content/repositories/snapshots")
      else
        Some("releases"  at nexus + "service/local/staging/deploy/maven2")
    },
    pomExtra := (
      <url>https://github.com/andrew-gh/scalabatch</url>
      )
  )

  lazy val root: Project = Project(
    id        = "root",
    base      = file("."),
    aggregate = Seq(core, rest),
    settings  = Defaults.coreDefaultSettings ++ Seq(
      packagedArtifacts := Map.empty
    )
  )

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

  (for {
    username <- Option(System.getenv().get("SONATYPE_USERNAME"))
    password <- Option(System.getenv().get("SONATYPE_PASSWORD"))
  } yield
    credentials += Credentials(
      "Sonatype Nexus Repository Manager",
      "oss.sonatype.org",
      username,
      password)
    ).getOrElse(credentials ++= Seq())

}