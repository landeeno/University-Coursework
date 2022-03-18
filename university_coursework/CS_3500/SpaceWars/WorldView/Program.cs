using Controller;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using System.Windows.Forms;

/// <summary>
/// Authors:
/// 
///     Haley Feten & Landon Crowther
///     CS 3500
///     Fall 2018
///     Nov. 19, 2018
///     
/// Class Description: 
/// 
///     This is entry point for the SpaceWars game. 
/// 
namespace WorldView
{
    static class Program
    {
        /// <summary>
        /// The main entry point for the application.
        /// </summary>
        [STAThread]
        static void Main()
        {
            Application.EnableVisualStyles();
            Application.SetCompatibleTextRenderingDefault(false);
            GameController gc = new GameController();
            Application.Run(new Form1(gc));
        }
    }
}
