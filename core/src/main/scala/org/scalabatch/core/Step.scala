package org.scalabatch.core

trait Step {

  def execute()

  def identifier:String

  def tasks:Seq[Task]

  def context = new JobContext

}
