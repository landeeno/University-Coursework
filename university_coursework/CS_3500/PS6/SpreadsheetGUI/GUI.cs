using SS;
using System;
using System.Collections.Generic;
using System.Drawing;
using System.Windows.Forms;

namespace SpreadsheetGUI
{
    public partial class Spreadsheet : Form
    {
        AbstractSpreadsheet mySpreadsheet;
        private bool drawSquare;
        private bool drawCircle;
        private bool drawTriangle;

        /// <summary>
        /// Constructor for Spreadsheet.
        /// </summary>
        public Spreadsheet()
        {
            InitializeComponent();
            SpreadsheetPanel.SelectionChanged += OnSelectionChanged;
            AcceptButton = InputButton;
            mySpreadsheet = new SS.Spreadsheet(s => true, s => s.ToUpper(), "ps6");
            drawSquare = false;
            drawCircle = false;
            drawTriangle = false;
            

        }

        /// <summary>
        /// Method that evaluates every time a new cell is clicked. 
        /// 
        /// </summary>
        /// <param name="ss"></param>
        private void OnSelectionChanged(SpreadsheetPanel ss)
        {
            SpreadsheetPanel.GetSelection(out int col, out int row);

            if (drawSquare)
            {
                InputSquare(col, row);
                drawSquare = false;
            }

            if (drawCircle)
            {
                InputCircle(col, row);
                drawCircle = false;
            }

            if (drawTriangle)
            {
                InputTriangle(col, row);
                drawTriangle = false;
            }

           GetContentsAndValue(col, row, out string contents, out string value);

            InputTextBox.Clear();
            InputTextBox.Focus();
            InputTextBox.Text = contents;

            //Puts cursor at the end of the text for the active textbox
            InputTextBox.SelectionStart = InputTextBox.Text.Length + 1;

            // Changes the address and value textbox
            CellNameTextBox.Text = GetCoord(col, row);
            ValueTextBox.Text = value;

         
        }

        /// <summary>
        /// Given cell coordinates (row, column), return (via reference) the cell's
        /// contents and value
        /// </summary>
        /// <param name="col"></param>
        /// <param name="row"></param>
        /// <param name="contents"></param>
        /// <param name="value"></param>
        private void GetContentsAndValue(int col, int row, out string contents, out string value)
        {
            contents = mySpreadsheet.GetCellContents(GetCoord(col, row)).ToString();
            value = mySpreadsheet.GetCellValue(GetCoord(col, row)).ToString();
        }


        /// <summary>
        /// Converts column and row to their cell address value.
        /// </summary>
        /// <param name="col"></param>
        /// <param name="row"></param>
        /// <returns></returns>
        private string GetCoord(int col, int row)
        {
            return "" + Convert.ToChar(65 + col) + (row + 1);
        }

        /// <summary>
        /// Converts the coordinates to the appropriate rows and columns characters.
        /// ie row A, row B
        /// </summary>
        /// <param name="coord"></param>
        /// <param name="col"></param>
        /// <param name="row"></param>
        private void CoordToColRow(string coord, out int col, out int row)
        {
            col = Convert.ToInt16(coord[0]) - 65;
            row = Int16.Parse(coord.Substring(1)) - 1;
        }

        /// <summary>
        /// Event Handler for when the Enter button is clicked. Updates contents of selected
        /// Cell and each associated cell.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void EnterButtonClick(object sender, EventArgs e)
        {
            SpreadsheetPanel.GetSelection(out int col, out int row);

            // Try catch finds a FormulaFormatException
            try
            {
                //Stores spreadsheet value in the spreadsheet class
                //Updates the cells that are connected and need to be changed.
                ISet<string> updateCells = mySpreadsheet.SetContentsOfCell(GetCoord(col, row), InputTextBox.Text);
                foreach (string cell in updateCells)
                {
                    CoordToColRow(cell, out int resetCol, out int resetRow);
                    string resetVal = mySpreadsheet.GetCellValue(cell).ToString();
                    SpreadsheetPanel.SetValue(resetCol, resetRow, resetVal).ToString();
                }

                //The value of the cell in the spreadsheet will have the cell value
                SpreadsheetPanel.SetValue(col, row, mySpreadsheet.GetCellValue(GetCoord(col, row)).ToString());

                // Advances to the cell below when the entire button is clicked
                SpreadsheetPanel.SetSelection(col, row + 1);
                InputTextBox.Text = "";

            }
            catch (SpreadsheetUtilities.FormulaFormatException)
            {
                MessageBox.Show("Invalid Formula");
            }
        }

        
        /// <summary>
        /// Event Handler for Load option
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void spreadsheetPanel1_Load(object sender, EventArgs e)
        {
            //Sets selection at A1 when speadsheet loads
            SpreadsheetPanel.SetSelection(0, 0);
            InputTextBox.Clear();
            InputTextBox.Focus();

            //Puts cursor at the end of the text for the active textbox
            InputTextBox.SelectionStart = InputTextBox.Text.Length + 1;

        }



