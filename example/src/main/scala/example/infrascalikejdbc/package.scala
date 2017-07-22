package example

import cats.data.Kleisli
import example.domain.{BookId, UserId}
import scalikejdbc.{Binders, DBSession}

import scala.concurrent.{ExecutionContext, Future}

package object infrascalikejdbc {
  type Result[A] = Kleisli[Future, DBSession, A]
  object Result {
    def apply[A](f: DBSession => Future[A]): Result[A] = Kleisli(f)

    def async[A](f: DBSession => A)(implicit ec: ExecutionContext): Result[A] =
      Kleisli(s => Future(f(s)))
  }

  implicit def bookIdBinder: Binders[BookId] =
    Binders.long.xmap(BookId.apply, _.value)

  implicit def userIdBinder: Binders[UserId] =
    Binders.long.xmap(UserId.apply, _.value)
}
