package example.domain

import com.github.tkawachi.dddtools.GenericEntity

case class BookId(value: Long)
case class Book(id: BookId, name: String, ownerId: UserId)
    extends GenericEntity[BookId]
