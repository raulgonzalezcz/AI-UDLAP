import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;
import java.util.Vector;

/**
 * Created by 2924 on 28/09/2016.
 */
public class Node implements Comparable
{
    static Random random = new Random(System.currentTimeMillis());
    static int numberOfExpantions = 0;
    //
    private Node father;
    private Vector<Node> children;
    private State state;
    double heuristicValue;
    //

    public Node()
    {
        children = null;
        father = null;
        state = null;
        heuristicValue = -1;
    }//end constructor


    public void setState(State aState)
    {
        state = aState;
        heuristicValue = state.heuristicFunction();
    }//end setState


    public State getState()
    {
        return state;
    }//end getState


    public void setFather(Node aNode)
    {
        father = aNode;
    }//end setFather

    public Node getFather()
    {
        return father;
    }//end getFather


    public Vector<Node> getChildren()
    {
        return children;
    }//end getChildren



    public Vector<Node> getRoute()
    {
        Vector<Node> route;
        Node node;
        //-----------------
        route = new Vector<Node>();
        route.add(this);
        node = getFather();
        while(node != null)
        {
            route.add(node);
            node = node.getFather();
        }//end while
        Collections.reverse(route);
        return route;
    }//end getRoute


    public void deliberation()
    {
        //
        Node aNode;
        Node anotherNode;
        int randomNumber;
        //
        if(children.size() > 0 )
        {
            Collections.sort(children);
            if (children.get(0).heuristicValue ==
                    (children.get(1).heuristicValue))
            {
                randomNumber = random.nextInt(100);
                if (randomNumber > 50)
                {
                    aNode = children.get(0);
                    anotherNode = children.get(1);
                    children.setElementAt(aNode,1);
                    children.setElementAt(anotherNode,0);
                }//end if
            }//end if
        }//end if
    }//end deliberation



    public void expand()
    {
        int i;
        //--------------------------
        numberOfExpantions = numberOfExpantions + 1;
        children = Knowledge3x3.getNextPossibleNodes(state);
        if (children != null)
        {
            i = 0;
            while (i < children.size())
            {
                children.get(i).setFather(this);
                i = i + 1;
            }//end while
        }//end while
    }//end expand


    public int compareTo(Object anObject)
    {
        Node aNode;
        //
        aNode = (Node) anObject;
        if(heuristicValue < aNode.heuristicValue)
            return -1;
        else
            if(heuristicValue > aNode.heuristicValue)
                return 1;
            else
                return 0;
            //end if-else
        //end if-else
    }//end compareTo



    public String toString()
    {
        return state.toString();
    }//end toString

}//end class Node

