package com.example

import akka.actor.{Actor, ActorLogging, Props}

class IotSupervisor extends Actor with ActorLogging {

  override def preStart(): Unit = log.info("IoT Application started")
  override def postStop(): Unit = log.info("IoT Application stopped")

  override def receive: Receive = Actor.emptyBehavior
}
object IotSupervisor {
  def props(): Props = Props(new IotSupervisor)
}
