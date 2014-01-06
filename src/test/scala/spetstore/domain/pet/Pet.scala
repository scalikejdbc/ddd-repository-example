package spetstore.domain.pet

import scalikejdbc.ddd.infrastructure.{ EntityIOContext, Entity }
import scala.util.Try
import spetstore.domain.basic.SexType

/**
 * ペットを表すエンティティ。
 *
 * @param id 識別子
 * @param petTypeId [[spetstore.domain.pet.PetTypeId]]
 * @param name 名前
 * @param description 説明
 * @param price 価格
 */
case class Pet(id: PetId = PetId(),
  petTypeId: PetTypeId,
  name: String,
  sexType: SexType.Value,
  description: Option[String] = None,
  price: BigDecimal,
  supplierId: SupplierId)
    extends Entity[PetId] {

  /**
   * [[spetstore.domain.pet.PetType]]を取得する。
   *
   * @param ptr [[spetstore.domain.pet.PetTypeRepository]]
   * @param ctx [[scalikejdbc.ddd.infrastructure.EntityIOContext]]
   * @return `Try`にラップされた[[spetstore.domain.pet.PetType]]
   */
  def petType(implicit ptr: PetTypeRepository, ctx: EntityIOContext): Try[PetType] =
    ptr.resolveEntity(petTypeId)

}

