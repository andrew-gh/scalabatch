package org.scalabatch.core

trait WriteTask extends Task {

  def write(records:List[_]):Unit

  def contextKeys:Seq[String]

  override def execute(): Unit = {
    val keys = if (contextKeys.isEmpty) stepContext().keys else contextKeys
    val entitySeq:List[_] = keys.flatMap(key=>stepContext().get(key).get).toList
    write(entitySeq)
  }

}
