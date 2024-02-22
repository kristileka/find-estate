name := "platform"
ThisBuild / scalaVersion := "2.13.13"

ThisBuild / version := "0.0"

PlayKeys.devSettings := Seq("play.server.http.port" -> "5001")


lazy val core = ProjectRef(file("../core"), "core")

lazy val root = (project in file("."))
  .enablePlugins(PlayScala)
  .aggregate(core)
  .dependsOn(core)
  .settings(
    name := """platform""",
    libraryDependencies ++= Seq(
      guice,
      "org.scalatestplus.play" %% "scalatestplus-play" % "5.1.0" % Test
    )
  )
