package com.github.tkawachi.dddtools.repository

import cats.data.State
import org.scalatest.FunSuite

class TestAppTest extends FunSuite {
  implicit val bookRepository: BookRepository[State[TestState, ?]] =
    new StateBookRepository
  implicit val userRepository: UserRepository[State[TestState, ?]] =
    new StateUserRepository
  val app: TestApp[State[TestState, ?]] = new TestApp[State[TestState, ?]]

  test("findOwner(BookId(1))") {
    val result = app.findOwner(BookId(1))

    assert(
      result
        .run(TestState(Map(UserId(1) -> User(UserId(1), "Taro")),
                       Map(BookId(1) -> Book(BookId(1), UserId(1)))))
        .value
        ._2 === Some(User(UserId(1), "Taro")))

    assert(
      result
        .run(TestState(Map(UserId(1) -> User(UserId(1), "Taro")),
                       Map(BookId(1) -> Book(BookId(1), UserId(2)))))
        .value
        ._2 === None)

    assert(
      result
        .run(TestState(Map.empty, Map.empty))
        .value
        ._2 === None)
  }
}
