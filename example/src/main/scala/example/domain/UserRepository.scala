package example.domain

import cats.~>
import com.github.tkawachi.dddtools.repository.StandardRepository

import scala.language.higherKinds

trait UserRepository[F[_]] extends StandardRepository[User, F] {
  self =>
  def findByName(name: String): F[Option[User]]

  def transform[G[_]](nat: F ~> G): UserRepository[G] = new UserRepository[G] {
    override def findByName(name: String): G[Option[User]] =
      nat(self.findByName(name))

    override def store(entity: User): G[Unit] = nat(self.store(entity))

    override def findById(id: UserId): G[Option[User]] = nat(self.findById(id))

    override def findByIds(ids: List[UserId]): G[List[User]] =
      nat(self.findByIds(ids))

    override def storeMulti(entities: List[User]): G[Unit] =
      nat(self.storeMulti(entities))

    override def deleteById(id: UserId): G[Unit] = nat(self.deleteById(id))

    override def deleteByIds(ids: List[UserId]): G[Unit] =
      nat(self.deleteByIds(ids))
  }
}

object UserRepository {
  def apply[F[_]](implicit instance: UserRepository[F]): UserRepository[F] =
    instance
}
