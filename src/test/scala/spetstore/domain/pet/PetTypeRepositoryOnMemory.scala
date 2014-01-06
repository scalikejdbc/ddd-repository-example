package spetstore.domain.pet

import scalikejdbc.ddd.infrastructure.RepositoryOnMemory

/**
 * [[spetstore.domain.pet.PetType]]のためのオンメモリリポジトリ。
 *
 * @param entities エンティティの集合
 */
private[pet] class PetTypeRepositoryOnMemory(entities: Map[PetTypeId, PetType])
    extends RepositoryOnMemory[PetTypeId, PetType](entities) with PetTypeRepository {

  protected def createInstance(entities: Map[PetTypeId, PetType]): This =
    new PetTypeRepositoryOnMemory(entities)

}
