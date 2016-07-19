package org.scalabatch.core

trait ReadTask[A] extends Task{

  def setReadCount(count:Long) ={
    val r = stat()
    r(identifier).readCount=count
  }

  def read(): List[A]

  override def executeTask(): Unit = {
    val c = stepContext()
    c(identifier) = read()
    setReadCount(c(identifier).size)
  }

}
