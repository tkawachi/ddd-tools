package example.domain

import com.github.tkawachi.dddtools.repository.StandardRepository

import scala.language.higherKinds

trait BookRepository[F[_]] extends StandardRepository[Book, F] {}

object BookRepository {
  def apply[F[_]](implicit instance: BookRepository[F]): BookRepository[F] =
    instance
}