package org.scalabatch

import java.io.File

import org.scalabatch.core.JobBuilder
import org.scalatest.FunSuite

import scala.io.Source

class FlatFileJobTest extends FunSuite {

    test("FlatFileJobTest"){
      val input = getClass.getResource("/input1.txt").getPath
      val output = input.replace("input1.txt", "output1.txt")
      new JobBuilder("1").addStep("1").addTask("1","FlatFileReadTask", input).
        addTask("2","FlatFileWriteTask", output).build().executeJob()
      assert(new File(output).exists(), "Output file cannot be read")
      val writtenContent = Source.fromFile(output).getLines().mkString
      assert(writtenContent.equals("Line 1Line 2"), "Content of output file differs from input file")
      new File(output).delete()
    }

}
