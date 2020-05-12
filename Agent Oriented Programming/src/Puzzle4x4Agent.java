/*
 * by Gerardo Ayala
 * Universidad de las Am?ricas Puebla
 * Copyright
 * gerardo.ayala@udlap.mx
 */

import java.util.Collections;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.Vector;

public class Puzzle4x4Agent  extends UDLAgent
{
    // The number of agent movements
    public static volatile int numberOfMovements;
    public boolean firstMilestoneAccomplished;
    public boolean secondMilestoneAccomplished;
    public boolean thirdMilestoneAccomplished;
    public boolean finalMilestoneAccomplished;
    //------------------------------

    public Puzzle4x4Agent(String anId, String aFileRoute,
                          String aFileName, String aFileSuffix)
    {
        super(anId, aFileRoute, aFileName, aFileSuffix);
        numberOfMovements = 0;
        firstMilestoneAccomplished = false;
        secondMilestoneAccomplished = false;
        thirdMilestoneAccomplished = false;
        finalMilestoneAccomplished = false;
    }//end constructor


    // Knowledge base is sorted randomly
    // This avoids repetition of movements
    // since all rules have the same priority
    public void sortKnowledgeBase()
    {
        int i;
        Random random;
        String aPredicate;
        StringTokenizer tokenizer;
        //------------
        random = new Random();
        i = 0;
        while (i < knowledgeBase.size())
        {
            aPredicate = knowledgeBase.get(i).commitments.get(0).predicate;
            tokenizer = new StringTokenizer(aPredicate,"(), ");
            if(tokenizer.nextToken().equals("secuencia"))
                knowledgeBase.get(i).priority = random.nextInt(100);
                //end if
            else
                knowledgeBase.get(i).priority = random.nextInt(10000);
            //end else
            i = i + 1;
        }//end while
        Collections.sort(knowledgeBase);
    }//end sortKnowledgeBase


    private boolean commitmentsWithPriority()
    {
        boolean hay;
        int i;
        //-----------
        hay = false;
        i = 0;
        while( i < commitments.size())
        {
            if(commitments.get(i).priority != 999)
                hay = true;
            //end if
            i = i + 1;
        }//end while
        return hay;
    }//end commitmentsWithPriority


    // include the commitments indicated in a rule
    // into the agent commitments
    public void includeCommitmentsFromRule(Rule aRule)
    {
        int j;
        int x;
        int y;
        String commitmentType;
        String direccion;
        StringTokenizer tokenizer;
        Commitment aCommitment;
        int prioridad;
        //-------------

        j = 0;
        while(j < aRule.commitments.size())
        {
            tokenizer = new StringTokenizer(aRule.commitments.get(j).predicate,"(), ");
            commitmentType = tokenizer.nextToken();
            if((commitmentType.equals("secuencia")) && (!commitmentsWithPriority()))
            {
                prioridad = 1;
                while( tokenizer.hasMoreElements())
                {
                    x = Integer.valueOf(tokenizer.nextToken());
                    y = Integer.valueOf(tokenizer.nextToken());
                    direccion = tokenizer.nextToken();
                    aCommitment  = new Commitment("mover("+ x + ","+ y+","+direccion+")");

                    aCommitment.setPriority(prioridad);
                    commitments.add(aCommitment);
                    prioridad = prioridad + 1;
                }//end while


            }//end if
            else
            {
                commitments.add(aRule.commitments.get(j));
            }//end else
            j = j + 1;
        }//end while

    }//end includeCommitmentsFromRule




    // When the first line is ready
    private boolean firstMilestoneAccomplished()
    {
        if(
                (beliefs.puzzle[0][0] == 1) &&
                        (beliefs.puzzle[0][1] == 2) &&
                        (beliefs.puzzle[0][2] == 3) &&
                        (beliefs.puzzle[0][3] == 4)
                )
            return true;
        else
            return false;
    }//end firstMilestoneAccomplished