        /// <summary>
        /// Event Handler for "new" button
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void ClickNew(object sender, EventArgs e)
        {
            new Spreadsheet().Show();
        }

        /// <summary>
        /// Event Handler for Save button
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void SaveClick(object sender, EventArgs e)
        {
            SaveAs();
        }




        /// <summary>
        /// Save as method in case a save is needed
        /// </summary>
        private void SaveAs()
        {
            // Create new save file dialog
            SaveFileDialog saveFileDialog = new SaveFileDialog();

            // Initial settings for save file dialog
            saveFileDialog.InitialDirectory = System.Environment.CurrentDirectory;

            // Only sprd files or all files
            saveFileDialog.Filter = ".sprd (*.sprd)|*.txt|All files (*.*)|*.*";
            saveFileDialog.Title = "Save file...";

            // Default extension
            saveFileDialog.DefaultExt = "sprd";

            // Show save file dialog
            saveFileDialog.ShowDialog();

            if (saveFileDialog.FileName != "")
            {
                mySpreadsheet.Save(saveFileDialog.FileName);
            }
        }

        /// <summary>
        /// 
        /// Prompts the user to save values when closing via "X" in corner
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void CloseForm(object sender, FormClosingEventArgs e)
        {
            // Only give a warning if the spreadsheet has been changed since its last save
            if (mySpreadsheet.Changed)
            {
                DialogResult result = MessageBox.Show("Save before closing?", "WARNING", MessageBoxButtons.YesNoCancel);
                // If yes is seleted, then it will save and then close
                if (result == DialogResult.Yes)
                {
                    SaveAs();
                }

                // If cancel is seleted, the sheet will not close
                else if (result == DialogResult.Cancel)
                {
                    e.Cancel = true;
                }
            }

        }

        /// <summary>
        /// Event Handler for the "close" button. Closes the current spreadsheet.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void CloseFormMenu(object sender, EventArgs e)
        {
            // Only give a warning if the spreadsheet has been changed since its last save
            if (mySpreadsheet.Changed)
            {
                DialogResult result = MessageBox.Show("Save before closing?", "WARNING", MessageBoxButtons.YesNoCancel);
                // If yes is seleted, then it will save and then close
                if (result == DialogResult.Yes)
                {
                    SaveAs();
                    Close();
                }

                // If cancel is seleted, the sheet will not close
                else if (result == DialogResult.No)
                {
                    Close();
                }
            }
            else
                Close();

        }

        /// <summary>
        /// Handler Method for the open button. Opens a file dialogue that can be used
        /// to navigate a file explorer and open other ".sprd" file types.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void OpenFile(object sender, EventArgs e)
        {
            OpenFileDialog openFileDialog = new OpenFileDialog();

            // Initial settings for open file dialog
            openFileDialog.InitialDirectory = System.Environment.CurrentDirectory;

            // Only sprd files or all files
            openFileDialog.Filter = ".sprd (*.sprd)|*.txt|All files (*.*)|*.*";
            openFileDialog.Title = "Open file...";

            // If this file was selected, then open a new spreadsheet
            if (openFileDialog.ShowDialog() == System.Windows.Forms.DialogResult.OK)
            {
                string filePath = openFileDialog.FileName;

                mySpreadsheet = new SS.Spreadsheet(filePath, s => true, s => s.ToUpper(), "ps6");

                // Update each value in the spreadsheet
                UpdateCellsInGUI(mySpreadsheet.GetNamesOfAllNonemptyCells());
            }


        }

        /// <summary>
        /// Iterates through all non-blank cells and makes sure their value is updated on the GUI
        /// </summary>
        /// <param name="nonEmptyCells"></param>        
        private void UpdateCellsInGUI(IEnumerable<string> nonEmptyCells)
        {
            foreach (string cell in nonEmptyCells)
            {
                CoordToColRow(cell, out int resetCol, out int resetRow);
                string resetVal = mySpreadsheet.GetCellValue(cell).ToString();
                SpreadsheetPanel.SetValue(resetCol, resetRow, resetVal).ToString();
            }
        }


