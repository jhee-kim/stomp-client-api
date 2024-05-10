package com.example.stomp

import org.slf4j.{Logger, LoggerFactory}
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication


object StompclientspringApplication extends App {
  private val log: Logger = LoggerFactory.getLogger(this.getClass.getName)

  SpringApplication.run(classOf[StompclientspringApplication])
}

@SpringBootApplication
class StompclientspringApplication