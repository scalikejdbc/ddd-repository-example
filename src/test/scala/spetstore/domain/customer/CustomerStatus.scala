package spetstore.domain.customer

/**
 * [[spetstore.domain.customer.Customer]]の状態を表す値オブジェクト。
 */
object CustomerStatus extends Enumeration {

  /**
   * 有効
   */
  val Enabled, /**
   * 無効
   */ Disabled = Value

}
