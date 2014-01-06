package spetstore.domain.customer

import spetstore.domain.basic.{ Contact, PostalAddress }

/**
 * [[spetstore.domain.customer.Customer]]のプロフィールを表す値オブジェクト。
 *
 * @param postalAddress [[spetstore.domain.basic.PostalAddress]]
 * @param contact [[spetstore.domain.basic.Contact]]
 */
case class CustomerProfile(postalAddress: PostalAddress,
  contact: Contact)

