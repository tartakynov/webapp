import sbt.Keys._
import sbt._

import scala.sys.process._

object NpmBuild extends AutoPlugin {

  object autoImport {
    lazy val npmClean = taskKey[Unit]("Clean client dist")

    lazy val npmBuild = taskKey[Unit]("Build client")
  }

  import autoImport._


  val shell: Seq[String] = Seq("bash", "-c")

  override lazy val projectSettings: Seq[Def.Setting[_]] = Seq(
    npmClean := {
      (shell :+ "npm run clean") !
    },
    npmBuild := {
      val s = streams.value
      val npmRunInstall = shell :+ "npm install"
      val npmRunBuild = shell :+ "npm run build"
      s.log.info("building client...")
      if ((npmRunInstall #&& npmRunBuild !) == 0) {
        s.log.success("client built successfully!")
      } else {
        throw new IllegalStateException("client build failed!")
      }
    }
  )

}
