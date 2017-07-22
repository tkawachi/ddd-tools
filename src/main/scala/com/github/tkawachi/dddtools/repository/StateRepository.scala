package com.github.tkawachi.dddtools.repository

import cats.Applicative
import cats.data.{State, StateT}
import com.github.tkawachi.dddtools.Entity

trait StateRepository[E <: Entity]
    extends BasicRepository[E, State[Map[E#Id, E], ?]]
    with DefaultBasicMultiRepository[E, State[Map[E#Id, E], ?]]
    with DeleteRepository[E, State[Map[E#Id, E], ?]]
    with DefaultDeleteMultiRepository[E, State[Map[E#Id, E], ?]]
    with FindAllRepository[E, State[Map[E#Id, E], ?]] {

  type RepoState[A] = State[Map[E#Id, E], A]

  override def store(entity: E): State[Map[E#Id, E], Unit] =
    State(s => (s.updated(entity.id, entity), ()))

  override def findById(id: E#Id): State[Map[E#Id, E], Option[E]] =
    State(s => (s, s.get(id)))

  override def deleteById(id: E#Id): State[Map[E#Id, E], Unit] =
    State(s => (s - id, ()))

  override def findAll(): State[Map[E#Id, E], List[E]] =
    State(s => (s, s.values.toList))

  override protected implicit def applicativeF
    : Applicative[State[Map[E#Id, E], ?]] = StateT.catsDataMonadForStateT
}
