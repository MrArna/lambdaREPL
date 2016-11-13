package interpreting

import parsing.AST.{Expression, FunctionApplication, Lambda, Variable}
import utils.Library

/**
  * Created by Marco on 10/11/16.
  */
//apply the basic beta substitution, recursively if required
class BetaSubstitution (argV: Variable, replacement: Expression) {

  val binder = new Binder(Library.load())

  def apply(term: Expression): Expression = term match {
    case Variable(argV.identifier, argV.scope)  => binder.bind(replacement, argV.scope.parent.get)
    case Variable(_, _)                         => term
    case FunctionApplication(fun, arg)          => FunctionApplication(apply(fun), apply(arg))
    case Lambda(arg, body)                      => Lambda(arg, apply(body))
  }
}
