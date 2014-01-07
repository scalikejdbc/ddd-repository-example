package persondb.domain.person

import scalikejdbc._, SQLInterpolation._
import scalikejdbc.ddd.infrastructure.JDBCRepository

case class PersonJDBCRepository() extends JDBCRepository[PersonId, Person] with PersonRepository {
  type This = PersonRepository
  override def defaultAlias = createAlias("p")
  override val connectionPoolName = 'person
  override val tableName = "person"

  override def extract(rs: WrappedResultSet, p: ResultName[Person]): Person = new Person(
    id = PersonId(java.util.UUID.fromString(rs.get(p.id))),
    firstName = rs.get(p.firstName),
    lastName = rs.get(p.lastName)
  )

  override def toNamedValues(entity: Person): Seq[(Symbol, Any)] = Seq(
    'id -> entity.id.value,
    'firstName -> entity.firstName,
    'lastName -> entity.lastName
  )
}