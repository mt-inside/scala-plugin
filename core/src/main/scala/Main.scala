package scalaPlugin.core

import java.nio.file.Paths

object Main extends App
{
  println("cwd " + Paths.get("").toAbsolutePath.toString)
  val ps = Loader("../plugins/target/scala-2.11/sampleplugin_2.11-1.0.jar")
  ps foreach { p => println(p.quack) }

  System.exit(0)
}
