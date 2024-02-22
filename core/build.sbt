lazy val commonSettings = Seq(
  organization := "com.twitter.clone",
  scalaVersion := "2.13.13"
)

lazy val core = project
  .in(file("."))
  .settings(commonSettings)
  .settings(
    name := "core",
  )
