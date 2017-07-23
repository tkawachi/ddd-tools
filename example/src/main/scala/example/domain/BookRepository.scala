package example.domain

import cats.~>
import com.github.tkawachi.dddtools.repository.StandardRepository
import com.github.tkawachi.dddtools.typeclass.FunctorK

import scala.language.higherKinds

trait BookRepository[F[_]] extends StandardRepository[Book, F] {
  self =>

  def mapK[G[_]](f: F ~> G): BookRepository[G] = new BookRepository[G] {
    override def deleteById(id: BookId): G[Unit] = f(self.deleteById(id))
    override def store(entity: Book): G[Unit] = f(self.store(entity))
    override def findById(id: BookId): G[Option[Book]] = f(self.findById(id))
    override def deleteByIds(ids: List[BookId]): G[Unit] =
      f(self.deleteByIds(ids))
    override def findByIds(ids: List[BookId]): G[List[Book]] =
      f(self.findByIds(ids))
    override def storeMulti(entities: List[Book]): G[Unit] =
      f(self.storeMulti(entities))
  }
}

object BookRepository {
  def apply[F[_]: BookRepository]: BookRepository[F] = implicitly

  implicit val functorKBookRepository: FunctorK[BookRepository] =
    new FunctorK[BookRepository] {
      override def mapK[G[_], H[_]](fg: BookRepository[G],
                                    f: ~>[G, H]): BookRepository[H] =
        fg.mapK(f)
    }
}
