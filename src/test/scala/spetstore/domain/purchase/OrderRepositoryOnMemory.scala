package spetstore.domain.purchase

import scalikejdbc.ddd.infrastructure.RepositoryOnMemory

/**
 *
 * @param entities エンティティのマップ
 */
private[purchase] class OrderRepositoryOnMemory(entities: Map[OrderId, Order])
    extends RepositoryOnMemory[OrderId, Order](entities) with OrderRepository {

  protected def createInstance(entities: Map[OrderId, Order]): This =
    new OrderRepositoryOnMemory(entities)

}
