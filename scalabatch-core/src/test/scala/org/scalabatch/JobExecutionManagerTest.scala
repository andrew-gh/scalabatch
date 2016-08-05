package org.scalabatch

import java.io.File

import org.scalabatch.core.JobBuilder
import org.scalatest.FunSuite

class JobExecutionManagerTest extends FunSuite {

  var marker: List[String] = Nil

  test(s"${getClass.getSimpleName}:jobListener") {
    new JobBuilder("1").addStep("1").
      addJobStartListener(s=>{
        marker::="s"
      }).
      addJobEndListener(s=>{
        marker::="f"
      }).build().executeJob()
    assert(marker.length==2)
    assert(marker.contains("s"))
    assert(marker.contains("f"))
  }

  test(s"${getClass.getSimpleName}:readWriteCount") {
    val input = getClass.getResource("/input1.txt").getPath
    val output = input.replace("input1.txt", "output1.txt")
    val job = new JobBuilder("1").addStep("1").
      addTask("FlatFileReadTask", Array(input)).
      addTask("FlatFileWriteTask", Array(output)).build()
    job.executeJob()
    assert(job.context.stat.get("1").get.get("1").get.readCount==2)
    assert(job.context.stat.get("1").get.get("2").get.writeCount==2)
    new File(output).delete()
  }

  test(s"${getClass.getSimpleName}:time") {
    val input = getClass.getResource("/input_large.txt").getPath
    val output = input.replace("input1.txt", "output_time.txt")
    val job = new JobBuilder("1").addStep("1").
      addTask("FlatFileReadTask", Array(input)).
      addTask("FlatFileWriteTask", Array(output)).build()
    job.executeJob()
    val statRead = job.context.stat.get("1").get.get("1").get
    val statWrite = job.context.stat.get("1").get.get("2").get
    println(s"start=${statRead.startTime}, end=${statWrite.endTime}")
    assert(statRead.startTime>0)
    assert(statWrite.endTime >0)
    new File(output).delete()
  }

}
