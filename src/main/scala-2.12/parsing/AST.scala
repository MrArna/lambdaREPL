package parsing

import interpreting.{Scope, ScopeManager}

import scala.util.parsing.input.Positional

/**
  * Created by Marco on 07/11/16.
  */
object AST
{

  //definition of lambda expression
  sealed trait Expression extends Positional

  //definition of the components of a lambda expression
  case class Variable(identifier: String, scope: Scope) extends Expression
  case class FunctionApplication(function: Expression, argument: Expression) extends Expression
  case class Lambda(argument: Variable, body: Expression) extends Expression


}
