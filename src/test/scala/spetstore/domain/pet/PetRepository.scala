package spetstore.domain.pet

import scalikejdbc.ddd.infrastructure.Repository

/**
 * [[spetstore.domain.pet.Pet]]のためのリポジトリ責務。
 */
trait PetRepository extends Repository[PetId, Pet] {

  type This = PetRepository

}

/**
 * コンパニオンオブジェクト。
 */
object PetRepository {

  /**
   * メモリ用リポジトリを生成する。
   *
   * @param entities エンティティの集合
   * @return [[spetstore.domain.pet.PetRepository]]
   */
  def ofMemory(entities: Map[PetId, Pet] = Map.empty): PetRepository =
    new PetRepositoryOnMemory(entities)

  /**
   * JDBC用リポジトリを生成する。
   *
   * @return [[spetstore.domain.pet.PetRepository]]
   */
  def ofJDBC: PetRepository =
    new PetRepositoryOnJDBC

}
