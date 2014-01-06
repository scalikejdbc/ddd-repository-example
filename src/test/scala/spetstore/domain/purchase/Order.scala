package spetstore.domain.purchase

import com.github.nscala_time.time.Imports._
import spetstore.domain.customer.CustomerRepository
import spetstore.domain.basic.PostalAddress
import scalikejdbc.ddd.infrastructure.{ EntityIOContext, Entity }
import scala.collection.mutable.ListBuffer
import scala.util.Try

/**
 * 注文を表すエンティティ。
 *
 * @param id 識別子
 * @param orderDate 注文日時
 * @param userName 購入者名
 * @param shippingAddress 出荷先の住所
 * @param orderItems [[spetstore.domain.purchase.OrderItem]]のリスト
 */
case class Order(id: OrderId = OrderId(),
  status: OrderStatus.Value,
  orderDate: DateTime,
  userName: String,
  shippingAddress: PostalAddress,
  orderItems: List[OrderItem])
    extends Entity[OrderId] {

  /**
   * [[spetstore.domain.purchase.OrderItem]]の個数
   */
  val sizeOfOrderItems: Int = orderItems.size

  /**
   * [[spetstore.domain.purchase.OrderItem]]の総数。
   */
  lazy val quantityOfOrderItems = orderItems.foldLeft(0)(_ + _.quantity)

  /**
   * 合計を取得する。
   *
   * @return 合計
   */
  lazy val totalPrice: BigDecimal =
    orderItems.map(_.subTotalPrice).reduceLeft(_ + _)

  /**
   * この注文に[[spetstore.domain.purchase.OrderItem]]を追加する。
   *
   * @param orderItem [[spetstore.domain.purchase.OrderItem]]
   * @return 新しい[[spetstore.domain.purchase.Order]]
   */
  def addOrderItem(orderItem: OrderItem): Order =
    copy(orderItems = orderItem :: orderItems)

  /**
   * この注文から[[spetstore.domain.purchase.OrderItem]]を削除する。
   *
   * @param orderItem [[spetstore.domain.purchase.OrderItem]]
   * @return 新しい[[spetstore.domain.purchase.Order]]
   */
  def removeOrderItem(orderItem: OrderItem): Order =
    if (orderItems.exists(_ == orderItem)) {
      copy(orderItems = orderItems.filterNot(_ == orderItem))
    } else {
      this
    }

  /**
   * この注文から指定したインデックスの
   * [[spetstore.domain.purchase.OrderItem]]を削除する。
   *
   * @param index インデックス
   * @return 新しい[[spetstore.domain.purchase.Order]]
   */
  def removeOrderItemByIndex(index: Int): Order = {
    require(orderItems.size > index)
    val lb = ListBuffer(orderItems: _*)
    lb.remove(index)
    copy(orderItems = lb.result())
  }

}

/**
 * コンパニオンオブジェクト。
 */
object Order {

  /**
   * [[spetstore.domain.purchase.Cart]]から
   * [[spetstore.domain.purchase.Order]]を
   * 生成する。
   *
   * @param cart [[spetstore.domain.purchase.Cart]]
   * @return [[spetstore.domain.purchase.Order]]
   */
  def fromCart(cart: Cart)(implicit cr: CustomerRepository, ctx: EntityIOContext): Try[Order] = Try {
    val customer = cart.customer.get
    val orderItems = cart.cartItems.map(OrderItem.fromCartItem)
    Order(OrderId(), OrderStatus.Pending, DateTime.now, customer.name, customer.profile.postalAddress, orderItems)
  }

}
