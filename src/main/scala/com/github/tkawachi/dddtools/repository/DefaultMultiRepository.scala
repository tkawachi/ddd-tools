package com.github.tkawachi.dddtools.repository

import cats.Applicative
import cats.implicits._
import com.github.tkawachi.dddtools.Entity

import scala.language.higherKinds

abstract class DefaultMultiRepository[E <: Entity, F[_]: Applicative]
    extends StandardRepository[E, F] {
  override def storeMulti(entities: List[E]): F[Unit] =
    entities.traverse_(store)

  override def findByIds(ids: List[E#Id]): F[List[E]] =
    ids.traverse(findById).map(_.flatten)

  override def deleteByIds(ids: List[E#Id]): F[Unit] =
    ids.traverse_(deleteById)
}
