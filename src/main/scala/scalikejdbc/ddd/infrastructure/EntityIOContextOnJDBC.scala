package scalikejdbc.ddd.infrastructure

import scalikejdbc.DBSession

/**
 * TODO English doc
 * JDBC用[[scalikejdbc.ddd.infrastructure.EntityIOContext]]。
 *
 * @param session DBセッション
 */
case class EntityIOContextOnJDBC(session: DBSession) extends EntityIOContext
