package j5ik2o.ddd.infrastructure

/**
 * Entity identifier.
 *
 * @tparam A value type of identifier
 */
trait Identifier[+A] {

  /**
   * Identifier value.
   */
  val value: A

  val isDefined: Boolean = true

  val isEmpty: Boolean = !isDefined

  override def equals(obj: Any) = obj match {
    case that: Identifier[_] => value == that.value
    case _ => false
  }

  override def hashCode = 31 * value.##

}

