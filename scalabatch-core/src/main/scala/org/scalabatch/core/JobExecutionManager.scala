package org.scalabatch.core

class JobExecutionManager {

  var jobStartListeners: List[String => Unit] = Nil

  var jobEndListeners: List[String => Unit] = Nil

  def addJobStartListener(listener: String => Unit) {
    jobStartListeners ::= listener
  }

  def addJobEndListener(listener: String => Unit) {
    jobEndListeners ::= listener
  }

  def beforeJobStart(event: String) = jobStartListeners.foreach(_(event))

  def afterJobEnd(event: String) = jobEndListeners.foreach(_(event))

}
