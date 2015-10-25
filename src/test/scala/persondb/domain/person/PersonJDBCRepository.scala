package persondb.domain.person

import scalikejdbc._
import scalikejdbc.ddd.infrastructure.JDBCRepository

case class PersonJDBCRepository() extends JDBCRepository[PersonId, Person] with PersonRepository {
  type This = PersonRepository
  override def defaultAlias = createAlias("p") // must be `def`
  override val connectionPoolName = 'person
  override val tableName = "person"

  override def extract(rs: WrappedResultSet, p: ResultName[Person]): Person = new Person(
    id = PersonId(java.util.UUID.fromString(rs.get(p.id))),
    name = Name(rs.get(p.field("firstName")), rs.get(p.field("lastName")))
  )

  override def toNamedValues(entity: Person): Seq[(Symbol, Any)] = Seq(
    'id -> entity.id.value,
    'firstName -> entity.name.firstName,
    'lastName -> entity.name.lastName
  )
}
