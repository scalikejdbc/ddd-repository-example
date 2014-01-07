package scalikejdbc.ddd.infrastructure

import scala.util.Try
import j5ik2o.ddd.infrastructure._

import scalikejdbc._, SQLInterpolation._
import skinny.orm._

/**
 * JDBC Repository implementation.
 */
abstract class JDBCRepository[ID <: Identifier[_], E <: Entity[ID]] extends Repository[ID, E] with SkinnyCRUDMapper[E] {

  override def primaryKeyName = "id"
  override def defaultAlias = createAlias(tableName)
  override def useAutoIncrementPrimaryKey = false

  protected def toNamedValues(entity: E): Seq[(Symbol, Any)]

  protected def withDBSession[A](ctx: EntityIOContext)(f: DBSession => A): Try[A] = Try {
    ctx match {
      case JDBCEntityIOContext(dbSession) => f(dbSession)
      case _ => throw new IllegalStateException(s"Unexpected context is bound (expected: JDBCEntityIOContext, actual: $ctx)")
    }
  }

  def existByIdentifier(identifier: ID)(implicit ctx: EntityIOContext): Try[Boolean] = withDBSession(ctx) { implicit s =>
    countBy(sqls.eq(defaultAlias.field(primaryKeyName), identifier.value)) > 0
  }

  override def existByIdentifiers(identifiers: Seq[ID])(implicit ctx: Ctx): Try[Boolean] = withDBSession(ctx) { implicit s =>
    countBy(sqls.in(defaultAlias.field(primaryKeyName), identifiers.map(_.value))) > 0
  }

  def resolveEntity(identifier: ID)(implicit ctx: EntityIOContext): Try[E] = withDBSession(ctx) { implicit s =>
    findBy(sqls.eq(defaultAlias.field(primaryKeyName), identifier.value)).getOrElse(throw EntityNotFoundException(identifier))
  }

  override def resolveEntities(identifiers: Seq[ID])(implicit ctx: Ctx): Try[Seq[E]] = withDBSession(ctx) { implicit s =>
    findAllBy(sqls.eq(defaultAlias.field(primaryKeyName), identifiers.map(_.value)))
  }

  def storeEntity(entity: E)(implicit ctx: EntityIOContext): Try[(This, E)] = withDBSession(ctx) { implicit s =>
    if (entity.id.isDefined) {
      val notFound = updateBy(sqls.eq(column.field(primaryKeyName), entity.id.value))
        .withAttributes(toNamedValues(entity).filterNot { case (k, _) => k.name == primaryKeyName }: _*) == 0
      if (notFound) {
        createWithAttributes(toNamedValues(entity): _*)
      }
    } else {
      createWithAttributes(toNamedValues(entity): _*)
    }
    (this.asInstanceOf[This], entity)
  }

  def deleteByIdentifier(identifier: ID)(implicit ctx: EntityIOContext): Try[(This, E)] = withDBSession(ctx) { implicit s =>
    findBy(sqls.eq(defaultAlias.field(primaryKeyName), identifier.value)).map { entity =>
      if (deleteBy(sqls.eq(column.field(primaryKeyName), identifier.value)) > 0) {
        (this.asInstanceOf[This], entity)
      } else {
        throw RepositoryIOException(s"Failed to delete $identifier")
      }
    }.getOrElse(throw RepositoryIOException(s"Failed to delete $identifier"))
  }

}