    // When the second line is ready
    private boolean secondMilestoneAccomplished()
    {
        if( (firstMilestoneAccomplished()) &&
                (beliefs.puzzle[1][0] == 5) &&
                (beliefs.puzzle[1][1] == 6) &&
                (beliefs.puzzle[1][2] == 7) &&
                (beliefs.puzzle[1][3] == 8)
                )
            return true;
        else
            return false;
    }//end secondMilestoneAccomplished

    // When the two lines are ready and 9 and 13 in position
    private boolean thirdMilestoneAccomplished()
    {
        if( (firstMilestoneAccomplished()) &&
                (secondMilestoneAccomplished()) &&
                (beliefs.puzzle[2][0] == 9) &&
                (beliefs.puzzle[3][0] == 13)
                )
            return true;
        else
            return false;
    }//end thirdMilestoneAccomplished

    // When the two lines are ready and 9 and 13 in position
    private boolean finalMilestoneAccomplished()
    {
        if( (firstMilestoneAccomplished()) &&
                (secondMilestoneAccomplished()) &&
                (thirdMilestoneAccomplished())  &&
                (beliefs.puzzle[2][1] == 10) &&
                (beliefs.puzzle[2][2] == 11) &&
                (beliefs.puzzle[2][3] == 12) &&
                (beliefs.puzzle[3][1] == 14) &&
                (beliefs.puzzle[3][2] == 15)

                )
            return true;
        else
            return false;
    }//end finalMilestoneAccomplished


    // This is the agent STRATEGY
    // The agent determines when not to consider a commitment
    private boolean shouldIncludeCommitment(Commitment aCommitment)
    {
        int i;
        int j;
        String direction;
        StringTokenizer tokenizer;
        String commitmentType;
        boolean shouldInclude;
        int token;
        //----------------

        shouldInclude = true;
        tokenizer = new StringTokenizer(aCommitment.predicate,"(, )");
        commitmentType = tokenizer.nextToken();
        i = Integer.valueOf(tokenizer.nextToken());
        j = Integer.valueOf(tokenizer.nextToken());
        direction = tokenizer.nextToken();
        token = beliefs.puzzle[i][j];

        // Reglas de ESTRATEGIA
        // donde no se consideran compromisos que no convienen
        ///////////////////////////////////////////////////////


        // si el token 1 ya est? en posici?n, no moverlo
        if(
                (token == 1) && (beliefs.puzzle[0][0] == 1)
                )
            shouldInclude = false;
        //end if


        // si el token es 2 y ya est?n 1 y 2 en posici?n, no moverlo
        if(
                ((token == 2) && (beliefs.puzzle[0][1] == 2)) &&
                        (beliefs.puzzle[0][0] == 1)
                )
            shouldInclude = false;
        //end if

        // si la primera l?nea est? colocada,
        // no mover ninguno de sus tokens
        if(
                (firstMilestoneAccomplished()) &&
                        ((token == 1) || (token == 2) || (token == 3) || (token == 4))
                )
            shouldInclude = false;
        //end if


        // si el 5 y la primera l?nea est?n colocados,
        // no mover el 5
        if(
                (firstMilestoneAccomplished()) &&
                        (token == 5) && (beliefs.puzzle[1][0] == 5)
                )
            shouldInclude = false;
        //end if


        // el token es 6, y la primera linea, el 5 y el 6
        // ya est?n en su posici?n, no moverlo
        if(
                (firstMilestoneAccomplished()) &&
                        ((token == 6) && (beliefs.puzzle[1][1] == 6)) &&
                        (beliefs.puzzle[1][0] == 5)
                )
            shouldInclude = false;
        //end if

        // si la primera  y segunda l?neas est?n colocadas,
        // no mover ninguno de los tokens de la segunda l?nea
        if(
                (firstMilestoneAccomplished()) &&
                        (secondMilestoneAccomplished()) &&
                        ((token == 5) || (token == 6) || (token == 7) || (token == 8))
                )
            shouldInclude = false;
        //end if

        // si la primera  y segunda l?neas est?n colocadas,
        // y tambien 9 y 13, no mover los tokens de segunda linea ni 9 ni 13
        if(
                (firstMilestoneAccomplished()) &&
                        (secondMilestoneAccomplished()) &&
                        (thirdMilestoneAccomplished()) &&
                        ((token == 5) || (token == 6) || (token == 7) || (token == 8)|| (token == 9) || (token == 13))
                )
            shouldInclude = false;
        //end if

        return shouldInclude;
    }//end shouldIncludeCommitment



