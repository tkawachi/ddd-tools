package example.infraskinnyorm

import example.domain.{Book, BookId, BookRepository}
import scalikejdbc._
import skinny.orm.{Alias, SkinnyCRUDMapperWithId}

import scala.concurrent.ExecutionContext

object JdbcBookRepository {
  val dao = new SkinnyCRUDMapperWithId[BookId, Book] {
    override def defaultAlias: Alias[Book] = createAlias("bo")

    override def extract(rs: WrappedResultSet, n: ResultName[Book]): Book =
      autoConstruct(rs, n)

    override def idToRawValue(id: BookId): Any = id.value

    override def rawValueToId(value: Any): BookId =
      BookId(value.asInstanceOf[Long])
  }
}

class JdbcBookRepository()(implicit ec: ExecutionContext)
    extends JdbcStandardRepository[Book](JdbcBookRepository.dao)(ec,
                                                                 bookIdBinder)
    with BookRepository[Db] {}
