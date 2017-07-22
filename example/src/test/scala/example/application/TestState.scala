package example.application

import example.domain.{Book, BookId, User, UserId}

case class TestState(users: Map[UserId, User], books: Map[BookId, Book])
