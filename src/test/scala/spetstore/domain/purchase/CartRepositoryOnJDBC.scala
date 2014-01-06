package spetstore.domain.purchase

import spetstore.domain.customer.CustomerId
import scalikejdbc.ddd.infrastructure.RepositoryOnJDBC
import java.util.UUID
import org.json4s.DefaultReaders._
import org.json4s.DefaultWriters._
import org.json4s._
import org.json4s.jackson.JsonMethods._
import scalikejdbc.WrappedResultSet
import spetstore.domain.infrastructure.json.CartFormats._

private[purchase] class CartRepositoryOnJDBC
    extends RepositoryOnJDBC[CartId, Cart] with CartRepository {

  override def tableName: String = "cart"

  override def columnNames: Seq[String] = Seq(
    "id",
    "customer_id",
    "cart_items"
  )

  protected def convertResultSetToEntity(resultSet: WrappedResultSet): Cart =
    Cart(
      id = CartId(UUID.fromString(resultSet.string("id"))),
      customerId = CustomerId(UUID.fromString(resultSet.string("customer_id"))),
      cartItems = parse(resultSet.string("cart_items")).as[List[CartItem]]
    )

  protected def convertEntityToValues(entity: Cart): Seq[Any] = Seq(
    entity.id,
    entity.customerId.value.toString,
    compact(entity.cartItems.toArray.asJValue)
  )

}
