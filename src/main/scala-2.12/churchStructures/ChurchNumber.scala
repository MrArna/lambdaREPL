package churchStructures

import interpreting.ScopeManager
import parsing.AST.{Expression, FunctionApplication, Lambda, Variable}
import parsing.VariableManager

/**
  * Created by Marco on 11/11/16.
  */

//extractor for numbers, uses the S Z trick. S count as 1 and Z count as Z, the number of S represents the number
object ChurchNumber {

    //translate a number in the corresponding AST version, according to church definition
    def apply(n: Int) = {
      var cn: Expression = VariableManager("z")
      for (i <- 1 to n)
        cn = FunctionApplication(VariableManager("s"), cn)
      Lambda(VariableManager("s"), Lambda(VariableManager("z"), cn))
    }

    // trasform the resulting expression in a number counting the S values, if not possible then no Int is returned
    def unapply(expr: Expression): Option[Int] = expr match {
      case Variable("Z", ScopeManager.TOP)             => Some(0)
      case FunctionApplication(Variable("S", ScopeManager.TOP), arg) => unapply(arg) map (_ + 1)
      case _                               => None
    }




}