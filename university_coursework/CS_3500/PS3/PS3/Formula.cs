// Skeleton written by Joe Zachary for CS 3500, September 2013
// Read the entire skeleton carefully and completely before you
// do anything else!

// Version 1.1 (9/22/13 11:45 a.m.)

// Change log:
//  (Version 1.1) Repaired mistake in GetTokens
//  (Version 1.1) Changed specification of second constructor to
//                clarify description of how validation works

// (Daniel Kopta) 
// Version 1.2 (9/10/17) 

// Change log:
//  (Version 1.2) Changed the definition of equality with regards
//                to numeric tokens
// Version 1.3 - Haley Feten, CS 3500, 9/21/18


using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Text.RegularExpressions;

namespace SpreadsheetUtilities
{
    /// <summary>
    /// Represents formulas written in standard infix notation using standard precedence
    /// rules.  The allowed symbols are non-negative numbers written using double-precision 
    /// floating-point syntax; variables that consist of a letter or underscore followed by 
    /// zero or more letters, underscores, or digits; parentheses; and the four operator 
    /// symbols +, -, *, and /.  
    /// 
    /// Spaces are significant only insofar that they delimit tokens.  For example, "xy" is
    /// a single variable, "x y" consists of two variables "x" and y; "x23" is a single variable; 
    /// and "x 23" consists of a variable "x" and a number "23".
    /// 
    /// Associated with every formula are two delegates:  a normalizer and a validator.  The
    /// normalizer is used to convert variables into a canonical form, and the validator is used
    /// to add extra restrictions on the validity of a variable (beyond the standard requirement 
    /// that it consist of a letter or underscore followed by zero or more letters, underscores,
    /// or digits.)  Their use is described in detail in the constructor and method comments.
    /// </summary>
    public class Formula
    {

        private string formula;
        private static Stack<string> operatorStack;
        private static Stack<double> valueStack;
        private HashSet<string> variables;

        /// <summary>
        /// Creates a Formula from a string that consists of an infix expression written as
        /// described in the class comment.  If the expression is syntactically invalid,
        /// throws a FormulaFormatException with an explanatory Message.
        /// 
        /// The associated normalizer is the identity function, and the associated validator
        /// maps every string to true.  
        /// </summary>
        public Formula(String formula) :
            this(formula, s => s, s => true)
        {
            

        }

        /// <summary>
        /// Throws exception if there are zero tokens.
        /// </summary>
        /// <param name="tokens"></param>
        private void OneTokenRule(IEnumerable<string> tokens)
        {
            if (tokens.Count() == 0)
                throw new FormulaFormatException("Does not contain at least one token.");
        }

        /// <summary>
        /// Determines is string is a valid formula.
        /// </summary>
        /// <param name="formula"></param>
        private void ValidityCheck(IEnumerable<string> tokens, Func<string, bool> validator)
        {
            foreach (string token in tokens)
            {
                if (!ValidToken(token, validator))
                    throw new FormulaFormatException(token + " is not a valid token.");
            }
        }


        /// <summary>
        /// Determines if input is a valid token and uses the validator.
        /// </summary>
        /// <param name="token"></param>
        /// <returns></returns>
        private bool ValidToken(string token, Func<string, bool> validator)
        {
            if (Double.TryParse(token, out double result))
            {
                return true;
            }
            else if (token.Equals("/"))
            {
                return true;
            }
            else if (token.Equals("+"))
            {
                return true;
            }
            else if (token.Equals("-"))
            {
                return true;
            }
            else if (token.Equals("*"))
            {
                return true;
            }           
            else if (token.Equals(")"))
            {
                return true;
            }
            else if (token.Equals("("))
            {
                return true;
            }
            else if (isVariable(token))
            {
                return true;
            }
            else if (validator(token))
            {
                return true;
            }
            return false;
        }





