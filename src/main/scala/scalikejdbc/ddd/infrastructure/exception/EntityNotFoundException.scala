package scalikejdbc.ddd.infrastructure.exception

import scalikejdbc.ddd.infrastructure.Identifier

/**
 * Represents entity not found.
 */
case class EntityNotFoundException(message: String) extends Exception(message)

/**
 * EntityNotFoundException factory.
 */
object EntityNotFoundException {

  def apply(identifier: Identifier[Any]): EntityNotFoundException = EntityNotFoundException(identifier)

}
