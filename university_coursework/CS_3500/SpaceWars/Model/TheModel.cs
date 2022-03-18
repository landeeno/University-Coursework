using SpaceWarsObjects;
using System;
using System.Collections.Generic;
using System.Linq;

namespace SpaceWars
{

    public class TheModel
    {

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
        private bool extraGameMode;


        private World theWorld;


        /// <summary>
        /// Constructor. Settings from XML
        /// </summary>
        /// <param name="projectileSpeed"></param>
        /// <param name="engineStrength"></param>
        /// <param name="shipSize"></param>
        /// <param name="turningRate"></param>
        /// <param name="starSize"></param>
        /// <param name="startingHitPoints"></param>
        /// <param name="theWorldSize"></param>
        /// <param name="fireDelay"></param>
        /// <param name="respawnDelay"></param>
        /// <param name="timePerFrame"></param>
        /// <param name="extraGameMode"></param>
        /// <param name="starMass"></param>
        /// <param name="starX"></param>
        /// <param name="starY"></param>
        public TheModel(int projectileSpeed, int engineStrength, int shipSize, int turningRate,
                 int starSize, int startingHitPoints, int theWorldSize, int fireDelay, int respawnDelay, int timePerFrame, bool extraGameMode, double starMass, double starX, double starY)
        {
            theWorld = new World(theWorldSize);
            this.projectileSpeed = projectileSpeed;
            this.engineStrength = engineStrength;
            this.shipSize = shipSize;
            this.starSize = starSize;
            this.turningRate = turningRate;
            this.startingHitPoints = startingHitPoints;
            this.theWorldSize = theWorldSize;
            this.fireDelay = fireDelay;
            this.respawnDelay = respawnDelay;
            this.timePerFrame = timePerFrame;
            this.starMass = starMass;
            this.extraGameMode = extraGameMode;

            Star star = new Star(0, new Vector2D(starX, starY), starMass);
            theWorld.AddStar(star);

            if (extraGameMode)
            {
                for (int starIndex = 1; starIndex <= 3; starIndex++)
                {
                    Vector2D loc = new Vector2D(RandomVector2D(false));


                    Star extraStar = new Star(starIndex, loc, starMass);
                    theWorld.AddStar(extraStar);

                }

            }

        }

        /// <summary>
        /// Getter method used by the Server class to return a copy of the Model's world
        /// </summary>
        /// <returns></returns>
        public World GetWorld()
        {
            return theWorld;
        }

        /// <summary>
        /// Process the data from client, either R , L, T, or F and call appropriate method
        /// </summary>
        /// <param name="ID"></param>
        /// <param name="data"></param>
        public void DoAction(int ID, string data)
        {
            foreach (char c in data)
            {

                switch (c)
                {

                    case 'R':
                        CalculateOrientation(ID, 'R');
                        break;
                    case 'L':
                        CalculateOrientation(ID, 'L');
                        break;
                    case 'T':
                        TurnThrustOn(ID);
                        break;
                    case 'F':
                        FireProjectile(ID);
                        break;
                    default:
                        break;

                }

            }
        }

        /// <summary>
        /// Toggle for thrust
        /// </summary>
        /// <param name="ID"></param>
        private void TurnThrustOn(int ID)
        {
            theWorld.GetShippyShip(ID).SetThrustOn();

        }

        /// <summary>
        /// Fire projecitle
        /// </summary>
        /// <param name="ID"></param>
        private void FireProjectile(int ID)
        {

            if (!theWorld.GetShippyShip(ID).IsAlive())
            {
                return;
            }

            if (theWorld.GetShippyShip(ID).AllowedToFire())
            {
                Vector2D shipOrientation = new Vector2D(theWorld.GetShippyShip(ID).GetNormalizedOrientation());
                Vector2D shipLocation = new Vector2D(theWorld.GetShippyShip(ID).GetLocation());
                Projectile p = new Projectile(++theWorld.projectileCounter, shipLocation, shipOrientation, true, ID);
                theWorld.AddProjectile(p);
                theWorld.GetShippyShip(ID).SetFiringCapability(false);
                theWorld.GetShippyShip(ID).framesSinceLastFire = 0;
            }

        }

        /// <summary>
        /// If a player is disconnected, no longer send data about the ship
        /// </summary>
        /// <param name="iD"></param>
        public void PlayerDisconnected(int ID)
        {
            theWorld.RemoveShip(ID);
        }


        /// <summary>
        /// Updates the world information that should occur in the next frame.  Moves all of the objects in the appropriate
        /// direction and checks for collisions.
        /// </summary>
        public void GetNextFrame()
        {
            lock (theWorld)
            {
                CheckFireDelay();
                CheckRespawnDelay();
                MoveAllObjects();
                CollisionCheck();
            }
        }

