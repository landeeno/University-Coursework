using NetworkController;
using Newtonsoft.Json;
using SpaceWarsObjects;
using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Text;
using System.Text.RegularExpressions;
using System.Xml;

namespace SpaceWars
{
    class Server
    {
        private Dictionary<int, SocketState> myClients;
        private TheModel theModel;
        private int clientTracker;

        private bool extraGameMode;




        private int projectileSpeed;
        private int engineStrength;
        private int shipSize;
        private int turningRate;
        private int starSize;
        private int startingHitPoints;
        private int theWorldSize;
        private int fireDelay;
        private int respawnDelay;
        private int timePerFrame;
        private double starX;
        private double starY;
        private double starMass;

        /// <summary>
        /// Initialzie SErver
        /// </summary>
        public Server()
        {
            clientTracker = 0;
            ReadXML();
            myClients = new Dictionary<int, SocketState>();
            theModel = new TheModel(projectileSpeed, engineStrength, shipSize, turningRate,
                             starSize, startingHitPoints, theWorldSize, fireDelay, respawnDelay, timePerFrame, extraGameMode, starMass, starX, starY);

        }



        static void Main(string[] args)
        {
            Console.WriteLine("Awaiting clients...");
            Server server = new Server();
            Networking.ServerAwaitingClientLoop(server.HandleNewClient);


            while (true)
            {
                server.Update();
            }

            Console.ReadLine();

        }



        /// <summary>
        /// What to do when a new client connects
        /// </summary>
        /// <param name="ss"></param>
        private void HandleNewClient(SocketState ss)
        {
            ss.callMe = ReceiveName;
            Console.WriteLine("New client added to the server.");
            Networking.GetData(ss);
        }

        /// <summary>
        /// Handle requrests from client
        /// </summary>
        /// <param name="ss"></param>
        private void HandleDataFromClient(SocketState ss)
        {


            lock (theModel)
            {
                // parse information from the SS
                string totalData = ss.sb.ToString();
                string[] dataParts = Regex.Split(totalData, @"(?<=[\n])");

                foreach (string data in dataParts)
                {
                    //if (data[0] == '(' && data[data.Length - 1] == ')')
                    {
                        Console.WriteLine(data);
                        theModel.DoAction(ss.ID, data);
                        ss.sb.Clear();
                    }
                }
            }

            Networking.GetData(ss);

        }

        /// <summary>
        /// 
        /// UPdate= whhat the client sees
        /// </summary>
        private void Update()
        {

            Stopwatch watch = new Stopwatch();
            watch.Start();

            while (watch.ElapsedMilliseconds < this.timePerFrame)
            {
                // do nothing - framerate
            }

            watch.Reset();


            // Lock the world model to avoid race conditions
            lock (this)
            {
                theModel.GetNextFrame();
                StringBuilder messages = GenerateWorldMessages();

                IEnumerable<SocketState> clientList = new HashSet<SocketState> (myClients.Values);
                
                // loop to make the message for each client
                foreach (SocketState ss in clientList)
                {
                    // send the world
                    // world message
                    // iterate through each object in the world
                    // append to message

                    byte[] worldBytes = Encoding.UTF8.GetBytes(messages.ToString());

                    // If not able to send message, then remove from client list
                    if (!Networking.Send(ss, worldBytes))
                    {
                        myClients.Remove(ss.ID);
                        theModel.PlayerDisconnected(ss.ID);
                    }
                }
            }




        }

        /// <summary>
        /// Create the messages that are sent to the clients
        /// </summary>
        /// <returns></returns>
        private StringBuilder GenerateWorldMessages()
        {


            StringBuilder worldMessage = new StringBuilder();

            IEnumerable<Ship> ships = theModel.GetWorld().GetShips();
            IEnumerable<Projectile> projectiles = theModel.GetWorld().GetProjectiles();
            IEnumerable<Star> stars = theModel.GetWorld().GetStars();
            lock (theModel)
            {
                foreach (Ship ship in ships)
                {

                    // TODO logic for live ships
                    JsonConvert.SerializeObject(ship);
                    worldMessage = worldMessage.Append(JsonConvert.SerializeObject(ship) + "\n");

                }

                foreach (Projectile p in projectiles)
                {
                    JsonConvert.SerializeObject(p);
                    worldMessage = worldMessage.Append(JsonConvert.SerializeObject(p) + "\n");
                }

                foreach (Star s in stars)
                {
                    JsonConvert.SerializeObject(s);
                    worldMessage = worldMessage.Append(JsonConvert.SerializeObject(s) + "\n");
                }
            }
            return worldMessage;
        }



