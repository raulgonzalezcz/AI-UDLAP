/*
 * by Gerardo Ayala
 * Universidad de las Americas Puebla
 * Copyright
 * gerardo.ayala@udlap.mx
 */

import java.util.Vector;

public class Main
{
    public static void main(String[] args)
    {
        Puzzle4x4Agent john;
        //
        int [][] puzzleInicial;
        int [][] puzzleFinal;
        Situation initialSituation;
        Situation goalSituation;
        //----------------

        //initialization of the Blackboard
        puzzleInicial = new int[4][4];


    /*
        // 80 movimientos
        //kociemba.org/fifteen/fifteensolver.html
        puzzleInicial[0][0] = 0;
        puzzleInicial[0][1] = 11;
        puzzleInicial[0][2] = 9;
        puzzleInicial[0][3] = 13;
        puzzleInicial[1][0] = 12;
        puzzleInicial[1][1] = 15;
        puzzleInicial[1][2] = 10;
        puzzleInicial[1][3] = 14;
        puzzleInicial[2][0] = 3;
        puzzleInicial[2][1] = 7;
        puzzleInicial[2][2] = 6;
        puzzleInicial[2][3] = 2;
        puzzleInicial[3][0] = 4;
        puzzleInicial[3][1] = 8;
        puzzleInicial[3][2] = 5;
        puzzleInicial[3][3] = 1;
*/

        puzzleInicial[0][0] = 15;
        puzzleInicial[0][1] = 14;
        puzzleInicial[0][2] = 13;
        puzzleInicial[0][3] = 12;
        puzzleInicial[1][0] = 11;
        puzzleInicial[1][1] = 10;
        puzzleInicial[1][2] = 9;
        puzzleInicial[1][3] = 8;
        puzzleInicial[2][0] = 7;
        puzzleInicial[2][1] = 6;
        puzzleInicial[2][2] = 5;
        puzzleInicial[2][3] = 4;
        puzzleInicial[3][0] = 3;
        puzzleInicial[3][1] = 1;
        puzzleInicial[3][2] = 2;
        puzzleInicial[3][3] = 0;

        puzzleFinal = new int[4][4];



        puzzleFinal[0][0] = 1;
        puzzleFinal[0][1] = 2;
        puzzleFinal[0][2] = 3;
        puzzleFinal[0][3] = 4;
        puzzleFinal[1][0] = 5;
        puzzleFinal[1][1] = 6;
        puzzleFinal[1][2] = 7;
        puzzleFinal[1][3] = 8;
        puzzleFinal[2][0] = 9;
        puzzleFinal[2][1] = 10;
        puzzleFinal[2][2] = 11;
        puzzleFinal[2][3] = 12;
        puzzleFinal[3][0] = 13;
        puzzleFinal[3][1] = 14;
        puzzleFinal[3][2] = 15;
        puzzleFinal[3][3] = 0;

        initialSituation = new Situation(puzzleInicial);
        goalSituation = new Situation(puzzleFinal);
        Blackboard.init(initialSituation, goalSituation);
        System.out.println("Estado actual:");
        System.out.println(Blackboard.currentSituation);
        System.out.println("Estado Final:");
        System.out.println(Blackboard.finalState);
        System.out.println("---------------------------------------");

        john = new Puzzle4x4Agent("John",
                "C:\\Users\\Raúl\\Desktop\\Artificial Intelligence\\Agent Oriented Programming",
                "ruleFile",
                "txt");
        john.start();
    }//end main
}//end class ProjectAgentJava

