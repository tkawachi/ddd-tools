package example

import cats.data.Kleisli
import example.domain.{BookId, UserId}
import scalikejdbc.{Binders, DBSession}

import scala.concurrent.{ExecutionContext, Future}

package object infrascalikejdbc {
  type Db[A] = Kleisli[Future, DBSession, A]
  object Db {
    def apply[A](f: DBSession => Future[A]): Db[A] = Kleisli(f)

    def async[A](f: DBSession => A)(implicit ec: ExecutionContext): Db[A] =
      Kleisli(s => Future(f(s)))
  }

  implicit def bookIdBinder: Binders[BookId] =
    Binders.long.xmap(BookId.apply, _.value)

  implicit def userIdBinder: Binders[UserId] =
    Binders.long.xmap(UserId.apply, _.value)
}
