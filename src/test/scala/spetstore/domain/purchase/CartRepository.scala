package spetstore.domain.purchase

import scalikejdbc.ddd.infrastructure.Repository

/**
 * [[spetstore.domain.purchase.Cart]]のためのリポジトリ責務。
 */
trait CartRepository extends Repository[CartId, Cart] {

  type This = CartRepository

}

/**
 * コンパニオンオブジェクト。
 */
object CartRepository {

  /**
   * メモリ用リポジトリを生成する。
   *
   * @param entities エンティティの集合
   * @return [[spetstore.domain.purchase.CartRepository]]
   */
  def ofMemory(entities: Map[CartId, Cart] = Map.empty): CartRepository =
    new CartRepositoryOnMemory(entities)

  /**
   * JDBC用リポジトリを生成する。
   *
   * @return [[spetstore.domain.purchase.CartRepository]]
   */
  def ofJDBC: CartRepository = new CartRepositoryOnJDBC

}
