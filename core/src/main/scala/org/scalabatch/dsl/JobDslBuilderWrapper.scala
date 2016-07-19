package org.scalabatch.dsl

import org.scalabatch.core.JobBuilder

object JobDslBuilderWrapper {

  implicit class JobDslBuilder(s:String) {

    val builder = new JobBuilder(s)

    //new JobBuilder("1").addStep("1").addTask("1","FlatFileReadTask", input).
    //addTask("2","FlatFileWriteTask", output).build().executeJob()

    def tasks(t:TaskDslDefinition):JobDslBuilder = {
      println(s"tasks $t")
      t.tasks.foreach(s => builder.addStep(s))
      this
    }

    def step(j:JobDslBuilder):JobDslBuilder = {
      println(s"step $j")
      builder.addStep("")
      this
    }

    def of(j:JobDslBuilder):JobDslBuilder = {
      println(s"of $j")
      this
    }

    def +(j:JobDslBuilder):JobDslBuilder = {
      println(s"+ $j")
      this
    }

    def -->(to:String):TaskDslDefinition = {
      println(s"of $s, $to")
      TaskDslDefinition(s::to::Nil)
    }

  }

  case object JobBuilderCompanion {
    var job = new JobDslBuilder("")
    def step = job
  }

}
