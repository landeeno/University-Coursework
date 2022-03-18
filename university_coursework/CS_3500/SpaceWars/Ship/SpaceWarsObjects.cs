using System;
using System.Collections;
using System.Collections.Generic;
using Newtonsoft.Json;
using SpaceWars;

/// <summary>
/// Authors:
/// 
///     Haley Feten & Landon Crowther
///     CS 3500
///     Fall 2018
///     Nov. 19, 2018
///     
/// Namespace Description: 
/// 
///     This namespace contains the information for the various objects in SpaceWars, including
///     Projectiles, Ships, and Stars
namespace SpaceWarsObjects
{
    /// <summary>
    /// JSON compatible Ship Class 
    /// </summary>
    [JsonObject(MemberSerialization.OptIn)]
    public class Ship 
    {
        [JsonProperty(PropertyName = "ship")]
        private int ID;

        [JsonProperty]
        private Vector2D loc;

        [JsonProperty]
        private Vector2D dir;

        [JsonProperty]
        private bool thrust;

        [JsonProperty]
        private string name;

        [JsonProperty]
        private int hp;

        /// <summary>
        /// Acceleration at this moment in time of the ship
        /// </summary>
        private Vector2D acceleration;

        /// <summary>
        ///  Velocity at this moment in time of the ship.
        /// </summary>
        private Vector2D velocity;

        /// <summary>
        /// The gravity of the sun affecting the ship
        /// </summary>
        private Vector2D gravity;

     

        [JsonProperty]
        private int score;

        private bool allowedToFire;
        public int framesSinceLastFire;
        public int framesAfterDeath;

        /// <summary>
        /// Constructor for Ship Class
        /// </summary>
        /// <param name="ID">Ship ID (as an int)</param>
        /// <param name="loc">Vector2D for Ship's location</param>
        /// <param name="dir">Vector2D for Ship's direction</param>
        /// <param name="thrust">Thrust for ship</param>
        /// <param name="name">Name of Player</param>
        /// <param name="hp">Ship's health</param>
        /// <param name="score">Ship's score</param>
        public Ship(int ID, Vector2D loc, Vector2D dir, bool thrust, string name, int hp, int score)
        {
            this.ID = ID;
            this.dir = dir;
            this.thrust = thrust;
            this.name = name;
            this.hp = hp;
            this.score = score;
            this.loc = loc;
            this.velocity = new Vector2D(0,0);
            this.acceleration = new Vector2D(0,0);
            this.gravity = new Vector2D(0, 0);
            allowedToFire = true;
            framesSinceLastFire = 0;
            framesAfterDeath = 0;
        }
        /// <summary>
        /// Updates the orientation of the ship with a new Vector2D
        /// </summary>
        /// <param name="orientation"></param>
        public void UpdateOrientation(Vector2D orientation)
        {
            this.dir = orientation;
        }

        /// <summary>
        /// Returns the score of the ship based on its HP
        /// </summary>
        /// <returns></returns>
        public int GetScore()
        {
            return score;
        }

        /// <summary>
        /// return whether or not the player canf ire
        /// </summary>
        /// <returns></returns>
        public bool AllowedToFire()
        {
            return allowedToFire;
        }

        /// <summary>
        /// determines when the player can fire agian
        /// </summary>
        /// <param name="canFire"></param>
        public void SetFiringCapability(bool canFire)
        {
            this.allowedToFire = canFire;
        }

        /// <summary>
        /// Returns ship location.
        /// </summary>
        /// <returns></returns>
        public Vector2D GetLocation()
        {
            return loc;
        }

        /// <summary>
        /// Returns true if the ship has remaining lives
        /// </summary>
        /// <returns></returns>
        public bool IsAlive()
        {
            return hp > 0;
        }

        /// <summary>
        /// Returns the ship ID.
        /// </summary>
        /// <returns></returns>
        public int GetID()
        {
            return ID;
        }

        /// <summary>
        /// Returns the orientation of the ship.
        /// </summary>
        /// <returns></returns>
        public Vector2D GetNormalizedOrientation()
        {
            return dir;
        }

        

        

        /// <summary>
        /// Decrements the HP of the ship. Called when the ship dies.
        /// </summary>
        public void Terminated()
        {
            hp = 0;
        }

        /// <summary>
        /// Called after a player has died and respawns.
        /// </summary>
        /// <param name="lifePoints"></param>
        /// <param name="loc"></param>
        /// <param name="dir"></param>
        public void BringBackToLife(int lifePoints, Vector2D loc, Vector2D dir)
        {
            hp = lifePoints;
            dir.Normalize();
            this.loc = loc;
            this.dir = dir;

        }

        /// <summary>
        /// Returns true or false if the ship is thrusting.
        /// </summary>
        /// <returns></returns>
        public bool Thrust()
        {
            return thrust;
        }

