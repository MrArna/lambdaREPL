package interpreting

import churchStructures.{ChurchBoolean, ChurchNumber}
import parsing.AST.{Expression, FunctionApplication, Lambda, Variable}
import utils.Printer

/**
  * Created by Marco on 09/11/16.
  */
//by default show each step in the evaluation
class Interpreter(eachStep: Boolean = true)
{

  val pretty = new Printer

  //definition of what is a value
  def isValue(term: Expression): Boolean = term match {
    case _: Lambda        => true
    case _: Variable      => true
    case ChurchNumber(_)  => true
    case ChurchBoolean(_) => true
    case _                => false
  }

  // evaluate recursivlely depending on the pattern matched if found a basic structure, than apply a beta substitution
  def evaluate(term: Expression):Expression = term match
  {
    case FunctionApplication(Lambda(argDef, body), arg) if isValue(arg) => new BetaSubstitution(argDef, arg) apply (body)

    case FunctionApplication(fun, arg) if isValue(fun) => FunctionApplication(fun, evaluate(arg))

    case FunctionApplication(fun, arg) => FunctionApplication(evaluate(fun), arg)
  }

  // evaluation in progress
  def apply(term: Expression): Expression =
    try {
      val term2 = evaluate(term)
      if (eachStep)
        println(s"step: ${pretty(term)} → ${pretty(term2)}")
        //println(s"step: ${term} → ${term2}")
      apply(term2)
    } catch {
      case _: MatchError => term
    }


}
