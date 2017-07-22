package example.application

import cats.data.State
import com.github.tkawachi.dddtools.repository.StateRepository
import example.domain.{User, UserRepository}

class StateUserRepository
    extends StateRepository[User, TestState](
      _.users,
      (db, users) => db.copy(users = users))
    with UserRepository[State[TestState, ?]] {

  override def findByName(name: String): State[TestState, Option[User]] =
    state(s => (s, s.values.find(user => user.name == name)))
}
