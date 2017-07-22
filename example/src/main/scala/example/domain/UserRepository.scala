package example.domain

import com.github.tkawachi.dddtools.repository.StandardRepository

import scala.language.higherKinds

trait UserRepository[F[_]] extends StandardRepository[User, F] {
  def findByName(name: String): F[Option[User]]
}

object UserRepository {
  def apply[F[_]](implicit instance: UserRepository[F]): UserRepository[F] =
    instance
}
