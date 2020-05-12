/*
 * by Gerardo Ayala
 * Universidad de las Am?ricas Puebla
 * Copyright
 * gerardo.ayala@udlap.mx
 */

import java.util.Vector;

public abstract class Blackboard
{
    private static volatile Vector<Message> allInformationMessages;
    private static volatile Vector<Message>  allRequestMessages;
    public static volatile Situation currentSituation;
    public static volatile Vector<Situation> history;
    public static volatile Situation finalState;
    static volatile boolean firstMilestoneAccomplished;
    static volatile boolean secondMilestoneAccomplished;
    static volatile boolean thirdMilestoneAccomplished;
    static volatile boolean finalMilestoneAccomplished;
    //-----------------------------


    public static void init(Situation initialSituation,
                            Situation finalSituation)
    {
        currentSituation = initialSituation;
        finalState = finalSituation;
        allInformationMessages = new Vector<Message> ();
        allRequestMessages = new Vector<Message> ();
        history = new Vector<Situation>();
        history.add(currentSituation);
        firstMilestoneAccomplished = false;
        secondMilestoneAccomplished = false;
        thirdMilestoneAccomplished = false;
        finalMilestoneAccomplished = false;

    }//end init


    public static Vector<Message> getInformationMessages(String anId)
    {
        return null;
    }//end getInformationMessages


    public static Vector<Message> getRequestMessages(String anId)
    {
        return null;
    }//end getRequestMessages


    public static Situation getCurrentSituation()
    {
        return currentSituation;
    }//end getSituation


    public static boolean alreadyHappened(Situation aSituation)
    {
        int i;
        boolean happened;
        //-----
        happened = false;
        i = 0;
        while (i < history.size())
        {
            if(aSituation.matches(history.get(i)))
                happened = true;
            //end if
            i = i + 1;
        }//end while
        return happened;
    }//end alreadyHappened


    public static void setCurrentSituation(Situation aNewSituation)
    {
        history.add(aNewSituation);
        currentSituation = aNewSituation;
    }//end getSituation


    public static void addInformationMessage(Message aMessage)
    {
        allInformationMessages.add(aMessage);
    }//end addInformationMessage


    public static void showHistory()
    {
        int i;
        //------
        i = 0;
        while(i < history.size())
        {
            System.out.println(history.get(i));
            i = i + 1;
        }//end while
    }//end showHistory


    public static void showInformationMessages()
    {
        int i;
        //------
        i = 0;
        while(i < allInformationMessages.size())
        {
            System.out.println(allInformationMessages.get(i));
            i = i + 1;
        }//end while
    }//end showInformationMessages



    public static void showRequestMessages()
    {
        int i;
        //------
        i = 0;
        while(i < allRequestMessages.size())
        {
            System.out.println(allRequestMessages.get(i));
            i = i + 1;
        }//end while
    }//end showRequestMessages


    // The agent shows its beliefs about the current situation
    public synchronized static void showCurrentSituation()
    {
        System.out.println("CURRENT SITUATION:");
        System.out.println(Blackboard.getCurrentSituation());
    }//end showCurrentSituation

}//end class Blackboard