    // The agent determines when not to consider a commitment
    public Vector<Commitment> setCommitmentCandidates(Vector<Commitment> commitments)
    {
        int index;
        Commitment aCommitment;
        //-------------------------

        commitmentCandidates = new Vector<Commitment>();
        index = 0;
        while(index < commitments.size())
        {
            aCommitment = commitments.get(index);
            if(shouldIncludeCommitment(aCommitment))
                commitmentCandidates.add(aCommitment);
            //end if
            index = index + 1;
        }//end while
        return commitmentCandidates;
    }//end setCommitmentCandidates





    // The agent interprets a commitment
    // producing the situation that may happen
    // if that commitment is applied
    public Situation interpretCommitment(Commitment aCommitment)
    {
        int i;
        int j;
        String direction;
        Situation possibleSituation;
        StringTokenizer tokenizer;
        String commitmentType;
        int[][] possibleSituationPuzzle;

        //-------------------------

        possibleSituationPuzzle = new int[4][4];
        i=0;
        while(i < 4)
        {
            j = 0;
            while (j < 4)
            {
                possibleSituationPuzzle[i][j] = beliefs.puzzle[i][j];
                j = j + 1;
            }//end while j
            i = i + 1;
        }//end while i

        tokenizer = new StringTokenizer(aCommitment.predicate,"(, )");
        commitmentType = tokenizer.nextToken();
        i = Integer.valueOf(tokenizer.nextToken());
        j = Integer.valueOf(tokenizer.nextToken());
        direction = tokenizer.nextToken();

        if(
                (commitmentType.equals("mover")) ||
                        (commitmentType.equals("secuencia"))
                )
        {
            if(direction.equals("derecha"))
                possibleSituationPuzzle[i][j+1] = possibleSituationPuzzle[i][j];
            //end if
            if(direction.equals("izquierda"))
                possibleSituationPuzzle[i][j-1] = possibleSituationPuzzle[i][j];
            //end if
            if(direction.equals("abajo"))
                possibleSituationPuzzle[i+1][j] = possibleSituationPuzzle[i][j];
            //end if
            if(direction.equals("arriba"))
                possibleSituationPuzzle[i-1][j] = possibleSituationPuzzle[i][j];
            //end if

        }//end if

        possibleSituationPuzzle[i][j] = 0;

        possibleSituation = new Situation(possibleSituationPuzzle);
        return possibleSituation;
    }//end interpret



    // The agent shows its action
    public void showAction(Commitment theSelectedCommitment)
    {
        int i, j;
        String direccion;
        StringTokenizer tokenizer;
        String commitmentType;
        //-------------------------
        Blackboard.showCurrentSituation();
        tokenizer = new StringTokenizer(theSelectedCommitment.predicate, "(, )");
        commitmentType = tokenizer.nextToken();
        i = Integer.valueOf(tokenizer.nextToken());
        j = Integer.valueOf(tokenizer.nextToken());
        direccion = tokenizer.nextToken();

        System.out.println("++++++++ Accion de "+ id);
        if(commitmentType.equals("mover"))
        {
            System.out.println("Mueve el token " +  "(" + i + "," + j +
                    ") hacia: " + direccion);
            numberOfMovements = numberOfMovements + 1;
            System.out.println("Numero de Movimientos = " + numberOfMovements);
        }//end if
    }//end showAction



