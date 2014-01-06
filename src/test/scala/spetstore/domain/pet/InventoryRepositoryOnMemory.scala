package spetstore.domain.pet

import scalikejdbc.ddd.infrastructure.RepositoryOnMemory

private[pet] class InventoryRepositoryOnMemory(entities: Map[InventoryId, Inventory])
    extends RepositoryOnMemory[InventoryId, Inventory](entities) with InventoryRepository {

  protected def createInstance(entities: Map[InventoryId, Inventory]): This =
    new InventoryRepositoryOnMemory(entities)

}