       /// <summary>
       /// Moves the current cell based on arrow key input.
       /// </summary>
       /// <param name="sender"></param>
       /// <param name="e"></param>
       private void MoveCell(object sender, PreviewKeyDownEventArgs e)
        {
            if (InputTextBox.Text == "")
            {

                SpreadsheetPanel.GetSelection(out int col, out int row);
                switch (e.KeyCode)
                {

                    case Keys.Down:
                        row = row + 1;
                        break;
                    case Keys.Up:
                        row = row - 1;
                        break;
                    case Keys.Left:
                        col--;
                        break;
                    case Keys.Right:
                        col++;
                        break;
                }

                if (col < 0)
                    col++;
                if (row < 0)
                    row++;
                // Use of Anti-Pattern for Grid dimensions -- did not want to modify SpreadsheetPanel class
                if (col > 25)
                    col--;
                if (row > 98)
                    row--;
                
                // move cell appropriately, make sure contents and value text box update
                SpreadsheetPanel.SetSelection(col, row);
                GetContentsAndValue(col, row, out string contents, out string value);
                CellNameTextBox.Text = GetCoord(col, row);
                ValueTextBox.Text = value;


            }

           


        }

        
        /// <summary>
        /// Sets default cell to cell A1
        /// Sets focus to InputTextBox
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void OpenForm(object sender, EventArgs e)
        {
            SpreadsheetPanel.SetSelection(0, 0);
            InputTextBox.Focus();
            CellNameTextBox.Text = GetCoord(0, 0);
        }

        /// <summary>
        /// Extra feature. Helper method that draws a square who's top-left corner
        /// is at the col, row index.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void DrawSquare(object sender, EventArgs e)
        {
            drawSquare = true;
        }

        /// <summary>
        /// Logic for drawing a square of "X" characters
        /// 
        /// Note -- slightly violates MVC
        /// </summary>
        /// <param name="col"></param>
        /// <param name="row"></param>
        private void InputSquare(int col, int row)
        {
            for (int i = col; i < (col+4); i++)
            {
                for (int j = row; j < (row + 4); j++)
                {
                    SpreadsheetPanel.SetValue(i, j, "X");

                }
            }

        }

        /// <summary>
        /// Helper method that draws a circle of "X" characters based off
        /// the col, row as the top-left corner
        /// 
        /// Note -- slightly violates MVC
        /// </summary>
        /// <param name="col"></param>
        /// <param name="row"></param>
        private void InputCircle(int col, int row)
        {
            InputSquare(col, row);
            SpreadsheetPanel.SetValue(col, row, "");
            SpreadsheetPanel.SetValue(col+3, row, "");
            SpreadsheetPanel.SetValue(col, row+3, "");
            SpreadsheetPanel.SetValue(col+3, row+3, "");
        }

        /// <summary>
        /// Event Handler for circle drawing
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void DrawCircle(object sender, EventArgs e)
        {
            drawCircle = true;
        }

        /// <summary>
        /// Event handler for traingle drawign
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void DrawTriangle(object sender, EventArgs e)
        {
            drawTriangle = true;
        }

        /// <summary>
        /// Simple logic for drawing a spreadsheet of "X"
        /// 
        /// Note -- extra feature slightly violates MVC
        /// </summary>
        /// <param name="col"></param>
        /// <param name="row"></param>
        private void InputTriangle(int col, int row)
        {
            SpreadsheetPanel.SetValue(col, row, "X");
            for (int i = col-1; i <= (col+1); i++)
            {
                SpreadsheetPanel.SetValue(i, row+1, "X");
            }
            for (int i = col - 2; i <= (col + 2); i++)
            {
                SpreadsheetPanel.SetValue(i, row + 2, "X");
            }


        }

        private void Help(object sender, EventArgs e)
        {
            string readme = "SpreadsheetPanel Utilities\n";
            readme += "Functionalities:\n";
            readme += "Special features:\n";
            readme += "Draw shape draws a circle in the grid. It does not make any effect in the numbers of the spreadsheet.When saving the spreadsheet, the shapes will disappear.\n";
            readme += "When a cell is empty, the spreadsheet is navigable through the arrow keys.Otherwise, the only way to exit the cell is through hitting the “enter” button by clicking or hitting enter on the keyboard.\n";
            readme += "The cell values are listed on the bottom cell.";
            MessageBox.Show(readme);
        }
    }
}
