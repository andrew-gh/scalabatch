package org.scalabatch.core

/**
  * Abstraction for representing atomic logical action i.e.
  * read file, write to database, call webservice etc
  */

trait Task {

  def execute() = {
    val s = stat()
    s(identifier).startTime=System.currentTimeMillis()
    executeTask()
    s(identifier).endTime=System.currentTimeMillis()
  }

  def executeTask()

  def identifier:String

  def context:JobContext

  def stepContext() ={
    context.context.get(parentStep).get
  }

  def stat() ={
    context.stat.get(parentStep).get
  }

  def parentStep:String

}
