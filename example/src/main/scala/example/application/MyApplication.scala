package example.application

import cats.Monad
import cats.data.OptionT
import example.domain.{BookId, BookRepository, User, UserRepository}

import scala.language.higherKinds

class MyApplication[F[_]: Monad: BookRepository: UserRepository] {
  def findOwner(bookId: BookId): F[Option[User]] =
    (for {
      book <- OptionT(BookRepository[F].findById(bookId))
      owner <- OptionT(UserRepository[F].findById(book.ownerId))
    } yield owner).value
}
