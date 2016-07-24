package org.scalabatch

import org.scalabatch.core.JobBuilder


object Launcher {

  def main(args: Array[String]) {
    println("Hi")
    val b = new JobBuilder("1")
//    val job = b.addStep("1").addTask("1","rest", "C:\\garbage\\sample").
//      addTask("2","FlatFileWriteTask", "C:\\garbage\\out.txt").build()
//    job.execute()
  }

}
