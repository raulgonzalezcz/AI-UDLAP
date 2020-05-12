/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.Button;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;

// Controller
public class Game implements ActionListener
{
    State state;
    GameInterface myInterface;
    static int userMovements;
    static int computerMovements;
    ////////////////////


    public Game(State aState, GameInterface anInterface)
    {
        state = aState;
        myInterface = anInterface;
        userMovements = 0;
        computerMovements = 0;
    }//end constructor


    private void updateInterface(String player, Button aButton)
    {
        if(player.equals("MIN"))
            aButton.setLabel("O");
        else
            aButton.setLabel("X");
        //end if
    }//end updateInterface


    private void updateState(String player, Button aButton)
    {
        int i,j;
        //--------
        i=0;
        while(i < 3)
        {
            j=0;
            while(j < 3)
            {
                if(aButton == myInterface.buttons[i][j])
                {
                    if(player.equals("MIN"))
                        state.grid[i][j] = 'O';
                    else
                        state.grid[i][j] = 'X';
                    //end if
                }//end if
                j = j + 1;
            }//end while
            i = i + 1;
        }//end while
    }//end updateState




    private Button agentMove(int x, int y)
    {
        Button theButton;
        //------------------------
        theButton = myInterface.buttons[x][y];
        return theButton;
    }//end agentMove



/*
    public static State minmax(State state)
    {
        LinkedList<State> candidates;
        State selectedState;
        int i;
        int j;
        Random random;
        //----------
        candidates = new LinkedList<State>();
        //-------
        // expansión del estado
        state.expansion('X');
        // expansión de cada hijo
        i = 0;
        while(i < state.children.size())
        {
            state.children.get(i).expansion('O');
            i = i + 1;
        }//end while
        // aplicación de la función de utilidad
        // a cada hijo
        i = 0;
        while(i < state.children.size())
        {
            state.children.get(i).payoffDefinition();
            i = i + 1;
        }//end while
        // seleccionamos el hijo con mayor utilidad
        Collections.sort(state.children);
        Collections.reverse(state.children);
        candidates.add(state.children.get(0));
        i = 0;
        while(i < state.children.size())
        {
            if(state.children.get(i).payoff == state.children.get(0).payoff)
                candidates.add(state.children.get(i));
            //end if
            i = i + 1;
        }//end while

        if(candidates.size()==1)
            selectedState = candidates.get(0);
        else
        {
            random = new Random();
            i = random.nextInt(candidates.size());
            selectedState = candidates.get(i);
        }//end else
        return selectedState;
    }//end minmax


*/

    private boolean canMove(char symbol)
    {
        boolean itCanMove;
        //
        itCanMove = false;
        if ((state.gameOver('X')) || (state.gameOver('O')))
            itCanMove = false;
            //end if
        else
        {
            if(symbol == 'X')
            {
                if (computerMovements < 4)
                    itCanMove = true;
                //end if
            }//end if
            else
            {
                if (userMovements < 5)
                    itCanMove = true;
                //end if
            }//end if
        }//end else
        return itCanMove;
    }//end canMove


    public void actionPerformed(ActionEvent event)
    {
        Button clickedButton;
        State estadoSeleccionado;
        ///////////////////////

        if (canMove('O'))
        {
            //User plays
            clickedButton = (Button) event.getSource();
            userMovements = userMovements + 1;
            updateState("MIN", clickedButton);
            updateInterface("MIN", clickedButton);
        }//end if
        if (canMove('X'))
        {
            // Computer plays

            estadoSeleccionado = MinMax.getNextMove(state);
            clickedButton = agentMove(estadoSeleccionado.row,estadoSeleccionado.column);
            computerMovements = computerMovements + 1;
            updateState("MAX", clickedButton);
            updateInterface("MAX", clickedButton);
        }//end if

    }//end actionPerformed

}//end class game
