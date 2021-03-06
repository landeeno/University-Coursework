using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Sockets;
using System.Text;
using System.Threading.Tasks;

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

    public SocketState(Socket s, int id)
    {
      theSocket = s;
      ID = id;
    }
  }

    

    public delegate void NetworkAction(SocketState s);

  public class Networking
  {



    public const int DEFAULT_PORT = 11000;


        /// <summary>
        /// Start attempting to connect to the server
        /// Move this function to a standalone networking library.
        /// </summary>
        /// <param name="hostName"> server to connect to </param>
        /// <returns></returns>
        public static Socket ConnectToServer(string hostName, NetworkAction networkAction)
        {
            System.Diagnostics.Debug.WriteLine("connecting  to " + hostName);

            // Create a TCP/IP socket.
            Socket socket;
            IPAddress ipAddress;

            Networking.MakeSocket(hostName, out socket, out ipAddress);

            SocketState ss = new SocketState(socket, -1);
            ss.callMe = networkAction;

            socket.BeginConnect(ipAddress, Networking.DEFAULT_PORT, ConnectedCallback, ss);

            return socket;

        }

        private static void ConnectedCallback(IAsyncResult ar)
        {
            SocketState ss = (SocketState)ar.AsyncState;
            
            try
            {
                // Complete the connection.
                ss.theSocket.EndConnect(ar);
            }
            catch (Exception e)
            {
                System.Diagnostics.Debug.WriteLine("Unable to connect to server. Error occured: " + e);
                return;
            }

            // Start an event loop to receive data from the server.
            ss.theSocket.BeginReceive(ss.messageBuffer, 0, ss.messageBuffer.Length, SocketFlags.None, ReceiveCallback, ss);
        }


        /// <summary>
        /// This function is "called" by the operating system when data arrives on the socket
        /// Move this function to a standalone networking library. 
        /// </summary>
        /// <param name="ar"></param>
        private static void ReceiveCallback(IAsyncResult ar)
        {
            SocketState ss = (SocketState)ar.AsyncState;

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

            // Continue the "event loop" that was started on line 100.
            // Start listening for more parts of a message, or more new messages
            ss.theSocket.BeginReceive(ss.messageBuffer, 0, ss.messageBuffer.Length, SocketFlags.None, ReceiveCallback, ss);

        }


        /// <summary>
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
            if (addr.AddressFamily != AddressFamily.InterNetworkV6)
            {
              foundIPV4 = true;
              ipAddress = addr;
              break;
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

    
    // TODO: Move all networking code to this class. Left as an exercise.
    // Networking code should be completely general-purpose, and useable by any other application.
    // It should contain no references to a specific project.
  }

}
