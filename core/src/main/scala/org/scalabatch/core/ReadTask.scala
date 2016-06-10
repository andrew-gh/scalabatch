package org.scalabatch.core

trait ReadTask[A] extends Task{

  def setReadCount(count:Long) ={
    val r = context.readCount.get(parentStep).get
    r(identifier)=count
  }

  def read(): List[A]

  override def execute(): Unit = {
    val c = stepContext()
    c(identifier) = read()
    setReadCount(c(identifier).size)
  }

}
