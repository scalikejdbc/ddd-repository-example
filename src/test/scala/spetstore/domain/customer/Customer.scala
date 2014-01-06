package spetstore.domain.customer

import spetstore.domain.basic.SexType
import spetstore.domain.pet.PetId
import spetstore.domain.purchase.{ Order, Cart, CartItem }
import scalikejdbc.ddd.infrastructure.{ EntityIOContext, Entity }
import scala.util.Try

/**
 * ペットストアの顧客を表すエンティティ。
 *
 * @param id 識別子
 * @param status [[spetstore.domain.customer.CustomerStatus]]
 * @param name 名前
 * @param sexType 性別
 * @param profile [[spetstore.domain.customer.CustomerProfile]]
 * @param config [[spetstore.domain.customer.CustomerConfig]]
 */
case class Customer(id: CustomerId = CustomerId(),
  status: CustomerStatus.Value,
  name: String,
  sexType: Option[SexType.Value] = None,
  profile: CustomerProfile,
  config: CustomerConfig)
    extends Entity[CustomerId] {

  def addCartItem(cart: Cart, cartItem: CartItem): Cart =
    cart.addCartItem(cartItem)

  def removeCartItemByPetId(cart: Cart, petId: PetId): Cart =
    cart.removeCartItemByPetId(petId)

  def newOrderFromCart(cart: Cart)(implicit cr: CustomerRepository, ctx: EntityIOContext): Try[Order] =
    Order.fromCart(cart)

}

