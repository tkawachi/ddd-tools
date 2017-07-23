package com.github.tkawachi.dddtools.typeclass

import cats.~>

import scala.language.higherKinds

// TODO delete?
trait FunctorK[F[_[_]]] {
  def mapK[G[_], H[_]](fg: F[G], f: G ~> H): F[H]
}

object FunctorK {
  def apply[F[_[_]]: FunctorK]: FunctorK[F] = implicitly
}
