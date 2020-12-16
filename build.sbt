name := "public"

version := "0.1"

scalaVersion := "2.12.12"

val scalaTestVersion = "3.2.3"

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % scalaTestVersion % Test
)
