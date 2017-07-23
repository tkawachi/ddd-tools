package example.application

import cats.Monad
import cats.data.OptionT
import cats.implicits._
import example.domain._

import scala.language.higherKinds

class MyApplication[F[_]: Monad: BookRepository: UserRepository: IdGenerator] {

  def findOwner(bookId: BookId): F[Option[User]] =
    (for {
      book <- OptionT(BookRepository[F].findById(bookId))
      owner <- OptionT(UserRepository[F].findById(book.ownerId))
    } yield owner).value

  def registerUser(userName: String): F[User] =
    for {
      long <- IdGenerator[F].generate
      user = User(UserId(long), userName)
      _ <- UserRepository[F].store(user)
    } yield user

  def registerBook(bookName: String, ownerId: UserId): F[Book] =
    for {
      long <- IdGenerator[F].generate
      book = Book(BookId(long), bookName, ownerId)
      _ <- BookRepository[F].store(book)
    } yield book

}
