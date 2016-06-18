package org.scalabatch.core

trait ReadTask[A] extends Task{

  def setReadCount(count:Long) ={
    val r = context.stat.get(parentStep).get
    r(identifier).readCount=count
  }

  def read(): List[A]

  override def execute(): Unit = {
    val c = stepContext()
    c(identifier) = read()
    setReadCount(c(identifier).size)
  }

}
