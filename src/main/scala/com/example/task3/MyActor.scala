package com.example.task3

import akka.actor.{Actor, ActorRef, Props}
import com.example.task3.MyActor.DoWork

object MyActor {
  case class DoWork(msg: String, vector: Vector[ActorRef], index: Int)
  def props = Props[MyActor]
}

class MyActor extends Actor {

  val r = new scala.util.Random

  Thread.sleep(1+r.nextInt(100))

  var counter = 0

  def receive: Receive = {

    case DoWork(msg, vector, index) =>

      counter += 1

      if(counter < 2) {

        if(vector.nonEmpty) {
          //if sender actor is 1 at index 0
          if(index == 0) {
            // neighbor one is actor n
            if(sender != vector(vector.length-1)){
              println(s"message sent to: ${vector(vector.length-1)}")
              vector(vector.length-1) ! DoWork(msg, vector, vector.length-1)
            }
            // neighbor two is actor 2
            if(sender != vector(1)){
              println(s"message sent to: ${vector(1)}")
              vector(1) ! DoWork(msg, vector, 1)
            }
            // if sender actor is n
          } else if( index == vector.length-1) {
            // neighbor one is actor n-1
            if(sender != vector(vector.length-2)){
              println(s"message sent to: ${vector(vector.length-2)}")
              vector(vector.length-2) ! DoWork(msg, vector, vector.length-2)
            }
            // neighbor two is actor 1
            if(sender != vector(0)) {
              println(s"message sent to: ${vector(0)}")
              vector(0) ! DoWork(msg, vector, 0)
            }
          } else {
            if(sender != vector(index-1)) {
              println(s"message sent to: ${vector(index-1)}")
              vector(index-1) ! DoWork(msg, vector, index-1)
            }
            if(sender != vector(index+1)) {
              println(s"message sent to: ${vector(index+1)}")
              vector(index+1) ! DoWork(msg, vector, index+1)
            }
          }
        }

      } else {
        println(s"Actor id: ${self.path.name}, Sender Actor: ${sender.path.name}, Message: $msg, Counter: $counter")
      }

  }

}
