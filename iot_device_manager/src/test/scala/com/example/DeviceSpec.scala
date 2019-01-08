package com.example

import akka.actor.{ActorSystem, PoisonPill}
import akka.testkit.{TestKit, TestProbe}
import org.scalatest.{BeforeAndAfterAll, Matchers, WordSpecLike}

class DeviceSpec(system: ActorSystem) extends TestKit(system) with Matchers with WordSpecLike with BeforeAndAfterAll {

  def this() = this(ActorSystem("test-iot-system"))

  override def afterAll: Unit = {
    shutdown(system)
  }

  "Device Actor" should {

    "respond with empty reading if no temperature is known" in {
      val probe = TestProbe()(system)
      val deviceActor = system.actorOf(Device.props("group", "device"))

      deviceActor.tell(Device.ReadTemperature(requestId = 42), probe.ref)
      val response = probe.expectMsgType[Device.RespondTemperature]
      response.requestId shouldBe 42L
      response.value shouldBe None
    }
  }

  "reply with latest temperature reading" in {
    val probe = TestProbe()(system)
    val deviceActor = system.actorOf(Device.props("group", "device"))

    deviceActor.tell(Device.RecordTemperature(requestId = 1, value = 24.0), probe.ref)
    probe.expectMsg(Device.RecordedTemperature(requestId = 1))

    deviceActor.tell(Device.ReadTemperature(requestId = 2), probe.ref)
    val response1 = probe.expectMsgType[Device.RespondTemperature]
    response1.requestId should ===(2L)
    response1.value should ===(Some(24.0))

    deviceActor.tell(Device.RecordTemperature(requestId = 3, 55.0), probe.ref)
    probe.expectMsg(Device.RecordedTemperature(requestId = 3))

    deviceActor.tell(Device.ReadTemperature(requestId = 4), probe.ref)
    probe.expectMsg(Device.RespondTemperature(requestId = 4, value = Some(55.0)))
  }

  "reply to registration requests" in {
    val probe = TestProbe()(system)
    val deviceActor = system.actorOf(Device.props("group", "device"))

    deviceActor.tell(DeviceManager.RequestTrackDevice("group", "device"), probe.ref)
    probe.expectMsg(DeviceManager.DeviceRegistered)
    probe.lastSender shouldBe deviceActor
  }

  "ignore wrong registration requests" in {
    val probe = TestProbe()(system)
    val deviceActor = system.actorOf(Device.props("group", "device"))

    deviceActor.tell(DeviceManager.RequestTrackDevice("wrongGroup", "device"), probe.ref)
    probe.expectNoMessage()

    deviceActor.tell(DeviceManager.RequestTrackDevice("group", "wrongDevice"), probe.ref)
    probe.expectNoMessage()
  }

  "be able to register a device actor" in {
    val probe = TestProbe()(system)
    val groupActor = system.actorOf(DeviceGroup.props("group"))

    groupActor.tell(DeviceManager.RequestTrackDevice("group", "device1"), probe.ref)
    probe.expectMsg(DeviceManager.DeviceRegistered)
    val deviceActor1 = probe.lastSender


    groupActor.tell(DeviceManager.RequestTrackDevice("group", "device2"), probe.ref)
    probe.expectMsg(DeviceManager.DeviceRegistered)
    val deviceActor2 = probe.lastSender
    deviceActor1 should !==(deviceActor2)

    deviceActor1.tell(Device.RecordTemperature(requestId = 1, value = 10.0), probe.ref)
    probe.expectMsg(Device.RecordedTemperature(requestId = 1))
    deviceActor2.tell(Device.RecordTemperature(requestId = 2, value = 15.0), probe.ref)
    probe.expectMsg(Device.RecordedTemperature(requestId = 2))


    deviceActor1.tell(Device.ReadTemperature(requestId = 15), probe.ref)
    probe.expectMsg(Device.RespondTemperature(requestId = 15, value = Some(10.0)))
    deviceActor2.tell(Device.ReadTemperature(requestId = 20), probe.ref)
    probe.expectMsg(Device.RespondTemperature(requestId = 20, value = Some(15.0)))
  }

  "ignore requests for wrong groupId" in {
    val probe = TestProbe()(system)
    val groupActor = system.actorOf(DeviceGroup.props("group"))

    groupActor.tell(DeviceManager.RequestTrackDevice("wrongGroup", "device1"), probe.ref)
    probe.expectNoMessage()
  }

  "return same actor for same deviceId" in {
    val probe = TestProbe()(system)
    val groupActor = system.actorOf(DeviceGroup.props("group"))

    groupActor.tell(DeviceManager.RequestTrackDevice("group", "device1"), probe.ref)
    probe.expectMsg(DeviceManager.DeviceRegistered)
    val deviceActor1 = probe.lastSender

    groupActor.tell(DeviceManager.RequestTrackDevice("group", "device1"), probe.ref)
    probe.expectMsg(DeviceManager.DeviceRegistered)
    val deviceActor2 = probe.lastSender

    deviceActor1 should ===(deviceActor2)
  }

  "be able to list active devices" in {
    val probe = TestProbe()(system)
    val groupActor = system.actorOf(DeviceGroup.props("group"))

    groupActor.tell(DeviceManager.RequestTrackDevice("group", "device1"), probe.ref)
    probe.expectMsg(DeviceManager.DeviceRegistered)

    groupActor.tell(DeviceManager.RequestTrackDevice("group", "device2"), probe.ref)
    probe.expectMsg(DeviceManager.DeviceRegistered)

    groupActor.tell(DeviceGroup.RequestDeviceList(0), probe.ref)
    probe.expectMsg(DeviceGroup.ReplyDeviceList(requestId = 0, ids = Set("device1", "device2")))
  }

  "be able to list active devices after one shuts down" in {
    val probe = TestProbe()(system)
    val groupActor = system.actorOf(DeviceGroup.props("group"))

    groupActor.tell(DeviceManager.RequestTrackDevice("group", "device1"), probe.ref)
    probe.expectMsg(DeviceManager.DeviceRegistered)
    val toShutDown = probe.lastSender

    groupActor.tell(DeviceManager.RequestTrackDevice("group", "device2"), probe.ref)
    probe.expectMsg(DeviceManager.DeviceRegistered)

    groupActor.tell(DeviceGroup.RequestDeviceList(0), probe.ref)
    probe.expectMsg(DeviceGroup.ReplyDeviceList(requestId = 0, ids = Set("device1", "device2")))

    probe.watch(toShutDown)
    toShutDown ! PoisonPill
    probe.expectTerminated(toShutDown)

    probe.awaitAssert {
      groupActor.tell(DeviceGroup.RequestDeviceList(1), probe.ref)
      probe.expectMsg(DeviceGroup.ReplyDeviceList(requestId = 1, ids = Set("device2")))
    }
  }
}
