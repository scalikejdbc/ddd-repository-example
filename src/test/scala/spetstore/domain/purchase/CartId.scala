package spetstore.domain.purchase

import scalikejdbc.ddd.infrastructure.Identifier
import java.util.UUID

/**
 * [[spetstore.domain.purchase.Cart]]のための識別子。
 *
 * @param value 識別子の値
 */
case class CartId(value: UUID = UUID.randomUUID())
  extends Identifier[UUID]

