package parsing

import churchStructures.ChurchNumber
import parsing.AST.{Expression, FunctionApplication, Lambda, Variable}

import scala.util.parsing.combinator.syntactical.StdTokenParsers
import scala.util.parsing.combinator.lexical.StdLexical
import scala.util.parsing.combinator._

/**
  * Created by Marco on 07/11/16.
  */
class LambdaParser extends StdTokenParsers with PackratParsers
{

  override type Tokens = StdLexical
  override val lexical = new StdLexical
  lexical.delimiters ++= Seq("λ","\\", ".", "(", ")",";","=")

  type P[+T] = PackratParser[T]
  lazy val expr: P[Expression]                    = application | notApp
  lazy val notApp                                 = variable | number | parens | lambda
  lazy val lambda: P[Lambda]                      = positioned(("λ" | "\\") ~> variable ~ "." ~ expr ^^ { case v ~ "." ~ e  => Lambda(v, e) })
  lazy val application: P[FunctionApplication]    = positioned(expr ~ notApp ^^ { case left ~ right => FunctionApplication(left, right) })
  lazy val variable: P[Variable]                  = positioned(ident ^^ VariableManager.apply)
  lazy val parens: P[Expression]                  = "(" ~> expr <~ ")"
  lazy val number: P[Lambda]                      = numericLit ^^ { case n => ChurchNumber(n.toInt) }


  def parse(source: String): ParseResult[Expression] =
  {
    val tokens = new lexical.Scanner(source)
    phrase(expr)(tokens)
  }


  lazy val defs                                   = repsep(defn, ";") <~ opt(";") ^^ Map().++
  lazy val defn                                   = ident ~ "=" ~ expr ^^ { case id ~ "=" ~ t => id -> t }

  def definitions(str: String): ParseResult[Map[String, Expression]] = {
    val tokens = new lexical.Scanner(str)
    phrase(defs)(tokens)
  }

}
