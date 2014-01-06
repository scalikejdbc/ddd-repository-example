package scalikejdbc.ddd.infrastructure

import scalikejdbc.DBSession
import j5ik2o.ddd.infrastructure.EntityIOContext

case class JDBCEntityIOContext(session: DBSession) extends EntityIOContext
