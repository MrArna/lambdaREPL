package interpreting

import parsing.AST.{Expression, FunctionApplication, Lambda, Variable}
import utils.Message

import scala.collection.mutable.ListBuffer

/**
  * Created by Marco on 09/11/16.
  */
//static binder
class Binder(definitions: Map[String, Expression])
{

  //error messages
  val messages = ListBuffer[Message]()
  val defs = definitions

  def apply(term: Expression) = bind(term, ScopeManager.TOP)

  //binding depends on the type of the expression
  def bind(term: Expression, parent: Scope): Expression = term match {

    //if lambda go in depth into the body with the binding
    case Lambda(argument, body) =>
      val lambdaScope = new Scope(Some(parent), Set(argument.identifier))
      Lambda(argument.copy(scope = lambdaScope), bind(body, lambdaScope))

    // if a variable with a name into definition binds the corresponding definition
    case v @ Variable(name, _) if (definitions contains name) =>
      bind(definitions(name), parent)

    // if a true variable find the scope and bind it
    case v @ Variable(name, _) =>
      (parent closestBinding name) match {
        case Some(scope) => v.copy(scope = scope)

        //hack in order to use numbers in church form
        case None if name(0).isUpper =>
          v.copy(scope = ScopeManager.TOP)

        //if no binding rise error message
        case None        =>
        {
          messages += Message(v.pos, "Unbound variable: " + name)
          v
        }
      }

      //if a function bind in depth the args and the function itself
    case FunctionApplication(fun, arg) =>
      FunctionApplication(bind(fun, parent), bind(arg, parent))

  }
}