package com.example

import akka.actor.{Actor, ActorSystem, Props}

import scala.io.StdIn

/*
* PARENT-CHILD ACTOR RELATIONSHIP
* */
object PrintMyActorRefActor {
  def props: Props =
    Props(new PrintMyActorRefActor)
}


class PrintMyActorRefActor extends Actor {
  override def receive: Receive = {
    case "printit" ⇒
      val secondRef = context.actorOf(Props.empty, "second-actor")
      println(s"Second: $secondRef")
  }
}


object StartStopActor1 {
  def props: Props = Props(new StartStopActor1)
}


class StartStopActor1 extends Actor {
  override def preStart(): Unit = {
    println("first started")
    context.actorOf(StartStopActor2.props, "second")
  }
  override def postStop(): Unit = println("first stopped")

  override def receive: Receive = {
    case "stop" => context.stop(self)
  }
}


object StartStopActor2 {
  def props: Props = Props(new StartStopActor2)
}


class StartStopActor2 extends Actor {
  override def preStart(): Unit = println("second started")
  override def postStop(): Unit = println("second stopped")

  override def receive: Receive = Actor.emptyBehavior
}


/*
* PARENT-CHILD FAILURE HANDLING
* */

class SupervisingActor extends Actor {
  val child = context.actorOf(SupervisedActor.props, "supervised-actor")

  override def receive: Receive = {
    case "failChild" => child ! "fail"
  }
}
object SupervisingActor {
  def props: Props = Props(new SupervisingActor)
}

class SupervisedActor extends Actor {
  override def preStart(): Unit = println("supervised actor started")
  override def postStop(): Unit = println("supervised actor stopped")

  override def receive: Receive = {
    case "fail" =>
      println("supervised actor fails now")
      throw new Exception("I failed")
  }
}
object SupervisedActor {
  def props: Props = Props(new SupervisedActor)
}



object ActorHierarchyExperiments extends App {
  val system = ActorSystem("testSystem")

  val first = system.actorOf(StartStopActor1.props, "first")
  first ! "stop"

  val firstRef = system.actorOf(PrintMyActorRefActor.props, "first-actor")
  println(s"First: $firstRef")
  firstRef ! "printit"

  val supervisor = system.actorOf(SupervisingActor.props, "supervising-actor")
  supervisor ! "failChild"

  println(">>> Press ENTER to exit <<<")
  try StdIn.readLine()
  finally system.terminate()
}