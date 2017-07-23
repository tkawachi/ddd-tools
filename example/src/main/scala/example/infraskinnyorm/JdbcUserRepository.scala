package example.infraskinnyorm

import example.domain.{User, UserId, UserRepository}
import scalikejdbc._
import skinny.orm.{Alias, SkinnyCRUDMapperWithId}

import scala.concurrent.ExecutionContext

object JdbcUserRepository {
  val dao = new SkinnyCRUDMapperWithId[UserId, User] {
    override def idToRawValue(id: UserId): Any = id.value

    override def rawValueToId(value: Any): UserId =
      UserId(value.asInstanceOf[Long])

    override def defaultAlias: Alias[User] = createAlias("u")

    override def extract(rs: WrappedResultSet,
                         n: scalikejdbc.ResultName[User]): User =
      autoConstruct(rs, n)
  }
}

class JdbcUserRepository()(implicit ec: ExecutionContext)
    extends JdbcStandardRepository[User](JdbcUserRepository.dao)(ec,
                                                                 userIdBinder)
    with UserRepository[Db] {
  override def findByName(name: String): Db[Option[User]] = Db.async {
    implicit s =>
      dao.findBy(sqls.eq(dao.column.c("name"), name))
  }
}
