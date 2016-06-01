package org.scalabatch.core

import org.scalabatch.reader.{DirectoryReadTask, FlatFileReadTask}
import org.scalabatch.writer.FlatFileWriteTask

import scala.collection.mutable

class JobBuilder(identifier:String) {

  val jobContext = new JobContext()

  val tasks = scala.collection.mutable.LinkedHashMap[String, mutable.MutableList[Task]]()

  def instance(identifier:String) = new JobBuilder(identifier)

  def addStep(identifier:String):JobBuilder = {
    tasks(identifier)=new mutable.MutableList[Task]()
    this
  }
//classOf[C].getConstructor(classOf[String]).newInstance("string")
  def addTask(identifier:String, typeName:String, path:String,
              args:Array[AnyRef] = Array.emptyObjectArray):JobBuilder = {
    if (tasks.isEmpty) return this
    val key = tasks.takeRight(1).iterator.next()._1
    val urls = urlses(getClass.getClassLoader)
//    println(urls.filterNot(_.toString.contains("ivy")).mkString("\n"))
    val t = typeName match {
      case "FlatFileReadTask" => new FlatFileReadTask(path, identifier, jobContext, key)
      case "FlatFileWriteTask" => new FlatFileWriteTask(path, identifier, Seq.empty, jobContext, key)
      case "DirectoryReadTask" => new DirectoryReadTask(path, identifier, jobContext, key)
      case "rest" => {
        val clazz = Class.forName("org.scalabatch.reader.RestReadTask")
        val constructor = clazz.getConstructors()(0)
        constructor.newInstance(path, identifier, jobContext, key).asInstanceOf[Task]
      }
    }
    tasks(key)=tasks(key):+t
    this
  }

  def urlses(cl: ClassLoader): Array[java.net.URL] = cl match {
    case null => Array()
    case u: java.net.URLClassLoader => u.getURLs() ++ urlses(cl.getParent)
    case _ => urlses(cl.getParent)
  }

  def build():Job = {
    tasks.foreach(s=>{
      jobContext.context(s._1) = collection.mutable.Map[String, List[_]]() ++=
        s._2.foldLeft(Map.empty[String, List[_]])((acc, elem)=>
          acc.updated(elem.identifier, List.empty))
    })
    val content = tasks.foldLeft(List.empty[Step])((acc, elem)=>
      acc.::(new SimpleStep(elem._1, elem._2, jobContext)))
    new SimpleJob("", content)
  }

}
