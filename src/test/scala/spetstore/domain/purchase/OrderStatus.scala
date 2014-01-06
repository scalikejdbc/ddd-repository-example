package spetstore.domain.purchase

object OrderStatus extends Enumeration {
  val Pending, Approved, Denied, Completed = Value
}
