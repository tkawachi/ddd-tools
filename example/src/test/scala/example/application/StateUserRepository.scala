package example.application

import cats.data.State
import com.github.tkawachi.dddtools.repository.{EntityMap, StateRepository}
import example.domain.{User, UserRepository}

class StateUserRepository
    extends StateRepository[User]
    with UserRepository[State[EntityMap[User], ?]] {

  override def findByName(name: String): State[EntityMap[User], Option[User]] =
    State(s => (s, s.values.find(user => user.name == name)))
}
