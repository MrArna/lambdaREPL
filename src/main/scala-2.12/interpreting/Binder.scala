package interpreting

import parsing.AST.{Expression, FunctionApplication, Lambda, Variable}
import parsing.VariableManager
import utils.Message

import scala.collection.mutable.ListBuffer

/**
  * Created by Marco on 09/11/16.
  */
class Binder(definitions: Map[String, Expression])
{
  val messages = ListBuffer[Message]()
  val defs = definitions

  def apply(term: Expression) = bind(term, ScopeManager.TOP)

  def bind(term: Expression, parent: Scope): Expression = term match {

    case Lambda(argument, body) =>
      val lambdaScope = new Scope(Some(parent), Set(argument.identifier))
      Lambda(argument.copy(scope = lambdaScope), bind(body, lambdaScope))


    case v @ Variable(name, _) if (definitions contains name) =>
      bind(definitions(name), parent)

    case v @ Variable(name, _) =>
      (parent closestBinding name) match {
        case Some(scope) => v.copy(scope = scope)

        case None if name(0).isUpper =>
          v.copy(scope = ScopeManager.TOP)

        case None        =>
        {
          messages += Message(v.pos, "Unbound variable: " + name)
          v
        }
      }

    case FunctionApplication(fun, arg) =>
      FunctionApplication(bind(fun, parent), bind(arg, parent))

  }
}