package com.github.tkawachi.dddtools.repository

import com.github.tkawachi.dddtools.Entity

import scala.language.higherKinds

trait BasicRepository[E <: Entity, F[_]] {
  def store(entity: E): F[Unit]

  def findById(id: E#Id): F[Option[E]]
}

trait DeleteSupport[E <: Entity, F[_]] {
  def deleteById(id: E#Id): F[Unit]
}

trait FindAllSupport[E <: Entity, F[_]] {
  def findAll(): F[Seq[E]]
}
