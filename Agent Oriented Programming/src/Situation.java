/*
 * by Gerardo Ayala
 * Universidad de las Am?ricas Puebla
 * Copyright
 * gerardo.ayala@udlap.mx
 */

import java.util.Vector;
import java.util.StringTokenizer;

// To be defined for each specific problem
// Situations need to be compared
// since there is a heuristic function 
// for the implementation of heuristic search
public class Situation implements Comparable
{
    // The 4 x 4 puzzle
    int[][] puzzle;
    // The commitment that caused this situation
    // when it was applied
    Commitment cause;

    //--------------------------


    public Situation(int[][] aPuzzle)
    {
        puzzle = new int[4][4];
        puzzle = aPuzzle;
        cause = null;
    }//end constructor

    // When 2 situations are the same
    // only considering the puzzle tokens
    public boolean matches(Situation aSituation)
    {
        int i;
        int j;
        boolean itMatches;
        //---------
        itMatches = true;
        i = 0;
        while (i < 4)
        {
            j = 0;
            while (j < 4)
            {
                if (aSituation.puzzle[i][j] != puzzle[i][j])
                    itMatches = false;
                //end if
                j = j + 1;
            }//end j
            i = i + 1;
        }//end i
        return itMatches;
    }//end matches


    private boolean conditionMatches(Condition aCondition)
    {
        boolean itMatches;
        StringTokenizer tokenizer;
        String conditionType;
        int i, j;
        int token;
        //--------------
        itMatches = false;
        tokenizer = new StringTokenizer(aCondition.conditionPredicate,"(, )");
        conditionType = tokenizer.nextToken();

        if(conditionType.equals("posicion"))
        {
            i = Integer.valueOf(tokenizer.nextToken());
            j = Integer.valueOf(tokenizer.nextToken());
            token = Integer.valueOf(tokenizer.nextToken());
            if(puzzle[i][j] == token)
                itMatches = true;
            //end if
        }//end if
        else
        if(conditionType.equals("primeraLineaLista"))
        {
            if(
                    (puzzle[0][0] == 1) &&
                            (puzzle[0][1] == 2) &&
                            (puzzle[0][2] == 3) &&
                            (puzzle[0][3] == 4)
                    )
                itMatches = true;
            //end if
        }//end if
        else
        if(conditionType.equals("dosLineasListas"))
        {
            if(
                    (puzzle[0][0] == 1) &&
                            (puzzle[0][1] == 2) &&
                            (puzzle[0][2] == 3) &&
                            (puzzle[0][3] == 4) &&
                            (puzzle[1][0] == 5) &&
                            (puzzle[1][1] == 6) &&
                            (puzzle[1][2] == 7) &&
                            (puzzle[1][3] == 8)
                    )
                itMatches = true;
            //end if
        }//end if
        else
        if(conditionType.equals("dosLineasYColumna"))
        {
            if(
                    (puzzle[0][0] == 1) &&
                            (puzzle[0][1] == 2) &&
                            (puzzle[0][2] == 3) &&
                            (puzzle[0][3] == 4) &&
                            (puzzle[1][0] == 5) &&
                            (puzzle[1][1] == 6) &&
                            (puzzle[1][2] == 7) &&
                            (puzzle[1][3] == 8) &&
                            (puzzle[2][0] == 9) &&
                            (puzzle[3][0] == 13)
                    )
                itMatches = true;
            //end if
        }//end if
        else
            System.out.println("Condition predicate "+
                    conditionType +
                    " is not valid.");
        //end else
        //end else
        return itMatches;
    }//end conditionMatches



    // When a Condition matches this Situation
    // only considering the blank position of the puzzle
    public boolean matchesCondition(Vector<Condition> aConditionList)
    {
        int i;
        boolean conditionListMatchesSituation;
        //---------
        conditionListMatchesSituation = true;

        i = 0;
        while(i < aConditionList.size())
        {
            if(!conditionMatches(aConditionList.get(i)))
                conditionListMatchesSituation = false;
            //end if
            i = i + 1;
        }//end while
        return conditionListMatchesSituation;
    }//end matches





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
        while(i < 4)
        {
            j = 0;
            while(j < 4)
            {
                token = puzzle[i][j];
                if (token != 0)
                { // we don't compute distance for element 0
                    targetX = (token - 1) / 4; // expected x-coordinate (row)
                    targetY = (token - 1) % 4; // expected y-coordinate (col)
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

        if(puzzle[0][0]==1 && puzzle[0][1]==2 &&
                puzzle[0][2]==3 && puzzle[0][3]==4)
        {
            manhattanDistance = manhattanDistance - 15;
        }//end if

        if(puzzle[0][0]==1 && puzzle[0][1]==2 &&
                puzzle[0][2]==3 && puzzle[0][3]==4 &&
                puzzle[1][0]==5 && puzzle[1][1]==6 &&
                puzzle[1][2]==7 && puzzle[1][3]==8)
        {
            manhattanDistance = manhattanDistance - 15;
        }//end if



        return manhattanDistance;

    }//end distanciaManhattan


    public double funcionHeuristica()
    {
        double valor;
        //------------

        valor = manhattanDistance();
        return valor;
    }//end funcionHeuristica




    // A Situation is better if its Manhattan distance is shorter
    public int compareTo(Object anObject)
    {
        Situation otherSituation;
        //----------------------

        otherSituation = (Situation) anObject;

        if(this.funcionHeuristica() > otherSituation.funcionHeuristica())
            return 1;
        else
            return -1;
        //end if else
    }//end compareTo


    private String makeTokenToDisplay(int numeroDeToken)
    {
        String token;
        //////////////
        token = null;
        if (numeroDeToken > 9)
            token = Integer.toString(numeroDeToken);
        //end if
        if (numeroDeToken < 10)
            token = Integer.toString(numeroDeToken) + " ";
        //end if
        if (numeroDeToken == 0)
            token = "  ";
        //end if
        return token;
    }//end makeTokenToDisplay



    // String representation of the Situation
    public String toString()
    {
        int i, j;
        String representacion;
        //////////
        representacion = "\n";
        i = 0;
        while (i < 4)
        {
            j = 0;
            while (j < 4)
            {
                representacion = representacion + makeTokenToDisplay(puzzle[i][j]) +  "  |  ";
                j = j + 1;
            }//end while
            representacion = representacion + "\n";
            i = i + 1;
        }//end while
        representacion = representacion + "Distancia Manhattan = " + manhattanDistance() + "\n";

        return representacion;


    }//end toString

}//end class Situation
