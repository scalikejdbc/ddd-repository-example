package spetstore.domain.pet

import scalikejdbc.ddd.infrastructure.Repository

/**
 * [[spetstore.domain.pet.PetType]]のためのリポジトリ責務。
 */
trait PetTypeRepository extends Repository[PetTypeId, PetType] {

  type This = PetTypeRepository

}

/**
 * コンパニオンオブジェクト。
 */
object PetTypeRepository {

  /**
   * メモリ用リポジトリを生成する。
   *
   * @param entities エンティティのマップ
   * @return [[spetstore.domain.pet.PetTypeRepository]]
   */
  def ofMemory(entities: Map[PetTypeId, PetType] = Map.empty): PetTypeRepository =
    new PetTypeRepositoryOnMemory(entities)

  /**
   * JDBC用リポジトリを生成する。
   *
   * @return [[spetstore.domain.pet.PetTypeRepository]]
   */
  def ofJDBC: PetTypeRepository =
    new PetTypeRepositoryOnJDBC

}

