package com.github.tkawachi.dddtools.repository

import cats.data.State

class StateBookRepository
    extends StateRepository[Book, TestState](
      _.books,
      (db, books) => db.copy(books = books))
    with BookRepository[State[TestState, ?]]
