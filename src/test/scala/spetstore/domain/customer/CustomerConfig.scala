package spetstore.domain.customer

import spetstore.domain.pet.{ Category, CategoryRepository, CategoryId }
import scalikejdbc.ddd.infrastructure.EntityIOContext
import scala.util.Try

/**
 * [[spetstore.domain.customer.Customer]]の設定を表す値オブジェクト。
 *
 * @param loginName ログイン名
 * @param password パスワード
 * @param favoriteCategoryId お気に入りカテゴリID
 */
case class CustomerConfig(loginName: String,
    password: String,
    favoriteCategoryId: Option[CategoryId] = None) {

  /**
   * お気に入りのカテゴリを取得する。
   *
   * @param cr [[spetstore.domain.pet.CategoryRepository]]
   * @param ctx [[spetstore.infrastructure.support.EntityIOContext]]
   * @return `Try`にラップされた[[spetstore.domain.pet.Category]]
   */
  def favoriteCategory(implicit cr: CategoryRepository, ctx: EntityIOContext): Try[Category] =
    Try(favoriteCategoryId.get).flatMap(cr.resolveEntity)

}

