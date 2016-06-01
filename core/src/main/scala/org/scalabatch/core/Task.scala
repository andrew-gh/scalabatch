package org.scalabatch.core

/**
  * Abstraction for representing atomic logical action i.e.
  * read file, write to database, call webservice etc
  */

trait Task {

  def execute()

  def identifier:String

  def context:JobContext

  def stepContext() ={
    context.context.get(parentStep).get
  }

  def parentStep:String

}
