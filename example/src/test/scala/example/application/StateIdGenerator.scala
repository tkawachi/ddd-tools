package example.application

import cats.data.State
import example.domain.IdGenerator

class StateIdGenerator extends IdGenerator[State[Long, ?]] {
  override def generate: State[Long, Long] = State(l => (l + 1, l))
}
