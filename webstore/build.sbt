organization := "com.dwarszawski"
name := "webstore"
version := "0.0.1-SNAPSHOT"
scalaVersion := "2.12.3"

val Http4sVersion = "0.17.4"
val LogbackVersion = "1.2.3"
val ScalazVersion = "7.2.16"
val CirceGenericVersion = "0.6.1"


libraryDependencies ++= Seq(
  "org.http4s" %% "http4s-blaze-server" % Http4sVersion,
  "org.http4s" %% "http4s-circe" % Http4sVersion,
  "org.http4s" %% "http4s-dsl" % Http4sVersion,
  "io.circe" %% "circe-parser" % CirceGenericVersion,
  "io.circe" %% "circe-generic-extras" % CirceGenericVersion,
  "io.circe" %% "circe-generic" % CirceGenericVersion,
  "org.scalaz" %% "scalaz-core" % ScalazVersion,
  "ch.qos.logback" % "logback-classic" % LogbackVersion
)

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "3.0.4" % "test",
  "org.scalactic" %% "scalactic" % "3.0.4",
  "org.scalamock" %% "scalamock-scalatest-support" % "3.6.0" % "test",
  "org.http4s" %% "http4s-blaze-client" % Http4sVersion % "test"
)
