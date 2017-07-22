package com.github.tkawachi.dddtools.repository

import com.github.tkawachi.dddtools.GenericEntity

case class UserId(value: Long)

case class User(id: UserId, name: String) extends GenericEntity[UserId]
