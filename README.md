CS474 @ UIC: HOMEWORK3
======================
Developed by Marco Arnaboldi (marnab2@uic.edu)

#Description
--------------------
In your fourth and the final homework assignment you will solidify the knowledge of lambda-calculus by designing and implementing an untyped lambda-calculus interpreter. Your goal is to gain experience with the fundamentals of the functional programming languages and to understand reduction and conversion rules of the lambda calculus. You will implement your lambda interpreter in Scala and you will build and RUN your project using the SBT with the runMain command from the command line. In your lambda interpreter, you will use the reserved keyword lambda for designating the Greek letter lambda. You will define the rules for creating valid names (e.g., what characters they can contain, can the names start with a digit) using a regular expression. You will use the latest community version of IntelliJ IDE for this assignment.



#Development & Design choices
-----------------
The application was developed with IntelliJ, with the use of SBT in order to manage the libraries. It has been designed in order to be as extendable as possible.
In detail, it's composed by 4 modules and different classes:

+ *Main*: this is the the core of the application, where the other classes are instantiated and used. It is in charge to expose a UI to the user and to call the right methods depending on the user inputs.
+ **Parsing**: this module contains the classes used in order to parse the lambda expressions prompted by the user.
    + *AST*: this class contains the definition of the core elements defining a lambda expression and the parser in order to parse the user expression
        + *Expression*: defined as a Trait in order to use polymorphism among elements
        + *Variable*: represents a Variable, with its identifier and its scope
        + *FunctionApplication*: represents a functions, that is an expression itself, and its argument, which is an expression itself.
        + *Lambda*: represents a lambda function with a variable as argument and a body as expression.
    + *VariableManager*: this object is in charge to create variables with the given name and the right scope.
    + *Parser*: this class is in charge to parse the user input and to create accordingly the AST representing the lambda expression.
+ **Interpreting**: this module contains useful classes interpret the lambda expression
    + *Scope*: this class represent the scope givent to a variable, it contains the id of the scope and provide a method in order to find the closest binding for that scope.
    + *ScopeManager*: this object is in charge to maintain update the scopes and to return a new scope when requested
    + *Binder*: this class is in charge to bind the variable to the correct scope.
    + *BetaSubstitution*: this class represents the behaviour of the beta-substitution
    + *Interpreter*: this class interprets the expression and call the beta-substitution when required
+ **Utils**: this module contains utils in order to help user comprehension
    + *Printer*: provide methods in order to print in an easy way to understand the lambda expression
    + *Message*: structure representing an error message for a mistake in the expression
    + *Library*: contains useful definition of lambda expression shortcuts, loaded on application launch
+ **churchStructures**: this module contains exractors in order to manage numbers and boolean operations
    + *ChurchNumber*: Extractor for numbers expressed in Church form. 
    + *ChurchBoolean*: Extractor for booleans expressed in Church form.

Further information about the methods and their behaviors can be found in the comment inside the code.

#Functionalities
----------------

######Main functionality

The application evaluate lambda expression prompted into the command line provided, showing all the evaluation steps.

######Additional functionalities
The application allows userd defined shortcuts and also the evaluation of boolean, number and recursive expression

#Usage
----------------

######Launch
To use the application, open the terminal and type as the following snippet of code, from the folder where the project is located:

`sbt run`

######In app

Instead of using lambda symbol, use '\'. E.g.: λx.x should be expressed as \x.x.
Define a function do: `myFunc = lambda expression`
To use number: `number (pow 2 2)` will return 4
To use boolean: `bool (and T T)` will return T

#Test
----------------

The tests and the application were developed in a OS X environment.

#### Fansuite
Test were made automated using the fansuite framework provided by the Scalatest library. 


#Acknowledgments
---------------
Inspiration was taken by the [Lambda function Tutorial](https://zeroturnaround.com/rebellabs/parsing-lambda-calculus-in-scala/) provided by ZeroTurnAround. The code was rewritten and readapted in order to implement the described functionalities.