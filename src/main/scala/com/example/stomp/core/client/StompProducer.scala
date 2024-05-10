package com.example.stomp.core.client

import com.example.stomp.core.handler.ProduceStompSessionHandler

class StompProducer(private val url: String,
                    private val headers: Option[Map[String, String]],
                    private val dest: String,
                    private val id: String,
                    private val password: String) extends StompClient(url, headers, dest, id, password) {

  override def init(): Unit = {
    setSessionHandler(new ProduceStompSessionHandler(dest))
    super.init()
  }

  def send(msg: String): Boolean = {
    sessionHandler.send(msg)
  }

  override def toString: String =
    s"""[STOMP Producer]
       |Broker URL  : $url
       |Destination : $dest
       |
       |""".stripMargin
}