    // the agent checks if it arrived to
    // a Milestone situation
    public void checkMilestones()
    {
        if(
                (beliefs.puzzle[0][0] == 1) &&
                        (beliefs.puzzle[0][1] == 2) &&
                        (beliefs.puzzle[0][2] == 3) &&
                        (beliefs.puzzle[0][3] == 4)
                )
        {
            Blackboard.firstMilestoneAccomplished = true;
        }//end if

        if(
                (beliefs.puzzle[0][0] == 1) &&
                        (beliefs.puzzle[0][1] == 2) &&
                        (beliefs.puzzle[0][2] == 3) &&
                        (beliefs.puzzle[0][3] == 4) &&
                        (beliefs.puzzle[1][0] == 5) &&
                        (beliefs.puzzle[1][1] == 6) &&
                        (beliefs.puzzle[1][2] == 7) &&
                        (beliefs.puzzle[1][3] == 8)
                )
        {
            Blackboard.secondMilestoneAccomplished = true;
        }//end if

        if(
                (beliefs.puzzle[0][0] == 1) &&
                        (beliefs.puzzle[0][1] == 2) &&
                        (beliefs.puzzle[0][2] == 3) &&
                        (beliefs.puzzle[0][3] == 4) &&
                        (beliefs.puzzle[1][0] == 5) &&
                        (beliefs.puzzle[1][1] == 6) &&
                        (beliefs.puzzle[1][2] == 7) &&
                        (beliefs.puzzle[1][3] == 8) &&
                        (beliefs.puzzle[2][0] == 9) &&
                        (beliefs.puzzle[3][0] == 13)
                )
        {
            Blackboard.thirdMilestoneAccomplished = true;
        }//end if

        if(
                (beliefs.puzzle[0][0] == 1) &&
                        (beliefs.puzzle[0][1] == 2) &&
                        (beliefs.puzzle[0][2] == 3) &&
                        (beliefs.puzzle[0][3] == 4) &&
                        (beliefs.puzzle[1][0] == 5) &&
                        (beliefs.puzzle[1][1] == 6) &&
                        (beliefs.puzzle[1][2] == 7) &&
                        (beliefs.puzzle[1][3] == 8) &&
                        (beliefs.puzzle[2][0] == 9) &&
                        (beliefs.puzzle[2][1] == 10) &&
                        (beliefs.puzzle[2][2] == 11) &&
                        (beliefs.puzzle[2][3] == 12) &&
                        (beliefs.puzzle[3][0] == 13) &&
                        (beliefs.puzzle[3][1] == 14) &&
                        (beliefs.puzzle[3][2] == 15)
                )
        {
            Blackboard.finalMilestoneAccomplished = true;
        }//end if
    }//end checkMilestones


    // the agent determines the Id
    // of the milestone
    public String getMilestoneId()
    {
        String milestoneId;
        //---------------------
        milestoneId = "ninguna";


        if(
                (!Blackboard.firstMilestoneAccomplished) &&
                        (beliefs.puzzle[0][0] == 1) &&
                        (beliefs.puzzle[0][1] == 2) &&
                        (beliefs.puzzle[0][2] == 3) &&
                        (beliefs.puzzle[0][3] == 4)
                )
        {
            milestoneId = "primeraMilestone";
        }//end if

        if(
                (!Blackboard.secondMilestoneAccomplished) &&
                        (beliefs.puzzle[0][0] == 1) &&
                        (beliefs.puzzle[0][1] == 2) &&
                        (beliefs.puzzle[0][2] == 3) &&
                        (beliefs.puzzle[0][3] == 4) &&
                        (beliefs.puzzle[1][0] == 5) &&
                        (beliefs.puzzle[1][1] == 6) &&
                        (beliefs.puzzle[1][2] == 7) &&
                        (beliefs.puzzle[1][3] == 8)
                )
        {
            milestoneId = "segundaMilestone";
        }//end if

        if(
                (!Blackboard.thirdMilestoneAccomplished) &&
                        (beliefs.puzzle[0][0] == 1) &&
                        (beliefs.puzzle[0][1] == 2) &&
                        (beliefs.puzzle[0][2] == 3) &&
                        (beliefs.puzzle[0][3] == 4) &&
                        (beliefs.puzzle[1][0] == 5) &&
                        (beliefs.puzzle[1][1] == 6) &&
                        (beliefs.puzzle[1][2] == 7) &&
                        (beliefs.puzzle[1][3] == 8) &&
                        (beliefs.puzzle[2][0] == 9) &&
                        (beliefs.puzzle[3][0] == 13)
                )
        {
            milestoneId = "terceraMilestone";
        }//end if

        if(
                (!Blackboard.finalMilestoneAccomplished) &&
                        (beliefs.puzzle[0][0] == 1) &&
                        (beliefs.puzzle[0][1] == 2) &&
                        (beliefs.puzzle[0][2] == 3) &&
                        (beliefs.puzzle[0][3] == 4) &&
                        (beliefs.puzzle[1][0] == 5) &&
                        (beliefs.puzzle[1][1] == 6) &&
                        (beliefs.puzzle[1][2] == 7) &&
                        (beliefs.puzzle[1][3] == 8) &&
                        (beliefs.puzzle[2][0] == 9) &&
                        (beliefs.puzzle[2][1] == 10) &&
                        (beliefs.puzzle[2][2] == 11) &&
                        (beliefs.puzzle[2][3] == 12) &&
                        (beliefs.puzzle[3][0] == 13) &&
                        (beliefs.puzzle[3][1] == 14) &&
                        (beliefs.puzzle[3][2] == 15)
                )
        {
            milestoneId = "finalMilestone";
        }//end if

        return milestoneId;
    }//end getMilestoneId


