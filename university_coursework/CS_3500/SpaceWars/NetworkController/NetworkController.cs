using System;
using System.Net;
using System.Net.Sockets;
using System.Text;
/// Authors:
/// 
///     Haley Feten & Landon Crowther
///     CS 3500
///     Fall 2018
///     Nov. 19, 2018
///     
/// Namsepace Description: 
/// 
///     This NetworkController class contains two important classes: SocketState and Networking. 
///     SocketState is what's used in communication between the Game Controller and the 
///     Network Controller. 
///     
///     The Networking class contains all the essentila methods for networking. 
///     
///     Neither class processes core game logic. 
namespace NetworkController
{
    /// <summary>
    /// This class holds all the necessary state to represent a socket connection
    /// Note that all of its fields are public because we are using it like a "struct"
    /// It is a simple collection of fields
    /// </summary>
    public class SocketState
    {
        public Socket theSocket;
        public int ID;
        public NetworkAction callMe;

        // This is the buffer where we will receive data from the socket
        public byte[] messageBuffer = new byte[1024];

        // This is a larger (growable) buffer, in case a single receive does not contain the full message.
        public StringBuilder sb = new StringBuilder();


        /// <summary>
        /// SocketState will contain important information about the status of the
        /// game. The SocketState object gets passed between the controller and the network class
        /// </summary>
        /// <param name="s"></param>
        /// <param name="id"></param>
        public SocketState(Socket s, int id)
        {
            theSocket = s;
            ID = id;
           
        }
    }

    /// <summary>
    /// Connection state of the server.
    /// </summary>
    public class ConnectionState
    {
        /// <summary>
        /// Listerer for the server.
        /// </summary>
        private TcpListener listener;

        /// <summary>
        /// Call me delegate of the server.
        /// </summary>
        private NetworkAction callMe;



        /// <summary>
        /// Used to help with networking
        /// </summary>
        public ConnectionState()
        {
        }

        /// <summary>
        /// Listener for thread
        /// </summary>
        public TcpListener Listener
        {
            set { listener = value; }
            get { return listener; }
        }

        /// <summary>
        /// Delegate for networking
        /// </summary>
        public NetworkAction CallMe
        {
            set { callMe = value; }
            get { return callMe; }
        }

    }





    // Delegate that will be changed and passed along in different parts of the connection process.
    public delegate void NetworkAction(SocketState s);

    /// <summary>
    /// This class does all the core networking work for the program. 
    /// </summary>
    public class Networking
    {
        public const int DEFAULT_PORT = 11000;

        private static int clientCount = 0;

        /// <summary>
        /// This method is called after the NetworkingAction delegate has been changed. It essentially
        /// asks for data from the server depending on how far along in the program everything is
        /// (ie building the world or processing objects)
        /// </summary>
        /// <param name="ss">socket state</param>
        public static void GetData(SocketState ss)
        {
            ss.theSocket.BeginReceive(ss.messageBuffer, 0, ss.messageBuffer.Length, SocketFlags.None, ReceiveCallback, ss);
        }

        /// <summary>
        /// This method allows data to be sent over a socket. It converts the message
        /// into bytes and sends over the SS. 
        /// </summary>
        /// <param name="ss"></param>
        /// <param name="messageBytes"></param>
        public static bool Send(SocketState ss, byte[] messageBytes)
        {
            try
            {
                ss.theSocket.BeginSend(messageBytes, 0, messageBytes.Length, SocketFlags.None, SendCallback, ss);
                return true;
            }
            catch
            {
                return false;
            }
        }


        /// <summary>
        /// Attempts to establish connection to the provided server. When information arrives,
        /// saves the callMe objecet in the SS NetworkingAction delegate.
        /// 
        /// Creates a socket and calls the BeginConnect method.
        /// </summary>
        /// <param name="hostName">name of server to connect to </param>
        /// <param name="networkAction">NetworkingAction delegate that will be invoked</param>
        /// <returns></returns>        
        public static Socket ConnectToServer(string hostName, NetworkAction networkAction)
        {
            System.Diagnostics.Debug.WriteLine("connecting  to " + hostName);

            Networking.MakeSocket(hostName, out Socket socket, out IPAddress ipAddress);

            SocketState ss = new SocketState(socket, -1);
            ss.callMe = networkAction;

            socket.BeginConnect(ipAddress, Networking.DEFAULT_PORT, ConnectedCallback, ss);

            return socket;

        }

        /// <summary>
        /// Referenced by BeginConnect method. It is called when the socket 
        /// connects to a server. 
        /// 
        /// After establishing connection, the callMe delegate must be evoked. This 
        /// saved information is updated in the socket state that was created.
        /// </summary>
        /// <param name="ar">The "state" of the object. This is cast to a SS object.</param>
        private static void ConnectedCallback(IAsyncResult ar)
        {
            SocketState ss = (SocketState)ar.AsyncState;

            try
            {
                // Complete the connection.
                ss.theSocket.EndConnect(ar);
                // Invoke the callMe delegate.
                ss.callMe(ss);
            }
            // Throw exception if unable to connect to the provided server.
            catch (Exception e)
            {
                System.Diagnostics.Debug.WriteLine("Unable to connect to server. Error occured: " + e);
                return;
            }


        }

