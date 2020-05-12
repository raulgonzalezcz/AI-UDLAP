import java.util.Vector;

/**
 * Created by 2924 on 28/09/2016.
 */
public abstract class Knowledge3x3
{
    public static Vector<String> getCommitments(State state)
    {
        Vector<String> candidates;
        //
        candidates = new Vector<String>();
        if (state.matrix[0][0] == 0)
            candidates.add("left");
        //end if

        if (state.matrix[0][0] == 0)
            candidates.add("up");
        //end if

        if (state.matrix[0][1] == 0)
            candidates.add("left");
        //end if

        if (state.matrix[0][1] == 0)
            candidates.add("right");
        //end if

        if (state.matrix[0][1] == 0)
            candidates.add("up");
        //end if

        if (state.matrix[0][2] == 0)
            candidates.add("right");
        //end if

        if (state.matrix[0][2] == 0)
            candidates.add("up");
        //end if

        if (state.matrix[1][0] == 0)
            candidates.add("down");
        //end if

        if (state.matrix[1][0] == 0)
            candidates.add("up");
        //end if

        if (state.matrix[1][0] == 0)
            candidates.add("left");
        //end if

        if (state.matrix[1][1] == 0)
            candidates.add("down");
        //end if

        if (state.matrix[1][1] == 0)
            candidates.add("right");
        //end if

        if (state.matrix[1][1] == 0)
            candidates.add("left");
        //end if

        if (state.matrix[1][1] == 0)
            candidates.add("up");
        //end if

        if (state.matrix[1][2] == 0)
            candidates.add("down");
        //end if

        if (state.matrix[1][2] == 0)
            candidates.add("right");
        //end if

        if (state.matrix[1][2] == 0)
            candidates.add("up");
        //end if

        if (state.matrix[2][0] == 0)
            candidates.add("down");
        //end if

        if (state.matrix[2][0] == 0)
            candidates.add("left");
        //end if

        if (state.matrix[2][1] == 0)
            candidates.add("down");
        //end if

        if (state.matrix[2][1] == 0)
            candidates.add("right");
        //end if

        if (state.matrix[2][1] == 0)
            candidates.add("left");
        //end if

        if (state.matrix[2][2] == 0)
            candidates.add("down");
        //end if

        if (state.matrix[2][2] == 0)
            candidates.add("right");
        //end if

        return candidates;
    }//end getCommitments


    /*
    Create the node and we find the position in the matrix for the 0. Then we move it up, down... 
    */
    private static Node createNode(State unstate,
                                   String unCommitment)
    {
        Node newNode;
        State newState;
        int rowWithZero;
        int columnWithZero;
        int i, j;
        int[][] newMatrix;
        //
        newMatrix = new int[3][3];
        rowWithZero = -1;
        columnWithZero = -1;
        i=0;
        while(i<3)
        {
            j=0;
            while(j < 3)
            {
                newMatrix[i][j]=unstate.matrix[i][j];
                if(unstate.matrix[i][j] == 0)
                {
                    rowWithZero = i;
                    columnWithZero = j;
                }//end if
                j = j + 1;
            }//end while
            i = i + 1;
        }//end while

        /// INTERPRETACION DE COMPROMISOS
        if(unCommitment.equals("up"))
        {
            newMatrix[rowWithZero][columnWithZero]=
                    unstate.matrix[rowWithZero+1][columnWithZero];
            newMatrix[rowWithZero+1][columnWithZero]= 0;
        }//end if
        if(unCommitment.equals("down"))
        {
            newMatrix[rowWithZero][columnWithZero]=
                    unstate.matrix[rowWithZero-1][columnWithZero];
            newMatrix[rowWithZero-1][columnWithZero]= 0;
        }//end if
        if(unCommitment.equals("right"))
        {
            newMatrix[rowWithZero][columnWithZero]=
                    unstate.matrix[rowWithZero][columnWithZero-1];
            newMatrix[rowWithZero][columnWithZero-1]= 0;
        }//end if
        if(unCommitment.equals("left"))
        {
            newMatrix[rowWithZero][columnWithZero]=
                    unstate.matrix[rowWithZero][columnWithZero+1];
            newMatrix[rowWithZero][columnWithZero+1]= 0;
        }//end if

        newNode = new Node();
        newState = new State();
        newState.setMatrix(newMatrix);
        newNode.setState(newState);
        return newNode;
    }//end createNode


/*
    Expansion of the node
    */

    public static Vector<Node> getNextPossibleNodes(State state)
    {
        Vector<Node> possibleNodes;
        Node node;
        Vector<String> commitments;
        String aCommitment;
        int i;
        //
        possibleNodes = new Vector<Node>();
        commitments = getCommitments(state);
        i=0;
        while(i < commitments.size())
        {
            aCommitment = commitments.get(i);
            node = createNode(state,aCommitment);
            possibleNodes.add(node);
            i = i + 1;
        }//end while

        return possibleNodes;
    }//end getNextPossibleNodes

}//end class
