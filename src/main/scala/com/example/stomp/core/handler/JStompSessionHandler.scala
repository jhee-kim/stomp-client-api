package com.example.stomp.core.handler

import org.slf4j.{Logger, LoggerFactory}
import org.springframework.messaging.simp.stomp.{StompCommand, StompHeaders, StompSession, StompSessionHandler, StompSessionHandlerAdapter}

import java.lang.reflect.Type

abstract class JStompSessionHandler extends StompSessionHandlerAdapter with StompSessionHandler  {
  private val log: Logger = LoggerFactory.getLogger(this.getClass.getName)

  override def afterConnected(session: StompSession, connectedHeaders: StompHeaders): Unit = {
     log.info(s"session : ${session.toString}\n headers: ${connectedHeaders.toString}")
  }

  override def getPayloadType(headers: StompHeaders): Type = classOf[String]

  override def handleFrame(headers: StompHeaders, payload: Any): Unit = {


  }

  override def handleTransportError(session: StompSession, exception: Throwable): Unit = {
    log.warn(s"session : ${session.toString}\n transport error: ${exception.toString}")

  }

  override def handleException(session: StompSession, command: StompCommand, headers: StompHeaders, payload: Array[Byte], exception: Throwable): Unit = {
    log.warn(s"session : ${session.toString}\n exception: ${exception.toString}")
  }

  def send(message: String): Boolean
}
