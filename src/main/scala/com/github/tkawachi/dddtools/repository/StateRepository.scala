package com.github.tkawachi.dddtools.repository

import cats.data.State
import com.github.tkawachi.dddtools.Entity

class StateRepository[E <: Entity, GlobalState](
    f: GlobalState => Map[E#Id, E],
    g: (GlobalState, Map[E#Id, E]) => GlobalState)
    extends DefaultMultiRepository[E, State[GlobalState, ?]]
    with FindAllRepository[E, State[GlobalState, ?]] {

  protected def state[A](
      body: Map[E#Id, E] => (Map[E#Id, E], A)): State[GlobalState, A] =
    State[Map[E#Id, E], A](body).transformS(f, g)

  override def store(entity: E): State[GlobalState, Unit] =
    state(s => (s.updated(entity.id, entity), ()))

  override def findById(id: E#Id): State[GlobalState, Option[E]] =
    state(s => (s, s.get(id)))

  override def deleteById(id: E#Id): State[GlobalState, Unit] =
    state(s => (s - id, ()))

  override def findAll(): State[GlobalState, List[E]] =
    state(s => (s, s.values.toList))
}
