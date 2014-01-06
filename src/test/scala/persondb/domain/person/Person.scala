package persondb.domain.person

import scalikejdbc.ddd.infrastructure.Entity

case class Person(id: PersonId, firstName: String, lastName: String) extends Entity[PersonId]
