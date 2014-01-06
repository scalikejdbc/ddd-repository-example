package spetstore.domain.pet

import scalikejdbc.ddd.infrastructure.Repository

/**
 * [[spetstore.domain.pet.Category]]のためのリポジトリ責務。
 */
trait CategoryRepository extends Repository[CategoryId, Category] {

  type This = CategoryRepository

}

/**
 * コンパニオンオブジェクト。
 */
object CategoryRepository {

  /**
   * メモリ用リポジトリを生成する。
   *
   * @param entities エンティティの集合
   * @return [[spetstore.domain.pet.CategoryRepository]]
   */
  def ofMemory(entities: Map[CategoryId, Category] = Map.empty): CategoryRepository =
    new CategoryRepositoryOnMemory(entities)

  /**
   * JDBC用リポジトリを生成する。
   *
   * @return [[spetstore.domain.pet.CategoryRepository]]
   */
  def ofJDBC: CategoryRepository =
    new CategoryRepositoryOnJDBC

}
