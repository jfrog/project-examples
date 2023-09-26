using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace MyLogger
{
    public class MyLogger
    {
        public void log(String message)
        {
            Console.WriteLine("[MyLogger]" + message);
        }
    }
}
