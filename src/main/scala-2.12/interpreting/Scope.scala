package interpreting

/**
  * Created by Marco on 09/11/16.
  */

class Scope(val parent: Option[Scope], val boundNames: Set[String])
{
  val id = ScopeManager.nextId

  // returns the closest binding for the given variable in the scope
  def closestBinding(name: String): Option[Scope] =
    if (boundNames contains name)
      Some(this)
    else
      parent flatMap (_ closestBinding name)

  override def toString: String = {
    id.toString
  }
}

