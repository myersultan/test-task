package com.example.task2

import akka.actor.{Actor, ActorRef, Props}
import com.example.task2.MyActor.DoWork

object MyActor {
  case class DoWork(counter: Int, vector: Vector[ActorRef], time: Long)
  def props = Props[MyActor]
}

class MyActor extends Actor {

  var count = 0

  def receive: Receive = {

    case DoWork(counter, vector, time) =>
      Thread.sleep(10)
      if(vector.nonEmpty) {
        count = counter + 1
        println(s"Actor ${self.path.name}, message received in ${System.currentTimeMillis() - time} milli-seconds")
        vector.head ! DoWork(count, vector.tail, System.currentTimeMillis())
      } else {
        println(s"Actor ${self.path.name}, message received in ${System.currentTimeMillis() - time} milli-seconds")
      }
  }

}
