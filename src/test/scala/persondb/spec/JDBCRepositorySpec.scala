package persondb.spec

import scalikejdbc.ddd.infrastructure.JDBCEntityIOContext
import persondb.domain.person.{ PersonJDBCRepository, PersonId, Person }
import persondb.infrastructure.database.{ PersonAutoRollback, PersonDB }

class JDBCRepositorySpec extends org.specs2.mutable.Specification with PersonDB {

  sequential

  lazy val repository = PersonJDBCRepository()

  "repository" should {

    "stores entity" in new PersonAutoRollback {
      implicit val ctx = JDBCEntityIOContext(session)
      val personId = PersonId()
      val person = Person(personId, "Junichi", "Kato")
      repository.storeEntity(person) must beSuccessfulTry
    }

    "contains a entity" in new PersonAutoRollback {
      implicit val ctx = JDBCEntityIOContext(session)
      val personId = PersonId()
      val person = Person(personId, "Junichi", "Kato")
      repository.storeEntity(person) must beSuccessfulTry
      repository.existByIdentifier(personId) must beSuccessfulTry
    }

    "gets a entity" in new PersonAutoRollback {
      implicit val ctx = JDBCEntityIOContext(session)
      val personId = PersonId()
      val person = Person(personId, "Junichi", "Kato")
      repository.storeEntity(person) must beSuccessfulTry
      repository.resolveEntity(personId) must beSuccessfulTry.like { case found => found must_== person }
    }

    "deletes a entity" in new PersonAutoRollback {
      implicit val ctx = JDBCEntityIOContext(session)
      val personId = PersonId()
      val person = Person(personId, "Junichi", "Kato")
      repository.storeEntity(person) must beSuccessfulTry
      repository.deleteByIdentifier(personId) must beSuccessfulTry
    }
  }

}
