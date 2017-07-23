package example.application

import cats.data.State
import cats.~>
import example.domain.{Book, BookId, User, UserId}

case class TestState(users: Map[UserId, User], books: Map[BookId, Book])

object TestState {
  val booksNat = new ~>[State[Map[BookId, Book], ?], State[TestState, ?]] {
    override def apply[A](
        fa: State[Map[BookId, Book], A]): State[TestState, A] =
      fa.transformS[TestState](_.books, (ts, books) => ts.copy(books = books))
  }
  val usersNat = new ~>[State[Map[UserId, User], ?], State[TestState, ?]] {
    override def apply[A](
        fa: State[Map[UserId, User], A]): State[TestState, A] =
      fa.transformS[TestState](_.users, (ts, users) => ts.copy(users = users))
  }
}
