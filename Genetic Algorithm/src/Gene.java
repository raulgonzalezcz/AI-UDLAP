
// The GENE defined
// by the programmer.
// A gene is a CITY, in this example.
// Here it corresponds to the coordinates X & Y of the city.

public class Gene
{
    int x;
    int y;
    //-------------

    public Gene(int x, int y)
    {
        this.x = x;
        this.y = y;
    }//end constructor


    // Gets city's x coordinate
    public int getX()
    {
        return x;
    }//getX


    // Gets city's y coordinate
    public int getY()
    {
        return y;
    }// getY



    public String toString()
    {
        return "(" + x + ", " + y + ")";
    }//end toString

}//end class Gene