package example.application

import cats.data.State
import cats.~>
import com.github.tkawachi.dddtools.repository.state.EntityMap
import example.domain.{Book, User}

case class TestState(users: EntityMap[User] = Map.empty,
                     books: EntityMap[Book] = Map.empty,
                     nextId: Long = 1L)

object TestState {
  val booksNat = new ~>[State[EntityMap[Book], ?], State[TestState, ?]] {
    override def apply[A](fa: State[EntityMap[Book], A]): State[TestState, A] =
      fa.transformS[TestState](_.books, (ts, books) => ts.copy(books = books))
  }
  val usersNat = new ~>[State[EntityMap[User], ?], State[TestState, ?]] {
    override def apply[A](fa: State[EntityMap[User], A]): State[TestState, A] =
      fa.transformS[TestState](_.users, (ts, users) => ts.copy(users = users))
  }

  val nextIdNat = new ~>[State[Long, ?], State[TestState, ?]] {
    override def apply[A](fa: State[Long, A]): State[TestState, A] =
      fa.transformS(_.nextId, (ts, nextId) => ts.copy(nextId = nextId))
  }
}
