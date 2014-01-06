package scalikejdbc.ddd.infrastructure

/**
 * TODO English doc
 * [[scalikejdbc.ddd.infrastructure.Entity]]の識別子を表すトレイト。
 *
 * @tparam A 識別子の値型
 */
trait Identifier[+A] {

  /**
   * 識別子の値。
   */
  def value: A

  val isDefined: Boolean = true

  val isEmpty: Boolean = !isDefined

  override def equals(obj: Any) = obj match {
    case that: Identifier[_] =>
      value == that.value
    case _ => false
  }

  override def hashCode = 31 * value.##

}

