package scalikejdbc.ddd.infrastructure

object EmptyIdentifier extends EmptyIdentifier

trait EmptyIdentifier extends Identifier[Nothing] {

  def value: Nothing = throw new NoSuchElementException

  override val isDefined = false

  override def equals(obj: Any) = obj match {
    case that: EmptyIdentifier => this eq that
    case _ => false
  }

  override def hashCode = 0

}