    // determine if there is a condition
    // for the new rule
    // that corresponds to a milestone
    public String newRuleFirstCondition(Situation threeSituationsAgo)
    {
        String firstPredicate;
        //---------------
        firstPredicate = "ninguno";

        if(
                (threeSituationsAgo.puzzle[0][0] == 1) &&
                        (threeSituationsAgo.puzzle[0][1] == 2) &&
                        (threeSituationsAgo.puzzle[0][2] == 3) &&
                        (threeSituationsAgo.puzzle[0][3] == 4) &&
                        (threeSituationsAgo.puzzle[1][0] == 5) &&
                        (threeSituationsAgo.puzzle[1][1] == 6) &&
                        (threeSituationsAgo.puzzle[1][2] == 7) &&
                        (threeSituationsAgo.puzzle[1][3] == 8) &&
                        (threeSituationsAgo.puzzle[2][0] == 9) &&
                        (threeSituationsAgo.puzzle[3][0] == 13)
                )
        {
            firstPredicate = "dosLineasYColumna";
        }//end if
        else
        {
            if(
                    (threeSituationsAgo.puzzle[0][0] == 1) &&
                            (threeSituationsAgo.puzzle[0][1] == 2) &&
                            (threeSituationsAgo.puzzle[0][2] == 3) &&
                            (threeSituationsAgo.puzzle[0][3] == 4) &&
                            (threeSituationsAgo.puzzle[1][0] == 5) &&
                            (threeSituationsAgo.puzzle[1][1] == 6) &&
                            (threeSituationsAgo.puzzle[1][2] == 7) &&
                            (threeSituationsAgo.puzzle[1][3] == 8)
                    )
            {
                firstPredicate = "dosLineasListas";
            }//end if
            else
            {
                if(
                        (threeSituationsAgo.puzzle[0][0] == 1) &&
                                (threeSituationsAgo.puzzle[0][1] == 2) &&
                                (threeSituationsAgo.puzzle[0][2] == 3) &&
                                (threeSituationsAgo.puzzle[0][3] == 4)
                        )
                {
                    firstPredicate = "primeraLineaLista";
                }//end if
            }//end else
        }//end else

        return firstPredicate;
    }//end newRuleFirstCondition



    private int getXOfAToken(Situation aSituation, int aToken)
    {
        int x;
        int i;
        int j;
        //
        x = -1;
        i = 0;
        j = 0;
        while (i < 4)
        {
            j = 0;
            while (j < 4)
            {
                if(aSituation.puzzle[i][j] == aToken)
                {
                    x = i;
                }//end if
                j = j + 1;
            }//end while
            i = i + 1;
        }//end while
        return x;
    }//end getXOfAToken

