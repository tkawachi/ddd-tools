package example.application

import cats.data.State
import com.github.tkawachi.dddtools.repository.state.EntityMap
import example.domain.{Book, User}

case class TestState(users: EntityMap[User] = Map.empty,
                     books: EntityMap[Book] = Map.empty,
                     nextId: Long = 1L)

object TestState {
  def bookToState[A](v: State[EntityMap[Book], A]): State[TestState, A] =
    v.transformS(_.books, (ts, books) => ts.copy(books = books))

  def userToState[A](v: State[EntityMap[User], A]): State[TestState, A] =
    v.transformS(_.users, (ts, users) => ts.copy(users = users))

  def nextIdToState[A](v: State[Long, A]): State[TestState, A] =
    v.transformS(_.nextId, (ts, nextId) => ts.copy(nextId = nextId))

}
