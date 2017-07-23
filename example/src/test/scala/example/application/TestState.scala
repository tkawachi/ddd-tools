package example.application

import cats.data.State
import cats.~>
import com.github.tkawachi.dddtools.repository.EntityMap
import example.domain.{Book, User}

case class TestState(users: EntityMap[User], books: EntityMap[Book])

object TestState {
  val booksNat = new ~>[State[EntityMap[Book], ?], State[TestState, ?]] {
    override def apply[A](fa: State[EntityMap[Book], A]): State[TestState, A] =
      fa.transformS[TestState](_.books, (ts, books) => ts.copy(books = books))
  }
  val usersNat = new ~>[State[EntityMap[User], ?], State[TestState, ?]] {
    override def apply[A](fa: State[EntityMap[User], A]): State[TestState, A] =
      fa.transformS[TestState](_.users, (ts, users) => ts.copy(users = users))
  }
}
