package spetstore.domain.purchase

import spetstore.domain.pet.Pet

/**
 * 注文する商品を表す値オブジェクト。
 *
 * @param pet [[spetstore.domain.pet.Pet]]
 * @param quantity 数量
 */
case class OrderItem(pet: Pet, quantity: Int) {

  /**
   * 小計。
   */
  val subTotalPrice: BigDecimal = pet.price * quantity

}

/**
 * コンパニオンオブジェクト。
 */
object OrderItem {

  /**
   * [[spetstore.domain.purchase.CartItem]]から
   * [[spetstore.domain.purchase.OrderItem]]を
   * 生成する。
   *
   * @param cartItem [[spetstore.domain.purchase.CartItem]]
   * @return [[spetstore.domain.purchase.OrderItem]]
   */
  def fromCartItem(cartItem: CartItem): OrderItem =
    OrderItem(cartItem.pet, cartItem.quantity)

}
