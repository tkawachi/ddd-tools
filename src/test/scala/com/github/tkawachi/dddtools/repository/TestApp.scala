package com.github.tkawachi.dddtools.repository

import cats.Monad
import cats.data.OptionT

import scala.language.higherKinds

class TestApp[F[_]: Monad: BookRepository: UserRepository] {
  def findOwner(bookId: BookId): F[Option[User]] =
    (for {
      book <- OptionT(BookRepository[F].findById(bookId))
      owner <- OptionT(UserRepository[F].findById(book.ownerId))
    } yield owner).value
}
