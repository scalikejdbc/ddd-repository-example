package spetstore.domain.purchase

import scalikejdbc.ddd.infrastructure.Identifier
import java.util.UUID

/**
 * [[spetstore.domain.purchase.Order]]のための識別子。
 *
 * @param value 識別子の値
 */
case class OrderId(value: UUID = UUID.randomUUID())
  extends Identifier[UUID]
