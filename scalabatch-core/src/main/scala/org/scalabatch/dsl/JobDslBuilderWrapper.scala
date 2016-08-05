package org.scalabatch.dsl

import org.scalabatch.core.JobBuilder

object JobDslBuilderWrapper {

  implicit class JobDslBuilder(s:String) {

    val builder = new JobBuilder(s)

    var tasks=List.empty[String]

    //new JobBuilder("1").addStep("1").addTask("1","FlatFileReadTask", input).
    //addTask("2","FlatFileWriteTask", output).build().executeJob()

    def tasks(t:TaskDslDefinition):JobDslBuilder = {
      println(s"tasks $t")
      builder.addStep("")
      t.tasks.foreach(t => builder.addTask(t))
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
