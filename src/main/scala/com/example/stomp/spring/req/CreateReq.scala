package com.example.stomp.spring.req

import scala.beans.BeanProperty


class CreateReq(@BeanProperty val name: String,
                @BeanProperty val url: String,
                @BeanProperty val dest: String,
                @BeanProperty val id: String,
                @BeanProperty val password: String)