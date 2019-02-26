package j5ik2o.ddd.infrastructure

/**
 * Represents entity not found.
 */
case class EntityNotFoundException(message: String) extends Exception(message)

/**
 * EntityNotFoundException factory.
 */
object EntityNotFoundException {

  def apply(identifier: Identifier[Any]): EntityNotFoundException = EntityNotFoundException(identifier.value.toString)

}
