/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



import java.util.LinkedList;

public class State implements Comparable
{
    char[][] grid;
    LinkedList<State> children;
    State father;
    char emptySpace = ' ';
    int payoff;
    int row;
    int column;
    //------------

    public State()
    {
        int i;
        int j;
        //--------
        grid = new char[3][3];
        i = 0;
        while(i < 3)
        {
            j = 0;
            while(j < 3)
            {
                grid[i][j]= emptySpace;
                j = j + 1;
            }//end while
            i = i + 1;
        }//end while
        children = new LinkedList<State>();
        row = -1;
        column = -1;
    }//end constructor


    public void setFather(State suPapa)
    {
        father = suPapa;
    }//end setFather


    private boolean diagonal(char symbol)
    {
        boolean isDiagonal;
        //------
        isDiagonal = false;

        if
                (
                (grid[0][0] == symbol) &&
                        (grid[0][0] == grid[1][1]) &&
                        (grid[0][0] == grid[2][2]) &&
                        (grid[0][0] != emptySpace)
                )
            isDiagonal = true;
        else
        if
                (
                (grid[0][2] == symbol) &&
                        (grid[0][2] == grid[1][1]) &&
                        (grid[0][2] == grid[2][0]) &&
                        (grid[0][2] != emptySpace)
                )
            isDiagonal = true;
        //end if
        //end else
        return isDiagonal;
    }//end diagonal



    private boolean horizontal(char symbol)
    {
        boolean isHorizontal;
        //------
        isHorizontal = false;

        if
                (
                (grid[0][0] == symbol) &&
                        (grid[0][0] == grid[0][1]) &&
                        (grid[0][0] == grid[0][2]) &&
                        (grid[0][0] != emptySpace)
                )
            isHorizontal = true;
        else
        if
                (
                (grid[1][0] == symbol) &&
                        (grid[1][0] == grid[1][1]) &&
                        (grid[1][0] == grid[1][2]) &&
                        (grid[1][0] != emptySpace)
                )
            isHorizontal = true;
            //end if
        else
        if
                (
                (grid[2][0] == symbol) &&
                        (grid[2][0] == grid[2][1]) &&
                        (grid[2][0] == grid[2][2]) &&
                        (grid[2][0] != emptySpace)
                )
            isHorizontal = true;
        //end if
        //end else
        //end else
        return isHorizontal;
    }//end horizontal



    private boolean vertical(char symbol)
    {
        boolean isVertical;
        //------
        isVertical = false;

        if
                (
                (grid[0][0] == symbol) &&
                        (grid[0][0] == grid[1][0]) &&
                        (grid[0][0] == grid[2][0]) &&
                        (grid[0][0] != emptySpace)
                )
            isVertical = true;
        else
        if
                (
                (grid[0][1] == symbol) &&
                        (grid[0][1] == grid[1][1]) &&
                        (grid[0][1] == grid[2][1]) &&
                        (grid[0][1] != emptySpace)
                )
            isVertical = true;
            //end if
        else
        if
                (
                (grid[0][2] == symbol) &&
                        (grid[0][2] == grid[1][2]) &&
                        (grid[0][2] == grid[2][2]) &&
                        (grid[0][2] != emptySpace)
                )
            isVertical = true;
        //end if
        //end else
        //end else
        return isVertical;
    }//end vertical



    private boolean gridFull()
    {
        int i, j;
        //-------------------

        i = 0;
        while (i < 3)
        {
            j = 0;
            while(j < 3)
            {
                if(grid[i][j] == emptySpace)
                    return false;
                //end if
                j = j + 1;
            }//end while
            i = i + 1;
        }//end while
        return true;
    }//end gridFull


/*
    private boolean draw(char symbol)
    {
        boolean isDraw;
        //-------------
        isDraw = false;
        if(
                !diagonal(symbol) &&
                        !horizontal(symbol) &&
                        !vertical(symbol) &&
                        gridFull()
                )
            isDraw = true;
        //end if
        return isDraw;
    }//end draw

*/

    public boolean gameOver(char symbol)
    {
        boolean isOver;
        //-------------------
        isOver = false;

        if(
                diagonal(symbol) ||
                        horizontal(symbol) ||
                        vertical(symbol)
                )
            isOver = true;
        //end if
        return isOver;
    }//end gameOver



    private boolean almostVerticalWin(char symbol)
    {
        boolean isAlmostVertical;
        int i;
        //----------
        isAlmostVertical = false;
        i = 0;
        while( ( i < 3) && !isAlmostVertical )
        {
            if
                    (
                    ((grid[0][i] == symbol) &&
                            (grid[1][i] == symbol) &&
                            (grid[2][i] == emptySpace)) ||
                            ((grid[0][i] == symbol) &&
                                    (grid[2][i] == symbol) &&
                                    (grid[1][i] == emptySpace)) ||
                            ((grid[1][i] == symbol) &&
                                    (grid[2][i] == symbol) &&
                                    (grid[0][i] == emptySpace))
                    )
                isAlmostVertical = true;
            //end if
            i = i + 1;
        }//end while
        return isAlmostVertical;
    }//end almostVertical


    private boolean almostHorizontalWin(char symbol)
    {
        boolean isAlmostHorizontal;
        int i;
        //----------

        isAlmostHorizontal = false;
        i = 0;
        while( ( i < 3) && !isAlmostHorizontal )
        {
            if
                    (
                    ((grid[i][0] == symbol) &&
                            (grid[i][1] == symbol) &&
                            (grid[i][2] == emptySpace)) ||
                            ((grid[i][0] == symbol) &&
                                    (grid[i][2] == symbol) &&
                                    (grid[i][1] == emptySpace)) ||
                            ((grid[i][1] == symbol) &&
                                    (grid[i][2] == symbol) &&
                                    (grid[i][0] == emptySpace))
                    )
                isAlmostHorizontal = true;
            //end if
            i = i + 1;
        }//end while
        return isAlmostHorizontal;
    }//end almostHorizontal