        /// <summary>
        /// turn thrust on
        /// </summary>
        public void SetThrustOn()
        {
            thrust = true;
        }

        /// <summary>
        /// turn the thrust off
        /// </summary>
        public void SetThrustOff()
        {
            thrust = false;
        }

        /// <summary>
        /// Returns the player name of the ship.
        /// </summary>
        /// <returns></returns>
        public string GetName()
        {
            return name;
        }

        /// <summary>
        /// return the velocity of the ship
        /// </summary>
        /// <returns></returns>
        public Vector2D GetVelocity()
        {
            return velocity;
        }



        /// <summary>
        /// Updates the location of the ship to a new Vector2D value
        /// </summary>
        /// <param name="newLocation"></param>
        public void UpdateLocation(Vector2D newLocation)
        {

           

            loc = newLocation;

            


        }

        /// <summary>
        /// deceremtn the player's hp
        /// </summary>
        public void HitByProjectile()
        {
            hp--;
        }

        /// <summary>
        /// UPdate the poin for theplayer
        /// </summary>
        public void AddPoint()
        {
            score++;
        }
    }
    /// <summary>
    /// Class for projectile objects.
    /// </summary>
    [JsonObject(MemberSerialization.OptIn)]
    public class Projectile
    {
        [JsonProperty(PropertyName = "proj")]
        private int ID;

        [JsonProperty]
        private Vector2D loc;

        [JsonProperty]
        private Vector2D dir;

        /// <summary>
        /// the bullet has made contact with aplayer
        /// </summary>
        public void DeadBullet()
        {
            alive = false;
        }



        /// <summary>
        /// Returns projectile ID.
        /// </summary>
        /// <returns></returns>
        public int GetID()
        {
            return ID;
        }

        [JsonProperty]
        private bool alive;

        /// <summary>
        /// Returns whether or not the projectile is moving (active)
        /// </summary>
        /// <returns></returns>
        public bool IsActive()
        {
            return alive;
        }

        [JsonProperty]
        private int owner;

        /// <summary>
        /// constructor for projectile
        /// </summary>
        /// <param name="ID"></param>
        /// <param name="loc"></param>
        /// <param name="dir"></param>
        /// <param name="alive"></param>
        /// <param name="owner"></param>
        public Projectile(int ID, Vector2D loc, Vector2D dir, bool alive, int owner)
        {
            this.ID = ID;
            this.dir = dir;
            this.alive = alive;
            this.owner = owner;
            this.loc = loc;
        }

        /// <summary>
        /// Returns location of projectile.
        /// </summary>
        /// <returns></returns>
        public Vector2D GetLocation()
        {
            return loc;
        }

        /// <summary>
        /// Returns projectile orientation.
        /// </summary>
        /// <returns></returns>
        public Vector2D GetOrientation()
        {
            return dir;
        }

        /// <summary>
        /// returns the owner of the projectile for point trakcing
        /// </summary>
        /// <returns></returns>
        public int GetOwner()
        {
            return this.owner;
        }

        /// <summary>
        /// change location of projectile
        /// </summary>
        /// <param name="currentLoc"></param>
        public void UpdateLocation(Vector2D currentLoc)
        {
            this.loc = currentLoc;
        }

        /// <summary>
        /// deactivate a projectile
        /// </summary>
        public void Deactivate()
        {
            this.alive = false; 
        }
    }
    /// <summary>
    /// Class for star objects.
    /// </summary>
    [JsonObject(MemberSerialization.OptIn)]
    public class Star
    {
        [JsonProperty(PropertyName = "star")]
        private int ID;

        [JsonProperty]
        private Vector2D loc;

        [JsonProperty]
        private double mass;

        /// <summary>
        /// Constructor of the star
        /// </summary>
        /// <param name="ID"></param>
        /// <param name="loc"></param>
        /// <param name="mass"></param>
        public Star(int ID, Vector2D loc, double mass)
        {
            this.ID = ID;
            this.loc = loc;
            this.mass = mass;
        }

        /// <summary>
        /// Returns star ID.
        /// </summary>
        /// <returns></returns>
        public int GetID()
        {
            return ID;
        }

        /// <summary>
        /// Returns star location.
        /// </summary>
        /// <returns></returns>
        public Vector2D GetLocation()
        {
            return loc;
        }

        /// <summary>
        /// Returns mass of object
        /// </summary>
        /// <returns></returns>
        public double GetMass()
        {
            return mass;
        }

        /// <summary>
        /// Changes the location of the object
        /// </summary>
        /// <param name="currentLoc"></param>
        public void UpdateLocation(Vector2D currentLoc)
        {
            this.loc = currentLoc;
        }
    }


}
