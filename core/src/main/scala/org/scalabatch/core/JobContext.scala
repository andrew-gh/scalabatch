package org.scalabatch.core

import scala.collection.mutable

class JobContext {

  val context = new scala.collection.mutable.HashMap[String, mutable.Map[String, List[_]]]()

  val readCount = new scala.collection.mutable.HashMap[String, mutable.Map[String, Long]]()

  val processCount = new scala.collection.mutable.HashMap[String, mutable.Map[String, Long]]()

  val writeCount = new scala.collection.mutable.HashMap[String, mutable.Map[String, Long]]()

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
