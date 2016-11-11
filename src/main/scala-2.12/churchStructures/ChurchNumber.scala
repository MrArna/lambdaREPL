package churchStructures

import interpreting.ScopeManager
import parsing.AST.{Expression, FunctionApplication, Lambda, Variable}
import parsing.VariableManager

/**
  * Created by Marco on 11/11/16.
  */
object ChurchNumber {

    def apply(n: Int) = {
      var cn: Expression = VariableManager("z")
      for (i <- 1 to n)
        cn = FunctionApplication(VariableManager("s"), cn)
      Lambda(VariableManager("s"), Lambda(VariableManager("z"), cn))
    }

    def unapply(expr: Expression): Option[Int] = expr match {
      case Variable("Z", ScopeManager.TOP)             => Some(0)
      case FunctionApplication(Variable("S", ScopeManager.TOP), arg) => unapply(arg) map (_ + 1)
      case _                               => None
    }




}