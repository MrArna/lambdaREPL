package interpreting

import org.scalatest.FunSuite
import parsing.LambdaParser
import utils.Library

/**
  * Created by Marco on 13/11/16.
  */
class BinderTest extends FunSuite
{

  test("A binder with a right input should return 0 warning messages"){

    val binder = new Binder(Library.load())
    val parser = new LambdaParser
    val input = "\\x.x 5"

    import parser.Success
    parser.parse(input) match {
      case Success(res,_) =>
        binder(res)
        assert(binder.messages.size == 0)
    }

  }

  test("A binder with a wrong input should return warning messages"){

    val binder = new Binder(Library.load())
    val parser = new LambdaParser
    val input = "\\x.x 5 l"

    import parser.Success
    parser.parse(input) match {
      case Success(res,_) =>
        binder(res)
        assert(binder.messages.size != 0)
    }

  }


}
