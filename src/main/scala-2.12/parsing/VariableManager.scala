package parsing

import interpreting.ScopeManager
import parsing.AST.Variable

/**
  * Created by Marco on 10/11/16.
  */

//manages the SCOPE assignment to the variable with a given name

object VariableManager
{
  def apply(name: String): Variable = Variable(name, ScopeManager.TOP)

}
