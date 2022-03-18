using Drawer;
using NetworkController;
using Newtonsoft.Json;
using Newtonsoft.Json.Linq;
using SpaceWars;
using SpaceWarsObjects;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Sockets;
using System.Text;
using System.Text.RegularExpressions;
using System.Threading.Tasks;
using System.Timers;
using System.Xml.Linq;

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
///     This class acts as the controller for the game. All of the information
///     received by the server is processed here. 
namespace Controller
{
    public class GameController
    {
        private World theWorld;

        // bool that is used to communicate with the GUI.
        public bool worldCreated;

        private Socket theServer;

        private int myID;

        private string myName;

        public delegate void MessagesArrivedHandler();

        public event MessagesArrivedHandler SpaceWarsObjectsArrived;
        /// <summary>
        /// Constructor for GameController. Initialized in the WorldView GUI class. 
        /// </summary>
        public GameController()
        {
            worldCreated = false;
            myName = "";
        }

        /// <summary>
        /// The player name that was entered upon connect.
        /// </summary>
        public string MyName
        {
            get
            {
                return myName;
            }
            set
            {
                myName = value;
            }
        }


        /// <summary>
        /// Returns the world.
        /// </summary>
        /// <returns></returns>
        public World GetWorld()
        {
            return theWorld;
        }

        /// <summary>
        /// Part of the Networking. Used to initialize contact with the GUI.
        /// </summary>
        /// <param name="ipAddress">IP address - used as input from GUI</param>
        public void Connect(string ipAddress)
        {
            theServer = Networking.ConnectToServer(ipAddress, FirstContact);
        }

        /// <summary>
        /// Acts as delegate for the NetworkingController.  
        /// Processes client name. 
        /// Sends information to Networking class. 
        /// Asks for updeates from networking class. 
        /// </summary>
        /// <param name="ss"></param>
        private void FirstContact(SocketState ss)
        {
            // changing the delegate. 
            ss.callMe = ReceiveStartup;
            byte[] messageBytes = Encoding.UTF8.GetBytes(this.MyName + "\n");
            Networking.Send(ss, messageBytes);
            Networking.GetData(ss);

        }

        /// <summary>
        /// Processes the world size and the player ID. 
        /// </summary>
        /// <param name="ss">Socket State</param>
        private void ReceiveStartup(SocketState ss)
        {
            // parse information from the SS
            string totalData = ss.sb.ToString();
            string[] dataParts = Regex.Split(totalData, @"(?<=[\n])");

            // playerID and worldSize will always come in this order
            int playerID = (int)Double.Parse(dataParts[0]);
            int worldSize = (int)Double.Parse(dataParts[1]);

            // create the world
            theWorld = new World(worldSize);
            // notify the GUI that the world has been created. Prevents
            // issues from multi threading. 
            worldCreated = true;

            // remove the player ID and world size from the the SS
            ss.sb.Remove(0, dataParts[0].Length);
            ss.sb.Remove(0, dataParts[1].Length);

            // change the delegate
            ss.callMe = ReceiveWorld;
            // ask for more data from the Networking class
            Networking.GetData(ss);

        }


        /// <summary>
        /// Add ships, players, stars, projectiles. This will process JSON messages. 
        /// </summary>
        /// <param name="ss">socket state</param>
        private void ReceiveWorld(SocketState ss)
        {

            // separate JSON objects into array
            string totalData = ss.sb.ToString();
            string[] dataParts = Regex.Split(totalData, @"(?<=[\n])");

            foreach (string part in dataParts)
            {
                if (part.Length == 0)
                    continue;
                if (part[part.Length - 1] != '\n')
                    break;

                // given the JSON string, parse it into ship/star/projectile
                ParseIntoObjectTypeAndAddToWorld(part);
                // JSON string processed, remove it from string
                ss.sb.Remove(0, part.Length);


            }

            //Notifty the GUI to update the display
            SpaceWarsObjectsArrived();
            // Ask for more data from Networking Class
            Networking.GetData(ss);
        }

        /// <summary>
        /// Method used to send data to the server for keypress items.
        /// </summary>
        /// <param name="message">message from arrowkey input</param>
        public void SendMessage(string message)
        {
            message = "(" + message + ")";
            byte[] messageBytes = Encoding.UTF8.GetBytes(message + "\n");
            theServer.Send(messageBytes);
        }

        /// <summary>
        /// Given a JSON object, determine which type of object it is and update it 
        /// in the World class
        /// </summary>
        /// <param name="part">JSON string</param>
        private void ParseIntoObjectTypeAndAddToWorld(string part)
        {
            // lock theWorld so that there isn't thread competition
            lock (theWorld)
            {
                JObject jsonObject = JObject.Parse(part);

                // Ship:
                if (jsonObject["ship"] != null)
                {
                    Ship ship = JsonConvert.DeserializeObject<Ship>(part);
                    theWorld.UpdateShip(ship);
                }

                // Projectile:
                if (jsonObject["proj"] != null)
                {
                    Projectile proj = JsonConvert.DeserializeObject<Projectile>(part);
                    theWorld.UpdateProjectile(proj);
                }

                // Star:
                if (jsonObject["star"] != null)
                {
                    Star star = JsonConvert.DeserializeObject<Star>(part);
                    theWorld.UpdateStar(star);
                }
            }
        }
    }
}