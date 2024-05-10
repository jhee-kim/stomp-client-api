package com.example.stomp.spring.req

import scala.beans.BeanProperty

class MessageReq(@BeanProperty val producer: String,
                 @BeanProperty val con: String)