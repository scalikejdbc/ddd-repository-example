package persondb.domain.person

import j5ik2o.ddd.infrastructure.Entity

case class Person(id: PersonId, name: Name) extends Entity[PersonId]