        /// <summary>
        /// Timer for respawning ships based on how many frames have passed
        /// </summary>
        private void CheckRespawnDelay()
        {
            foreach (Ship s in theWorld.GetShips())
            {
                lock (s)
                {
                    if (!s.IsAlive())
                    {
                        if (s.framesAfterDeath < respawnDelay)
                        {

                            s.framesAfterDeath++;
                        }
                        else
                        {

                            s.BringBackToLife(startingHitPoints, RandomVector2D(true), RandomVector2D(false));
                            s.framesAfterDeath = 0;
                        }
                    }
                }
            }
        }

        /// <summary>
        /// Method that checks for collisions between players and stars and players and projectiles
        /// </summary>
        private void CollisionCheck()
        {


            // check for collision with star and ship
            foreach (Ship s in theWorld.GetShips())
            {
                foreach (Star star in theWorld.GetStars())
                {
                    Vector2D difference = star.GetLocation() - s.GetLocation();
                    double starX = star.GetLocation().GetX();
                    double starY = star.GetLocation().GetY();

                    if (Math.Abs(difference.GetX()) < (starSize + shipSize) && Math.Abs(difference.GetY()) < (starSize + shipSize))
                    {
                        s.Terminated();
                    }
                }


            }

            // check for collision with ship and projectile
            foreach (Projectile p in theWorld.GetProjectiles())
            {

                Vector2D pLoc = p.GetLocation();

                if (!p.IsActive())
                {
                    continue;
                }

                foreach (Ship s in theWorld.GetShips())
                {
                    // only check alive ships
                    if (!s.IsAlive())
                    {
                        continue;
                    }

                    // no friendly fire
                    if (s.GetID() == p.GetOwner())
                    {
                        continue;
                    }

                    Vector2D sLoc = s.GetLocation();

                    Vector2D difference = pLoc - sLoc;
                    if (Math.Abs(difference.GetX()) < (shipSize) && Math.Abs(difference.GetY()) < (shipSize))
                    {
                        p.DeadBullet();
                        s.HitByProjectile();
                        theWorld.GetShippyShip(p.GetOwner()).AddPoint();
                    }







                }
            }


        }

        /// <summary>
        /// Frame rate timer for when a ship can shoot another projecitle
        /// </summary>
        private void CheckFireDelay()
        {

            foreach (Ship s in theWorld.GetShips())
            {
                if (s.framesSinceLastFire < fireDelay)
                {
                    s.framesSinceLastFire++;
                }
                else
                {
                    s.SetFiringCapability(true);
                }
            }

        }

        /// <summary>
        /// Sends calls to update locations of each objecdt
        /// </summary>
        private void MoveAllObjects()
        {
            MoveShips();
            MoveProjectiles();
            if (extraGameMode)
            {
                MoveStars();
            }
        }

        /// <summary>
        /// ONly used if extra game mode is enabled. Randomly moevs the locatio of the stars
        /// </summary>
        private void MoveStars()
        {
            IEnumerable<Star> starList = theWorld.GetStars();

            foreach (Star s in starList)
            {
                Vector2D currentLoc = s.GetLocation();

                Vector2D randomVel = new Vector2D(RandomVector2D(false));
                Vector2D randomDir = new Vector2D(RandomVector2D(false));
                randomDir.Normalize();

                double random = randomVel.GetX();

                randomDir = randomDir * 40;

                currentLoc += randomDir;

                CheckBounds(ref currentLoc);

                s.UpdateLocation(currentLoc);
            }


            }

            /// <summary>
            /// Logic for ship movement
            /// </summary>
            private void MoveShips()
            {
                // Assuming that there is only one start that has gravity...
                Star star = theWorld.GetStars().First();
                foreach (Ship s in theWorld.GetShips())
                {

                    if (s.IsAlive())
                    {


                        Vector2D thrust = new Vector2D(0, 0);
                        Vector2D gravity = star.GetLocation() - s.GetLocation();
                        gravity.Normalize();
                        gravity = gravity * star.GetMass();

                        //
                        if (s.Thrust())
                        {
                            thrust = s.GetNormalizedOrientation();
                            thrust *= engineStrength;
                            s.SetThrustOff();

                        }


                        Vector2D acceleration = gravity + thrust;
                        Vector2D velocity = s.GetVelocity() + acceleration;
                        //s.SetVelocity(velocity);
                        Vector2D location = s.GetLocation() + velocity;
                        CheckBounds(ref location);

                        s.UpdateLocation(location);
                    }


                }
            }

