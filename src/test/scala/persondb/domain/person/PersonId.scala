package persondb.domain.person

import java.util.UUID
import j5ik2o.ddd.infrastructure.Identifier

case class PersonId(value: UUID = UUID.randomUUID()) extends Identifier[UUID]
