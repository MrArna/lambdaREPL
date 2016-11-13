package utils

import interpreting.{Binder, Interpreter}
import org.scalatest.FunSuite
import parsing.LambdaParser

/**
  * Created by Marco on 13/11/16.
  */
class PrinterTest extends FunSuite
{

  test("A printer with a right input should print it"){

    val binder = new Binder(Library.load())
    val parser = new LambdaParser
    val interpreter = new Interpreter()
    val printer = new Printer
    val input = "number (add 5 3)"


    import parser.Success
    parser.parse(input) match {
      case Success(res,_) =>
        assert(printer(interpreter.apply(binder(res))).equals("8"))
    }

  }


}
