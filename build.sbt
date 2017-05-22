val commonSettings = Seq(
  organization := "com.github.tkawachi",
  licenses := Seq("MIT" -> url("http://opensource.org/licenses/MIT")),
  scmInfo := Some(
    ScmInfo(
      url("https://github.com/tkawachi/ddd-tools/"),
      "scm:git:github.com:tkawachi/ddd-tools.git"
    )),
  scalaVersion := "2.12.2",
  crossScalaVersions := Seq("2.11.11", "2.12.2"),
  scalacOptions ++= Seq(
    "-deprecation",
    "-encoding",
    "UTF-8",
    "-feature",
    "-unchecked",
    "-Xfatal-warnings",
    "-Xlint"
  ),
  addCompilerPlugin(
    "org.spire-math" % "kind-projector" % "0.9.3" cross CrossVersion.binary)
)

lazy val root = project
  .in(file("."))
  .settings(commonSettings: _*)
  .settings(
    name := "ddd-tools",
    description := "DDD tools",
    libraryDependencies ++= Seq(
      "org.typelevel" %% "cats" % "0.9.0",
      "org.scalatest" %% "scalatest" % "3.0.3" % "test",
      "org.scalacheck" %% "scalacheck" % "1.13.4" % "test"
    )
  )
