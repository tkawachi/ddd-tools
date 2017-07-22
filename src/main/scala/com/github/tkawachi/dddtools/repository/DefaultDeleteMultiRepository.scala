package com.github.tkawachi.dddtools.repository

import cats.Applicative
import cats.implicits._
import com.github.tkawachi.dddtools.Entity

import scala.language.higherKinds

trait DefaultDeleteMultiRepository[E <: Entity, F[_]]
    extends DeleteRepository[E, F]
    with DeleteMultiRepository[E, F] {

  protected implicit def applicativeF: Applicative[F]

  override def deleteByIds(ids: List[E#Id]): F[Unit] = ids.traverse_(deleteById)
}
