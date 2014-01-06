package spetstore.infrastructure

import scalikejdbc.ddd.infrastructure._

/**
 * テスト用のエンティティ。
 */
case class Person(id: PersonId, firstName: String, lastName: String)
  extends Entity[PersonId]
