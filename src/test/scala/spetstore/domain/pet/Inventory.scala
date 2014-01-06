package spetstore.domain.pet

import scalikejdbc.ddd.infrastructure.Entity

/**
 * 在庫を表すエンティティ。
 *
 * @param id [[spetstore.domain.pet.InventoryId]]
 * @param petId [[spetstore.domain.pet.PetId]]
 * @param quantity 在庫数量
 */
case class Inventory(id: InventoryId, petId: PetId, quantity: Int)
  extends Entity[InventoryId]

