package j5ik2o.ddd.infrastructure

/**
 * Represents responsibility of an entity in DDD
 */
trait Entity[ID <: Identifier[_]] {

  /**
   * Identifier
   */
  val id: ID

  override def equals(obj: Any): Boolean = this match {
    case that: Entity[_] => id == that.id
    case _ => false
  }

  override def hashCode: Int = 31 * id.##

}
