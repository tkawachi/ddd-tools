package example.domain

import cats.~>
import com.github.tkawachi.dddtools.repository.StandardRepository
import com.github.tkawachi.dddtools.typeclass.FunctorK

import scala.language.higherKinds

trait UserRepository[F[_]] extends StandardRepository[User, F] {
  self =>
  def findByName(name: String): F[Option[User]]

  def mapK[G[_]](f: F ~> G): UserRepository[G] = new UserRepository[G] {
    override def findByName(name: String): G[Option[User]] =
      f(self.findByName(name))
    override def store(entity: User): G[Unit] = f(self.store(entity))
    override def findById(id: UserId): G[Option[User]] = f(self.findById(id))
    override def findByIds(ids: List[UserId]): G[List[User]] =
      f(self.findByIds(ids))
    override def storeMulti(entities: List[User]): G[Unit] =
      f(self.storeMulti(entities))
    override def deleteById(id: UserId): G[Unit] = f(self.deleteById(id))
    override def deleteByIds(ids: List[UserId]): G[Unit] =
      f(self.deleteByIds(ids))
  }
}

object UserRepository {
  def apply[F[_]](implicit instance: UserRepository[F]): UserRepository[F] =
    instance

  implicit val functorKUserRepository: FunctorK[UserRepository] =
    new FunctorK[UserRepository] {
      override def mapK[G[_], H[_]](fg: UserRepository[G],
                                    f: ~>[G, H]): UserRepository[H] =
        fg.mapK(f)
    }
}
