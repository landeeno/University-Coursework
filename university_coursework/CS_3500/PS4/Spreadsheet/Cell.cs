using System;

using System.Collections.Generic;

using System.Linq;

using System.Text;

using System.Threading.Tasks;

using SpreadsheetUtilities;




namespace SS

{

    class Cell

    {

        // Contents of cell

        private object content;

        public Cell()
        {
            content = "";
        }



        public object Contents

        {

            get

            {

                return content;

            }




            set => content = value;




        }

    }
}



    