package scalikejdbc.ddd.infrastructure

import scala.util.Try
import j5ik2o.ddd.infrastructure._

import scalikejdbc._
import skinny.orm._

/**
 * JDBC Repository implementation.
 */
abstract class JDBCRepository[ID <: Identifier[_], E <: Entity[ID]] extends Repository[ID, E] with SkinnyCRUDMapper[E] {

  override def primaryKeyFieldName = "id"
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
    val count = countBy(sqls.eq(defaultAlias.field(primaryKeyFieldName), identifier.value))
    if (count == 0) false
    else if (count == 1) true
    else throw new IllegalStateException(s"$count entities are found for identifier: $identifier")
  }

  override def existByIdentifiers(identifiers: ID*)(implicit ctx: Ctx): Try[Boolean] = withDBSession(ctx) { implicit s =>
    countBy(sqls.in(defaultAlias.field(primaryKeyFieldName), identifiers.map(_.value))) > 0
  }

  def resolveEntity(identifier: ID)(implicit ctx: EntityIOContext): Try[E] = withDBSession(ctx) { implicit s =>
    findBy(sqls.eq(defaultAlias.field(primaryKeyFieldName), identifier.value)).getOrElse(throw EntityNotFoundException(identifier))
  }

  override def resolveEntities(identifiers: ID*)(implicit ctx: Ctx): Try[Seq[E]] = withDBSession(ctx) { implicit s =>
    findAllBy(sqls.in(defaultAlias.field(primaryKeyFieldName), identifiers.map(_.value)))
  }

  def storeEntity(entity: E)(implicit ctx: EntityIOContext): Try[(This, E)] = withDBSession(ctx) { implicit s =>
    if (entity.id.isDefined) {
      val count = updateBy(sqls.eq(column.field(primaryKeyFieldName), entity.id.value))
        .withAttributes(toNamedValues(entity).filterNot { case (k, _) => k.name == primaryKeyFieldName }: _*)
      if (count == 0) createWithAttributes(toNamedValues(entity): _*)
      else if (count > 1) throw new IllegalStateException(s"$count entities are found for identifier: $entity.id")
    } else {
      createWithAttributes(toNamedValues(entity): _*)
    }
    (this.asInstanceOf[This], entity)
  }

  def deleteByIdentifier(identifier: ID)(implicit ctx: EntityIOContext): Try[(This, E)] = withDBSession(ctx) { implicit s =>
    findBy(sqls.eq(defaultAlias.field(primaryKeyFieldName), identifier.value)).map { entity =>
      val count = deleteBy(sqls.eq(column.field(primaryKeyFieldName), identifier.value))
      if (count == 1) (this.asInstanceOf[This], entity)
      else if (count > 1) throw new IllegalStateException(s"$count entities are found for identifier: $identifier")
      else throw RepositoryIOException(s"Entity (identifier: $identifier) is not found when deleting")
    }.getOrElse(throw RepositoryIOException(s"Entity (identifier: $identifier) is not found"))
  }

}
