﻿using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Net;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using NetworkController;
using System.Net.Sockets;
using System.Threading;
using System.Text.RegularExpressions;

namespace ChatClient
{
  public partial class Form1 : Form
  {

    private Socket theServer;

    public Form1()
    {
      InitializeComponent();
      messageToSendBox.KeyDown += new KeyEventHandler(MessageEnterHandler);
    }

    /// <summary>
    /// Connect button event handler
    /// </summary>
    /// <param name="sender"></param>
    /// <param name="e"></param>
    private void connectButton_Click(object sender, EventArgs e)
    {
      // TODO: This needs better error handling. Left as an exercise.
      // If the server box is empty, it gives a message, but doesn't allow us to try to reconnect.
      // It also doesn't handle unreachable addresses.
      if (serverAddress.Text == "")
      {
        MessageBox.Show("Please enter a server address");
        return;
      }


      // Disable the controls and try to connect
      connectButton.Enabled = false;
      serverAddress.Enabled = false;

      theServer = Networking.ConnectToServer(serverAddress.Text, ProcessMessage);

    }


    
        

        /// <summary>
        /// This function is "called" by the operating system when the remote site acknowledges the connect request
        /// Move this function to a standalone networking library.
        /// </summary>
        /// <param name="ar"></param>
        

    /// <summary>
    /// This is the worker method that processes a message when received.
    /// For proper MVC, this should go in its own controller
    /// </summary>
    /// <param name="ss">The SocketState on which the message was received</param>
    private void ProcessMessage(SocketState ss)
    {
      string totalData = ss.sb.ToString();
      string[] parts = Regex.Split(totalData, @"(?<=[\n])");

      // Loop until we have processed all messages.
      // We may have received more than one.

      foreach (string p in parts)
      {
        // Ignore empty strings added by the regex splitter
        if (p.Length == 0)
          continue;
        // The regex splitter will include the last string even if it doesn't end with a '\n',
        // So we need to ignore it if this happens. 
        if (p[p.Length-1] != '\n')
          break;

        // Display the message
        // "messages" is the big message text box in the form.
        // We must use a MethodInvoker, because only the thread that created the GUI can modify it.
        this.Invoke(new MethodInvoker(
          () => messages.AppendText(p)));

        // Then remove it from the SocketState's growable buffer
        ss.sb.Remove(0, p.Length);
      }
    }
      

    /// <summary>
    /// This is the event handler when the enter key is pressed in the messageToSend box
    /// </summary>
    /// <param name="sender">The Form control that fired the event</param>
    /// <param name="e">The key event arguments</param>
    private void MessageEnterHandler(object sender, KeyEventArgs e)
    {
      if (e.KeyCode == Keys.Enter)
      {
        string message = messageToSendBox.Text;
        // Append a newline, since that is our protocol's terminating character for a message.
        byte[] messageBytes = Encoding.UTF8.GetBytes(message + "\n");
        messageToSendBox.Text = "";
        theServer.BeginSend(messageBytes, 0, messageBytes.Length, SocketFlags.None, SendCallback, theServer);
      }
    }

    /// <summary>
    /// A callback invoked when a send operation completes
    /// Move this function to a standalone networking library. 
    /// </summary>
    /// <param name="ar"></param>
    private void SendCallback(IAsyncResult ar)
    {
      Socket s = (Socket)ar.AsyncState;
      // Nothing much to do here, just conclude the send operation so the socket is happy.
      s.EndSend(ar);
    }

  }
}
