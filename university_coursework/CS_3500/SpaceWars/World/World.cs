using SpaceWarsObjects;
using System;
using System.Collections.Generic;
using System.Linq;

/// Authors:
/// 
///     Haley Feten & Landon Crowther
///     CS 3500
///     Fall 2018
///     Nov. 19, 2018
namespace SpaceWars
{

    /// <summary>
    /// This class contains all the information for the World. 
    /// </summary>
    public class World
    {
        // Important World data structures.
        private Dictionary<int, Ship> myShips;

        // int = projectileID
        private Dictionary<int, Projectile> myProjectiles;
        private Dictionary<int, Star> myStars;
        public int worldSize;
        public int projectileCounter;


        /// <summary>
        /// World constructor. Makes a new world and initializes data structures.
        /// </summary>
        /// <param name="worldSize">Size of world</param>
        public World( int worldSize)
        {
            // initialize data structures.
            myShips = new Dictionary<int, Ship>();
            myProjectiles = new Dictionary<int, Projectile>();
            myStars = new Dictionary<int, Star>();
            // set ID and world size. 
            this.worldSize = worldSize;
            projectileCounter = 0;

        }

       

       



        

        /// <summary>
        /// Returns the world size that was given.
        /// </summary>
        public int WorldSize
        {
            get
            {
                return worldSize;
            }
        }

        /// <summary>
        /// Adds star to world
        /// </summary>
        /// <param name="star"></param>
        public void AddStar(Star star)
        {
            myStars.Add(star.GetID(), star);
        }




        /// <summary>
        /// Getter method for returning the ships. 
        /// </summary>
        /// <returns></returns>
        public IEnumerable<Ship> GetShips()
        {

            return myShips.Values;
        }

        public Ship GetShippyShip(int ID)
        {
            myShips.TryGetValue(ID, out Ship shippyShip);
            return shippyShip;
        }


        /// <summary>
        /// Updates the star information with the updated star corresponding with its ID. 
        /// </summary>
        /// <param name="star"></param>
        public void UpdateStar(Star star)
        {
            myStars[star.GetID()] = star;
        }

        /// <summary>
        /// Updates the projectile information with the updated projectile corresponding with its ID.
        /// </summary>
        /// <param name="projectile"></param>
        public void UpdateProjectile(Projectile projectile)
        {
            if (projectile.IsActive())
                myProjectiles[projectile.GetID()] = projectile;
            else
                myProjectiles.Remove(projectile.GetID());

        }

        /// <summary>
        /// Updates the ship information with the updated ship corresponding with its ID.
        /// </summary>
        /// <param name="ship"></param>
        public void UpdateShip(Ship ship)
        {
            if (ship.IsAlive())
                myShips[ship.GetID()] = ship;
            else
                myShips.Remove(ship.GetID());
        }

     

       

        /// <summary>
        /// USED FOR TESTING PURPOSES
        /// Adds ship to world
        /// </summary>
        /// <param name="s"></param>
        public void AddShip(Ship s)
        {
            myShips.Add(s.GetID(), s);
        }

        /// <summary>
        /// Adds porjectile to world
        /// </summary>
        /// <param name="p"></param>
        public void AddProjectile(Projectile p)
        {
            lock (this)
            {
                myProjectiles.Add(p.GetID(), p);
            }
        }

        /// <summary>
        /// Getter method for all the projectiles on the screen. 
        /// </summary>
        /// <returns></returns>
        public IEnumerable<Projectile> GetProjectiles()
        {
            return myProjectiles.Values;
        }

        /// <summary>
        /// Removes the ship from the world. Make the HP = 0.
        /// </summary>
        /// <param name="ID"></param>
        public void RemoveShip(int ID)
        {
            myShips.TryGetValue(ID, out Ship s);
            s.Terminated();
            myShips.Remove(ID);
        }

        /// <summary>
        /// Getter method for all the stars on the screen. 
        /// </summary>
        /// <returns></returns>
        public IEnumerable<Star> GetStars()
        {
            return myStars.Values;
        }

        /// <summary>
        /// Removes projectile from world
        /// </summary>
        /// <param name="p"></param>
        public void RemoveProjectile(Projectile p)
        {
            
            myProjectiles.TryGetValue(p.GetID(), out Projectile val);
            val.Deactivate();
            myProjectiles.Remove(p.GetID());
            
        }
    }

}

