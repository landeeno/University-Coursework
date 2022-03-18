using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using SpreadsheetUtilities;

namespace SS

{


    public class Spreadsheet : AbstractSpreadsheet

    {

        Dictionary<string, Cell> cells = new Dictionary<string, Cell>();
        DependencyGraph dependencyGraph = new DependencyGraph();

        public override object GetCellContents(string name)
        {
            CheckName(name);
            if (cells.TryGetValue(name, out Cell cell))
            {
                return cell.Contents;
            }
            return "";
        }

        /// <summary>
        /// Validates if correct variable name. Throws invalid name exception if not a valid variable.
        /// </summary>
        /// <param name="name"></param>
        private void CheckName(string name)
        {
            if (name is null)
            {
                throw new InvalidNameException();
            }

            char[] variables = name.ToCharArray();
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
                        throw new InvalidNameException();
                    }

                }
                else if (variables[variableCounter].Equals('_'))
                {
                    if (containsUnderscore)
                    {
                        throw new InvalidNameException();
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
                        throw new InvalidNameException();
                    }
                }

                //If illegal variable piece, return false
                else
                {
                    throw new InvalidNameException();
                }

                // If looking up t reveals it has no value(the delegate throws).

            }

            if (containsLetter == false || containsNumber == false)
            {
                throw new InvalidNameException();
            }
            return;
        }

                    

        public override IEnumerable<string> GetNamesOfAllNonemptyCells()
        {
            // Returns all nonempty cells in the list of full cells.
            return cells.Keys;
        }

        public override ISet<string> SetCellContents(string name, double number)
        {
            
            //First checks name of cell if it's valid and not null
            CheckName(name);
            RemoveDependency(name);
            Cell cellToAdd = new Cell();
            cellToAdd.Contents = number;

            // If contains, remove the key to add again
            if (cells.ContainsKey(name))
            {
                cells.Remove(name);
            }

            // Add double to the cell with the number
            cells.Add(name, cellToAdd);

            // Get all items directly and indirectly dependent on this cell
            ISet<string> cellList = new HashSet<string>(GetCellsToRecalculate(name));
            return cellList;
        }





        public override ISet<string> SetCellContents(string name, string text)
        {
            if (text is null)
            {
                throw new ArgumentNullException();
            }
            CheckName(name);
            RemoveDependency(name);

            // If empty text, do not add it to the list of nonempty cells
            if (text.Equals(""))
                return new HashSet<string>();

            //First checks name of cell if it's valid and not null

            // Add double to the cell with the number
            Cell cellToAdd = new Cell();
            cellToAdd.Contents = text;

            // If contains, remove the key to add again
            if (cells.ContainsKey(name))
            {
                cells.Remove(name);
            }

            // Add double to the cell with the number
            cells.Add(name, cellToAdd);

            // Get all items directly and indirectly dependent on this cell
            ISet<string> cellList = new HashSet<string>(GetCellsToRecalculate(name));
            return cellList;
        }

        public override ISet<string> SetCellContents(string name, Formula formula)
        {
            //If formula parameter is null, throws a argument null exception.
            if (formula is null)
                throw new ArgumentNullException();
           
            //First checks name of cell if it's valid and not null
            CheckName(name);

            // All the cells to recalculated, if there is a circular dependency, then it will throw an exception
            

            //Check for circular dependency,
            // if so, throw error

            //Get variables from formula
            IEnumerable<string> dependees = formula.GetVariables();

            HashSet<string> circularCells = new HashSet<string>(GetCellsToRecalculate(name));
            foreach (string dependee in dependees)
            {
                if (circularCells.Contains(dependee))
                    throw new CircularException();
            }

            RemoveDependency(name);
            // Add dependees
            foreach (string dependee in dependees)
            {
                dependencyGraph.AddDependency(dependee, name);

            }

            Cell cellToAdd = new Cell();
            cellToAdd.Contents = formula;

            // If contains, remove the key to add again
            if (cells.ContainsKey(name))
            {
                cells.Remove(name);
            }

            // Add double to the cell with the formula
            cells.Add(name, cellToAdd);


            // Get all items directly and indirectly dependent on this cell
            ISet<string> cellList = new HashSet<string>(GetCellsToRecalculate(name));
            return cellList;
        }

        private void RemoveDependency(string name)
        {
            IEnumerable<string> dependents = new HashSet<string>(dependencyGraph.GetDependees(name));
            foreach (string dependent in dependents)
            {
                dependencyGraph.RemoveDependency(dependent, name);
            }
        }

        protected override IEnumerable<string> GetDirectDependents(string name)
        {
            CheckName(name);
            return dependencyGraph.GetDependents(name);
        }
    } }