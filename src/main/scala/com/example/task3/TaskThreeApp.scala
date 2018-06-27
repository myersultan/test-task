package com.example.task3

import akka.actor.{ActorRef, ActorSystem, Props}
import akka.routing.RoundRobinRoutingLogic
import com.example.task3.MyActor.DoWork
import com.typesafe.config.ConfigFactory

object TaskThreeApp extends App {
  val system: ActorSystem = ActorSystem("taskThree")

  val config = ConfigFactory.load()

  val r = new scala.util.Random
  val numberOfActors = config.getString("N").toInt
  var n = numberOfActors

  var id = 1
  var counter = 0
  var vector: Vector[ActorRef] = Vector.empty
  val time = System.currentTimeMillis()

  val randomIndex = r.nextInt(numberOfActors)

  println(s"Random number: ${randomIndex}")

  while(n != 0) {
    val actor = system.actorOf(MyActor.props, s"$id")
    vector = vector :+ actor
    id += 1
    n -= 1
  }

  val msg = s"hello from ${vector(randomIndex).path.name}"
  vector(randomIndex) ! DoWork(msg, vector, randomIndex)

}
