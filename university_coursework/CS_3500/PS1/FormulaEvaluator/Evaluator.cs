using System;
using System.Collections;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Text.RegularExpressions;
using System.Threading.Tasks;

namespace FormulaEvaluator
{
    /// <summary>
    /// Evaluator class, containing "Evaluate" method for PS1.
    /// </summary>
    public class Evaluator

    {
        private static Stack<int> valueStack;
        private static Stack<string> operatorStack;
        public delegate int Lookup(String v);

        /// <summary>
        /// Evaluates integer arithmetic expressions written using standard infix notation. 
        /// It respects the usual precedence rules and integer arithmetic.
        /// The only legal tokens are the four operator symbols (+ - * /), left parentheses, 
        /// right parentheses, non-negative integers, whitespace, and variables
        /// consisting of one or more letters followed by one or more digits. Letters can be lowercase or uppercase.
        /// </summary>
        /// <param name="inputString"></param>
        /// <param name="variableEvaluator"></param>
        /// <returns></returns>
        public static int Evaluate(String inputString, Lookup variableEvaluator)
        {
            string[] tokens = tokenize(inputString);


            valueStack = new Stack<int>();
            operatorStack = new Stack<string>();

            foreach (string tokenPiece in tokens)
            {
                string token = tokenPiece.Trim();

                // Converts token to a digit if it is a variable
                
                if (isVariable(token))
                {
                    // If the variable is not a digit, throws an error
                    token = VariableLookup(token, variableEvaluator);
                }
                // If the token is an integer
                if (int.TryParse(token, out int value))
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
                        if(operatorStack.Count == 0)
                        {
                            throw new ArgumentException("Unclosed parenthesis.");
                        }
                        if (!LeftParenthesis(operatorStack.Peek()))
                        {
                            throw new ArgumentException("Unclosed parenthesis.");
                        }
                        operatorStack.Pop();
                        MultiplyOrDivideInStack();
                    }

                }
                // Throws an error if the token is not an empty space, all other strings should have been addressed.
                else if (!IsEmptySpacer(token))
                {
                    throw new ArgumentException("Invalid input in equation");
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
            if (operatorStack.Count == 0 && valueStack.Count == 1)
            {
                return valueStack.Pop();
            }
            // If there are too many items in operator stack or value stack, then there is an unbalanced digit or sign.
            throw new ArgumentException("Too many operators or numbers.");



        }

        /// <summary>
        /// Looks up variable value with string, and returns it as a string.
        /// </summary>
        /// <param name="variable"></param>
        /// <param name="variableEvaluator"></param>
        /// <returns></returns>
        private static string VariableLookup(string variable, Lookup variableEvaluator)
        {
            try
            {
                return variableEvaluator(variable).ToString();
            }
            catch
            {
                throw new ArgumentException("Not valid variable. Variable does not exist");
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
        /// Separates tokens into a string array separated by operators and whitespace.
        /// </summary>
        /// <param name="inputString"></param>
        /// <returns></returns>
        private static string[] tokenize(string inputString)
        {
            return Regex.Split(inputString, "(\\()|(\\))|(-)|(\\+)|(\\*)|(/)|( )");

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
                    int mult = valueStack.Pop() * valueStack.Pop();
                    valueStack.Push(mult);
                }
                else if ("/".Equals(operatorStack.Peek()))
                {
                    operatorStack.Pop();
                    int firstValue = valueStack.Pop();
                    int secondValue = valueStack.Pop();
                    if (secondValue == 0)
                    {
                        throw new ArgumentException("Illegal division by 0.");
                    }
                    int mult = secondValue / firstValue;
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

            if (valueStack.Count() == 0)
            {
                return;
                //throw new Exception("Too many signs in the equation.");
            }

            if (PlusOrMinusOperator(operatorStack.Peek()))
            {
                if ("+".Equals(operatorStack.Peek()))
                {
                    operatorStack.Pop();
                    if (valueStack.Count < 2)
                    {
                        throw new ArgumentException("Extra operator.");
                    }
                    int addition = valueStack.Pop() + valueStack.Pop();
                    valueStack.Push(addition);
                }
                else if ("-".Equals(operatorStack.Peek()))
                {
                    operatorStack.Pop();
                    int secondValue = valueStack.Pop();
                    if (valueStack.Count == 0)
                    {
                        throw new ArgumentException("Negative integers are not allowed.");
                    }

                    int firstValue = valueStack.Pop();
                    int subtraction = firstValue - secondValue;
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
            for (int variableCounter = 0; variableCounter < variables.Count(); variableCounter++)
            {

                //If the value is a letter, it will mark "containsLetter as true." If the numbers have already started
                //and a letter appears, then false will return
                if (Char.IsLetter(variables[variableCounter]))
                {
                    containsLetter = true;
                    if (containsNumber == true)
                    {
                        return false;
                    }

                }
                //If the value is a digit and letters have not appeared, then it will return false.
                // It will mark contains digit as true.
                else if (Char.IsDigit(variables[variableCounter]))
                {
                    containsNumber = true;
                    if (containsLetter == false)
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
        private static Boolean Divide(int value)
        {
            if (operatorStack.Count() == 0)
            {
                return false;
            }
            // If / was at the top of the stack
            if ("/".Equals(operatorStack.Peek()))
            {
                //Preventing division by 0
                if (value == 0)
                {
                    throw new ArgumentException("Illegal division by 0.");
                }

                // If value stack is empty
                if (valueStack.Count() == 0)
                {
                    throw new ArgumentException("Value stack is empty.");
                }

                int newPush = valueStack.Pop() / value;
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
        private static Boolean Multiply(int value)
        {
            if (operatorStack.Count() == 0)
            {
                return false;
            }

            if ("*".Equals(operatorStack.Peek()))
            {
                // If value stack is empty
                if (valueStack.Count() == 0)
                {
                    throw new ArgumentException("Value stack is empty.");
                }

                int newPush = valueStack.Pop() * value;
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



    }

}

