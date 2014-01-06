package spetstore.domain.pet

import scalikejdbc.ddd.infrastructure.Identifier
import java.util.UUID

/**
 * [[spetstore.domain.pet.Supplier]]のための識別子。
 *
 * @param value 識別子の値
 */
case class SupplierId(value: UUID = UUID.randomUUID()) extends Identifier[UUID]
