using System;

namespace SpreadsheetGUI
{
    partial class Spreadsheet
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        

        

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.SpreadsheetPanel = new SS.SpreadsheetPanel();
            this.InputTextBox = new System.Windows.Forms.TextBox();
            this.CellNameTextBox = new System.Windows.Forms.TextBox();
            this.ValueTextBox = new System.Windows.Forms.TextBox();
            this.InputButton = new System.Windows.Forms.Button();
            this.menuStrip2 = new System.Windows.Forms.MenuStrip();
            this.fileToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.New = new System.Windows.Forms.ToolStripMenuItem();
            this.openToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.SaveMenu = new System.Windows.Forms.ToolStripMenuItem();
            this.closeToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.helpToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.lostClickHereToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.bonusToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.squareMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.circleToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.triangleToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.saveFileDialog1 = new System.Windows.Forms.SaveFileDialog();
            this.menuStrip2.SuspendLayout();
            this.SuspendLayout();
            // 
            // SpreadsheetPanel
            // 
            this.SpreadsheetPanel.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) 
            | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this.SpreadsheetPanel.Location = new System.Drawing.Point(-1, 110);
            this.SpreadsheetPanel.Margin = new System.Windows.Forms.Padding(4);
            this.SpreadsheetPanel.Name = "SpreadsheetPanel";
            this.SpreadsheetPanel.Size = new System.Drawing.Size(1392, 751);
            this.SpreadsheetPanel.TabIndex = 0;
            // 
            // InputTextBox
            // 
            this.InputTextBox.Anchor = System.Windows.Forms.AnchorStyles.Top;
            this.InputTextBox.Location = new System.Drawing.Point(307, 12);
            this.InputTextBox.Margin = new System.Windows.Forms.Padding(4);
            this.InputTextBox.Name = "InputTextBox";
            this.InputTextBox.Size = new System.Drawing.Size(872, 31);
            this.InputTextBox.TabIndex = 1;
            this.InputTextBox.PreviewKeyDown += new System.Windows.Forms.PreviewKeyDownEventHandler(this.MoveCell);
            // 
            // CellNameTextBox
            // 
            this.CellNameTextBox.Anchor = System.Windows.Forms.AnchorStyles.Bottom;
            this.CellNameTextBox.Location = new System.Drawing.Point(43, 889);
            this.CellNameTextBox.Margin = new System.Windows.Forms.Padding(4);
            this.CellNameTextBox.Name = "CellNameTextBox";
            this.CellNameTextBox.ReadOnly = true;
            this.CellNameTextBox.Size = new System.Drawing.Size(454, 31);
            this.CellNameTextBox.TabIndex = 2;
            // 
            // ValueTextBox
            // 
            this.ValueTextBox.Anchor = System.Windows.Forms.AnchorStyles.Bottom;
            this.ValueTextBox.Location = new System.Drawing.Point(547, 889);
            this.ValueTextBox.Margin = new System.Windows.Forms.Padding(4);
            this.ValueTextBox.Name = "ValueTextBox";
            this.ValueTextBox.ReadOnly = true;
            this.ValueTextBox.Size = new System.Drawing.Size(460, 31);
            this.ValueTextBox.TabIndex = 3;
            // 
            // InputButton
            // 
            this.InputButton.Anchor = System.Windows.Forms.AnchorStyles.Top;
            this.InputButton.Location = new System.Drawing.Point(1212, 6);
            this.InputButton.Margin = new System.Windows.Forms.Padding(4);
            this.InputButton.Name = "InputButton";
            this.InputButton.Size = new System.Drawing.Size(150, 46);
            this.InputButton.TabIndex = 4;
            this.InputButton.Text = "Enter";
            this.InputButton.UseVisualStyleBackColor = true;
            this.InputButton.Click += new System.EventHandler(this.EnterButtonClick);
            // 
            // menuStrip2
            // 
            this.menuStrip2.ImageScalingSize = new System.Drawing.Size(32, 32);
            this.menuStrip2.Items.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.fileToolStripMenuItem,
            this.helpToolStripMenuItem,
            this.bonusToolStripMenuItem});
            this.menuStrip2.Location = new System.Drawing.Point(0, 0);
            this.menuStrip2.Name = "menuStrip2";
            this.menuStrip2.Padding = new System.Windows.Forms.Padding(12, 4, 0, 4);
            this.menuStrip2.Size = new System.Drawing.Size(1404, 46);
            this.menuStrip2.TabIndex = 6;
            this.menuStrip2.Text = "menuStrip2";
            // 
            // fileToolStripMenuItem
            // 
            this.fileToolStripMenuItem.DropDownItems.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.New,
            this.openToolStripMenuItem,
            this.SaveMenu,
            this.closeToolStripMenuItem});
            this.fileToolStripMenuItem.Name = "fileToolStripMenuItem";
            this.fileToolStripMenuItem.Size = new System.Drawing.Size(64, 38);
            this.fileToolStripMenuItem.Text = "File";
            // 
            // New
            // 
            this.New.Name = "New";
            this.New.Size = new System.Drawing.Size(173, 38);
            this.New.Text = "New";
            this.New.Click += new System.EventHandler(this.ClickNew);
            // 
            // openToolStripMenuItem
            // 
            this.openToolStripMenuItem.Name = "openToolStripMenuItem";
            this.openToolStripMenuItem.Size = new System.Drawing.Size(173, 38);
            this.openToolStripMenuItem.Text = "Open";
            this.openToolStripMenuItem.Click += new System.EventHandler(this.OpenFile);
            // 
            // SaveMenu
            // 
            this.SaveMenu.Name = "SaveMenu";
            this.SaveMenu.Size = new System.Drawing.Size(173, 38);
            this.SaveMenu.Text = "Save";
            this.SaveMenu.Click += new System.EventHandler(this.SaveClick);
            // 
            // closeToolStripMenuItem
            // 
            this.closeToolStripMenuItem.Name = "closeToolStripMenuItem";
            this.closeToolStripMenuItem.Size = new System.Drawing.Size(173, 38);
            this.closeToolStripMenuItem.Text = "Close";
            this.closeToolStripMenuItem.Click += new System.EventHandler(this.CloseFormMenu);
            // 
            // helpToolStripMenuItem
            // 
            this.helpToolStripMenuItem.DropDownItems.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.lostClickHereToolStripMenuItem});
            this.helpToolStripMenuItem.Name = "helpToolStripMenuItem";
            this.helpToolStripMenuItem.Size = new System.Drawing.Size(77, 38);
            this.helpToolStripMenuItem.Text = "Help";
            // 
            // lostClickHereToolStripMenuItem
            // 
            this.lostClickHereToolStripMenuItem.Name = "lostClickHereToolStripMenuItem";
            this.lostClickHereToolStripMenuItem.Size = new System.Drawing.Size(324, 38);
            this.lostClickHereToolStripMenuItem.Text = "Lost? Click here!";
            this.lostClickHereToolStripMenuItem.Click += new System.EventHandler(this.Help);
            // 
            // bonusToolStripMenuItem
            // 
            this.bonusToolStripMenuItem.DropDownItems.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.squareMenuItem,
            this.circleToolStripMenuItem,
            this.triangleToolStripMenuItem});
            this.bonusToolStripMenuItem.Name = "bonusToolStripMenuItem";
            this.bonusToolStripMenuItem.Size = new System.Drawing.Size(103, 38);
            this.bonusToolStripMenuItem.Text = "Shapes";
            // 
            // squareMenuItem
            // 
            this.squareMenuItem.Name = "squareMenuItem";
            this.squareMenuItem.Size = new System.Drawing.Size(198, 38);
            this.squareMenuItem.Text = "Square";
            this.squareMenuItem.Click += new System.EventHandler(this.DrawSquare);
            // 
            // circleToolStripMenuItem
            // 
            this.circleToolStripMenuItem.Name = "circleToolStripMenuItem";
            this.circleToolStripMenuItem.Size = new System.Drawing.Size(198, 38);
            this.circleToolStripMenuItem.Text = "Circle";
            this.circleToolStripMenuItem.Click += new System.EventHandler(this.DrawCircle);
            // 
            // triangleToolStripMenuItem
            // 
            this.triangleToolStripMenuItem.Name = "triangleToolStripMenuItem";
            this.triangleToolStripMenuItem.Size = new System.Drawing.Size(198, 38);
            this.triangleToolStripMenuItem.Text = "Triangle";
            this.triangleToolStripMenuItem.Click += new System.EventHandler(this.DrawTriangle);
            // 
            // Spreadsheet
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(12F, 25F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(1404, 949);
            this.Controls.Add(this.InputButton);
            this.Controls.Add(this.ValueTextBox);
            this.Controls.Add(this.CellNameTextBox);
            this.Controls.Add(this.InputTextBox);
            this.Controls.Add(this.SpreadsheetPanel);
            this.Controls.Add(this.menuStrip2);
            this.Margin = new System.Windows.Forms.Padding(4);
            this.Name = "Spreadsheet";
            this.Text = "Form1";
            this.Activated += new System.EventHandler(this.OpenForm);
            this.FormClosing += new System.Windows.Forms.FormClosingEventHandler(this.CloseForm);
            this.Click += new System.EventHandler(this.Form1_Click);
            this.menuStrip2.ResumeLayout(false);
            this.menuStrip2.PerformLayout();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        private void Form1_Click(object sender, EventArgs e)
        {
            //throw new NotImplementedException();
        }

       

        #endregion

        private SS.SpreadsheetPanel SpreadsheetPanel;
        private System.Windows.Forms.TextBox InputTextBox;
        private System.Windows.Forms.TextBox CellNameTextBox;
        private System.Windows.Forms.TextBox ValueTextBox;
        private System.Windows.Forms.Button InputButton;
        private System.Windows.Forms.MenuStrip menuStrip2;
        private System.Windows.Forms.ToolStripMenuItem fileToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem New;
        private System.Windows.Forms.ToolStripMenuItem openToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem SaveMenu;
        private System.Windows.Forms.ToolStripMenuItem closeToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem helpToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem lostClickHereToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem bonusToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem squareMenuItem;
        private System.Windows.Forms.SaveFileDialog saveFileDialog1;
        private System.Windows.Forms.ToolStripMenuItem circleToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem triangleToolStripMenuItem;
    }
}

