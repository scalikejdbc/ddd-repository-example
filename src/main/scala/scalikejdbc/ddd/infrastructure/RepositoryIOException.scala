package scalikejdbc.ddd.infrastructure

/**
 * TODO English doc
 * リポジトリ内部で発生するI/O例外。
 *
 * @param message メッセージ
 */
case class RepositoryIOException(message: String) extends Exception(message)