        /// <summary>
        /// Creates a Formula from a string that consists of an infix expression written as
        /// described in the class comment.  If the expression is syntactically incorrect,
        /// throws a FormulaFormatException with an explanatory Message.
        /// 
        /// The associated normalizer and validator are the second and third parameters,
        /// respectively.  
        /// 
        /// If the formula contains a variable v such that normalize(v) is not a legal variable, 
        /// throws a FormulaFormatException with an explanatory message. 
        /// 
        /// If the formula contains a variable v such that isValid(normalize(v)) is false,
        /// throws a FormulaFormatException with an explanatory message.
        /// 
        /// Suppose that N is a method that converts all the letters in a string to upper case, and
        /// that V is a method that returns true only if a string consists of one letter followed
        /// by one digit.  Then:
        /// 
        /// new Formula("x2+y3", N, V) should succeed
        /// new Formula("x+y3", N, V) should throw an exception, since V(N("x")) is false
        /// new Formula("2x+y3", N, V) should throw an exception, since "2x+y3" is syntactically incorrect.
        /// </summary>
        public Formula(String formula, Func<string, string> normalize, Func<string, bool> isValid)
        {
            // Collect the variables that would be changed
            this.variables = CollectNormalizedVariables(formula, normalize);

            // Normalize the formula
            this.formula = normalize(formula);

            //Break apart the tokens
            IEnumerable<string> tokens = GetTokens(formula);

            //Checks all of the rules and if they're valid
            ValidityCheck(tokens, isValid);
            OneTokenRule(tokens);
            RightParenthesisRule(tokens);
            BalancedParenthesisRule(tokens);
            StartingTokenRule(tokens, isValid);
            EndingTokenRule(tokens, isValid);
            ParenthesisFollowingRule(tokens, isValid);
            ExtraFollowingRule(tokens, isValid);

        }

        /// <summary>
        /// Any token that immediately follows a number, a variable, or a closing 
        /// parenthesis must be either an operator or a closing parenthesis.
        /// </summary>
        /// <param name="tokens"></param>
        private void ExtraFollowingRule(IEnumerable<string> tokens, Func<string, bool> IsValid)
        {
            bool checkNext = false;
            foreach (string token in tokens)
            {
                if (checkNext)
                {
                    if (!IsOperator(token) && !RightParenthesis(token))
                    {
                        throw new FormulaFormatException("Breaks extra following rule.");
                    }
                    if (!RightParenthesis(token))
                    {
                        checkNext = false;
                    }

                }
                if (!IsOperator(token))
                    if (IsDigit(token) || IsValid(token) || RightParenthesis(token))
                    {
                        checkNext = true;
                    }

            }
        }

        /// <summary>
        /// Returns true if the string returns a digit.
        /// </summary>
        /// <param name="lastToken"></param>
        /// <returns></returns>
        private bool IsDigit(string input)
        {
            return Double.TryParse(input, out double result);
        }

       

        /// <summary>
        /// Any token that immediately follows an opening parenthesis or an operator
        /// must be either a number, a variable, or an opening parenthesis.
        /// </summary>
        /// <param name="tokens"></param>
        private void ParenthesisFollowingRule(IEnumerable<string> tokens, Func<string, bool> isValid)
        {
            bool paren = false;
            foreach (string token in tokens)
            {
                if (paren)
                {
                    if (AddMinusMultiplyDivide(token) || RightParenthesis(token))
                        throw new FormulaFormatException("Breaks parenthesis following rule.");
                    else
                        paren = false;
                }
                
                if (LeftParenthesis(token) || PlusOrMinusOperator(token))
                    paren = true;
                    
            }
        }

        /// <summary>
        /// The last token of an expression must be a number, 
        /// a variable, or a closing parenthesis.
        /// </summary>
        /// <param name="tokens"></param>
        private void EndingTokenRule(IEnumerable<string> tokens, Func<string, bool> IsValid)
        {
            string lastToken = "";
            foreach (string token in tokens)
                lastToken = token;
            if (AddMinusMultiplyDivide(lastToken))
                throw new FormulaFormatException("Breaks ending token rule.");
            if (!IsDigit(lastToken) && !IsValid(lastToken) && !RightParenthesis(lastToken))
                throw new FormulaFormatException("Breaks ending token rule.");
        }

