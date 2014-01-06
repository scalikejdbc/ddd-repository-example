package persondb.domain.person

import scalikejdbc.ddd.infrastructure.Repository

trait PersonRepository extends Repository[PersonId, Person]
