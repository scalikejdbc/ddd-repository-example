package j5ik2o.ddd.infrastructure

import scala.util.{ Failure, Success, Try }

/**
 * Represents responsibility of repository in DDD.
 */
trait Repository[ID <: Identifier[_], E <: Entity[ID]] {

  /**
   * Derived type of repository.
   */
  type This <: Repository[ID, E]

  type Ctx = EntityIOContext

  def existByIdentifier(identifier: ID)(implicit ctx: Ctx): Try[Boolean]

  def existByIdentifiers(identifiers: Seq[ID])(implicit ctx: Ctx): Try[Boolean] = {
    traverse(identifiers)(existByIdentifier).map(_.forall(_ == true))
  }

  def resolveEntity(identifier: ID)(implicit ctx: Ctx): Try[E]

  def resolveEntities(identifiers: Seq[ID])(implicit ctx: Ctx): Try[Seq[E]] = traverse(identifiers)(resolveEntity)

  def storeEntity(entity: E)(implicit ctx: Ctx): Try[(This, E)]

  def storeEntities(entities: Seq[E])(implicit ctx: Ctx): Try[(This, Seq[E])] = {
    // asInstanceOf is required
    traverseWithThis(entities) { (repo, entity) => repo.storeEntity(entity).asInstanceOf[Try[(This, E)]] }
  }

  def deleteByIdentifier(identifier: ID)(implicit ctx: Ctx): Try[(This, E)]

  def deleteByIdentifiers(identifiers: ID*)(implicit ctx: Ctx): Try[(This, Seq[E])] = {
    // asInstanceOf is required
    traverseWithThis(identifiers) { (repo, id) => repo.deleteByIdentifier(id).asInstanceOf[Try[(This, E)]] }
  }

  protected final def traverseWithThis[A](values: Seq[A])(processor: (This, A) => Try[(This, E)])(implicit ctx: Ctx): Try[(This, Seq[E])] = Try {
    values.foldLeft((this.asInstanceOf[This], Seq.empty[E])) {
      case ((repo, entities), value) => processor(repo, value).map { case (r, e) => (r, entities :+ e) }.get
    }
  }

  protected def traverseWithoutFailures[A, R](values: Seq[A])(f: (A) => Try[R])(implicit ctx: Ctx): Try[Seq[R]] = {
    traverse[A, R](values, true)(f)
  }

  protected def traverse[A, R](values: Seq[A], forceSuccess: Boolean = false)(f: (A) => Try[R])(implicit ctx: Ctx): Try[Seq[R]] = {
    values.map(f).foldLeft(Try(Seq.empty[R])) { (entitiesTry, entityTry) =>
      (for { entities <- entitiesTry; entity <- entityTry } yield entities :+ entity).recoverWith {
        case e => if (forceSuccess) Success(entitiesTry.getOrElse(Seq.empty[R])) else Failure(e)
      }
    }
  }

}
