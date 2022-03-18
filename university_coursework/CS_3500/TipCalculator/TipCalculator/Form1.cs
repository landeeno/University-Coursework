using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace TipCalculator
{
    public partial class Form1 : Form
    {
        public Form1()
        {
            InitializeComponent();
        }

        private void button1_Click(object sender, EventArgs e)
        {
            if (!(totalBill.Text is ""))
                if (Double.TryParse(totalBill.Text, out double bill))
                    outputBox.Text = (bill * 1.2).ToString();
                else
                    MessageBox.Show("You need to input a number!");

        }
    }
}
