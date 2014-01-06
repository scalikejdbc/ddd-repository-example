package scalikejdbc.ddd.infrastructure

/**
 * TODO English doc
 * DDDのエンティティ責務を表すトレイト。
 *
 * @tparam ID [[scalikejdbc.ddd.infrastructure.Identifier]]
 */
trait Entity[ID <: Identifier[_]] {

  /**
   * 識別子。
   */
  val id: ID

  override def equals(obj: Any): Boolean = this match {
    case that: Entity[_] => id == that.id
    case _ => false
  }

  override def hashCode: Int = 31 * id.##

}