    private boolean almostRightDiagonalWin(char symbol)
    {
        boolean isAlmostRightDiagonal;
        //----------

        isAlmostRightDiagonal = false;
        if
                (
                ((grid[0][0] == symbol) &&
                        (grid[1][1] == symbol) &&
                        (grid[2][2] == emptySpace)) ||
                        ((grid[0][0] == symbol) &&
                                (grid[1][1] == emptySpace) &&
                                (grid[2][2] == symbol)) ||
                        ((grid[0][0] == emptySpace) &&
                                (grid[1][1] == symbol) &&
                                (grid[2][2] == symbol))
                )
            isAlmostRightDiagonal = true;
        //end if
        return isAlmostRightDiagonal;
    }//end almostRightDiagonalWin


    private boolean almostLeftDiagonalWin(char symbol)
    {
        boolean isAlmostLeftDiagonal;
        //----------
        isAlmostLeftDiagonal = false;
        if
                (
                ((grid[0][2] == symbol) &&
                        (grid[1][1] == symbol) &&
                        (grid[2][0] == emptySpace)) ||
                        ((grid[0][2] == symbol) &&
                                (grid[1][1] == emptySpace) &&
                                (grid[2][0] == symbol)) ||
                        ((grid[0][2] == emptySpace) &&
                                (grid[1][1] == symbol) &&
                                (grid[2][0] == symbol))
                )
            isAlmostLeftDiagonal = true;
        //end if
        return isAlmostLeftDiagonal;
    }//end almostLeftDiagonalWin


    // MAX juega las X
    public int utilityFunction()
    {
        int utility;
        //--------
        utility = 0;

        if(gameOver('X'))
            utility = utility + 900;
        if(almostVerticalWin('X'))
            utility = utility + 20;
        if(almostHorizontalWin('X'))
            utility = utility + 20;
        if(almostRightDiagonalWin('X'))
            utility = utility + 20;
        if(almostLeftDiagonalWin('X'))
            utility = utility + 20;
        // y para las 'O' (del usuario)
        if(gameOver('O'))
            utility = utility - 900;
        if(almostVerticalWin('O'))
            utility = utility - 1;
        if(almostHorizontalWin('O'))
            utility = utility - 1;
        if(almostRightDiagonalWin('O'))
            utility = utility - 1;
        if(almostLeftDiagonalWin('O'))
            utility = utility - 1;

        return utility;
    }//payoffFunction



    public void payoffDefinition()
    {
        int utilityOfAChild;
        int biggerUtilityAmongChildren;
        int i;
        //-------------
        biggerUtilityAmongChildren = -999;
        i = 0;
        while(i<children.size())
        {
            utilityOfAChild = children.get(i).utilityFunction();
            if(utilityOfAChild > biggerUtilityAmongChildren)
                biggerUtilityAmongChildren = utilityOfAChild;
            //end if
            i = i + 1;
        }//end while
        payoff = biggerUtilityAmongChildren;
    }//end payoffDefinition



    private boolean gridIsFull()
    {
        boolean isFull;
        int i, j;
        //
        isFull = true;
        i = 0;
        while (i < 3)
        {
            j = 0;
            while (j < 3)
            {
                if (grid[i][j] == emptySpace)
                {
                    isFull = false;
                }//end if
                j = j + 1;
            }//end while
            i = i + 1;
        }//end while

        return isFull;
    }//end gridIsFull


    public void expansion(char symbol)
    {
        State aChild;
        int i, j, indexOfChild;
        //-----------
        if(!gridIsFull())
        {
            i = 0;
            while (i < 3)
            {
                j = 0;
                while (j < 3)
                {
                    if (grid[i][j] == emptySpace)
                    {
                        aChild = new State();
                        aChild.setFather(this);
                        aChild.grid[i][j] = symbol;
                        if (symbol == 'X')
                        {
                            aChild.row = i;
                            aChild.column = j;
                        }//end if
                        children.add(aChild);
                    }//end if
                    j = j + 1;
                }//end while
                i = i + 1;
            }//end while


            indexOfChild = 0;
            while (indexOfChild < children.size())
            {
                //herencia
                i = 0;
                while (i < 3) {
                    j = 0;
                    while (j < 3) {
                        if (grid[i][j] != emptySpace)
                        {
                            aChild = children.get(indexOfChild);
                            aChild.grid[i][j] = grid[i][j];
                        }//end if
                        j = j + 1;
                    }//end while
                    i = i + 1;
                }//end while
                indexOfChild = indexOfChild + 1;
            }//end while
        }//end if
        payoffDefinition();
    }//end expansion


    public int compareTo(Object anObject)
    {
        int result;
        State anState;
        //
        anState = (State) anObject;
        result = 0;
        if(payoff > anState.payoff)
            result = 1;
        else
        if(payoff < anState.payoff)
            result = -1;
        //end if
        //end else
        return result;
    }//end compareTo


    public String toString()
    {
        return  "\n"+ grid[0][0] + "|" + grid[0][1] + "|" + grid[0][2] + "\n"+
                grid[1][0] + "|" + grid[1][1] + "|" + grid[1][2] + "\n"+
                grid[2][0] + "|" + grid[2][1] + "|" + grid[2][2] + "\n"+"\n";
    }//end toString

}//end State

