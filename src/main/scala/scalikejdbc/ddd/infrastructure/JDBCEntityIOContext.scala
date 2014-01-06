package scalikejdbc.ddd.infrastructure

import scalikejdbc.DBSession

/**
 * [[scalikejdbc.ddd.infrastructure.EntityIOContext]] JDBC implementation.
 *
 * @param session DB session
 */
case class JDBCEntityIOContext(session: DBSession) extends EntityIOContext
