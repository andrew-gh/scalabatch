package org.scalabatch.reader
import scala.io.Source._

import org.scalabatch.core.{JobContext, ReadTask}

class FlatFileReadTask(val path:String, val identifier:String, val context:JobContext,
                       val parentStep:String) extends ReadTask[String]{

  override def read() = {
    fromFile(path).getLines().toList
  }

}
