package com.example.stomp.core.client

import com.example.stomp.spring.req.CreateReq

import scala.collection.mutable

object StompClientManager {
  private val producers: mutable.Map[String, StompProducer] = mutable.Map.empty
  private val consumers: mutable.Map[String, StompConsumer] = mutable.Map.empty

  def createProducer(req: CreateReq): StompProducer = {
    val producer = new StompProducer(req.getUrl, None, req.getDest, req.getId, req.getPassword)
    producer.init()
    producers.addOne(req.getName -> producer)
    producer
  }

  def createConsumer(req: CreateReq): StompConsumer = {
    val consumer = new StompConsumer(req.getUrl, None, req.getDest, req.getId, req.getPassword)
    consumer.init()
    consumers.addOne(req.getName -> consumer)
    consumer
  }

  def getProducer(name: String): Option[StompProducer] = Option(producers(name))
  
  def getProducerList: Seq[(String, StompProducer)] = producers.toList
  def getConsumerList: Seq[(String, StompConsumer)] = consumers.toList

  def deleteProducer(name: String): Boolean = {
    if (producers.contains(name)) {
      producers(name).close()
      producers.remove(name).isDefined
    }
    else false
  }
  def deleteConsumer(name: String): Boolean = {
    if (consumers.contains(name)) {
      consumers(name).close()
      consumers.remove(name).isDefined
    }
    else false
  }
}
