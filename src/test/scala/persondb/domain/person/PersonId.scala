package persondb.domain.person

import java.util.UUID
import scalikejdbc.ddd.infrastructure.Identifier

case class PersonId(value: UUID = UUID.randomUUID()) extends Identifier[UUID]
