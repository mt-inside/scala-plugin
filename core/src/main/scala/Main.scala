package scalaPlugin.core

import java.nio.file.Paths

object Main extends App
{
  println(Paths.get("").toAbsolutePath.toString)
  val p = Loader("scalaPlugin.plugins.SamplePlugin", "../plugins/target/scala-2.11/sampleplugin_2.11-1.0.jar")
  println(p.quack)

  System.exit(0)
}
