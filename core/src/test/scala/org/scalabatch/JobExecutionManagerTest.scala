package org.scalabatch

import java.io.File

import org.scalabatch.core.JobBuilder
import org.scalatest.FunSuite

class JobExecutionManagerTest extends FunSuite {

  var marker: List[String] = Nil

  test("JobListenerTest") {
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

  test("JobContextCountTest") {
    val input = getClass.getResource("/input1.txt").getPath
    val output = input.replace("input1.txt", "output1.txt")
    val job = new JobBuilder("1").addStep("1").
      addTask("1","FlatFileReadTask", input).
      addTask("2","FlatFileWriteTask", output).build()
    job.executeJob()
    assert(job.context.readCount.get("1").get.get("1").get==2)
    assert(job.context.writeCount.get("1").get.get("2").get==2)
    new File(output).delete()
  }
}
