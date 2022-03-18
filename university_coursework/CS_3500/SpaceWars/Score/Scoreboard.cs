using SpaceWars;
using SpaceWarsObjects;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.Drawing;


namespace WorldView
{
    public class Scoreboard : Panel
    {
        private Dictionary<int, int> playerScores;
        private World theWorld;
        int height;
        int width;


        public Scoreboard(World w)
        {
            theWorld = w;
            playerScores = new Dictionary<int, int>();
            height = theWorld.worldSize;
            width = height / 4;
        }

        /// <summary>
        /// Adds the player to the score board
        /// </summary>
        /// <param name="ship"></param>
        public void AddPlayer(Ship ship)
        {
            playerScores.Add(ship.GetID(), ship.GetHP());
        }

        /// <summary>
        /// Updates score when there is a change to the HP
        /// </summary>
        /// <param name="ship"></param>
        public void UpdateScore(Ship ship)
        {

        }


        

    }


}
