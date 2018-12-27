package com.example

import akka.actor.ActorSystem

import scala.io.StdIn

object IotApp {

  def main(args: Array[String]): Unit = {
    val system = ActorSystem("iot-system")

    try {
      // top level actor
      val supervisor = system.actorOf(IotSupervisor.props(), "iot-supervisor")
      // exit system after ENTER is pressed
      StdIn.readLine()
    }
    finally {
      system.terminate()
    }
  }
}
