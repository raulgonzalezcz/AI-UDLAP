/*
 * by Gerardo Ayala
 * Universidad de las Am�ricas Puebla
 * Copyright
 * gerardo.ayala@udlap.mx
 */


public class Message
{
    String source;
    String destination;
    String body;
    //---------------

    public Message(String theSource,
                   String theDestination,
                   String theBody)
    {
        source = theSource;
        destination = theDestination;
        body = theBody;
    }//end constructor

    public String toString()
    {
        return source + " / " + destination + " / " + body;
    }//end toString
}//end class Message
