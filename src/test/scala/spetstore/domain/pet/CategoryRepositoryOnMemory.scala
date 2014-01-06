package spetstore.domain.pet

import scalikejdbc.ddd.infrastructure.RepositoryOnMemory

/**
 * [[spetstore.domain.pet.CategoryRepository]]のためのオンメモリリポジトリ。
 *
 * @param entities エンティティの集合
 */
private[pet] class CategoryRepositoryOnMemory(entities: Map[CategoryId, Category])
    extends RepositoryOnMemory[CategoryId, Category](entities) with CategoryRepository {

  protected def createInstance(entities: Map[CategoryId, Category]): This =
    new CategoryRepositoryOnMemory(entities)

}
