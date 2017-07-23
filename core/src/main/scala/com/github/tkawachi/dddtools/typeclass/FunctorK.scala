package com.github.tkawachi.dddtools.typeclass

import cats.~>

import scala.language.higherKinds

trait FunctorK[F[_[_]]] {
  def mapK[G[_], H[_]](fg: F[G], f: G ~> H): F[H]
}

object FunctorK {
  def apply[F[_[_]]](implicit instance: FunctorK[F]): FunctorK[F] = instance
}
