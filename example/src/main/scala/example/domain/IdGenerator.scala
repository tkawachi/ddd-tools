package example.domain

import cats.~>

import scala.language.higherKinds

trait IdGenerator[F[_]] {
  self =>
  def generate: F[Long]

  def mapK[G[_]](f: F ~> G): IdGenerator[G] = new IdGenerator[G] {
    override def generate: G[Long] = f(self.generate)
  }
}

object IdGenerator {
  def apply[F[_]: IdGenerator]: IdGenerator[F] = implicitly
}
