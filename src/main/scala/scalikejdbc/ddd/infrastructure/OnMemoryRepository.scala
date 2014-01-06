package scalikejdbc.ddd.infrastructure

import scala.util.{ Success, Try }
import scalikejdbc.ddd.infrastructure.exception.EntityNotFoundException

abstract class OnMemoryRepository[ID <: Identifier[_], E <: Entity[ID]](entities: Map[ID, E])
    extends Repository[ID, E] {

  protected def createInstance(entities: Map[ID, E]): This

  def existByIdentifier(identifier: ID)(implicit ctx: EntityIOContext): Try[Boolean] =
    Success(entities.contains(identifier))

  def resolveEntity(identifier: ID)(implicit ctx: EntityIOContext): Try[E] = Try {
    entities.get(identifier).getOrElse(throw EntityNotFoundException(identifier))
  }

  def storeEntity(entity: E)(implicit ctx: EntityIOContext): Try[(This, E)] = Success {
    (createInstance(entities + (entity.id -> entity)), entity)
  }

  def deleteByIdentifier(identifier: ID)(implicit ctx: EntityIOContext): Try[(This, E)] = Try {
    entities.get(identifier).map { entity => (createInstance(entities - identifier), entity) }
      .getOrElse(throw EntityNotFoundException(identifier))
  }

}
