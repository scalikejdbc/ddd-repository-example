package scalikejdbc.ddd.infrastructure

import scala.util.Try
import scalikejdbc._, SQLInterpolation._
import skinny.orm._
import scalikejdbc.ddd.infrastructure.exception.{ RepositoryIOException, EntityNotFoundException }

/**
 * JDBC Repository implementation.
 */
abstract class JDBCRepository[ID <: Identifier[_], E <: Entity[ID]]
    extends Repository[ID, E] with SkinnyCRUDMapper[E] {

  override def primaryKeyName = "id"
  override def defaultAlias = syntax
  override def useAutoIncrementPrimaryKey = false

  protected def toNamedValues(entity: E): Seq[(Symbol, Any)]

  protected def extractDBSession(ctx: EntityIOContext): DBSession = ctx match {
    case JDBCEntityIOContext(dbSession) => dbSession
    case _ => throw new IllegalStateException(s"Unexpected context is bound (expected: JDBCEntityIOContext, actual: $ctx)")
  }

  def existByIdentifier(identifier: ID)(implicit ctx: EntityIOContext): Try[Boolean] = Try {
    implicit val dbSession = extractDBSession(ctx)
    countBy(sqls.eq(column.c(primaryKeyName), identifier.value)) > 0
  }

  def resolveEntity(identifier: ID)(implicit ctx: EntityIOContext): Try[E] = Try {
    implicit val dbSession = extractDBSession(ctx)
    findBy(sqls.eq(column.c(primaryKeyName), identifier.value)).getOrElse(throw EntityNotFoundException(identifier))
  }

  def storeEntity(entity: E)(implicit ctx: EntityIOContext): Try[(This, E)] = Try {
    implicit val dbSession = extractDBSession(ctx)
    if (entity.id.isDefined) {
      val notFound = updateBy(sqls.eq(column.c(primaryKeyName), entity.id.value)).withAttributes(toNamedValues(entity): _*) == 0
      if (notFound) {
        createWithAttributes(toNamedValues(entity): _*)
      }
    } else {
      createWithAttributes(toNamedValues(entity): _*)
    }
    (this.asInstanceOf[This], entity)
  }

  def deleteByIdentifier(identifier: ID)(implicit ctx: EntityIOContext): Try[(This, E)] = {
    implicit val dbSession = extractDBSession(ctx)
    Try(findBy(sqls.eq(column.c(primaryKeyName), identifier.value)).map { entity =>
      if (deleteBy(sqls.eq(column.c(primaryKeyName), identifier.value)) > 0) {
        (this.asInstanceOf[This], entity)
      } else {
        throw RepositoryIOException(s"Failed to delete $identifier")
      }
    }.getOrElse(throw RepositoryIOException(s"Failed to delete $identifier")))
  }

}
