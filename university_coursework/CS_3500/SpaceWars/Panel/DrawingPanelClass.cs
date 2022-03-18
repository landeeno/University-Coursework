using SpaceWars;
using SpaceWarsObjects;
using System;
using System.Diagnostics;
using System.Drawing;
using System.IO;
using System.Reflection;
using System.Windows.Forms;

/// <summary>
/// Authors:
/// 
///     Haley Feten & Landon Crowther
///     CS 3500
///     Fall 2018
///     Nov. 19, 2018
///     
/// Class Description: 
/// 
///     This class does all of the drawing for the program. 
namespace Drawer
{
    public class DrawingPanel : Panel
    {
        /// String used in finding the file path of the image folder.
        string imageFolder;
        // the World reference made from the controller
        private World theWorld;

        /// <summary>
        /// Constructor for a drawing folder. Only called by the GUI.
        /// </summary>
        /// <param name="w"></param>
        public DrawingPanel(World w)
        {
            theWorld = w;
            DoubleBuffered = true;

            //Gives the root of the image folder
            imageFolder = AppDomain.CurrentDomain.BaseDirectory;
            imageFolder = imageFolder.Replace("WorldView\\bin\\Debug", "References\\Libraries\\Images");

        }

        /// <summary>
        /// Helper method for DrawObjectWithTransform
        /// </summary>
        /// <param name="size">The world (and image) size</param>
        /// <param name="w">The worldspace coordinate</param>
        /// <returns></returns>
        private static int WorldSpaceToImageSpace(int size, double w)
        {
            return (int)w + size / 2;
        }

        // A delegate for DrawObjectWithTransform
        // Methods matching this delegate can draw whatever they want using e  
        public delegate void ObjectDrawer(object o, PaintEventArgs e);

        /// <summary>
        /// This method performs a translation and rotation to drawn an object in the world.
        /// </summary>
        /// <param name="e">PaintEventArgs to access the graphics (for drawing)</param>
        /// <param name="o">The object to draw</param>
        /// <param name="worldSize">The size of one edge of the world (assuming the world is square)</param>
        /// <param name="worldX">The X coordinate of the object in world space</param>
        /// <param name="worldY">The Y coordinate of the object in world space</param>
        /// <param name="angle">The orientation of the objec, measured in degrees clockwise from "up"</param>
        /// <param name="drawer">The drawer delegate. After the transformation is applied, the delegate is invoked to draw whatever it wants</param>
        private void DrawObjectWithTransform(PaintEventArgs e, object o, int worldSize, double worldX, double worldY, double angle, ObjectDrawer drawer)
        {
            // Perform the transformation
            int x = WorldSpaceToImageSpace(worldSize, worldX);
            int y = WorldSpaceToImageSpace(worldSize, worldY);
            e.Graphics.TranslateTransform(x, y);
            e.Graphics.RotateTransform((float)angle);
            // Draw the object 
            drawer(o, e);
            // Then undo the transformation
            e.Graphics.ResetTransform();

        }


        /// <summary>
        /// Acts as a drawing delegate for DrawObjectWithTransform
        /// After performing the necessary transformation (translate/rotate)
        /// DrawObjectWithTransform will invoke this method
        /// 
        /// This method is responsible for drawing the ships. 
        /// 
        /// </summary>
        /// <param name="o">The object to draw</param>
        /// <param name="e">The PaintEventArgs to access the graphics</param>
        private void ShipDrawer(object o, PaintEventArgs e)
        {
            Ship s = o as Ship;

            // size
            int width = 30;
            int height = 30;

            // Image for when the thrust is off. 
            if (!s.Thrust())
            {
                string shipFolder = imageFolder + "Ships\\";
                int imageID = s.GetID() % (Directory.GetFiles(shipFolder)).Length;
                string shipPath = (Directory.GetFiles(shipFolder))[imageID];
                Image shipImage = Image.FromFile(shipPath);
                e.Graphics.DrawImage(shipImage, -(width / 2), -(height / 2), width, height);
            }

            // Image for when thrust is on.
            else
            {
                string thrustFolder = imageFolder + "Thrust\\";
                int imageID = s.GetID() % (Directory.GetFiles(thrustFolder)).Length;
                string thrustPath = (Directory.GetFiles(thrustFolder))[imageID];
                Image thrustImage = Image.FromFile(thrustPath);
                e.Graphics.DrawImage(thrustImage, -(width / 2), -(height / 2), width, height);
            }


        }

