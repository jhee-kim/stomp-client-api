package com.example.stomp.spring.controller

import com.example.stomp.core.client.{StompClientManager, StompProducer}
import com.example.stomp.spring.req.{CreateReq, MessageReq}
import org.slf4j.{Logger, LoggerFactory}
import org.springframework.web.bind.annotation.{DeleteMapping, GetMapping, PathVariable, PostMapping, RequestBody, RequestMapping, RestController}

@RestController
@RequestMapping(value = Array("/v1/client"))
class StompClientController {
  private val log: Logger = LoggerFactory.getLogger(this.getClass.getName)

  @PostMapping(value = Array("producer"))
  def createProducer(@RequestBody req: CreateReq): Any = {
    StompClientManager.createProducer(req).toString
  }

  @PostMapping(value = Array("consumer"))
  def creatConsumer(@RequestBody req: CreateReq): Any = {
    StompClientManager.createConsumer(req).toString
  }

  @GetMapping(value = Array("producer"))
  def getProducerList: Any = {
    StompClientManager.getProducerList.toString()
  }
  @GetMapping(value = Array("consumer"))
  def getConsumerList: Any = {
    StompClientManager.getConsumerList.toString()
  }

  @DeleteMapping(value = Array("producer/{name}"))
  def deleteProducer(@PathVariable("name") name: String): Unit = {
    StompClientManager.deleteProducer(name)
  }

  @DeleteMapping(value = Array("consumer/{name}"))
  def deleteConsumer(@PathVariable("name") name: String): Unit = {
    StompClientManager.deleteConsumer(name)
  }

  @PostMapping(value = Array("send"))
  def sendMessage(@RequestBody messageReq: MessageReq): Any = {
    log.info(s"send message : [ ${messageReq.getCon} ] to [ ${messageReq.getProducer} ] ")
    StompClientManager.getProducer(messageReq.getProducer) match {
      case Some(p) => if (p.send(messageReq.getCon)) "Message send success." else "Message send failed."
      case _ => s"Producer ${messageReq.getProducer} not found."
    }
  }
}
