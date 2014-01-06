package spetstore.domain.pet

import spetstore.domain.basic.{ Contact, PostalAddress }
import scalikejdbc.ddd.infrastructure.Entity

/**
 * 仕入れ先を表すエンティティ。
 *
 * @param id [[spetstore.domain.pet.SupplierId]]
 * @param name 名前
 * @param postalAddress 住所
 * @param contact 連絡先
 */
case class Supplier(id: SupplierId,
  name: String,
  postalAddress: PostalAddress,
  contact: Contact)
    extends Entity[SupplierId]

