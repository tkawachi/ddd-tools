package example.application

import cats.data.State
import com.github.tkawachi.dddtools.repository.StateRepository
import example.domain.{Book, BookRepository}

class StateBookRepository
    extends StateRepository[Book, TestState](
      _.books,
      (db, books) => db.copy(books = books))
    with BookRepository[State[TestState, ?]]
