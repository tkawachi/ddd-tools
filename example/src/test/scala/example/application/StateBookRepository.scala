package example.application

import cats.data.State
import com.github.tkawachi.dddtools.repository.{EntityMap, StateRepository}
import example.domain.{Book, BookRepository}

class StateBookRepository
    extends StateRepository[Book]
    with BookRepository[State[EntityMap[Book], ?]]
