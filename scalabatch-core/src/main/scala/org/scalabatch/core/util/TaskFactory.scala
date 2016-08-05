package org.scalabatch.core.util

import org.scalabatch.core.Task

object TaskFactory {

  def apply(className:String, args:AnyRef*): Task = {
    if (args.isEmpty) instantiate(className, Seq("default"):_*) else instantiate(className, args:_*)
  }

  def instantiate(name:String, args:AnyRef*) : Task = {
    val clazz = Class.forName(name)
    val constructor = clazz.getConstructors()(0)
    constructor.newInstance(args:_*).asInstanceOf[Task]
  }

}
