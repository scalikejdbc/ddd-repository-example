package spetstore.infrastructure

import java.util.UUID
import scalikejdbc.ddd.infrastructure._

/**
 * テスト用の識別子。
 */
case class PersonId(value: UUID = UUID.randomUUID())
  extends Identifier[UUID]