        /// <summary>
        /// Reads & implements following information from XML file: 
        /// 
        ///     starting HP
        ///     Projectile Speed
        ///     Engine Strength
        ///     Turning Rate
        ///     Ship Size
        ///     Star Size
        ///     World Size
        ///     Firing Rate
        ///     Respawn Rate
        ///     Time per Frame
        ///     StartinHP
        ///     
        ///     
        /// 
        /// 
        /// </summary>
        private void ReadXML()
        {

            string settingsFolder = AppDomain.CurrentDomain.BaseDirectory;
            string fileName = settingsFolder.Replace("\\TheServer\\bin\\Debug\\", "\\References\\Libraries\\settings.xml");

            ImportXML(fileName);
        }

        /// <summary>
        /// Logic for importing the settings.
        /// </summary>
        /// <param name="filename"></param>
        /// <returns></returns>
        private LinkedList<Star> ImportXML(string filename)
        {
            LinkedList<Star> retVal = new LinkedList<Star>();

            using (XmlReader reader = XmlReader.Create(filename))
            {

                while (reader.Read())
                {
                    
                    if (reader.IsStartElement())
                    {
                        switch (reader.Name)
                        {

                            case "ExtraGameMode":
                                reader.Read();
                                string gameMode = reader.Value;
                                if (gameMode.Equals("true"))
                                    this.extraGameMode = true;
                                else
                                    extraGameMode = false;
                                break;
                                


                            case "UniverseSize":
                                reader.Read();
                                Double.TryParse(reader.Value, out double worldSize);
                                theWorldSize = (int)worldSize;
                                break;

                            case "MSPerFrame":
                                reader.Read();
                                Double.TryParse(reader.Value, out double outputVal);
                                timePerFrame = (int)outputVal;
                                break;

                            case "FramesPerShot":
                                reader.Read();
                                Double.TryParse(reader.Value, out double val);
                                this.fireDelay = (int)val;
                                break;

                            case "RespawnRate":
                                reader.Read();
                                Double.TryParse(reader.Value, out double val2);
                                this.respawnDelay = (int)val2;
                                break;

                            case "StartingHP":
                                reader.Read();
                                Double.TryParse(reader.Value, out double hp);
                                this.startingHitPoints = (int)hp;
                                break;

                            case "Star":
                                break;

                            case "x":
                                reader.Read();
                                Double.TryParse(reader.Value, out double val3);
                                starX = val3;
                                break;

                            case "y":
                                reader.Read();
                                Double.TryParse(reader.Value, out double val4);

                                starY = val4;
                                break;

                            case "mass":
                                reader.Read();
                                Double.TryParse(reader.Value, out double val5);
                                starMass = val5;                                
                                break;

                            case "ProjectileSpeed":
                                reader.Read();
                                Double.TryParse(reader.Value, out double val6);
                                this.projectileSpeed = (int)val6;
                                break;

                            case "EngineStrength":
                                reader.Read();
                                Double.TryParse(reader.Value, out double val7);
                                this.engineStrength = (int)val7;
                                break;

                            case "TurningRate":
                                reader.Read();
                                Double.TryParse(reader.Value, out double val8);
                                this.turningRate = (int)val8;
                                break;

                            case "ShipSize":
                                reader.Read();
                                Double.TryParse(reader.Value, out double val9);
                                this.shipSize = (int)val9;
                                break;

                            case "StarSize":
                                reader.Read();
                                Double.TryParse(reader.Value, out double val11);
                                this.starSize = (int)val11;
                                break;


                        }
                    }
                }
            }

            return retVal;
        }



        /// <summary>
        /// Delegate for when a new client is received.
        /// </summary>
        /// <param name="ss"></param>
        private void ReceiveName(SocketState ss)
        {

            NewClient(ss, out int ID, out string name);



            ss.ID = ID;
            ss.callMe = HandleDataFromClient;
            SendClientStartup(ss);
            myClients.Add(ID, ss);
            lock (theModel)
            {
                theModel.AddNewClientShip(ID, name, this.startingHitPoints);
            }
            Networking.GetData(ss);

        }

        /// <summary>
        /// Startup info for client.
        /// </summary>
        /// <param name="ss"></param>
        private void SendClientStartup(SocketState ss)
        {
            string startUp = "" + ss.ID + "\n" + theWorldSize + "\n";
            byte[] messageBytes = Encoding.UTF8.GetBytes(startUp);
            
            // If not able to send out the word size and ID, then deletes the client from the client list
            if (!Networking.Send(ss, messageBytes))
            {
                myClients.Remove(ss.ID);
            }
        }

        /// <summary>
        /// With the socket state, it returns the client ID and the client's name.
        /// </summary>
        /// <param name="ss"></param>
        /// <param name="ID"></param>
        /// <param name="name"></param>
        private void NewClient(SocketState ss, out int ID, out string name)
        {
            ID = ++clientTracker;
            name = Encoding.UTF8.GetString(ss.messageBuffer, 0, 15);

        }
    }
}
