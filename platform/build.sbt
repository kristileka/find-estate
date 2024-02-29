import com.github.tototoshi.sbt.slick.CodegenPlugin.autoImport.{
  slickCodegenDatabasePassword,
  slickCodegenDatabaseUrl,
  slickCodegenJdbcDriver
}
import _root_.slick.codegen.SourceCodeGenerator
import _root_.slick.{ model => m }

lazy val databaseUrl      = sys.env.getOrElse("DB_DEFAULT_URL", "jdbc:postgresql://localhost:5431/find-estate")
lazy val databaseUser     = sys.env.getOrElse("DB_DEFAULT_USER", "find-estate-user")
lazy val databasePassword = sys.env.getOrElse("DB_DEFAULT_PASSWORD", "find-estate-password")

val FlywayVersion = "10.6.0"

(ThisBuild / version) := "1.1-SNAPSHOT"

(ThisBuild / libraryDependencies) ++= Seq(
  guice
)

(ThisBuild / crossScalaVersions) := Seq("2.13.12", "3.3.1")
(ThisBuild / scalaVersion) := crossScalaVersions.value.head
(ThisBuild / scalacOptions) ++= Seq(
  "-encoding",
  "UTF-8",
  "-deprecation",
  "-feature",
  "-unchecked",
  "-Xlint",
  "-Ywarn-numeric-widen"
)

lazy val flyway = (project in file("modules/flyway"))
  .enablePlugins(FlywayPlugin)
  .settings(
    libraryDependencies += "org.flywaydb" % "flyway-core" % FlywayVersion,
    flywayLocations := Seq("classpath:db/migration"),
    flywayUrl := databaseUrl,
    flywayUser := databaseUser,
    flywayPassword := databasePassword,
    flywayBaselineOnMigrate := true,
  )

lazy val api = (project in file("modules/api"))

lazy val postgres = (project in file("modules/postgres"))
  .enablePlugins(CodegenPlugin)
  .settings(
    libraryDependencies ++= Seq(
      "com.typesafe.slick" %% "slick" % "3.4.1",
      "com.typesafe.slick" %% "slick-hikaricp" % "3.4.1",
      "org.postgresql" % "postgresql" % "42.7.1"
    ),
    slickCodegenDatabaseUrl := databaseUrl,
    slickCodegenDatabaseUser := databaseUser,
    slickCodegenDatabasePassword := databasePassword,
    slickCodegenJdbcDriver := "org.postgresql.Driver",
    slickCodegenDriver := _root_.slick.jdbc.PostgresProfile,
    slickCodegenOutputPackage := "com.find.estate.postgres",
    slickCodegenExcludedTables := Seq("schema_version"),
    slickCodegenCodeGenerator := { (model: m.Model) =>
      new SourceCodeGenerator(model) {
        override def Table = new Table(_) {
          override def Column = new Column(_) {
            override def rawType = this.model.tpe match {
              case "java.sql.Timestamp" => "java.time.LocalDateTime" // kill j.s.Timestamp
              case _ =>
                super.rawType
            }
          }
        }
      }
    },
    (Compile / sourceGenerators) += slickCodegen.taskValue
  )
  .aggregate(api)
  .dependsOn(api)

lazy val root = (project in file("."))
  .enablePlugins(PlayScala)
  .settings(
    name := """find-estate""",
    TwirlKeys.templateImports += "com.find.estate.customer.Customer",
    libraryDependencies ++= Seq(
      guice,
      ws                       % Test,
      "org.flywaydb"           % "flyway-core" % FlywayVersion % Test,
      "org.scalatestplus.play" %% "scalatestplus-play" % "7.0.1" % Test,
      "org.postgresql" % "postgresql" % "42.7.1"
    ),
    (Test / fork) := true
  )
  .aggregate(postgres)
  .dependsOn(postgres)
