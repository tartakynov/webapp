import NpmBuild.autoImport._
import com.typesafe.sbt.SbtNativePackager.autoImport.NativePackagerHelper._

enablePlugins(PlayScala, NpmBuild)

scalaVersion := "2.12.6"

name := "webapp"

organization := "com.example"

// Library dependencies
libraryDependencies ++= Seq(
  guice
)

// Test dependencies
libraryDependencies ++= Seq(
  "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.2"
).map(_ % Test)

mappings in Assets ++= contentOf("client/dist")

(packageBin in Universal) := (packageBin in Universal).dependsOn(npmBuild).value
