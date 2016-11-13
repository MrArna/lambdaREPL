package churchStructures

import org.scalatest.FunSuite
import parsing.AST.{Lambda, Variable}
import parsing.VariableManager

/**
  * Created by Marco on 13/11/16.
  */
class ChurchNumber$Test extends FunSuite
{

  test("When a number is passed a Church number expression should returned ") {


    assert(ChurchNumber.apply(0).equals(Lambda(VariableManager("s"), Lambda(VariableManager("z"), VariableManager("z")))))

  }




}
