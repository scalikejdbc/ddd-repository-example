package persondb.spec

import org.specs2.mutable.Specification
import persondb.domain.person.{ Name, PersonRepository, PersonId, Person }
import j5ik2o.ddd.infrastructure.onmemory.{ OnMemoryRepository, OnMemoryEntityIOContext }

class OnMemoryRepositorySpec extends Specification {

  case class PersonOnMemoryRepository(entities: Map[PersonId, Person] = Map.empty)
    extends OnMemoryRepository[PersonId, Person](entities) with PersonRepository {
    type This = PersonRepository
    protected def createInstance(entities: Map[PersonId, Person]): This = new PersonOnMemoryRepository(entities)
  }

  implicit val ctx = OnMemoryEntityIOContext

  "repository" should {
    "stores a entity" in {
      val personId = PersonId()
      val person = Person(personId, Name("Junichi", "Kato"))
      PersonOnMemoryRepository().
        storeEntity(person) must beSuccessfulTry.like {
          case (PersonOnMemoryRepository(entities), _) => entities.contains(personId) must beTrue
        }
    }

    "contains a entity" in {
      val personId = PersonId()
      val person = Person(personId, Name("Junichi", "Kato"))
      val entities = Map(personId -> person)
      entities.contains(personId) must beTrue
      PersonOnMemoryRepository(entities).
        existByIdentifier(personId) must beSuccessfulTry
    }

    "gets a entity" in {
      val personId = PersonId()
      val person = Person(personId, Name("Junichi", "Kato"))
      PersonOnMemoryRepository(Map(personId -> person)).
        resolveEntity(personId) must beSuccessfulTry.like {
          case entity => entity must_== person
        }
    }

    "deletes a entity" in {
      val personId = PersonId()
      val person = Person(personId, Name("Junichi", "Kato"))
      PersonOnMemoryRepository(Map(personId -> person)).
        deleteByIdentifier(personId) must beSuccessfulTry.like {
          case (PersonOnMemoryRepository(entities), _) => entities.contains(personId) must beFalse
        }
    }
  }

}
