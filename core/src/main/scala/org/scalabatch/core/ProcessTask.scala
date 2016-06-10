package org.scalabatch.core

trait ProcessTask extends Task{

  def setProcessCount(count:Long) ={
    val r = context.processCount.get(parentStep).get
    r(identifier)=count
  }

  def transform(records:List[_]):List[_]

  override def execute(): Unit = {
    val c = stepContext()
    var count=0
    c.keys.foreach(key=>{
      c(key)=transform(c.get(key).get)
      count+=c(key).size
    })
    setProcessCount(count)
  }

}
