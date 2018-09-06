import sbt.Keys._
import sbt._

import scala.sys.process._

object NpmBuild extends AutoPlugin {

  object autoImport {
    lazy val npmBuild = taskKey[Unit]("Execute frontend scripts")
  }

  import autoImport._

  override lazy val projectSettings: Seq[Def.Setting[_]] = Seq(
    npmBuild := {
      val s: TaskStreams = streams.value
      val shell: Seq[String] = Seq("bash", "-c")
      val npmInstall: Seq[String] = shell :+ "npm install"
      val npmBuild: Seq[String] = shell :+ "npm run build"
      s.log.info("building frontend...")
      if ((npmInstall #&& npmBuild !) == 0) {
        s.log.success("frontend build successful!")
      } else {
        throw new IllegalStateException("frontend build failed!")
      }
    }
  )

}