        /// <summary>
        /// Checks if add, minus, multiply, or divide.
        /// </summary>
        /// <param name="lastToken"></param>
        /// <returns></returns>
        private bool AddMinusMultiplyDivide(string lastToken)
        {
            if (lastToken.Equals("+") || lastToken.Equals("-") || lastToken.Equals("/") || lastToken.Equals("*"))
            {
                return true;
            }
            return false;
        }

        /// <summary>
        /// The starting token must be a number, variable or opening parenthesis.
        /// </summary>
        /// <param name="tokens"></param>
        private void StartingTokenRule(IEnumerable<string> tokens, Func<string, bool> isValid)
        {

            string token = tokens.ElementAt(0);
            if (AddMinusMultiplyDivide(token))
                throw new FormulaFormatException("Starts with operator.");
            if (Double.TryParse(token, out double output))
                return;
            if (LeftParenthesis(token))
                return;
            if (isValid(token))
                return;
            throw new FormulaFormatException("Not a valid starting token.");
        }

        /// <summary>
        /// Checks if the parenthesis are balanced.
        /// </summary>
        /// <param name="tokens"></param>
        private void BalancedParenthesisRule(IEnumerable<string> tokens)
        {
            int rightParenthesis = 0;
            int leftParenthesis = 0;
            foreach (string token in tokens)
            {
                if (RightParenthesis(token))
                    rightParenthesis++;
                if (LeftParenthesis(token))
                    leftParenthesis++;
            }
            if (rightParenthesis != leftParenthesis)
                throw new FormulaFormatException("Breaks balanced parenthesis rule.");
        }

        /// <summary>
        /// Checks tokens to see if going down the line, if closing parenthesis ever exeed the 
        /// beginning parenthesis.
        /// </summary>
        /// <param name="tokens"></param>
        private void RightParenthesisRule(IEnumerable<string> tokens)
        {
            int rightParenthesis = 0;
            int leftParenthesis = 0;
            foreach (string token in tokens)
            {
                if (RightParenthesis(token))
                    rightParenthesis++;
                if (rightParenthesis > leftParenthesis)
                    throw new FormulaFormatException("Breaks right parenthesis rule.");
                if (LeftParenthesis(token))
                    leftParenthesis++;
            }
        }

