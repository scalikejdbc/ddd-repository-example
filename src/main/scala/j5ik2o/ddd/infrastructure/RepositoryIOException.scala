package j5ik2o.ddd.infrastructure

/**
 * Represents IO error.
 */
case class RepositoryIOException(message: String) extends Exception(message)

