package interpreting

/**
  * Created by Marco on 09/11/16.
  */

// manages scopes
object ScopeManager
{

  var id = 0
  def nextId = { val i = id; id += 1; i }
  val TOP = new Scope(None, Set())
}