        /// <summary>
        /// Evaluates this Formula, using the lookup delegate to determine the values of
        /// variables.  When a variable symbol v needs to be determined, it should be looked up
        /// via lookup(normalize(v)). (Here, normalize is the normalizer that was passed to 
        /// the constructor.)
        /// 
        /// For example, if L("x") is 2, L("X") is 4, and N is a method that converts all the letters 
        /// in a string to upper case:
        /// 
        /// new Formula("x+7", N, s => true).Evaluate(L) is 11
        /// new Formula("x+7").Evaluate(L) is 9
        /// 
        /// Given a variable symbol as its parameter, lookup returns the variable's value 
        /// (if it has one) or throws an ArgumentException (otherwise).
        /// 
        /// If no undefined variables or divisions by zero are encountered when evaluating 
        /// this Formula, the value is returned.  Otherwise, a FormulaError is returned.  
        /// The Reason property of the FormulaError should have a meaningful explanation.
        ///
        /// This method should never throw an exception.
        /// </summary>
        public object Evaluate(Func<string, double> lookup)
        {
            try
            {
                IEnumerable<string> tokens = GetTokens(formula);
                valueStack = new Stack<double>();
                operatorStack = new Stack<string>();

                foreach (string tokenPiece in tokens)
                {
                    string token = tokenPiece.Trim();

                    // Converts token to a digit if it is a variable

                    if (isVariable(token))
                    {
                        // If the variable is not a digit, throws an error
                        try
                        {
                            token = lookup(token).ToString();
                        }
                        catch (Exception)
                        {
                            throw new FormulaFormatException("Invalid variable.");
                        }
                    }
                    // If the token is an integer
                    if (Double.TryParse(token, out double value))
                    {
                        if (operatorStack.Count != 0)
                        {
                            if ("*".Equals(operatorStack.Peek()))
                            {
                                Multiply(value);
                            }
                            else if ("/".Equals(operatorStack.Peek()))
                            {
                                Divide(value);
                            }
                            else
                            {
                                valueStack.Push(value);
                            }
                        }

                        //Pushes value is there is no multiplication/division, etc following in the operator stack
                        else
                        {
                            valueStack.Push(value);
                        }
                    }

                    // Enters this loop if the value is an operator
                    else if (IsOperator(token))
                    {
                        // If it is + or -, does algorithm to add or subtract if there is already a + or - in stack; otherwise
                        // pushes operator on to the stack.
                        if (PlusOrMinusOperator(token))
                        {
                            AddOrSubtractFromStack();
                            operatorStack.Push(token);
                        }
                        //If the operator is * or /, pushes on to stack.
                        else if (MultiplyOrDivideOperator(token))
                        {
                            operatorStack.Push(token);
                        }
                        // If the operator is (, pushes on to the operator stack.
                        else if (LeftParenthesis(token))
                        {
                            operatorStack.Push(token);
                        }
                        // If the operator is ), checks to see if a + or - is in the stack to be performed.
                        // Throws and error if there is not a left parenthesis on the top of the stack.
                        // Pops the left parenthesis.
                        // Multiplies or divides if the operator was in the stack.
                        else if (RightParenthesis(token))
                        {
                            AddOrSubtractFromStack();
                            operatorStack.Pop();
                            MultiplyOrDivideInStack();
                        }

                    }
                    // Throws an error if the token is not an empty space, all other strings should have been addressed.
                    else if (!IsEmptySpacer(token))
                    {
                        throw new FormulaFormatException("Invalid input in equation");
                    }

                }

                // After the foreach loop, if the operator stack = 0, and the value stack = 1, then pop the final value.
                if (operatorStack.Count == 0 && valueStack.Count == 1)
                {

                    return valueStack.Pop();
                }

                // Otherwise, the only item left should be + or -...address this and add or subtract
                AddOrSubtractFromStack();
                //Return final value

                return valueStack.Pop();
            }
            catch (FormulaFormatException)
            {
                return new FormulaError("Illegal division by 0 or illegal variable.");
            }

            // If there are too many items in operator stack or value stack, then there is an unbalanced digit or sign.

        }

        /// <summary>
        /// Looks up variable value with string, and returns it as a string.
        /// </summary>
        /// <param name="variable"></param>
        /// <param name="variableEvaluator"></param>
        /// <returns></returns>
        private static string VariableLookup(string variable, Func<string, double> lookup)
        {
            try
            {
                return lookup(variable).ToString();
            }
            catch
            {
                throw new FormulaFormatException("Not valid variable. Variable does not exist");
            }

        }

        /// <summary>
        /// Returns true if the string only consists of whitespace or "".
        /// </summary>
        /// <param name="token"></param>
        /// <returns></returns>
        private static bool IsEmptySpacer(string token)
        {
            if ("".Equals(token) || " ".Equals(token))
            {
                return true;
            }
            return false;
        }

      
       

        /// <summary>
        /// If * or / is at the top of the operator stack, pop the value stack twice and the operator stack once. 
        /// Apply the popped operator to the popped numbers. Push the result onto the value stack.
        /// </summary>
        /// <param name="token"></param>
        /// <returns></returns>
        private static void MultiplyOrDivideInStack()
        {
            if (operatorStack.Count != 0 && valueStack.Count >= 2)
            {
                if ("*".Equals(operatorStack.Peek()))
                {
                    operatorStack.Pop();
                    double mult = valueStack.Pop() * valueStack.Pop();
                    valueStack.Push(mult);
                }
                else if ("/".Equals(operatorStack.Peek()))
                {
                    operatorStack.Pop();
                    double secondValue = valueStack.Pop();
                    double firstValue = valueStack.Pop();
                    if (secondValue == 0)
                    {
                       throw new FormulaFormatException();
                    }
                    double mult = firstValue / secondValue;
                    valueStack.Push(mult);
                }
            }

        }


