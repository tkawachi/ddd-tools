package com.github.tkawachi.dddtools.repository.state

import cats.data.State
import com.github.tkawachi.dddtools.Entity
import com.github.tkawachi.dddtools.repository.{
  DefaultMultiRepository,
  FindAllRepository
}

class StateRepository[E <: Entity]
    extends DefaultMultiRepository[E, State[EntityMap[E], ?]]
    with FindAllRepository[E, State[EntityMap[E], ?]] {

  override def store(entity: E): State[EntityMap[E], Unit] =
    State(s => (s.updated(entity.id, entity), ()))

  override def findById(id: E#Id): State[EntityMap[E], Option[E]] =
    State(s => (s, s.get(id)))

  override def deleteById(id: E#Id): State[EntityMap[E], Unit] =
    State(s => (s - id, ()))

  override def findAll(): State[EntityMap[E], List[E]] =
    State(s => (s, s.values.toList))
}
