using Microsoft.VisualStudio.TestTools.UnitTesting;
using Controller;
using SpaceWars;
using SpaceWarsObjects;
using Newtonsoft.Json;

namespace SpaceTesting
{
    [TestClass]
    public class UnitTest1
    {
        [TestMethod]
        public void TestMethod1()
        {
            //TestServer server = new TestServer();
            //server.StartServer();
            //GameController theController = new GameController();
            //theController.Connect("localhost");

            //while(!server.IsConnected())
            //{

            //}

            GameController cc = new GameController();
            World world = new World();
            Ship ship = new Ship(0, new Vector2D(0, 0), new Vector2D(0, 0), true, "haley", 5, 3);
            //string json = JsonConvert.SerializeObject(ship);
            //cc.AddToNewWorld(json, world);
            //cc.GetWorld();
            

            


        }
    }
}
