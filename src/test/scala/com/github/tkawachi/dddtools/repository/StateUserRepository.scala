package com.github.tkawachi.dddtools.repository

import cats.data.State

class StateUserRepository
    extends StateRepository[User, TestState](
      _.users,
      (db, users) => db.copy(users = users))
    with UserRepository[State[TestState, ?]] {

  override def findByName(name: String): State[TestState, Option[User]] =
    state(s => (s, s.values.find(user => user.name == name)))
}
