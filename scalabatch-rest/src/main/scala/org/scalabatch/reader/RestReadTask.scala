package org.scalabatch.reader

import org.scalabatch.core.{JobContext, ReadTask}

import scalaj.http.Http

class RestReadTask (val path:String, val identifier:String, val context:JobContext,
                    val parentStep:String) extends ReadTask[String]{

  override def read(): List[String] = {
    val request = Http("http://jsonplaceholder.typicode.com/posts/1")
    request.asString.body.split("\r\n").toList
  }

}
