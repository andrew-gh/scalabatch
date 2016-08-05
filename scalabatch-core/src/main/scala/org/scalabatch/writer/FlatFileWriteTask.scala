package org.scalabatch.writer

import org.scalabatch.core.{JobContext, Step, WriteTask}
import java.io._

class FlatFileWriteTask(val path:String, val identifier:String, val contextKeys:Seq[String],
                        val context:JobContext, val parentStep:String) extends WriteTask{

  override def write(seq: List[_]): Unit = {
    val file = new File(path)
    val bw = new BufferedWriter(new FileWriter(file))
    seq.foreach(line=>{
      bw.write(line.toString)
      bw.newLine()
    })
    bw.close()
  }

}
