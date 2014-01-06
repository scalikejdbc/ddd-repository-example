package spetstore.domain.purchase

import scalikejdbc.ddd.infrastructure.Repository

/**
 * [[spetstore.domain.purchase.Order]]のためのリポジトリ責務。
 */
trait OrderRepository extends Repository[OrderId, Order] {

  type This = OrderRepository

}

/**
 * コンパニオンオブジェクト。
 */
object OrderRepository {

  /**
   * メモリ用リポジトリを生成する。
   *
   * @param entities エンティティのマップ
   * @return [[spetstore.domain.purchase.OrderRepository]]
   */
  def ofMemory(entities: Map[OrderId, Order] = Map.empty): OrderRepository =
    new OrderRepositoryOnMemory(entities)

  /**
   * JDBC用リポジトリを生成する。
   *
   * @return [[spetstore.domain.purchase.OrderRepository]]
   */
  def ofJDBC: OrderRepository = new OrderRepositoryOnJDBC

}

