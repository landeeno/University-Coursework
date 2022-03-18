using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using FormulaEvaluator;

namespace FormulaEvaluatorTest
{
    /// <summary>
    /// Tester for the PS1 homework assignment using Evaluate(). The Lookup returns 0 no matter the Lookup Value.
    /// </summary>
    class PS1Test
    {

        /// <summary>
        /// Variable lookup, only returns 0 no matter the outlut.
        /// </summary>
        /// <param name="v"></param>
        /// <returns></returns>
        public static int variableLookup(string v)
        {
            return 0;
        }

        /// <summary>
        /// Returns true if an exception was thrown.
        /// </summary>
        /// <param name="input"></param>
        /// <returns></returns>
        public static bool ExceptionCaught(string input)
        {
            try
            {
                FormulaEvaluator.Evaluator.Evaluate(input, variableLookup);
                return false;
            }
            catch (Exception)
            {
                return true;
            }
        }

        /// <summary>
        /// Returns true if the expected string matches the actual returned string.
        /// </summary>
        /// <param name="input"></param>
        /// <param name="expectedValue"></param>
        /// <returns></returns>
        public static bool ReturnsExpectedValue(string input, string expectedValue)
        {
            try
            {
                int actualValue = FormulaEvaluator.Evaluator.Evaluate(input, variableLookup);
                if (actualValue == int.Parse(expectedValue))
                {
                    return true;
                }
                throw new Exception("Does not return expected value.");
            }
            catch (Exception)
            {
                return false;
            }
        }

        static void Main(string[] args)
        {


            // Input values which should throw an exception.
            string[,] inputsWithExcpetion = new string[,] { { "-3 + 3"},
            {"5++5" },
            {"5 5" },
            {"()3++8" },
            {" 5 / 0" },
            {"AA4 + A33 + 4A3" },
            {"(5*4-3)/0*7" },
            {"3**7" },
            {"5//4" },
            {"3*()()()()()()()+7" },
            {"3*()-()()()7" },
            {"3339*7-(7/0)" },
            {"this is a test" },
            {"A+3" },
            {"3A2" },
            {"2--" },
            {"d" },
            {"99999a" },
            {"three" },
            {"0*0/0" },
            {"3/a9" },
            {"" },
            {" " },
            { "4 * (9-3) * 3 -4 4"},
            { "(((((16/4))))"} };

            // Values that should not throw an exception, followed by its expected string.
            string[,] inputValuesNoException = new string[,] {{"(2+35)", "37"},
                {"3-10", "-7" },
                {"5 / 2", "2" },
                {"A4 + A3", "0" },
                { "()3+8", "11"},
                { "(5*(2*2*2)-4*(4-1))/(2)*30", "420"},
            {"2*35", "70" },
            { "(5-3) * 4", "8"},
            {"A4 + A5", "0" },
            {"3*()7", "21" },
            {"3*()()()()7", "21" },
            {"a4+3-B8", "3" },
            {"aaaaaaa99999999 - 3", "-3" },
            {"3 / 2*a9", "0" },
            {"45", "45" },
            {" 3 -     3", "0" },
            {"(((((16/4)))))-9", "-5" } };

            int exceptionTests = inputsWithExcpetion.Length;
            int exceptionsCaught = 0;

            // Testing values which should throw an exception. Prints an error if an exception is not thrown.
            foreach (string input in inputsWithExcpetion)
            {
                if (ExceptionCaught(input))
                {
                    exceptionsCaught++;
                }

                else
                {
                    Console.Write("Failed: " + input + "\n");
                }
            }




            int testsRun = inputValuesNoException.GetLength(0);
            int testsPassed = 0;

            // Testing if the correct output comes out. Prints value if the value is not what is expected.
            for (int counter = 0; counter < inputValuesNoException.GetLength(0); counter++)
            {
                if (ReturnsExpectedValue(inputValuesNoException[counter, 0], inputValuesNoException[counter, 1]))
                {
                    testsPassed++;
                }
                else
                {
                    Console.Write("Failed: " + inputValuesNoException[counter, 0] + "\n");
                }
            }

            // Print success rate.
            Console.Write("\n");
            Console.Write("Tests passed: " + (exceptionsCaught + testsPassed) + "\n");
            Console.Write("Total tests: " + (testsRun + exceptionTests) + "\n");
            Console.Read();


        }
    }
}
