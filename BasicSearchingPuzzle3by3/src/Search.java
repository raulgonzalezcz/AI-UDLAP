import java.util.Vector;

/**
 *
 * Created by gerardoayala on 3/31/16.
 */
public abstract class Search
{
    public static Node foundNode;
    public static boolean found;
    public static Result result;
    public static Vector<State> memory;
    //

    public static boolean nodeIsFinalState(Node aNode, Node goal)
    {
        boolean isFinalState;
        //
        isFinalState = false;
        if(aNode.getState().equals(goal.getState()))
        {
            System.out.println("Solution Found!");
            isFinalState = true;
        }//end if
        return isFinalState;
    }//end nodeIsFinalState
}//end class
