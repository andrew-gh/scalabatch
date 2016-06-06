package org.scalabatch

import java.io.File

import org.scalabatch.core.JobBuilder
import org.scalatest.FunSuite

class JobExecutionManagerTest extends FunSuite {

  var marker: List[String] = Nil

  test("FlatFileJobTest") {
    val input = getClass.getResource("/input1.txt").getPath
    val output = input.replace("input1.txt", "output1.txt")
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
}