        /// <summary>
        /// Returns true if multiply or divide operator.
        /// </summary>
        /// <param name="token"></param>
        /// <returns></returns>
        private static bool MultiplyOrDivideOperator(string token)
        {
            if ("*".Equals(token))
            {
                return true;
            }
            if ("/".Equals(token))
            {
                return true;
            }
            return false;
        }

        /// <summary>
        /// Returns true if the value is a right parenthesis.
        /// </summary>
        /// <param name="token"></param>
        /// <returns></returns>
        private static bool RightParenthesis(string token)
        {
            if (")".Equals(token))
            {
                return true;
            }
            return false;
        }

        /// <summary>
        /// Returns true is the value is a left parenthesis.
        /// </summary>
        /// <param name="token"></param>
        /// <returns></returns>
        private static bool LeftParenthesis(string token)
        {
            if ("(".Equals(token))
            {
                return true;
            }
            return false;
        }

        /// <summary>
        /// If + or - are on the top of the operator stack, then the operation will be performed.
        /// </summary>
        private static void AddOrSubtractFromStack()
        {
            if (operatorStack.Count() == 0)
            {
                return;
            }

            

            if (PlusOrMinusOperator(operatorStack.Peek()))
            {
                if ("+".Equals(operatorStack.Peek()))
                {
                    operatorStack.Pop();

                    double addition = valueStack.Pop() + valueStack.Pop();
                    valueStack.Push(addition);
                }
                else if ("-".Equals(operatorStack.Peek()))
                {
                    operatorStack.Pop();
                    double secondValue = valueStack.Pop();


                    double firstValue = valueStack.Pop();
                    double subtraction = firstValue - secondValue;
                    valueStack.Push(subtraction);
                }
            }
        }

        /// <summary>
        /// Returns true if the input is a + or - operator.
        /// </summary>
        /// <param name="token"></param>
        /// <returns></returns>
        private static bool PlusOrMinusOperator(string token)
        {
            if (token == "+" || token == "-")
            {
                return true;
            }
            return false;
        }

        /// <summary>
        /// Returns true if the variable follows the proper variable format with letters followed by numbers.
        /// </summary>
        /// <param name="token"></param>
        /// <returns></returns>
        private static bool isVariable(string token)
        {
            char[] variables = token.ToCharArray();
            bool containsNumber = false;
            bool containsLetter = false;
            bool containsUnderscore = false;
            for (int variableCounter = 0; variableCounter < variables.Count(); variableCounter++)
            {

                //If the value is a letter, it will mark "containsLetter as true." If the numbers have already started
                //and a letter appears, then false will return
                if (Char.IsLetter(variables[variableCounter]))
                {
                    containsLetter = true;
                    if (containsNumber || containsUnderscore)
                    {
                        return false;
                    }

                }
                else if (variables[variableCounter].Equals('_'))
                {
                    if (containsUnderscore)
                    {
                        return false;
                    }
                    containsUnderscore = true;
                }
                //If the value is a digit and letters have not appeared, then it will return false.
                // It will mark contains digit as true.
                else if (Char.IsDigit(variables[variableCounter]))
                {
                    containsNumber = true;
                    if (!containsLetter)
                    {
                        return false;
                    }
                }

                //If illegal variable piece, return false
                else
                {
                    return false;
                }

                // If looking up t reveals it has no value(the delegate throws).

            }

            if (containsLetter == false || containsNumber == false)
            {
                return false;
            }
            return true;
        }

        /// <summary>
        /// Returns true if divides value in stack when a division sign is found. Returns false if the operator
        /// stack is empty or if the operation is not performed. Does not allow division by 0.
        /// </summary>
        /// <param name="value"></param>
        /// <returns></returns>
        private static bool Divide(double value)
        {
            
            // If / was at the top of the stack
            if ("/".Equals(operatorStack.Peek()))
            {
                //Preventing division by 0
                if (value == 0)
                {
                    throw new FormulaFormatException("Can't divide by 0");
                }

                double newPush = valueStack.Pop() / value;
                operatorStack.Pop();
                valueStack.Push(newPush);
                return true;
            }
            return false;
        }

