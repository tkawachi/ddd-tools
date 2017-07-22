package com.github.tkawachi.dddtools.repository

import cats.data.State
import com.github.tkawachi.dddtools.Entity
import org.scalatest.FunSuite

class TestAppTest extends FunSuite {
  implicit val bookRepository: BookRepository[State[TestState, ?]] =
    new StateBookRepository
  implicit val userRepository: UserRepository[State[TestState, ?]] =
    new StateUserRepository
  val app: TestApp[State[TestState, ?]] = new TestApp[State[TestState, ?]]

  implicit class EntityList[E <: Entity](list: List[E]) {
    def toIdMap: Map[E#Id, E] = list.map(e => e.id -> e).toMap
  }

  test("findOwner(BookId(1))") {
    val result = app.findOwner(BookId(1))

    assert(
      result
        .run(TestState(List(User(UserId(1), "Taro")).toIdMap,
                       List(Book(BookId(1), UserId(1))).toIdMap))
        .value
        ._2 === Some(User(UserId(1), "Taro")))

    assert(
      result
        .run(TestState(List(User(UserId(1), "Taro")).toIdMap,
                       List(Book(BookId(1), UserId(2))).toIdMap))
        .value
        ._2 === None)

    assert(
      result
        .run(TestState(Map.empty, Map.empty))
        .value
        ._2 === None)
  }
}
