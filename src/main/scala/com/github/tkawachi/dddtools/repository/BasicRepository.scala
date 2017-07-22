package com.github.tkawachi.dddtools.repository

import com.github.tkawachi.dddtools.Entity

import scala.language.higherKinds

trait BasicRepository[E <: Entity, F[_]] {
  def store(entity: E): F[Unit]

  def findById(id: E#Id): F[Option[E]]
}

trait BasicMultiRepository[E <: Entity, F[_]] {
  def storeMulti(entities: List[E]): F[Unit]

  def findByIds(ids: List[E#Id]): F[List[E]]
}

trait DeleteRepository[E <: Entity, F[_]] {
  def deleteById(id: E#Id): F[Unit]
}

trait DeleteMultiRepository[E <: Entity, F[_]] {
  def deleteByIds(ids: List[E#Id]): F[Unit]
}

trait FindAllRepository[E <: Entity, F[_]] {
  def findAll(): F[List[E]]
}
