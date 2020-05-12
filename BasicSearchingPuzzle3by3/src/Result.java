import java.util.Vector;

/**
 * Created by gerardoayala on 2/18/16.
 */
public class Result
{
    private boolean found;
    private Vector<Node> plan;
    //

    public Result()
    {
        found = false;
        plan = null;
    }//end constructor


    public void setFound(boolean hasBeenFound)
    {
        found = hasBeenFound;
    }//end setFound


    public void setPlan(Vector<Node> aPlan)
    {
        plan = aPlan;
    }//end setPlan


    public Vector<Node> getPlan()
    {
        return plan;
    }//end getPlan


}//end class Result
