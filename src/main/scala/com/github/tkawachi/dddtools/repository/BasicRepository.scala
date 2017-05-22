package com.github.tkawachi.dddtools.repository

import cats.Applicative
import cats.instances.all._
import cats.syntax.all._
import com.github.tkawachi.dddtools.Entity

import scala.language.higherKinds

trait BasicRepository[E <: Entity, F[_]] {
  def store(entity: E): F[Unit]

  def findById(id: E#Id): F[Option[E]]

  def storeMulti(entities: List[E])(implicit A: Applicative[F]): F[Unit] =
    entities.traverse_(store)

  def findByIds(ids: List[E#Id])(implicit A: Applicative[F]): F[List[E]] =
    ids.traverse(findById).map(_.flatten)
}

trait DeleteSupport[E <: Entity, F[_]] {
  def deleteById(id: E#Id): F[Unit]
}

trait FindAllSupport[E <: Entity, F[_]] {
  def findAll(): F[List[E]]
}
