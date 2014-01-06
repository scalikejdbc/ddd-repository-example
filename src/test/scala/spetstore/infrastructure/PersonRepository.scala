package spetstore.infrastructure

import scalikejdbc.ddd.infrastructure._

/**
 * テスト用のリポジトリ責務。
 */
trait PersonRepository extends Repository[PersonId, Person]
