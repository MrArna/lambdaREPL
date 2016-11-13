import interpreting.{Binder, Interpreter}
import parsing.LambdaParser
import utils.{Library, Printer}

/**
  * Created by Marco on 08/11/16.
  */
object Main {

  val parser = new LambdaParser()
  var binder = new Binder(Library.load())
  val pretty = new Printer
  val interpreter = new Interpreter()

  def main(args: Array[String]) {

    while (true) {
      val input = scala.io.StdIn.readLine("λ> ")
      if (input contains "=")
        handleDef(input)
      else
        handleExpr(input)
    }
  }

  def handleDef(input: String) =
    parseInput(parser.definitions, input) { defs =>
      println("Defined: " + defs.keys.mkString(", "))
      binder = new Binder(binder.defs ++ defs)
    }

  def handleExpr(input: String) =
    parseInput(parser.parse, input) { expr =>
      val bound = binder(expr)
      if (binder.messages.isEmpty)
        println(pretty(interpreter(bound)))
      else {
        for (m <- binder.messages)
          println(m.pos.longString + m.msg)
        binder.messages.clear()
      }
    }

  def parseInput[T](p: String => parser.ParseResult[T], input: String)(success: T => Unit): Unit = {
    import parser.{NoSuccess, Success}
    p(input) match {
      case Success(res, _) => success(res)
      case NoSuccess(err, _) => println(err)
    }
  }
}