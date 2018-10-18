import scala.sys.process
import scala.util.Try

import com.typesafe.sbt.web.Import._
import play.sbt.PlayScala
import sbt._
import sbt.Keys._

/**
  * Adds /version.txt into Play static assets
  */
object WebVersion extends AutoPlugin {

  override def requires: sbt.Plugins = PlayScala

  override lazy val projectSettings: Seq[Def.Setting[_]] = Seq(
    resourceGenerators in Assets += Def.task {
      val commit = Try(process.Process("git rev-parse HEAD").!!).toOption.map(rev => s"git-commit=$rev")
      val content = Seq(Some(s"version=${version.value}"), commit).flatten.mkString(" ")
      val file = WebKeys.webTarget.value / "version" / "version.txt"
      IO.write(file, content)
      Seq(file)
    }.taskValue,
    managedResourceDirectories in Assets += WebKeys.webTarget.value / "version"
  )

}
