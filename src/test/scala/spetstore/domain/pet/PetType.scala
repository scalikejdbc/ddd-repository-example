package spetstore.domain.pet

import scalikejdbc.ddd.infrastructure.Entity

/**
 * ペットの品種を表すエンティティ。
 *
 * @param id 識別子
 * @param categoryId [[spetstore.domain.pet.CategoryId]]
 * @param name 名前
 * @param description 説明
 */
case class PetType(id: PetTypeId = PetTypeId(),
  categoryId: CategoryId,
  name: String,
  description: Option[String] = None)
    extends Entity[PetTypeId]

