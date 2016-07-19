package org.scalabatch.dsl

trait TaskDslTrait {

}

case class TaskDslDefinition(tasks:List[String]) extends TaskDslTrait {
  def -->(task:String) = TaskDslDefinition(tasks:+task)
}