        /// <summary>
        /// Returns true if multiplies value in stack when a multiplication sign is found in the operator stack.
        /// </summary>
        /// <param name="value"></param>
        /// <returns></returns>
        private static bool Multiply(double value)
        {
           

            if ("*".Equals(operatorStack.Peek()))
            {
                double newPush = valueStack.Pop() * value;
                operatorStack.Pop();
                valueStack.Push(newPush);
                return true;
            }
            return false;
        }

        /// <summary>
        /// Returns true if the token is a permitted operator.
        /// </summary>
        /// <param name="token"></param>
        /// <returns></returns>
        private static bool IsOperator(string token)
        {
            if (token == "/")
            {
                return true;
            }
            if (token == "+")
            {
                return true;
            }
            if (token == "-")
            {
                return true;
            }
            if (token == "*")
            {
                return true;
            }
            if (token == "(")
            {
                return true;
            }
            if (token == ")")
            {
                return true;
            }
            return false;
        }


        /// <summary>
        /// Enumerates the normalized versions of all of the variables that occur in this 
        /// formula.  No normalization may appear more than once in the enumeration, even 
        /// if it appears more than once in this Formula.
        /// 
        /// For example, if N is a method that converts all the letters in a string to upper case:
        /// 
        /// new Formula("x+y*z", N, s => true).GetVariables() should enumerate "X", "Y", and "Z"
        /// new Formula("x+X*z", N, s => true).GetVariables() should enumerate "X" and "Z".
        /// new Formula("x+X*z").GetVariables() should enumerate "x", "X", and "z".
        /// </summary>
        public IEnumerable<String> GetVariables()
        {
            return variables.AsEnumerable();
        }

        /// <summary>
        /// Collects the variables if they are normalized
        /// </summary>
        /// <param name="formula"></param>
        /// <returns></returns>
        private HashSet<string> CollectNormalizedVariables(string formula, Func<string, string> normalize)
        {
            //Determines if there is a normalizer
            bool normalizerExists = false;
            if (!normalize("a").Equals("a"))
                normalizerExists = true;

            IEnumerable<string> tokenList = GetTokens(formula);
            HashSet<string> normalized = new HashSet<string>();

            if (!normalizerExists)
            {
                foreach (string token in tokenList)
                {
                    if (!IsDigit(token) && !IsOperator(token))
                        if (!normalized.Contains(normalize(token)))
                            normalized.Add(normalize(token));
                }
                return normalized;
            }
            else
            {
                foreach (string token in tokenList)
                {
                    if (!IsDigit(token) && !IsOperator(token))
                        if (!normalized.Contains(normalize(token)) && !normalize(token).Equals(token))
                            normalized.Add(normalize(token));
                }
                return normalized;
            }
        }

        /// <summary>
        /// Returns a string containing no spaces which, if passed to the Formula
        /// constructor, will produce a Formula f such that this.Equals(f).  All of the
        /// variables in the string should be normalized.
        /// 
        /// For example, if N is a method that converts all the letters in a string to upper case:
        /// 
        /// new Formula("x + y", N, s => true).ToString() should return "X+Y"
        /// new Formula("x + Y").ToString() should return "x+Y"
        /// </summary>
        public override string ToString()
        {
            string output = "";
            IEnumerable<string> formulaArray = GetTokens(formula);
            foreach (string token in formulaArray)
            {
                if (Double.TryParse(token, out double result))
                {
                    output = output + result.ToString();
                }
                else
                {
                    output = output + token.Trim();
                }
            }
            return output;
        }

