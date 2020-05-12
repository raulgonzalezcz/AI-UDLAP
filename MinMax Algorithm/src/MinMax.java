/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;

/**
 * Created by gerardoayala on 4/11/16.
 */
public class MinMax
{
    //

    public static LinkedList<State> minmaxFuction(State state)
    {
        int i;
        int j;
        //----------
        // expansion del estado
        state.expansion('X');
        // expansion de cada hijo
        i = 0;
        while(i < state.children.size())
        {
            state.children.get(i).expansion('O');
            j = 0;
            while(j < state.children.get(i).children.size())
            {
                state.children.get(i).children.get(j).expansion('X');
                j = j + 1;
            }//end while
            i = i + 1;
        }//end while
        return state.children;
    }//end minmaxFunction


    public static State getNextMove(State state)
    {
        LinkedList<State> candidates;
        LinkedList<State> children;
        State selectedState;
        int i;
        Random random;
        //----------
        candidates = new LinkedList<State>();
        children = minmaxFuction(state);

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
        i = 0;
        if(candidates.size()==1)
            selectedState = candidates.get(0);
        else
        {
            random = new Random();
            i = random.nextInt(candidates.size());
            selectedState = candidates.get(i);
        }//end else
        return selectedState;
    }//end getNextMove
}//end class Minmax
