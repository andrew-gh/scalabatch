package org.scalabatch.core

import org.scalabatch.stat.Stat

import scala.collection.mutable

class JobContext {

  val context = new scala.collection.mutable.HashMap[String, mutable.Map[String, List[_]]]()

  val readCount = new scala.collection.mutable.HashMap[String, mutable.Map[String, Long]]()

  val processCount = new scala.collection.mutable.HashMap[String, mutable.Map[String, Long]]()

  val writeCount = new scala.collection.mutable.HashMap[String, mutable.Map[String, Long]]()

  val stat = new scala.collection.mutable.HashMap[String, mutable.Map[String, Stat]]()

  /*def create(steps:Seq[Step]):JobContext = {
    steps.foreach(s=>{
        val tasks = s.tasks.
          foldLeft(Map.empty[String, List])((acc, elem)=>
            acc.updated(elem.identifier, List.empty))
        context+=(s->tasks)
    })
    this
  }*/

}
