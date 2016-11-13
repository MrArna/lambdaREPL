package utils

import churchStructures.{ChurchBoolean, ChurchNumber}
import parsing.AST.{Expression, FunctionApplication, Lambda, Variable}

/**
  * Created by Marco on 09/11/16.
  */
class Printer {

  def apply(expr: Expression): String =
  {

    if (ChurchNumber.unapply(expr).isEmpty) {
      expr match {
        case Lambda (arg, body) => p"λ$arg.$body"
        case FunctionApplication (fun, arg) => p"$fun $arg"
        case Variable (identifier, scope) => s"$identifier"
        case ChurchNumber (i) => i.toString
        case ChurchBoolean (b) => b.toString

      }
    }
    else
    {
      ChurchNumber.unapply(expr).get.toString
    }
  }

  implicit class PrettyPrinting(val sc: StringContext) {
    def p(args: Expression*) = sc.s((args map parensIfNeeded):_*)
  }

  def parensIfNeeded(expr: Expression) = expr match {
    case Variable(identifier,scope) => identifier
    case _         => "(" + apply(expr) + ")"
  }

}