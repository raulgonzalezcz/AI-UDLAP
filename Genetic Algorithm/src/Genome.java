/**
 *
 * @author Gerardo Ayala.
 * April 2014.
 *
 */

import java.util.LinkedList;

// A GENOME (CHROMOSOME) is a list of GENEs
// where we can define its length.
public class Genome extends LinkedList<Gene>
{
    public static int length;
    //----------------

    public static void setLength(int aLength)
    {
        length = aLength;
    }//end setLength

}//end class Genome
