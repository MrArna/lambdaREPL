package interpreting

import org.scalatest.FunSuite
import parsing.AST.Expression
import parsing.LambdaParser
import utils.Library

/**
  * Created by Marco on 13/11/16.
  */
class InterpreterTest extends FunSuite
{

  test("An interpreter with a right input should return an expression"){

    val binder = new Binder(Library.load())
    val parser = new LambdaParser
    val interpreter = new Interpreter()
    val input = "number (add 5 3)"


    import parser.Success
    parser.parse(input) match {
      case Success(res,_) =>
        assert(interpreter.apply(binder(res)).isInstanceOf[Expression])
    }

  }


}
