package com.github.tkawachi.dddtools.repository

import cats.data.State
import com.github.tkawachi.dddtools.Entity

class StateRepository[E <: Entity]
    extends DefaultMultiRepository[E, State[Map[E#Id, E], ?]]
    with FindAllRepository[E, State[Map[E#Id, E], ?]] {

  override def store(entity: E): State[Map[E#Id, E], Unit] =
    State(s => (s.updated(entity.id, entity), ()))

  override def findById(id: E#Id): State[Map[E#Id, E], Option[E]] =
    State(s => (s, s.get(id)))

  override def deleteById(id: E#Id): State[Map[E#Id, E], Unit] =
    State(s => (s - id, ()))

  override def findAll(): State[Map[E#Id, E], List[E]] =
    State(s => (s, s.values.toList))
}
