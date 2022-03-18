using Controller;
using Drawer;
using SpaceWars;
using System;
using System.Drawing;
using System.IO;
using System.Threading;
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
///     This is the GUI for the Space Wars class. 
/// 
/// </summary>
namespace WorldView
{
    public partial class Form1 : Form

    {

        /// <summary>
        /// The game controller utilizes the game logic for objects lives and location.
        /// </summary>
        private GameController theController;

        /// <summary>
        /// The world contains all the player information
        /// </summary>
        private World theWorld;


        /// <summary>
        /// The drawing panel draws each object on the form.
        /// </summary>
        DrawingPanel drawingPanel;

        /// <summary>
        /// Intializes the world view on the form, utilizing the drawing panel to draw the images.
        /// </summary>
        /// <param name="theController"></param>
        public Form1(GameController theController)
        {
            InitializeComponent();
            AcceptButton = beginConnect;
            this.theController = theController;
            theController.SpaceWarsObjectsArrived += DisplayWorld;
            KeyDown += new KeyEventHandler(HitButton);
        }


        /// <summary>
        /// Event Handler for the connect button. 
        /// 
        /// Makes sure that a player name has been inputted, tries to connect to the input server (if applicable)
        /// 
        /// Once connection has been established, removes the startup display and starts the game. 
        /// 
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void beginConnect_Click(object sender, System.EventArgs e)
        {
            try
            {   // must have a player name
                if (playerName.Text.Equals(""))
                    throw new ArgumentException();

                // set player name
                theController.MyName = playerName.Text;


                // Connects the controller with the IP address
                string IP = serverAddressTextbox.Text;
                theController.Connect(IP);

                // Removes the controls for the text box, enter button, and label
                Controls.Remove(playerName);
                Controls.Remove(playerNameLabel);
                Controls.Remove(beginConnect);
                Controls.Remove(serverAddressTextbox);
                Controls.Remove(serverLabel);

                /*
                 *this while loop ensures that the GUI does not progress 
                 * until after the controller has initialized the world. This
                 * is necessary because they are on different threads.                 
                 * */
                 while (theController.worldCreated == false)
                {
                   
                }
                theWorld = theController.GetWorld();

                // set up the client and add the components.
                ClientSize = new Size(theController.GetWorld().WorldSize, theController.GetWorld().WorldSize);
                drawingPanel = new DrawingPanel(theController.GetWorld());
                drawingPanel.Location = new Point(0, 0);
                drawingPanel.Size = new Size(ClientSize.Width, ClientSize.Height);
                Controls.Add(drawingPanel);
            }
            catch 
            {

                if (playerName.Text.Equals(""))
                    MessageBox.Show("Enter a valid name.");
                else
                     MessageBox.Show("Not a valid connection. Please try again.");
            }
           

        }

        /// <summary>
        /// When each object arrives in the space wars objects arrived method in the game controller, it 
        /// invalidates the form and repaints.
        /// </summary>
        private void DisplayWorld()
        {
            try
            {
                MethodInvoker invoker = new MethodInvoker(
                    () => Invalidate(true));
                Invoke(invoker);
            }
            catch
            {
            }
        }

        /// <summary>
        /// Event Handler for arrow key / RLTF key inputs.
        /// 
        /// When a key is pressed, it sends it to the server for fire, thrust, left and right with the letters
        /// and with the arrow keys.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void HitButton(object sender, KeyEventArgs e)
        {
            string message = "";
            if (e.KeyCode == Keys.R || e.KeyCode == Keys.Right)
            {
                message = "R";
            }
            if (e.KeyCode == Keys.L || e.KeyCode == Keys.Left)
            {
                message = "L";
            }
            if (e.KeyCode == Keys.T || e.KeyCode == Keys.Up)
            {
                message = "T";
            }
            if (e.KeyCode == Keys.F || e.KeyCode == Keys.Space)
            {
                message = "F";
            }

            theController.SendMessage(message);
        }

        /// <summary>
        /// When help is clicked on the game, it returns the documentation from the README file.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void getHelpToolStripMenuItem_Click(object sender, EventArgs e)
        {
            // Get the folder where the readme is stored
            string textFolder = AppDomain.CurrentDomain.BaseDirectory;
            string textFile = textFolder.Replace("WorldView\\bin\\Debug\\", "References\\README.txt");

            // Display the README in the message box
            MessageBox.Show(File.ReadAllText(textFile));

        }

       
    }
}
