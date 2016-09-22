import io.gatling.sbt.GatlingPlugin

name := "gatlingTest"

version := "1.0"

scalaVersion := "2.11.8"

val root = (project in file("."))
  .enablePlugins(GatlingPlugin)


libraryDependencies ++= Seq(
  "io.gatling.highcharts" % "gatling-charts-highcharts" % "2.2.2" % "test",
  "io.gatling" % "gatling-test-framework" % "2.2.3-SNAPSHOT" % "test"
)