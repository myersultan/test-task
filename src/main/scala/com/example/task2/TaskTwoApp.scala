package com.example.task2

import akka.actor.{ActorRef, ActorSystem}
import com.example.task2.MyActor.DoWork
import com.typesafe.config.ConfigFactory

object TaskTwoApp extends App {

  val system: ActorSystem = ActorSystem("taskTwo")

  val config = ConfigFactory.load()

  var n = config.getString("N").toInt

  var id = 1
  var counter = 0
  var vector: Vector[ActorRef] = Vector.empty
  val time = System.currentTimeMillis();

  // creating n actors in the loop
  while(n != 0) {
    val actor = system.actorOf(MyActor.props, s"$id")
    vector = vector :+ actor
    id += 1
    n -= 1
  }

  // sending message to the actor with id 1
  if(vector.nonEmpty) {
    vector.head ! DoWork(counter, vector.tail, System.currentTimeMillis())
  }


}