        /// <summary>
        /// Called when new data arrives. If the amount of data arrived is 0, 
        /// it is assumed that the connection has been terminated. If data exists, 
        /// the method will
        ///     Append data to the growable buffer (for incomplete messages)
        ///     extract the socket state from the IAsyncResult, 
        ///     Invoke the callMe delegate provided in SS
        /// </summary>
        /// <param name="ar"></param>
        private static void ReceiveCallback(IAsyncResult ar)
        {
            SocketState ss = (SocketState)ar.AsyncState;
            try
            {
                

                int bytesRead = ss.theSocket.EndReceive(ar);

                // If the socket is still open
                if (bytesRead > 0)
                {
                    string theMessage = Encoding.UTF8.GetString(ss.messageBuffer, 0, bytesRead);
                    // Append the received data to the growable buffer.
                    // It may be an incomplete message, so we need to start building it up piece by piece
                    ss.sb.Append(theMessage);

                    ss.callMe(ss);
                }
            }
            catch
            {
                //ss.theSocket.BeginDisconnect(false, )
            }



        }


        /// <summary>
        /// Helper method used to initialize the socket. 
        /// Creates a Socket object for the given host string
        /// </summary>
        /// <param name="hostName">The host name or IP address</param>
        /// <param name="socket">The created Socket</param>
        /// <param name="ipAddress">The created IPAddress</param>
        public static void MakeSocket(string hostName, out Socket socket, out IPAddress ipAddress)
        {
            ipAddress = IPAddress.None;
            socket = null;
            try
            {
                // Establish the remote endpoint for the socket.
                IPHostEntry ipHostInfo;

                // Determine if the server address is a URL or an IP
                try
                {
                    ipHostInfo = Dns.GetHostEntry(hostName);
                    bool foundIPV4 = false;
                    foreach (IPAddress addr in ipHostInfo.AddressList)
                    {
                        if (addr.AddressFamily != AddressFamily.InterNetworkV6)
                        {
                            foundIPV4 = true;
                            ipAddress = addr;
                            break;
                        }
                    }
                    // Didn't find any IPV4 addresses
                    if (!foundIPV4)
                    {
                        System.Diagnostics.Debug.WriteLine("Invalid addres: " + hostName);
                        throw new ArgumentException("Invalid address");
                    }
                }
                catch (Exception)
                {
                    // see if host name is actually an ipaddress, i.e., 155.99.123.456
                    System.Diagnostics.Debug.WriteLine("using IP");
                    ipAddress = IPAddress.Parse(hostName);
                }

                // Create a TCP/IP socket.
                socket = new Socket(ipAddress.AddressFamily, SocketType.Stream, ProtocolType.Tcp);

                socket.SetSocketOption(SocketOptionLevel.IPv6, SocketOptionName.IPv6Only, false);

                // Disable Nagle's algorithm - can speed things up for tiny messages, 
                // such as for a game
                socket.NoDelay = true;

            }
            catch (Exception e)
            {

                System.Diagnostics.Debug.WriteLine("Unable to create socket. Error occured: " + e);
                throw new ArgumentException("Invalid address");
            }
        }


        /// <summary>
        /// Used after initializeing server to wait for clients to connect
        /// </summary>
        /// <param name="action"></param>
        public static void ServerAwaitingClientLoop(NetworkAction action)
        {


            int port = 11000;
            TcpListener listener = new TcpListener(port);
            listener.Start();

            ConnectionState cs = new ConnectionState();
            cs.Listener = listener;
            cs.CallMe = action;

            listener.BeginAcceptSocket(AcceptNewClient, cs);

           
        }

        /// <summary>
        /// Creates a socket state for the new client and calls BeginAcceptSocket 
        /// </summary>
        /// <param name="ar"></param>
        private static void AcceptNewClient(IAsyncResult ar)
        {
            ConnectionState connectionState = (ConnectionState)ar.AsyncState;

            Socket socket = connectionState.Listener.EndAcceptSocket(ar);

            Networking.clientCount++;
            SocketState ss =  new SocketState(socket, clientCount);

            ss.theSocket = socket;
            ss.callMe = connectionState.CallMe;
            ss.callMe(ss);
            connectionState.Listener.BeginAcceptSocket(AcceptNewClient, connectionState);





        }


        /// <summary>
        /// A callback invoked when a send operation completes
        /// Move this function to a standalone networking library. 
        /// </summary>
        /// <param name="ar"></param>
        private static void SendCallback(IAsyncResult ar)
        {
            SocketState ss = (SocketState)ar.AsyncState;
            // Nothing much to do here, just conclude the send operation so the socket is happy.
            Socket s = ss.theSocket;
            s.EndSend(ar);
        }

        
    }

}