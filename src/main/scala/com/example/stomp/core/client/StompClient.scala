package com.example.stomp.core.client

import com.example.stomp.core.handler.JStompSessionHandler
import org.springframework.messaging.MessageHeaders
import org.springframework.messaging.converter.StringMessageConverter
import org.springframework.messaging.simp.stomp.{StompCommand, StompHeaderAccessor, StompHeaders}
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler
import org.springframework.web.socket.WebSocketHttpHeaders
import org.springframework.web.socket.client.standard.StandardWebSocketClient
import org.springframework.web.socket.messaging.WebSocketStompClient

import java.util.UUID

class StompClient(private val url: String,
                  private val headers: Option[Map[String, String]],
                  private val dest: String,
                  private val id: String,
                  private val password: String) {

  private var _client: Option[WebSocketStompClient] = None

  private val stompHeaders: StompHeaders = {
    val _h = new StompHeaders()
    _h.add(StompHeaders.ACCEPT_VERSION, "1.2")
    _h.add(StompHeaders.DESTINATION, dest)
    _h.add(StompHeaders.HEARTBEAT, "10000,10000")
    _h.add(StompHeaders.ID, UUID.randomUUID().toString)
    _h.add(StompHeaders.LOGIN, id)
    _h.add(StompHeaders.PASSCODE, password)
    _h
  }

  private var _sessionHandler: Option[JStompSessionHandler] = None


  private[client] def setSessionHandler(handler: JStompSessionHandler): Unit = _sessionHandler = Option(handler)
  private[client] def sessionHandler = _sessionHandler.getOrElse(throw new RuntimeException("SessionHandler not set."))
  private[client] def client = _client.getOrElse(throw new RuntimeException("Failed to create client."))

  def addHeader(key: String, value: String): Unit = stompHeaders.add(key, value)

  def init(): Unit = {
    _client = Option(new WebSocketStompClient(new StandardWebSocketClient()))
    client.setMessageConverter(new StringMessageConverter())

    val taskScheduler: ThreadPoolTaskScheduler = new ThreadPoolTaskScheduler()
    taskScheduler.setThreadNamePrefix("stomp-heartbeat-")
    taskScheduler.initialize()

    client.setTaskScheduler(taskScheduler)

    client.connectAsync(url,
      if (headers.isDefined) headers.get.foldRight(new WebSocketHttpHeaders())((v, h) => { h.add(v._1, v._2); h }) else null,
      stompHeaders, sessionHandler)
  }

  def close(): Unit = {
    client.stop()
  }
}