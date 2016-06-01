package org.scalabatch.core

trait ProcessTask extends Task{

  def transform(records:List[_]):List[_]

  override def execute(): Unit = {
    val c = stepContext()
    c.keys.foreach(key=>{
      c(key)=transform(c.get(key).get)
    })
  }

}
