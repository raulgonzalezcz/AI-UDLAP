/*
 * by Gerardo Ayala
 * Universidad de las Américas Puebla
 * Copyright
 * gerardo.ayala@udlap.mx
 */


//To be defined for each specific knowledge base
public class Condition
{
    public String conditionPredicate;
    //--------------------------

    public Condition(String aConditionPredicate)
    {
        conditionPredicate = aConditionPredicate;
    }//end constructor


    public String toString()
    {
        return conditionPredicate;
    }//end toString

}//end class Condition
