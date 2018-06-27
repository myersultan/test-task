package com.example.task1

import akka.actor.{Actor, ActorRef, Props}

object MyActor {
  case class DoWork(counter: Int, vector: Vector[ActorRef])
  def props = Props[MyActor]
}

class MyActor extends Actor {
  import MyActor._

  var count = 0

  def receive: Receive = {

    case DoWork(counter, vector) =>

      if(vector.nonEmpty) {
        count = counter + 1
        println(s"Loop: $count ->>>>")
        println(s"Sender actor: ${sender.path.name}")
        println(s"Receiver actor: ${self.path.name}")
        println("----------------------------------")
        vector.head ! DoWork(count, vector.tail)
      } else {
        println(s"Number of hoops: $counter")
      }


  }

}
