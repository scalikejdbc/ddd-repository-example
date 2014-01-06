package spetstore.domain.customer

import scalikejdbc.ddd.infrastructure.{ EntityIOContext, Repository }
import scala.util.Try

/**
 * [[spetstore.domain.customer.Customer]]のためのリポジトリ責務。
 */
trait CustomerRepository extends Repository[CustomerId, Customer] {

  type This = CustomerRepository

  /**
   * 指定したログイン名に該当する顧客を解決する。
   *
   * @param loginName ログイン名
   * @param ctx [[scalikejdbc.ddd.infrastructure.EntityIOContext]]
   * @return `Try`にラップされた[[spetstore.domain.customer.Customer]]
   */
  def resolveByLoginName(loginName: String)(implicit ctx: EntityIOContext): Try[Customer]

}

/**
 * コンパニオンオブジェクト。
 */
object CustomerRepository {

  /**
   * メモリ用リポジトリを生成する。
   *
   * @param entities エンティティの集合
   * @return [[spetstore.domain.customer.CustomerRepository]]
   */
  def ofMemory(entities: Map[CustomerId, Customer] = Map.empty): CustomerRepository =
    new CustomerRepositoryOnMemory(entities)

  /**
   * JDBC用リポジトリを生成する。
   *
   * @return [[spetstore.domain.customer.CustomerRepository]]
   */
  def ofJDBC: CustomerRepository =
    new CustomerRepositoryOnJDBC

}
