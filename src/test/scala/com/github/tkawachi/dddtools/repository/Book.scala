package com.github.tkawachi.dddtools.repository

import com.github.tkawachi.dddtools.GenericEntity

case class BookId(value: Long)
case class Book(id: BookId, ownerId: UserId) extends GenericEntity[BookId]
