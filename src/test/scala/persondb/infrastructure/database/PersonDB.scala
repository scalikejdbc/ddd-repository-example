package persondb.infrastructure.database

import scalikejdbc._

trait PersonDB {

  Class.forName("org.h2.Driver")
  ConnectionPool.add('person, "jdbc:h2:mem:person", "user", "pass")

  NamedDB('person) autoCommit { implicit s =>
    sql"""
create table person (
  id varchar(64) not null primary key,
  first_name varchar(64),
  last_name varchar(64)
)
""".execute.apply()
  }
}

