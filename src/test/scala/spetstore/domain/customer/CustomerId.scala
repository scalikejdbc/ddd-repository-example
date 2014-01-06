package spetstore.domain.customer

import scalikejdbc.ddd.infrastructure.Identifier
import java.util.UUID

/**
 * [[spetstore.domain.customer.Customer]]のための識別子。
 *
 * @param value 識別子の値
 */
case class CustomerId(value: UUID = UUID.randomUUID())
  extends Identifier[UUID]
