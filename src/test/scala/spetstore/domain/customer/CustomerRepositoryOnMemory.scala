package spetstore.domain.customer

import scalikejdbc.ddd.infrastructure.{ EntityIOContext, RepositoryOnMemory }
import scala.util.{ Success, Try }

/**
 * [[spetstore.domain.customer.CustomerRepository]]のためのオンメモリリポジトリ。
 *
 * @param entities エンティティの集合
 */
private[customer] class CustomerRepositoryOnMemory(entities: Map[CustomerId, Customer])
    extends RepositoryOnMemory[CustomerId, Customer](entities) with CustomerRepository {

  protected def createInstance(entities: Map[CustomerId, Customer]): This =
    new CustomerRepositoryOnMemory(entities)

  def resolveByLoginName(loginName: String)(implicit ctx: EntityIOContext): Try[Customer] = Success {
    entities.map(_._2).toList.filter(_.config.loginName == loginName).head
  }

}
