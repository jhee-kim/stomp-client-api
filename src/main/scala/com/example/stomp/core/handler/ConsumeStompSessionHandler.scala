package com.example.stomp.core.handler

import org.slf4j.{Logger, LoggerFactory}
import org.springframework.messaging.simp.stomp.{StompCommand, StompFrameHandler, StompHeaders, StompSession}

import java.lang.reflect.Type

class ConsumeStompSessionHandler(dest: String) extends JStompSessionHandler {
  private val log: Logger = LoggerFactory.getLogger(this.getClass.getName)

  private var _session: Option[StompSession] = None

  //private def session = _session.getOrElse(throw new RuntimeException("Session not found."))

  override def afterConnected(session: StompSession, connectedHeaders: StompHeaders): Unit = {
    log.info(s"session : ${session.toString}\n headers: ${connectedHeaders.toString}")
    //_session = Option(session)
    session.subscribe(dest, new StompFrameHandler {
      override def getPayloadType(headers: StompHeaders): Type = classOf[String]

      override def handleFrame(headers: StompHeaders, payload: Any): Unit = {
        log.info(s"[StompFrameHandler-handleFrame]\nheaders : ${headers.toString}\npayload : ${payload.toString}")
      }
    })
  }

  override def getPayloadType(headers: StompHeaders): Type = classOf[String]

  override def handleFrame(headers: StompHeaders, payload: Any): Unit = {
    log.info(s"[handleFrame]\nheaders : ${headers.toString}\npayload : ${payload.toString}")

  }

  override def handleTransportError(session: StompSession, exception: Throwable): Unit = {
    log.warn(s"session : ${session.toString}\n transport error: ${exception.toString}")
    exception.printStackTrace()

  }

  override def handleException(session: StompSession, command: StompCommand, headers: StompHeaders, payload: Array[Byte], exception: Throwable): Unit = {
    log.warn(s"session : ${session.toString}\n exception: ${exception.toString}")
    exception.printStackTrace()
  }

  override def send(message: String): Boolean = true
}
