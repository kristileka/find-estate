lazy val commonSettings = Seq(
  organization := "com.twitter.clone",
  scalaVersion := "2.13.13"
)

lazy val core = project
  .in(file("."))
  .settings(commonSettings)
  .settings(
    name := "core",
    libraryDependencies := Seq(
      "com.typesafe.slick" %% "slick"       % "3.4.1",
      "org.postgresql"     % "postgresql"   % "42.7.1",
      "org.flywaydb"       %% "flyway-play" % "9.0.0"
    )
  )
