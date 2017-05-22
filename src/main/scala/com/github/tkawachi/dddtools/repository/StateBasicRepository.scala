package com.github.tkawachi.dddtools.repository

import cats.data.State
import com.github.tkawachi.dddtools.Entity

class StateBasicRepository[E <: Entity]
    extends BasicRepository[E, State[Map[E#Id, E], ?]]
    with DeleteSupport[E, State[Map[E#Id, E], ?]]
    with FindAllSupport[E, State[Map[E#Id, E], ?]] {

  override def store(entity: E): State[Map[E#Id, E], Unit] =
    State(s => (s.updated(entity.id, entity), ()))

  override def findById(id: E#Id): State[Map[E#Id, E], Option[E]] =
    State(s => (s, s.get(id)))

  override def deleteById(id: E#Id): State[Map[E#Id, E], Unit] =
    State(s => (s - id, ()))

  override def findAll(): State[Map[E#Id, E], Seq[E]] =
    State(s => (s, s.values.toSeq))
}
