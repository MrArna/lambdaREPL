package parsing

import churchStructures.ChurchNumber
import parsing.AST.{Expression, FunctionApplication, Lambda, Variable}

import scala.util.parsing.combinator.syntactical.StdTokenParsers
import scala.util.parsing.combinator.lexical.StdLexical
import scala.util.parsing.combinator._

/**
  * Created by Marco on 07/11/16.
  */
class LambdaParser extends StdTokenParsers with PackratParsers //use packrat for left recursion
{

  override type Tokens = StdLexical
  override val lexical = new StdLexical

  // define delimiters for the parser
  lexical.delimiters ++= Seq("λ","\\", ".", "(", ")",";","=")



  // define grammar for the parser, ~ means concat, ^^ means apply this function upon parsing
  type P[+T] = PackratParser[T]
  lazy val expr: P[Expression]                    = application | notApp
  lazy val notApp                                 = variable | number | parens | lambda
  // if lambda found create a lambda with that variable and that expression as params
  lazy val lambda: P[Lambda]                      = positioned(("λ" | "\\") ~> variable ~ "." ~ expr ^^ { case v ~ "." ~ e  => Lambda(v, e) })
  // if application fuound, create a application with the left and right args
  lazy val application: P[FunctionApplication]    = positioned(expr ~ notApp ^^ { case left ~ right => FunctionApplication(left, right) })
  // if var found create a new variable with that identifier
  lazy val variable: P[Variable]                  = positioned(ident ^^ VariableManager.apply)
  lazy val parens: P[Expression]                  = "(" ~> expr <~ ")"
  //if number found, transle into a church equivalent version
  lazy val number: P[Lambda]                      = numericLit ^^ { case n => ChurchNumber(n.toInt) }


  def parse(source: String): ParseResult[Expression] =
  {
    val tokens = new lexical.Scanner(source)
    phrase(expr)(tokens)
  }


  // handling user definition
  lazy val defs                                   = repsep(defn, ";") <~ opt(";") ^^ Map().++
  lazy val defn                                   = ident ~ "=" ~ expr ^^ { case id ~ "=" ~ t => id -> t }


  def definitions(str: String): ParseResult[Map[String, Expression]] = {
    val tokens = new lexical.Scanner(str)
    phrase(defs)(tokens)
  }

}
