package persondb.domain.person

import java.util.UUID
import scalikejdbc._
import scalikejdbc.ddd.infrastructure.JDBCRepository
import PersonJDBCRepository._

case class PersonJDBCRepository() extends JDBCRepository[UUID, PersonId, Person] with PersonRepository {
  type This = PersonRepository
  override def defaultAlias = createAlias("p") // must be `def`
  override val connectionPoolName = 'person
  override val tableName = "person"

  override def extract(rs: WrappedResultSet, p: ResultName[Person]): Person = new Person(
    id = PersonId(UUID.fromString(rs.get(p.id))),
    name = Name(rs.get(p.field("firstName")), rs.get(p.field("lastName"))))

  override def toNamedValues(entity: Person): Seq[(Symbol, Any)] = Seq(
    'id -> entity.id.value,
    'firstName -> entity.name.firstName,
    'lastName -> entity.name.lastName)
}

object PersonJDBCRepository {
  // https://github.com/scalikejdbc/scalikejdbc/issues/520
  implicit val uuidBinderFactory: ParameterBinderFactory[UUID] = ParameterBinderFactory { value => (stmt, idx) => stmt.setObject(idx, value)
  }
}