        /// <summary>
        /// If obj is null or obj is not a Formula, returns false.  Otherwise, reports
        /// whether or not this Formula and obj are equal.
        /// 
        /// Two Formulae are considered equal if they consist of the same tokens in the
        /// same order.  To determine token equality, all tokens are compared as strings 
        /// except for numeric tokens and variable tokens.
        /// Numeric tokens are considered equal if they are equal after being "normalized" 
        /// by C#'s standard conversion from string to double, then back to string. This 
        /// eliminates any inconsistencies due to limited floating point precision.
        /// Variable tokens are considered equal if their normalized forms are equal, as 
        /// defined by the provided normalizer.
        /// 
        /// For example, if N is a method that converts all the letters in a string to upper case:
        ///  
        /// new Formula("x1+y2", N, s => true).Equals(new Formula("X1  +  Y2")) is true
        /// new Formula("x1+y2").Equals(new Formula("X1+Y2")) is false
        /// new Formula("x1+y2").Equals(new Formula("y2+x1")) is false
        /// new Formula("2.0 + x7").Equals(new Formula("2.000 + x7")) is true
        /// </summary>
        public override bool Equals(object obj)
        {
            try
            {
                return this.ToString().Equals(obj.ToString());
            }
            catch (Exception)
            {
                return false;
            }
        }

        /// <summary>
        /// Reports whether f1 == f2, using the notion of equality from the Equals method.
        /// Note that if both f1 and f2 are null, this method should return true.  If one is
        /// null and one is not, this method should return false.
        /// </summary>
        public static bool operator ==(Formula f1, Formula f2)
        {
            try
            {
                return f1.Equals(f2);
            }
            catch (Exception)
            {
                return false;
            }
        }

        /// <summary>
        /// Reports whether f1 != f2, using the notion of equality from the Equals method.
        /// Note that if both f1 and f2 are null, this method should return false.  If one is
        /// null and one is not, this method should return true.
        /// </summary>
        public static bool operator !=(Formula f1, Formula f2)
        {
            return !f1.Equals(f2);
        }

        /// <summary>
        /// Returns a hash code for this Formula.  If f1.Equals(f2), then it must be the
        /// case that f1.GetHashCode() == f2.GetHashCode().  Ideally, the probability that two 
        /// randomly-generated unequal Formulae have the same hash code should be extremely small.
        /// </summary>
        public override int GetHashCode()
        {
            return this.ToString().GetHashCode();
        }

        /// <summary>
        /// Given an expression, enumerates the tokens that compose it.  Tokens are left paren;
        /// right paren; one of the four operator symbols; a string consisting of a letter or underscore
        /// followed by zero or more letters, digits, or underscores; a double literal; and anything that doesn't
        /// match one of those patterns.  There are no empty tokens, and no token contains white space.
        /// </summary>
        private static IEnumerable<string> GetTokens(String formula)
        {
            // Patterns for individual tokens
            String lpPattern = @"\(";
            String rpPattern = @"\)";
            String opPattern = @"[\+\-*/]";
            String varPattern = @"[a-zA-Z_](?: [a-zA-Z_]|\d)*";
            String doublePattern = @"(?: \d+\.\d* | \d*\.\d+ | \d+ ) (?: [eE][\+-]?\d+)?";
            String spacePattern = @"\s+";

            // Overall pattern
            String pattern = String.Format("({0}) | ({1}) | ({2}) | ({3}) | ({4}) | ({5})",
                                            lpPattern, rpPattern, opPattern, varPattern, doublePattern, spacePattern);

            // Enumerate matching tokens that don't consist solely of white space.
            foreach (String s in Regex.Split(formula, pattern, RegexOptions.IgnorePatternWhitespace))
            {
                if (!Regex.IsMatch(s, @"^\s*$", RegexOptions.Singleline))
                {
                    yield return s;
                }
            }

        }
    }

    /// <summary>
    /// Used to report syntactic errors in the argument to the Formula constructor.
    /// </summary>
    public class FormulaFormatException : Exception
    {
        public FormulaFormatException()
        {
        }

        /// <summary>
        /// Constructs a FormulaFormatException containing the explanatory message.
        /// </summary>
        public FormulaFormatException(String message)
            : base(message)
        {
        }
    }

    /// <summary>
    /// Used as a possible return value of the Formula.Evaluate method.
    /// </summary>
    public struct FormulaError
    {
        /// <summary>
        /// Constructs a FormulaError containing the explanatory reason.
        /// </summary>
        /// <param name="reason"></param>
        public FormulaError(String reason)
            : this()
        {
            Reason = reason;
        }

        /// <summary>
        ///  The reason why this FormulaError was created.
        /// </summary>
        public string Reason { get; private set; }
    }
}
