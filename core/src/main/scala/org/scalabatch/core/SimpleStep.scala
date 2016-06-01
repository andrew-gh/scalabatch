package org.scalabatch.core

class SimpleStep(val identifier:String, val tasks:Seq[Task], context:JobContext) extends Step{
  override def execute(): Unit = {
    tasks.foreach(_.execute())
  }
}
