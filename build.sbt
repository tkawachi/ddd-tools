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
    // "-Xfatal-warnings",
    "-Xlint"
  ),
  libraryDependencies ++= Seq(
    "org.scalatest" %% "scalatest" % "3.0.3" % "test",
    "org.scalacheck" %% "scalacheck" % "1.13.4" % "test"
  ),
  addCompilerPlugin(
    "org.spire-math" % "kind-projector" % "0.9.3" cross CrossVersion.binary)
)

lazy val root = project
  .in(file("."))
  .settings(commonSettings: _*)
  .aggregate(core, example)

lazy val core = project
  .settings(
    commonSettings
      ++ Seq(
        name := "ddd-tools",
        description := "DDD tools",
        libraryDependencies ++= Seq(
          "org.typelevel" %% "cats" % "0.9.0"
        )
      ): _*)

lazy val example = project
  .settings(
    commonSettings ++ Seq(
      name := "ddd-tools-example",
      libraryDependencies ++= Seq(
        "org.skinny-framework" %% "skinny-orm" % "2.3.7",
        "com.h2database" % "h2" % "1.4.196",
        "ch.qos.logback" % "logback-classic" % "1.1.11")
    ): _*)
  .dependsOn(core)
