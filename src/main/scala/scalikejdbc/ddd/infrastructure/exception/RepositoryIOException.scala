package scalikejdbc.ddd.infrastructure.exception

/**
 * Represents IO error.
 */
case class RepositoryIOException(message: String) extends Exception(message)

