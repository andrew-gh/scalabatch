package org.scalabatch.core

trait Job {

  def execute()

  def identifier:String

  val steps:Seq[Step]

}
