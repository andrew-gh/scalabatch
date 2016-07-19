package org.scalabatch.core

import java.util.UUID

import org.scalabatch.reader.{DirectoryReadTask, FlatFileReadTask}
import org.scalabatch.stat.Stat
import org.scalabatch.writer.FlatFileWriteTask

import scala.collection.mutable

class JobBuilder(identifier:String) {

  val jobContext = new JobContext()

  val executionManager = new JobExecutionManager()

  val tasks = scala.collection.mutable.LinkedHashMap[String, mutable.MutableList[Task]]()

  def instance(identifier:String) = new JobBuilder(identifier)

  var taskCounter=1;

  def addJobStartListener(listener: String => Unit): JobBuilder ={
    executionManager.addJobStartListener(listener)
    this
  }

  def addJobEndListener(listener: String => Unit): JobBuilder ={
    executionManager.addJobEndListener(listener)
    this
  }

  def addStep(identifier:String):JobBuilder = {
    tasks(identifier)=new mutable.MutableList[Task]()
    this
  }

  def addTask(params:String):JobBuilder = {
    val parsed = params.split(":")
    parsed match {
      case Array(_) => addTask(parsed.head, Array.empty[String])
      case _ => addTask(parsed.head, parsed.tail)
    }
  }

  def addTask(typeName:String,
              args:Array[String] = Array.empty[String]):JobBuilder = {
    if (tasks.isEmpty) return this
    val key = tasks.takeRight(1).iterator.next()._1
    val t = typeName match {
      case "FlatFileReadTask" => new FlatFileReadTask(args(0).toString, taskCounter.toString, jobContext, key)
      case "FlatFileWriteTask" => new FlatFileWriteTask(args(0).toString, taskCounter.toString, Seq.empty, jobContext, key)
      case "DirectoryReadTask" => new DirectoryReadTask(args(0).toString, taskCounter.toString, jobContext, key)
      case "rest" => {
        val clazz = Class.forName("org.scalabatch.reader.RestReadTask")
        val constructor = clazz.getConstructors()(0)
        constructor.newInstance(args(0).toString, identifier, jobContext, key).asInstanceOf[Task]
      }
    }
    tasks(key)=tasks(key):+t
    taskCounter+=1
    this
  }

  def build():Job = {
    tasks.foreach(s=>{
      jobContext.context(s._1) = collection.mutable.Map[String, List[_]]() ++=
        s._2.foldLeft(Map.empty[String, List[_]])((acc, elem)=>
          acc.updated(elem.identifier, List.empty))
      jobContext.readCount(s._1) = collection.mutable.Map[String, Long]() ++=
        s._2.foldLeft(Map.empty[String, Long])((acc, elem)=>
          acc.updated(elem.identifier, 0))
      jobContext.writeCount(s._1) = collection.mutable.Map[String, Long]() ++=
        s._2.foldLeft(Map.empty[String, Long])((acc, elem)=>
          acc.updated(elem.identifier, 0))
      jobContext.processCount(s._1) = collection.mutable.Map[String, Long]() ++=
        s._2.foldLeft(Map.empty[String, Long])((acc, elem)=>
          acc.updated(elem.identifier, 0))
      jobContext.stat(s._1) = collection.mutable.Map[String, Stat]() ++=
        s._2.foldLeft(Map.empty[String, Stat])((acc, elem)=>
          acc.updated(elem.identifier, new Stat()))
    })
    val content = tasks.foldLeft(List.empty[Step])((acc, elem)=>
      acc.::(new SimpleStep(elem._1, elem._2, jobContext)))
    new SimpleJob("", content, executionManager, jobContext)
  }

}
