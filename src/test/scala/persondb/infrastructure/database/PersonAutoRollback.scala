package persondb.infrastructure.database

import scalikejdbc.specs2.mutable.AutoRollback
import scalikejdbc.NamedDB

trait PersonAutoRollback extends AutoRollback {
  override def db = NamedDB('person).toDB
}
