package org.scalabatch.core

trait ProcessTask extends Task{

  def setProcessCount(count:Long) ={
    val r = context.stat.get(parentStep).get
    r(identifier).processCount=count
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
