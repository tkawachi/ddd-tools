package example.application

import cats.data.State
import com.github.tkawachi.dddtools.repository.StateRepository
import example.domain.{User, UserId, UserRepository}

class StateUserRepository
    extends StateRepository[User]
    with UserRepository[State[Map[UserId, User], ?]] {

  override def findByName(
      name: String): State[Map[UserId, User], Option[User]] =
    State(s => (s, s.values.find(user => user.name == name)))
}