            /// <summary>
            /// Logic for projectile movement
            /// </summary>
            private void MoveProjectiles()
            {
                IEnumerable<Projectile> projectileList = theWorld.GetProjectiles();
                List<Projectile> deletedProjectiles = new List<Projectile>();
                foreach (Projectile p in projectileList)
                {
                    Vector2D currentLoc = p.GetLocation();
                    Vector2D dir = p.GetOrientation();
                    Vector2D velocity = dir * projectileSpeed;
                    currentLoc += velocity;

                    p.UpdateLocation(currentLoc);

                    if (ProjectileOutOfBounds(p))
                    {
                        deletedProjectiles.Add(p);
                    }

                }

                // remove out of bounds projectiles
                foreach (Projectile p in deletedProjectiles)
                {
                    theWorld.RemoveProjectile(p);
                }
            }

            /// <summary>
            /// Checks to see when projectile has left the system so it can be removed and no longer tracked
            /// </summary>
            /// <param name="p"></param>
            /// <returns></returns>
            private bool ProjectileOutOfBounds(Projectile p)
            {
                double x = p.GetLocation().GetX();
                double y = p.GetLocation().GetY();
                double halfSize = theWorld.WorldSize;

                // check right edge:
                if (x > halfSize || x < -halfSize || y < -halfSize || y > halfSize)
                {
                    return true;
                }

                return false;



            }

            /// <summary>
            /// Checks if the location exceeds the bounds of the world size.
            /// </summary>
            /// <param name="location"></param>
            private void CheckBounds(ref Vector2D location)
            {
                double x = location.GetX();
                double y = location.GetY();
                double halfSize = theWorld.WorldSize / 2;

                // check right edge:
                if (x > halfSize)
                {
                    x = -halfSize;
                }

                // check left edge:
                if (x < -halfSize)
                {
                    x = halfSize;
                }

                // top edge
                if (y < -halfSize)
                {
                    y = halfSize;
                }

                // bottom edge
                if (y > halfSize)
                {
                    y = -halfSize;
                }

                location = new Vector2D(x, y);
            }

            /// <summary>
            /// Used to adjust the orientation of a shipo when the rotate commands are called
            /// </summary>
            /// <param name="ID"></param>
            /// <param name="c"></param>
            private void CalculateOrientation(int ID, char c)
            {

                Vector2D orientation = GetShipOrientation(ID);

                switch (c)
                {
                    case 'R':
                        orientation.Rotate(turningRate);
                        break;
                    case 'L':
                        orientation.Rotate(-turningRate);
                        break;
                    default:
                        break;
                }

                theWorld.GetShippyShip(ID).UpdateOrientation(orientation);


            }


               
            /// <summary>
            /// Calculate the orientatio of a ship based on its ID. 
            /// </summary>
            /// <param name="ID"></param>
            /// <returns></returns>
            private Vector2D GetShipOrientation(int ID)
            {
                Vector2D orientation = theWorld.GetShippyShip(ID).GetNormalizedOrientation();
                orientation.Normalize();
                return orientation;

            }

            /// <summary>
            /// Used when a new client connects and is added to the game
            /// </summary>
            /// <param name="ID"></param>
            /// <param name="name"></param>
            /// <param name="HP"></param>
            public void AddNewClientShip(int ID, string name, int HP)
            {
                Vector2D loc = RandomVector2D(true);
                Vector2D orientation = RandomVector2D(false);
                orientation.Normalize();

                Ship s = new Ship(ID, loc, orientation, false, name, HP, 0);

                theWorld.AddShip(s);
            }

            /// <summary>
            /// Used to generate random Vector2D within bounds of world. 
            /// </summary>
            /// <param name="avoidStar">true if the vector2D needs to not be where the stars are. Don't think its working.</param>
            /// <returns></returns>
            private Vector2D RandomVector2D(bool avoidStar)
            {


                Random random = new Random();
                double rndmX = theWorld.worldSize / 2 - (random.NextDouble() * theWorld.worldSize);
                double rndmY = theWorld.worldSize / 2 - (random.NextDouble() * theWorld.worldSize);


                if (avoidStar)
                {
                    foreach (Star s in theWorld.GetStars())
                    {
                        Vector2D starLoc = s.GetLocation();

                        while (Math.Abs(rndmX - starLoc.GetX()) < starSize)
                        {
                            rndmX = theWorld.worldSize / 2 - (random.NextDouble() * theWorld.worldSize);
                        }

                        while (Math.Abs(rndmY - starLoc.GetY()) < starSize)
                        {
                            rndmY = theWorld.worldSize / 2 - (random.NextDouble() * theWorld.worldSize);
                        }
                    }
                }


                return new Vector2D(rndmX, rndmY);

            }
        }
    }
