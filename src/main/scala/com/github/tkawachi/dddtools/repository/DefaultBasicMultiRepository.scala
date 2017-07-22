package com.github.tkawachi.dddtools.repository

import cats.Applicative
import cats.implicits._
import com.github.tkawachi.dddtools.Entity

import scala.language.higherKinds

trait DefaultBasicMultiRepository[E <: Entity, F[_]]
    extends BasicRepository[E, F]
    with BasicMultiRepository[E, F] {

  protected implicit def applicativeF: Applicative[F]

  override def storeMulti(entities: List[E]): F[Unit] =
    entities.traverse_(store)

  override def findByIds(ids: List[E#Id]): F[List[E]] =
    ids.traverse(findById).map(_.flatten)
}
