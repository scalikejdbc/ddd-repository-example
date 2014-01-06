package spetstore.domain.purchase

import spetstore.domain.pet.Pet

/**
 * 注文する商品を表す値オブジェクト。
 *
 * @param pet [[spetstore.domain.pet.Pet]]
 * @param quantity 数量
 * @param inStock 後で購入する場合true
 */
case class CartItem(pet: Pet, quantity: Int, inStock: Boolean) {

  /**
   * 小計。
   */
  lazy val subTotalPrice: BigDecimal = pet.price * quantity

  /**
   * 数量をインクリメントする。
   *
   * @return [[spetstore.domain.purchase.CartItem]]
   */
  def incrementQuantity: CartItem = addQuantity(1)

  /**
   * 数量を追加する。
   *
   * @return [[spetstore.domain.purchase.CartItem]]
   */
  def addQuantity(otherQuantity: Int): CartItem = copy(quantity = quantity + otherQuantity)

  /**
   * 数量を更新する。
   *
   * @param quantity 数量
   * @return [[spetstore.domain.purchase.CartItem]]
   */
  def withQuantity(quantity: Int): CartItem = copy(quantity = quantity)

}
