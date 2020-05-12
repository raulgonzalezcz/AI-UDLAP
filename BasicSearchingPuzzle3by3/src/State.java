/**
 * Created by 2924 on 26/09/2016.
 */
public class State
{
    int matrix[][];
    //

    public State()
    {
        matrix = new int[3][3];
    }//end constructor




    public void setMatrix(int[][] aMatrix)
    {
        matrix = aMatrix;
    }//end setMatrix


    @Override
    public boolean equals(Object anObject)
    {
        int i, j;
        boolean areEqual;
        State otherState;
        //
        otherState = (State) anObject;
        areEqual = true;
        i = 0;
        while (i < 3)
        {
            j = 0;
            while(j < 3)
            {
                if (matrix[i][j] != otherState.matrix[i][j])
                    areEqual = false;
                j = j + 1;
            }//end while
            i = i + 1;
        }//end while
        return areEqual;
    }//end equals


    public String toString()
    {
        String string;
        //
        string =  "\n";
        string = string + matrix[0][0] + "  ";
        string = string + matrix[0][1] + "  ";
        string = string + matrix[0][2] + "\n";
        string = string + matrix[1][0] + "  ";
        string = string + matrix[1][1] + "  ";
        string = string + matrix[1][2] + "\n";
        string = string + matrix[2][0] + "  ";
        string = string + matrix[2][1] + "  ";
        string = string + matrix[2][2] + "\n" + "\n";
        return string;
    }//end toString




    // HEURISTIC FUNCTION
    // FOR IMPLEMENTING A HEURISTIC SEARCH:
    // Manhattan Distance
    private double manhattanDistance()
    {
        int i, j;
        double manhattanDistance;
        int token;
        int targetX;
        int targetY;
        int differenceX;
        int differenceY;
        //------------------
        manhattanDistance = 0;
        i=0;
        while(i < 3)
        {
            j = 0;
            while(j < 3)
            {
                token = matrix[i][j];
                if (token != 0)
                { // we don't compute distance for element 0
                    targetX = (token - 1) / 3; // expected x-coordinate (row)
                    targetY = (token - 1) % 3; // expected y-coordinate (col)
                    differenceX = i - targetX; // x-distance to expected coordinate
                    differenceY = j - targetY; // y-distance to expected coordinate
                    manhattanDistance  = manhattanDistance +
                            Math.abs(differenceX) +
                            Math.abs(differenceY);
                }//end if
                j = j + 1;
            }//end while j
            i = i + 1;
        }//end while i
        return manhattanDistance;
    }//end distanciaManhattan



    public double heuristicFunction()
    {
        double valor;
        //------------
        valor = manhattanDistance();

        return valor;
    }//end heuristicFunction



}//end class Estado
