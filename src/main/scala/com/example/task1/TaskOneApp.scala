package com.example.task1

import akka.actor.{ActorRef, ActorSystem}
import com.example.task1.MyActor.DoWork
import com.typesafe.config.ConfigFactory

object TaskOneApp extends App {

  val system: ActorSystem = ActorSystem("taskOne")

  val config = ConfigFactory.load()

  var n = config.getString("N").toInt

//  println(s"number from config: ${n+1}")

  var id = 1
  var counter = 0
  var vector: Vector[ActorRef] = Vector.empty

  // creating n actors in the loop
  while(n != 0) {
    val actor = system.actorOf(MyActor.props, s"$id")
    vector = vector :+ actor
    id += 1
    n -= 1
  }

  println(s"System created ${vector.length} actors: ", vector)

  // sending message to the actor with id 1
  if(vector.nonEmpty) {
    vector.head ! DoWork(counter, vector.tail)
  }


}
