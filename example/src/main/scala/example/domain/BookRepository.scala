package example.domain

import cats.~>
import com.github.tkawachi.dddtools.repository.StandardRepository

import scala.language.higherKinds

trait BookRepository[F[_]] extends StandardRepository[Book, F] {
  self =>
  def transform[G[_]](nat: F ~> G): BookRepository[G] = new BookRepository[G] {
    override def deleteById(id: BookId): G[Unit] = nat(self.deleteById(id))

    override def store(entity: Book): G[Unit] = nat(self.store(entity))

    override def findById(id: BookId): G[Option[Book]] = nat(self.findById(id))

    override def deleteByIds(ids: List[BookId]): G[Unit] =
      nat(self.deleteByIds(ids))

    override def findByIds(ids: List[BookId]): G[List[Book]] =
      nat(self.findByIds(ids))

    override def storeMulti(entities: List[Book]): G[Unit] =
      nat(self.storeMulti(entities))
  }
}

object BookRepository {
  def apply[F[_]](implicit instance: BookRepository[F]): BookRepository[F] =
    instance
}
