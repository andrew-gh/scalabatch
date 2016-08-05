package org.scalabatch.core

trait Job {

  def execute()

  def executeJob(): Unit = {
    executionManager.beforeJobStart(identifier)
    execute()
    executionManager.afterJobEnd(identifier)
  }

  def identifier:String

  val steps:Seq[Step]

  def executionManager:JobExecutionManager

  def context:JobContext

}
