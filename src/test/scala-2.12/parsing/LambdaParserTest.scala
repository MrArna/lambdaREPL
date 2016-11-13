package parsing

import org.scalatest.FunSuite
import parsing.AST.Expression

/**
  * Created by Marco on 13/11/16.
  */
class LambdaParserTest extends FunSuite
{
  test("A parser with a right input should return an expression on success"){

    val parser = new LambdaParser
    val input = "\\x.x 5"

    import parser.Success

    parser.parse(input) match {
      case Success(res, _) => assert(res.isInstanceOf[Expression])
    }
  }

  test("A parser with a wrong input should return an error on erro"){

    val parser = new LambdaParser
    val input = "\\x.x?5"

    import parser.NoSuccess

    parser.parse(input) match {
      case NoSuccess(err, _) => assert(err.nonEmpty)
    }


  }

}
