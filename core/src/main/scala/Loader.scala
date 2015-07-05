package scalaPlugin.core

import java.io.File
import java.net.{URL, URLClassLoader}

object Loader
{
  def apply( name : String, path : String ) : Plugin =
  {
    val cl = new URLClassLoader(
      Array[URL](new File(path).toURI.toURL),
      this.getClass.getClassLoader
    )
    val c = cl.loadClass(name)
    c.newInstance.asInstanceOf[Plugin]
  }
}

