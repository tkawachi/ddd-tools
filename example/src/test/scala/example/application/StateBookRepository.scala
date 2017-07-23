package example.application

import cats.data.State
import com.github.tkawachi.dddtools.repository.StateRepository
import example.domain.{Book, BookId, BookRepository}

class StateBookRepository
    extends StateRepository[Book]
    with BookRepository[State[Map[BookId, Book], ?]]
