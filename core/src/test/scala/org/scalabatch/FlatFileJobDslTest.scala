package org.scalabatch

import java.io.File

import org.scalabatch.core.JobBuilder
import org.scalatest.FunSuite
import org.scalabatch.dsl.JobDslBuilderWrapper._
import org.scalabatch.dsl.JobDslBuilderWrapper.JobBuilderCompanion._

import scala.io.Source

class FlatFileJobDslTest extends FunSuite {

  test("FlatFileJobTest"){
    val input = getClass.getResource("/input1.txt").getPath
    val output = input.replace("input1.txt", "output1.txt")

    val j = job of (step tasks s"FlatFileReadTask:$input" --> s"FlatFileWriteTask:$output")
    j.builder.build().execute()

    assert(new File(output).exists(), "Output file cannot be read")
    val writtenContent = Source.fromFile(output).getLines().mkString
    assert(writtenContent.equals("Line 1Line 2"), "Content of output file differs from input file")
    new File(output).delete()
  }

}
