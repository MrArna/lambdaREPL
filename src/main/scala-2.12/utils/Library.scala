package utils

import parsing.AST.Expression
import parsing.LambdaParser

/**
  * Created by Marco on 10/11/16.
  */
object Library {
  val source = """
    id = \x.x;
    true  = \t. \f. t;
    false = \t. \f. f;
    if    = \c. \t. \e. c t e;
    or    = \a. \b. a true b;
    and   = \a. \b. a b false;

    pair   = \f. \s. \b. b f s;
    first  = \p. p true;
    second = \p. p false;

    succ = \n.\s.\z. s (n s z);
    add  = \a.\b.\s.\z. a s (b s z);
    mul  = \a.\b.\s. a (b s);
    pow  = \a.\b. b a;

    bool   = \b.b T F;
    number = \n.n S Z;
               """


  //laod definitions in AST form
  def load() = {
    val parse = new LambdaParser()
    import parse.{ Success, NoSuccess }
    parse.definitions(source) match {
      case Success(lib, _)   => lib
      case NoSuccess(err, _) => println(err); Map[String, Expression]()
    }
  }
}