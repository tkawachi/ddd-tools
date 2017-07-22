package example.infrascalikejdbc

import example.domain.{Book, BookId, BookRepository}
import scalikejdbc._
import cats.implicits._
import skinny.orm.{Alias, SkinnyCRUDMapper}
import scala.concurrent.blocking

import scala.concurrent.ExecutionContext

class JdbcBookRepository()(implicit ec: ExecutionContext)
    extends BookRepository[Result] {

  import JdbcBookRepository._

  override def store(entity: Book): Result[Unit] =
    for {
      existing <- findById(entity.id)
      _ <- (existing: Option[Book])
        .map(_ =>
          Result.async { implicit s =>
            blocking {
              dao.updateById(entity.id.value).withAttributes(???)
            }
        })
        .getOrElse(Result.async { implicit s =>
          blocking {
            dao.createWithAttributes(???)
          }
        })
    } yield ()

  override def findById(id: BookId): Result[Option[Book]] =
    Result.async { implicit s =>
      blocking(dao.findById(id.value))
    }

  override def storeMulti(entities: List[Book]): Result[Unit] =
    entities.traverse_(store)

  override def findByIds(ids: List[BookId]): Result[List[Book]] =
    Result.async { implicit s =>
      blocking {
        dao.findAllByIds(ids.map(_.value): _*)
      }
    }

  override def deleteByIds(ids: List[BookId]): Result[Unit] =
    ids.traverse_(deleteById)

  override def deleteById(id: BookId): Result[Unit] = Result.async {
    implicit s =>
      blocking(dao.deleteById(id.value))
  }
}

object JdbcBookRepository {
  val dao = new SkinnyCRUDMapper[Book] {
    override def defaultAlias: Alias[Book] = createAlias("bo")

    override def extract(rs: WrappedResultSet, n: ResultName[Book]): Book =
      autoConstruct(rs, n)

    autoNamedValues
  }
}
