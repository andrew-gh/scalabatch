package org.scalabatch.reader

import java.io.File

import org.scalabatch.core.{JobContext, ReadTask}

import scala.io.Source._

class DirectoryReadTask(val path:String, val identifier:String,
                        val context:JobContext, val parentStep:String,
                        val regexp:String = ".*") extends ReadTask[String]{

  override def read(): List[String] = {
    listFiles(new File(path)).flatMap(file => fromFile(file.getCanonicalPath).getLines().toList)
  }


  def listFiles(f: File): List[File] = {
    val these = f.listFiles.toList
    these ++ these.filter(_.isDirectory).flatMap(listFiles)
  }

}
