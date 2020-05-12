/*
 * by Gerardo Ayala
 * Universidad de las AmŽricas Puebla
 * Copyright
 * gerardo.ayala@udlap.mx
 */

public class Commitment implements Comparable
{
    public int priority;
    protected String predicate;

    Commitment(String aString)
    {
        predicate = aString;
        priority = 999;
    }//end constructor


    public void setPriority(int aPriority)
    {
        priority = aPriority;
    }//end setPriority


    public int compareTo(Object anObject)
    {
        Commitment otherCommitment;
        //----------------------

        otherCommitment = (Commitment) anObject;

        if(this.priority > otherCommitment.priority)
            return 1;
            //end if
        else
            return -1;
        //end if else

    }//end compareTo


    public String toString()
    {
        return predicate + " / " + priority;
    }//end toString

}//end class Commitment
