import NativePackagerHelper._
import NpmBuild.autoImport.npmBuild

name := """webapp"""
organization := "com.example"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala, NpmBuild)

scalaVersion := "2.12.6"

libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.2" % Test

mappings in Assets ++= contentOf("client/dist")

(packageBin in Universal) := (packageBin in Universal).dependsOn(npmBuild).value
