package spetstore.domain.pet

import scalikejdbc.ddd.infrastructure.Repository

trait InventoryRepository extends Repository[InventoryId, Inventory] {

  type This = InventoryRepository

}

object InventoryRepository {

  def ofMemory(entities: Map[InventoryId, Inventory] = Map.empty): InventoryRepository =
    new InventoryRepositoryOnMemory(entities)

}

