package spetstore.domain.pet

import scalikejdbc.ddd.infrastructure.Identifier
import java.util.UUID

/**
 * 商品区分の識別子。
 *
 * @param value 識別子の値。
 */
case class PetTypeId(value: UUID = UUID.randomUUID())
  extends Identifier[UUID]
