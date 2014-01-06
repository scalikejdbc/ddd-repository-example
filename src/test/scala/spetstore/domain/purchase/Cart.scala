package spetstore.domain.purchase

import spetstore.domain.customer.{ Customer, CustomerRepository, CustomerId }
import spetstore.domain.pet.{ PetId, Pet }
import scalikejdbc.ddd.infrastructure.{ EntityIOContext, Entity }
import scala.util.Try

/**
 * ショッピングカートを表すエンティティ。
 *
 * @param cartItems [[spetstore.domain.purchase.CartItem]]のリスト
 */
case class Cart(id: CartId = CartId(),
    customerId: CustomerId,
    cartItems: List[CartItem]) extends Entity[CartId] {

  /**
   * [[spetstore.domain.customer.Customer]]を取得する。
   *
   * @param cr [[spetstore.domain.customer.CustomerRepository]]
   * @return `Try`にラップされた[[spetstore.domain.customer.Customer]]
   */
  def customer(implicit cr: CustomerRepository, ctx: EntityIOContext): Try[Customer] =
    cr.resolveEntity(customerId)

  /**
   * [[spetstore.domain.purchase.CartItem]]の個数。
   */
  val sizeOfCartItems = cartItems.size

  /**
   * [[spetstore.domain.purchase.CartItem]]の総数。
   */
  val quantityOfCartItems = cartItems.foldLeft(0)(_ + _.quantity)

  /**
   * 合計金額。
   */
  lazy val totalPrice = cartItems.foldLeft(BigDecimal(0))(_ + _.subTotalPrice)

  /**
   * [[spetstore.domain.pet.PetId]]が含まれるかを検証する。
   *
   * @param itemId [[spetstore.domain.pet.PetId]]
   * @return 含まれる場合はtrue
   */
  def containsItemId(itemId: PetId): Boolean =
    cartItems.exists {
      _.pet.id == itemId
    }

  /**
   * このカートに[[spetstore.domain.purchase.CartItem]]を追加する。
   *
   * @param cartItem [[spetstore.domain.purchase.CartItem]]
   * @return 新しい[[spetstore.domain.purchase.Cart]]
   */
  def addCartItem(cartItem: CartItem): Cart = {
    require(cartItem.quantity > 0)
    cartItems.find(_.pet == cartItem.pet).map {
      currentItem =>
        val newCartItem = currentItem.addQuantity(cartItem.quantity).ensuring(_.quantity > 0)
        copy(cartItems = newCartItem :: cartItems.filterNot(_.pet == cartItem.pet))
    }.getOrElse {
      copy(cartItems = cartItem :: cartItems)
    }
  }

  /**
   * このカートに[[spetstore.domain.purchase.CartItem]]を追加する。
   *
   * @param pet [[spetstore.domain.pet.Pet]]
   * @param quantity 個数
   * @param isInStock ストックする場合true
   * @return 新しい[[spetstore.domain.purchase.Cart]]
   */
  def addCartItem(pet: Pet, quantity: Int, isInStock: Boolean): Cart =
    addCartItem(CartItem(pet, quantity, isInStock))

  /**
   * [[spetstore.domain.pet.PetId]]を使って
   * [[spetstore.domain.purchase.CartItem]]を
   * 削除する。
   *
   * @param petId [[spetstore.domain.pet.PetId]]
   * @return 新しい[[spetstore.domain.purchase.Cart]]
   */
  def removeCartItemByPetId(petId: PetId): Cart =
    cartItems.find(_.pet.id == petId).map {
      e =>
        copy(cartItems = cartItems.filterNot(_.pet.id == petId))
    }.getOrElse(this)

  /**
   * 特定の[[spetstore.domain.purchase.CartItem]]の数量を
   * インクリメントする。
   *
   * @param petId [[spetstore.domain.pet.PetId]]
   * @return 新しい[[spetstore.domain.purchase.Cart]]
   */
  def incrementQuantityByItemId(petId: PetId): Cart =
    cartItems.find(_.pet.id == petId).map {
      cartItem =>
        val newCartItem = cartItem.incrementQuantity.ensuring(_.quantity > 0)
        copy(cartItems = newCartItem :: cartItems.filterNot(_.pet.id == petId))
    }.getOrElse(this)

  /**
   * 特定の[[spetstore.domain.purchase.CartItem]]の数量を更新する。
   *
   * @param petId [[spetstore.domain.pet.PetId]]
   * @param quantity 数量
   * @return 新しい[[spetstore.domain.purchase.Cart]]
   */
  def updateQuantityByItemId(petId: PetId, quantity: Int): Cart = {
    require(quantity > 0)
    cartItems.find(_.pet.id == petId).map {
      cartItem =>
        val newCartItem = cartItem.withQuantity(quantity).ensuring(_.quantity > 0)
        copy(cartItems = newCartItem :: cartItems.filterNot(_.pet.id == petId))
    }.getOrElse(this)
  }

}
