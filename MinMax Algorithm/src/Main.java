/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 * Created by gerardoayala on 4/6/16.
 */

public class Main
{
    public static void main(String[] any)
    {
        State initialState;
        GameInterface myInterface;
        Game controller;
        //---------------------

        initialState = new State();
        myInterface = new GameInterface();
        controller = new Game(initialState, myInterface);
        myInterface.setActionListener(controller);
        myInterface.startGame();

    }//end main

}//end main class
