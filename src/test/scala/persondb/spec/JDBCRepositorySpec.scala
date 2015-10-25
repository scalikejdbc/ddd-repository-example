package persondb.spec

import scalikejdbc._
import scalikejdbc.ddd.infrastructure.JDBCEntityIOContext
import j5ik2o.ddd.infrastructure.EntityIOContext
import persondb.domain.person.{ Name, PersonJDBCRepository, PersonId, Person }
import persondb.infrastructure.database.{ PersonAutoRollback, PersonDB }

class JDBCRepositorySpec extends org.specs2.mutable.Specification with PersonDB {

  sequential

  def withContext[A](session: DBSession)(f: (EntityIOContext) => A): A = f(JDBCEntityIOContext(session))

  lazy val repository = PersonJDBCRepository()

  "repository" should {

    "stores entity" in new PersonAutoRollback {
      withContext(session) { implicit ctx =>
        val personId = PersonId()
        val person = Person(personId, Name("Junichi", "Kato"))
        repository.storeEntity(person) must beSuccessfulTry
      }
    }

    "stores multiple entities" in new PersonAutoRollback {
      withContext(session) { implicit ctx =>
        val (id1, id2) = (PersonId(), PersonId())
        val (e1, e2) = (Person(id1, Name("Junichi", "Kato")), Person(id2, Name("Kazuhiro", "Sera")))
        repository.storeEntities(e1, e2) must beSuccessfulTry
        repository.existByIdentifier(id1) must beSuccessfulTry
        repository.existByIdentifier(id2) must beSuccessfulTry
      }
    }

    "contains a entity" in new PersonAutoRollback {
      withContext(session) { implicit ctx =>
        val personId = PersonId()
        val person = Person(personId, Name("Junichi", "Kato"))
        repository.storeEntity(person) must beSuccessfulTry
        repository.existByIdentifier(personId) must beSuccessfulTry
      }
    }

    "contains multiple entities" in new PersonAutoRollback {
      withContext(session) { implicit ctx =>
        val (id1, id2) = (PersonId(), PersonId())
        val (e1, e2) = (Person(id1, Name("Junichi", "Kato")), Person(id2, Name("Kazuhiro", "Sera")))
        repository.storeEntities(e1, e2) must beSuccessfulTry
        repository.existByIdentifier(id1) must beSuccessfulTry
        repository.existByIdentifier(id2) must beSuccessfulTry
        repository.existByIdentifiers(id1, id2) must beSuccessfulTry
      }
    }

    "gets a entity" in new PersonAutoRollback {
      withContext(session) { implicit ctx =>
        val personId = PersonId()
        val person = Person(personId, Name("Junichi", "Kato"))
        repository.storeEntity(person) must beSuccessfulTry
        repository.resolveEntity(personId) must beSuccessfulTry.like { case found => found must_== person }
      }
    }

    "gets multiple entities" in new PersonAutoRollback {
      withContext(session) { implicit ctx =>
        val (id1, id2) = (PersonId(), PersonId())
        val (e1, e2) = (Person(id1, Name("Junichi", "Kato")), Person(id2, Name("Kazuhiro", "Sera")))
        repository.storeEntities(e1, e2) must beSuccessfulTry
        repository.resolveEntities(id1, id2) must beSuccessfulTry.like { case entities => entities.toSet must_== Set(e1, e2) }
      }
    }

    "deletes a entity" in new PersonAutoRollback {
      withContext(session) { implicit ctx =>
        val personId = PersonId()
        val person = Person(personId, Name("Junichi", "Kato"))
        repository.storeEntity(person) must beSuccessfulTry
        repository.deleteByIdentifier(personId) must beSuccessfulTry
      }
    }

    "deletes multiple entities" in new PersonAutoRollback {
      withContext(session) { implicit ctx =>
        val (id1, id2) = (PersonId(), PersonId())
        val (e1, e2) = (Person(id1, Name("Junichi", "Kato")), Person(id2, Name("Kazuhiro", "Sera")))
        repository.storeEntities(e1, e2) must beSuccessfulTry
        repository.deleteByIdentifiers(id1, id2) must beSuccessfulTry
        repository.resolveEntities(id1, id2) must beSuccessfulTry.like { case entities => entities must_== Nil }
      }
    }

  }

}
