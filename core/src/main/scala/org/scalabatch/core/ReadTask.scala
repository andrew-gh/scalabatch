package org.scalabatch.core

trait ReadTask[A] extends Task{

  def read(): List[A]

  override def execute(): Unit = {
    val c = stepContext()
    c(identifier) = read()
  }

}
