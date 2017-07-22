package com.github.tkawachi.dddtools.repository

case class TestState(users: Map[UserId, User], books: Map[BookId, Book])
