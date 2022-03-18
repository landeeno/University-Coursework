using Microsoft.VisualStudio.TestTools.UnitTesting;
using SpaceWars;
using SpaceWarsObjects;
using System.Collections.Generic;

namespace ModelTests
{
    [TestClass]
    public class UnitTest1
    {
        [TestMethod]
        public void TestProjectileFire()
        {
            World w = MakeDefaultWorld();
            Ship s = MakeDefaultShip();
            w.AddShip(s);
            TheModel model = new TheModel(w, 5, 1);

            List<Projectile> currentProjectiles = (List<Projectile>)model.theWorld.GetProjectiles();



            Assert.AreEqual(0, currentProjectiles.Count);

            model.DoAction(00, "F");

            currentProjectiles = (List<Projectile>)model.theWorld.GetProjectiles();

            Assert.AreEqual(1, currentProjectiles.Count);





        }

        [TestMethod]
        public void TestShipBeingAdded()
        {
            World w = MakeDefaultWorld();
            Ship s = MakeDefaultShip();
            TheModel model = new TheModel(w, 5, 1);

            List<Projectile> currentShips = (List<Projectile>)model.theWorld.GetShips();
            Assert.AreEqual(0, currentShips.Count);
            model.theWorld.AddShip(s);
            currentShips = (List<Projectile>)model.theWorld.GetShips();
            Assert.AreEqual(1, currentShips.Count);


        }



        private Ship MakeDefaultShip()
        {
            Vector2D zeroLocation = new Vector2D(0, 0);
            Ship s = new Ship(0, zeroLocation, zeroLocation, 0, "testShip", 5, 0);
            return s;
        }

        private World MakeDefaultWorld()
        {
            return new World(999, 750);
        }


    }



}