        /// <summary>
        /// Draws the star on the screen, imported from the images folder. Acts as a drawing delegate.
        /// Star is stationary and doesn't move throughout the program. 
        /// </summary>
        /// <param name="o">object to draw</param>
        /// <param name="e">The PaintEventArgs to access the graphics</param>
        private void StarDrawer(object o, PaintEventArgs e)
        {
            Star s = o as Star;

            int width = 30;
            int height = 30;

            string imagePath = imageFolder + "star.jpg";

            Image image = Image.FromFile(imagePath);
            e.Graphics.DrawImage(image, -(width / 2), -(height / 2), width, height);


        }

        /// <summary>
        /// This method draws the name of the ships for the scoreboard.
        /// Acts as a delegate for DrawObjectWithTransform.
        /// </summary>
        /// <param name="o">object to draw</param>
        /// <param name="e">The PaintEventArgs to access the graphics</param>
        private void NameDrawer(object o, PaintEventArgs e)
        {
            Ship s = o as Ship;
            e.Graphics.DrawString(s.GetName() + "\t"+ s.GetScore() + " pts", new Font("Arial", 10), Brushes.White, new PointF());
        }

        /// <summary>
        /// Acts as a drawing delegate for DrawObjectWithTransform
        /// After performing the necessary transformation (translate/rotate)
        /// DrawObjectWithTransform will invoke this method
        /// </summary>
        /// <param name="o">The object to draw</param>
        /// <param name="e">The PaintEventArgs to access the graphics</param>
        private void ProjectileDrawer(object o, PaintEventArgs e)
        {
            Projectile p = o as Projectile;

            int width = 30;
            int height = 30;

            string projFolder = imageFolder + "Proj\\";
            int imageID = p.GetOwner() % (Directory.GetFiles(projFolder)).Length;
            string imagePath = (Directory.GetFiles(projFolder))[imageID];

            Image image = Image.FromFile(imagePath);
            e.Graphics.DrawImage(image, -(width / 2), -(height / 2), width, height);
        }


        // This method is invoked when the DrawingPanel needs to be re-drawn
        protected override void OnPaint(PaintEventArgs e)
        {

            lock (theWorld)
            {
                int Xpoint = theWorld.WorldSize / 2 - 100;
                int Ypoint = (-theWorld.WorldSize / 2) + 100;

                // Draw the ships
                foreach (Ship s in theWorld.GetShips())
                {
                    DrawObjectWithTransform(e, s, Size.Width, s.GetLocation().GetX(), s.GetLocation().GetY(), s.GetNormalizedOrientation().ToAngle(), ShipDrawer);
                    //DrawObjectWithTransform(e, s, Size.Width, Xpoint, Ypoint, 0, HPDrawer);
                    DrawObjectWithTransform(e, s, Size.Width, Xpoint - 120, Ypoint - 5, 0, NameDrawer);
                    Ypoint += 15;
                }

     
                
                // Draw the projectiles
                foreach (Projectile p in theWorld.GetProjectiles())
                {
                    DrawObjectWithTransform(e, p, Size.Width, p.GetLocation().GetX(), p.GetLocation().GetY(), p.GetOrientation().ToAngle(), ProjectileDrawer);
                }
                // Draw the star
                foreach (Star p in theWorld.GetStars())
                {
                    DrawObjectWithTransform(e, p, Size.Width, p.GetLocation().GetX(), p.GetLocation().GetY(), 0, StarDrawer);
                }
            }



            base.OnPaint(e);
        }

   }


}
