package churchStructures

import interpreting.ScopeManager
import parsing.AST.{Expression, Variable}

/**
  * Created by Marco on 11/11/16.
  */
object ChurchBoolean
{

  def unapply(expr: Expression): Option[Boolean] = expr match {
    case Variable("T", ScopeManager.TOP)  => Some(true)
    case Variable("F", ScopeManager.TOP)  => Some(false)
    case _                                => None
  }


}