    private int getYOfAToken(Situation aSituation, int aToken)
    {
        int y;
        int i;
        int j;
        //
        y = -1;
        i = 0;
        j = 0;
        while (i < 4)
        {
            j = 0;
            while (j < 4)
            {
                if(aSituation.puzzle[i][j] == aToken)
                {
                    y = j;
                }//end if
                j = j + 1;
            }//end while
            i = i + 1;
        }//end while
        return y;
    }//end getYOfAToken


    // The agent learns.
    // constructs a new rule when a milestone is accomplished
    // MACRO-OPERATIONS
    public Vector<Rule> learn(Vector<Rule> knowledgebase, Commitment selectedCommitment)
    {
        Situation situation3movementsAgo;
        Commitment firstCommitment;
        Commitment secondCommitment;
        Commitment lastCommitment;
        Commitment newCommitment;
        Condition newCondition;
        String predicate;
        Vector<Condition> conditions;
        Vector<Commitment> commitments;
        StringTokenizer tokenizer;
        String predicateHead;
        int i,j;
        int x, y;
        int x1,x2,x3;
        int y1,y2, y3;
        String direction1, direction2, direction3;
        Rule newRule;
        boolean found;
        boolean ruleAlreadyExists;
        String milestoneID;
        //-------------------------------

        if(Blackboard.history.size()>3)
        {
            situation3movementsAgo = Blackboard.history.get(Blackboard.history.size()-4);
            milestoneID = getMilestoneId();
            if(!milestoneID.equals("ninguna"))
            {
                firstCommitment = Blackboard.history.get(Blackboard.history.size()-3).cause;
                secondCommitment = Blackboard.history.get(Blackboard.history.size()-2).cause;
                lastCommitment = selectedCommitment;
                conditions = new Vector<Condition>();

                // checa si hay que poner un predicado primero
                // referente a una milestone
                predicate = newRuleFirstCondition(situation3movementsAgo);

                if(!predicate.equals("ninguno"))
                {
                    // condicion de una milestone
                    newCondition = new Condition(predicate);
                    conditions.add(newCondition);
                }//end if

                if(predicate.equals("ninguno"))
                {
                    i= 0;
                    while(i < 5)
                    {
                        x=getXOfAToken(situation3movementsAgo, i);
                        y=getYOfAToken(situation3movementsAgo, i);
                        newCondition = new Condition("posicion("+ x + ","+ y +"," + i + ")");
                        conditions.add(newCondition);
                        i = i + 1;
                    }//end while
                }//end if
                else
                {
                    if(predicate.equals("primeraLineaLista"))
                    {
                        x=getXOfAToken(situation3movementsAgo, 0);
                        y=getYOfAToken(situation3movementsAgo, 0);
                        newCondition = new Condition("posicion("+ x + ","+ y +"," + 0 + ")");
                        conditions.add(newCondition);
                        i= 5;
                        while(i < 9)
                        {
                            x=getXOfAToken(situation3movementsAgo, i);
                            y=getYOfAToken(situation3movementsAgo, i);
                            newCondition = new Condition("posicion("+ x + ","+ y +"," + i + ")");
                            conditions.add(newCondition);
                            i = i + 1;
                        }//end while
                    }//end if
                    else
                    {
                        if(predicate.equals("dosLineasListas"))
                        {
                            x=getXOfAToken(situation3movementsAgo, 0);
                            y=getYOfAToken(situation3movementsAgo, 0);
                            newCondition = new Condition("posicion("+ x + ","+ y +"," + 0 + ")");
                            conditions.add(newCondition);

                            x=getXOfAToken(situation3movementsAgo, 9);
                            y=getYOfAToken(situation3movementsAgo, 9);
                            newCondition = new Condition("posicion("+ x + ","+ y +"," + 9 + ")");
                            conditions.add(newCondition);

                            x=getXOfAToken(situation3movementsAgo, 13);
                            y=getYOfAToken(situation3movementsAgo, 13);
                            newCondition = new Condition("posicion("+ x + ","+ y +"," + 13 + ")");
                            conditions.add(newCondition);

                        }//end if
                        else
                        {
                            if(predicate.equals("dosLineasYColumna"))
                            {
                                x=getXOfAToken(situation3movementsAgo, 0);
                                y=getYOfAToken(situation3movementsAgo, 0);
                                newCondition = new Condition("posicion("+ x + ","+ y +"," + 0 + ")");
                                conditions.add(newCondition);
                                x=getXOfAToken(situation3movementsAgo, 10);
                                y=getYOfAToken(situation3movementsAgo, 10);
                                newCondition = new Condition("posicion("+ x + ","+ y +"," + 10 + ")");
                                conditions.add(newCondition);
                                x=getXOfAToken(situation3movementsAgo, 11);
                                y=getYOfAToken(situation3movementsAgo, 11);
                                newCondition = new Condition("posicion("+ x + ","+ y +"," + 11 + ")");
                                conditions.add(newCondition);
                                x=getXOfAToken(situation3movementsAgo, 12);
                                y=getYOfAToken(situation3movementsAgo, 12);
                                newCondition = new Condition("posicion("+ x + ","+ y +"," + 12 + ")");
                                conditions.add(newCondition);
                                x=getXOfAToken(situation3movementsAgo, 14);
                                y=getYOfAToken(situation3movementsAgo, 14);
                                newCondition = new Condition("posicion("+ x + ","+ y +"," + 14 + ")");
                                conditions.add(newCondition);
                                x=getXOfAToken(situation3movementsAgo, 15);
                                y=getYOfAToken(situation3movementsAgo, 15);
                                newCondition = new Condition("posicion("+ x + ","+ y +"," + 15 + ")");
                                conditions.add(newCondition);


                            }//end if
                        }//end else
                    }//end else
                }//end else


                // now constructs the rule conclusion
                // as a sequence of moves
                predicate = firstCommitment.predicate;
                tokenizer = new StringTokenizer(predicate,"(, )");
                predicateHead = tokenizer.nextToken();
                x1 = Integer.valueOf(tokenizer.nextToken());
                y1 = Integer.valueOf(tokenizer.nextToken());
                direction1 = tokenizer.nextToken();

                predicate = secondCommitment.predicate;
                tokenizer = new StringTokenizer(predicate,"(, )");
                predicateHead = tokenizer.nextToken();
                x2 = Integer.valueOf(tokenizer.nextToken());
                y2 = Integer.valueOf(tokenizer.nextToken());
                direction2 = tokenizer.nextToken();

                predicate = lastCommitment.predicate;
                tokenizer = new StringTokenizer(predicate,"(, )");
                predicateHead = tokenizer.nextToken();
                x3 = Integer.valueOf(tokenizer.nextToken());
                y3 = Integer.valueOf(tokenizer.nextToken());
                direction3 = tokenizer.nextToken();

                predicate = "secuencia("+
                        x1 + "," + y1 + "," + direction1 + ","+
                        x2 + "," + y2 + "," + direction2 + ","+
                        x3 + "," + y3 + "," + direction3 + ")";
                newCommitment = new Commitment(predicate);
                commitments = new Vector<Commitment>();
                commitments.add(newCommitment);

                // includes the new rule into the knowledge base
                newRule = new Rule(knowledgeBase.size(), conditions, commitments);
                ruleAlreadyExists = false;
                i = 0;
                while(i < knowledgeBase.size())
                {
                    if(knowledgeBase.get(i).equals(newRule))
                    {
                        ruleAlreadyExists = true;
                        break;
                    }//end if
                    i = i + 1;
                }//end while
                if(!ruleAlreadyExists)
                {
                    knowledgeBase.add(newRule);
                }//end if
            }//end if
        }//end if
        return knowledgeBase;
    }//end learn



}//end class Puzzle4x4Agent
