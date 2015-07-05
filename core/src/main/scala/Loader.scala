package scalaPlugin.core

import java.io.File
import java.net.{URL, URLClassLoader}
import java.util.jar._

import scala.collection.JavaConversions._

object Loader
{
  def apply( path : String ) = classesInJar( path ).map( instantiate )

  def classesInJar( path : String ) =
  {
    println( s"searching jar at $path" )

    val cl = new URLClassLoader(
      Array[URL](new File(path).toURI.toURL),
      this.getClass.getClassLoader
    )

    def tryLoadClass( fileName : String ) =
    {
      val className = fileName.replace(".class", "").replace("/", ".")
      println(s"  trying jar entry $fileName (class name $className)")
      val c = cl.loadClass(className)
      if( isPlugin(c) )
      {
        println(s"    class $className has correct interface")
        Some(c.asInstanceOf[Class[Plugin]])
      }
      else
      {
        println(s"    class $className does not have interface")
        None
      }
    }

    def isPlugin( c : Class[_] ) =
      // Ideally c.isInstanceOf[Class[Plugin]] could be used, but the type
      // parameter of Class isn't available at run-time due to the JVM's type
      // erasue
      c.getInterfaces.exists(ci => ci == classOf[Plugin])

    (new JarFile(path))
      .entries //This is a java.util.Enumerator, implicit conversion to scala Iterator ftw
      .map( _.getName )
      .filter( _.endsWith(".class") )
      .collect( Function.unlift(tryLoadClass) )
  }

  def instantiate( c : Class[Plugin] ) : Plugin =
    c.newInstance.asInstanceOf[Plugin]
}
