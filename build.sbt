import sbt.Keys._
import sbt._
import sbtrelease.Version

name := "scala-serverless-api-dynamodb-example"

resolvers += Resolver.sonatypeRepo("public")
scalaVersion := "2.12.2"
releaseNextVersion := { ver => Version(ver).map(_.bumpMinor.string).getOrElse("Error") }
assemblyJarName in assembly := "scala_serverless_api_dynamodb_example.jar"

libraryDependencies ++= Seq(
  "com.amazonaws" % "aws-lambda-java-events" % "1.3.0",
  "com.amazonaws" % "aws-lambda-java-core" % "1.1.0",
  "com.gu" %% "scanamo" % "0.9.5",
  "org.json4s" %% "json4s-jackson" % "3.5.2"
)

scalacOptions ++= Seq(
  "-unchecked",
  "-deprecation",
  "-feature",
  "-Xfatal-warnings")
