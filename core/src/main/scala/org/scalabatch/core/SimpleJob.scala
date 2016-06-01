package org.scalabatch.core

class SimpleJob(val identifier:String, val steps:Seq[Step]) extends Job{
  override def execute(): Unit = {
    steps.foreach(_.execute())
  }
}