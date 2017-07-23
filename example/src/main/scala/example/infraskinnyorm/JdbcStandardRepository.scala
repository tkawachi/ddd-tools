package example.infraskinnyorm

import cats.implicits._
import com.github.tkawachi.dddtools.Entity
import com.github.tkawachi.dddtools.repository.StandardRepository
import scalikejdbc._
import skinny.orm.SkinnyCRUDMapperWithId

import scala.concurrent.{ExecutionContext, blocking}

class JdbcStandardRepository[E <: Entity](
    protected val dao: SkinnyCRUDMapperWithId[E#Id, E])(
    implicit ec: ExecutionContext,
    pbf: ParameterBinderFactory[E#Id])
    extends StandardRepository[E, Db] {

  override def store(entity: E): Db[Unit] =
    for {
      nUpdated <- Db.async(s =>
        dao.updateById(entity.id).withAttributes(???)(s))
      _ <- if (nUpdated == 0) {
        Db.async { implicit s =>
          blocking {
            dao.updateById(entity.id).withAttributes(???)
          }
        }
      } else {
        Db.unit
      }
    } yield ()

  override def findById(id: E#Id): Db[Option[E]] =
    Db.async { implicit s =>
      blocking(dao.findById(id))
    }

  override def storeMulti(entities: List[E]): Db[Unit] =
    entities.traverse_(store)

  override def findByIds(ids: List[E#Id]): Db[List[E]] =
    Db.async { implicit s =>
      blocking {
        dao.findAllByIds(ids: _*)
      }
    }

  override def deleteByIds(ids: List[E#Id]): Db[Unit] =
    Db.async { implicit s =>
      blocking {
        dao.deleteBy(sqls.in(dao.primaryKeyField, ids))
      }
    }

  override def deleteById(id: E#Id): Db[Unit] = Db.async { implicit s =>
    blocking(dao.deleteById(id))
  }
}
