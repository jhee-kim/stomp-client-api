package com.example.stomp.core.client

import com.example.stomp.core.handler.ConsumeStompSessionHandler

class StompConsumer(private val url: String,
                    private val headers: Option[Map[String, String]],
                    private val dest: String,
                    private val id: String,
                    private val password: String) extends StompClient(url, headers, dest, id, password) {

  override def init(): Unit = {
    setSessionHandler(new ConsumeStompSessionHandler(dest))
    super.init()
  }

  override def toString: String =
    s"""[STOMP Consumer]
       |Broker URL  : $url
       |Destination : $dest
       |
       |""".stripMargin
}
