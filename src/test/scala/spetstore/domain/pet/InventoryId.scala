package spetstore.domain.pet

import java.util.UUID
import scalikejdbc.ddd.infrastructure.Identifier

case class InventoryId(value: UUID)
  extends Identifier[UUID]
