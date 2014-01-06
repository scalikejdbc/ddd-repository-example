package persondb.domain.person

import j5ik2o.ddd.infrastructure.Repository

trait PersonRepository extends Repository[PersonId, Person]
