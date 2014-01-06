package scalikejdbc.ddd.infrastructure

/**
 * Represents empty identifier.
 */
object EmptyIdentifier extends EmptyIdentifier

/**
 * Represents empty identifier.
 */
trait EmptyIdentifier extends Identifier[Nothing] {

  override val value: Nothing = throw new NoSuchElementException

  override val isDefined = false

  override def equals(obj: Any) = obj match {
    case that: EmptyIdentifier => this eq that
    case _ => false
  }

  override def hashCode = 0

}