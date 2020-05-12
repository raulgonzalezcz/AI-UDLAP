/*
 * by Gerardo Ayala
 * Universidad de las Am?ricas Puebla
 * Copyright
 * gerardo.ayala@udlap.mx
 */

import java.util.Vector;
import java.util.Collections;
import java.util.Random;

/**
 *
 * @author Gerardo Ayala
 * @version January 2015
 */
// The agent is autonomous
// so it has its own thread process
public abstract class UDLAgent extends Thread
{
    // The unique id of the agent
    public String id;

    // The location, name and suffix of the knowledge base file
    protected String ruleFileRoute;
    protected String ruleFileName;
    protected String ruleFileSuffix;

    // The agent has a set of rules representing its basic behavior
    protected Vector<Rule> knowledgeBase;

    // The agent communicates via messages
    private Vector<Message> informationMessages;
    private Vector<Message> requestMessages;

    // the agent will establish its commitments
    // based on what it believes about its world
    protected Situation beliefs;

    // The agent deliberates by determining
    // what would be a new possible situation
    // in case it applies a given commitment
    private Situation newPossibleSituation;

    // Once the agent decides what to do,
    // the new situation will be established
    private Situation newSituation;

    // The agent removes from its commitments those that
    // are not considered appropriate
    // based on what it believes about the current situation
    // That is why the agent defines a set of candidate commitments
    protected Vector<Commitment> commitmentCandidates;

    // The agent is autonomous (proactive)
    // since it decides what to do
    // establishing its own commitments
    // based on its basic behavior
    protected Vector<Commitment> commitments;

    // The agent selects one commitment from the candidateCommitments
    protected Commitment selectedCommitment;

    // The agent has pending commitments
    protected Vector<Commitment> pendingCommitments;

    // Flag that indicates that somebody asks the agent to stop
    protected boolean agentHasToStop;

    // Flag that indicates that the final states has been reached
    private boolean agentHasFinished;

    // The best game
    private Vector<Situation> bestGame;

    ////////////////////////////////////////////////////////////
    // Methods

    protected UDLAgent(String anId, String aRuleFileRoute,
                       String aRuleFileName, String aRuleFileSuffix)
    {
        requestMessages = new Vector<Message>();
        agentHasToStop = false;
        agentHasFinished = false;
        id = anId;
        ruleFileRoute = aRuleFileRoute;
        ruleFileName = aRuleFileName;
        ruleFileSuffix = aRuleFileSuffix;
        pendingCommitments = new Vector<Commitment>();
    }//end constructor UDLAgent




    // The agent provides its id
    public String getAgentId()
    {
        return id;
    }//end getId


    /////////////////////////////////////////////////////////
    // abstract methods declaration
    /////////////////////////////////////////////////////////


    // All UDLAgents must interpret their commitments
    // producing a situation as a result of applying one commitment
    // to the current situation.
    abstract protected Situation interpretCommitment(Commitment aCommitment);

    // All UDLAgents must show their actions
    // when they apply a given commitment
    abstract protected void showAction(Commitment theSelectedCommitment);

    // All UDLAgents must define the set of candidate commitments
    // from the set of all commitments.
    abstract protected Vector<Commitment> setCommitmentCandidates(Vector<Commitment> commitments);

    // All UDLAgents must sort the rules of its knowledge base.
    // When learning is not required,
    // this is required in order to provide same priority to all
    // rules, in order to avoid repetition of actions and situations.
    // When learning is required, the agent sets priority to its rules
    // according to the result they provide.
    abstract protected void sortKnowledgeBase();

    // All UDLAgents must include the commitments indicated in a rule
    // into the agent commitments
    abstract protected void includeCommitmentsFromRule(Rule aRule);

    // All UDLAgents must have a learning capability
    abstract protected Vector<Rule> learn(Vector<Rule> knowledgeBase, Commitment commitment);


    // All UDLAgents must provide a boolean value to the Blackboard attributes
    // firstMilestoneAccomplished and secondMilestoneAccomplished
    abstract protected void checkMilestones();

    ////////////////////////////////////////////////////////////////////////////



    // Add the rules into the knowledge base of the agent
    // reading them from a txt file.
    // The agent code is INDEPENDENT of its rules.
    private Vector<Rule> loadKnowledgeBase()
    {
        Condition aCondition;
        Commitment aCommitment;
        Vector<Condition> conditionList;
        Vector<Commitment> commitmentList;
        Rule aRule;
        SequentialFile ruleFile;
        int ruleNumber;
        String aConditionText;
        String aCommitmentText;
        String comment;
        boolean reglaPrioritaria;
        //-----------
        ruleFile = new SequentialFile(ruleFileRoute,ruleFileName,ruleFileSuffix);
        ruleFile.open();
        knowledgeBase = new Vector<Rule>();
        reglaPrioritaria = false;
        while(!ruleFile.eof)
        {
            // crea las condiciones y los compromisos de la regla
            conditionList = new Vector<Condition>();
            commitmentList = new Vector<Commitment>();
            // asigna el n?mero (ID) de la regla
            ruleNumber = ruleFile.readInt();

            // asigna la condici?n
            aConditionText = ruleFile.readString();
            while(!aConditionText.equals("endOfConditions"))
            {
                aCondition = new Condition(aConditionText);
                conditionList.add(aCondition);
                aConditionText = ruleFile.readString();
            }//end while

            // asigna el compromiso
            aCommitmentText  = ruleFile.readString();
            while(!aCommitmentText.equals("endOfRule"))
            {
                aCommitment = new Commitment(aCommitmentText);
                commitmentList.add(aCommitment);
                reglaPrioritaria = false;
                if(aCommitmentText.contains("secuencia"))
                    reglaPrioritaria = true;
                //end if
                aCommitmentText = ruleFile.readString();
            }//end while

            // crea la regla y la incluye
            aRule = new Rule(ruleNumber,conditionList, commitmentList);
            if(reglaPrioritaria)
                aRule.setPriority(1);
            //end if
            knowledgeBase.add(aRule);
        }//end while
        return knowledgeBase;
    }//end loadKnowledgeBase



    private Vector<Situation> loadBestGame()
    {
        SequentialFile bestGameFile;
        Vector<Situation> recordedGame;
        int i,j,k;
        int numberOflines;
        int numberOfSituations;
        int[][] puzzle;
        Situation situation;
        //
        recordedGame = new Vector<Situation>();

        bestGameFile = new SequentialFile("/Users/gerardoayala/Desktop/datos",
                "bestGameFile","txt");
        bestGameFile.open();
        numberOflines = bestGameFile.getNumberOfLines();
        if(numberOflines > 1)
        {
            numberOfSituations = (numberOflines - 1) / 16;
            k = 0;
            while(k < numberOfSituations)
            {
                puzzle = new int[4][4];
                i = 0;
                while( i < 4)
                {
                    j = 0;
                    while( j < 4)
                    {
                        puzzle[i][j] = bestGameFile.readInt();
                        j = j + 1;
                    }//end while
                    i = i + 1;
                }//end while
                situation = new Situation(puzzle);
                recordedGame.add(situation);
                k = k + 1;
            }//end while

        }//end if

        bestGameFile=null;
        return recordedGame;
    }//end loadBestGame



    // The agent gets the information messages sent to it
    // from the Blackboard
    private Vector<Message>  readInformationMessages()
    {
        informationMessages = new Vector<Message>();
        informationMessages = Blackboard.getInformationMessages(id);
        return informationMessages;
    }//end readInformationMessages




    // The agent gets the request messages sent to it
    // from the Blackboard
    private Vector<Message> readRequestMessages()
    {
        requestMessages = Blackboard.getRequestMessages(id);
        return requestMessages;
    }//end readRequestMessages



    // The agent perceives the situation
    // defining its beliefs about the current situation
    private Situation beliefRevision()
    {
        // the perception process must be not interrupted
        beliefs = Blackboard.getCurrentSituation();
        return beliefs;
    }//end beliefRevision




    // The agents determines if the current situation
    // is still not the desired final state
    private boolean currentSituationIsNotTheGoalState()
    {
        if(beliefs.matches(Blackboard.finalState))
            return false;
            //end if
        else
            return true;
        //end else
    }//end currentSituationIsNotTheGoalState




    // This is the agent deliberation process.
    // It corresponds to the generation of possible commitments
    // based on its knowledge base and beliefs.
    private Vector<Commitment>  establishCommitments(Situation beliefs,
                                                     Vector<Rule> knowledgeBase)
    {
        int i;
        Rule aRule;
        //------
        if(!hasPendingCommitments(commitments))
        {
            commitments = new Vector<Commitment>();
            sortKnowledgeBase();
            i = 0;
            while (i < knowledgeBase.size())
            {
                aRule = knowledgeBase.get(i);
                if(beliefs.matchesCondition(aRule.conditions))
                {
                    includeCommitmentsFromRule(aRule);
                }//end if
                i = i + 1;
            }//end while
        }//end if
        return commitments;
    }//end establishCommitments



    private boolean hasPendingCommitments(Vector<Commitment> commitments)
    {
        if((commitments != null) && (commitments.size()!=0))
        {
            Collections.sort(commitments);
            if(commitments.get(0).priority < 999)
                return true;
                //end if
            else
                return false;
            //end else
        }//end if
        else
            return false;
        //end else
    }//end hasPendingCommitments

    private Commitment getFromBestGame(Vector<Situation> bestGame,
                                       Vector<Situation> situationCandidates)
    {
        Commitment bestCommitment;
        int i, j;
        boolean found;
        //
        bestCommitment = null;
        found = false;
        i = 0;
        while(i < situationCandidates.size() && !found)
        {
            j = 0;
            while(j < bestGame.size() && !found)
            {
                if(situationCandidates.get(i).matches(bestGame.get(j)))
                {
                    bestCommitment = situationCandidates.get(i).cause;
                    found = true;
                }//end if
                j = j + 1;
            }//end while
            i = i + 1;
        }//end while
        return bestCommitment;
    }//end getFromBestGame


    // The agent selects one of the commitment candidates
    // there are 2 options:
    // 1. Considers only commitment candidates that produce situations
    // that have not happened yet.
    // Then selects the best situation,
    // based on a heuristic funcion (defined in the Situation class)
    // 2. If all possible situations already happened, then
    // it selects randomly a commitment candidate.
    private Commitment selectCommitment(Vector<Commitment> commitmentCandidates)
    {
        int i;
        boolean thereAreSituationCandidates;
        Situation newPossibleSituation;
        Vector<Situation> situationCandidates;
        Random random;
        int moneda;
        ///////////////
        random = new Random();
        situationCandidates = new Vector<Situation>();
        thereAreSituationCandidates = false;
        i = 0;
        while(i < commitmentCandidates.size())
        {
            if(commitmentCandidates.get(i).priority == 1)
                return commitmentCandidates.get(i);
            //end if

            newPossibleSituation = interpretCommitment(commitmentCandidates.get(i));
            // solamente se consideran situaciones NUEVAS
            if(!Blackboard.alreadyHappened(newPossibleSituation))
            {
                newPossibleSituation.cause = commitmentCandidates.get(i);
                situationCandidates.add(newPossibleSituation);
                thereAreSituationCandidates = true;
            }//end if
            i = i + 1;
        }//end while
        // If there are no situations that had not happened
        // the agent selects randomly a candidate commitment
        if (!thereAreSituationCandidates)
        {
            if(commitmentCandidates.size()==1)
                selectedCommitment = commitmentCandidates.get(0);
                //end if
            else
            {
                selectedCommitment =
                        commitmentCandidates.get(random.nextInt(commitmentCandidates.size()-1));
            }//else
        }//end if

        // If there are situations that had not happened
        // the agent selects the best situation
        // according to a heuristic function
        // defined in the Situation class
        else
        {

            Collections.sort(situationCandidates);
            if((situationCandidates.size() > 1) &&
                    (situationCandidates.get(0).funcionHeuristica() ==
                            situationCandidates.get(1).funcionHeuristica()))
            {
                moneda = random.nextInt(100);
                if(moneda > 80)
                {
                    selectedCommitment = situationCandidates.get(0).cause;
                }//
                else
                {
                    selectedCommitment = getFromBestGame(bestGame,situationCandidates);
                    if(selectedCommitment == null)
                    {
                        selectedCommitment = situationCandidates.get(0).cause;
                    }//end if
                    else
                    {
                        System.out.println("///////////////////////////////////// remember");
                    }//end else
                }//end else
            }//end if
            else
            {
                selectedCommitment = situationCandidates.get(0).cause;
            }//end if
        }//end if
        return selectedCommitment;
    }//end selectCommitment




    // The agent applies a selected commitment
    // modifying the world and
    // and determines if the new state is the desired final state
    private Situation executeCapabilities(Situation beliefs,
                                          Commitment selectedCommitment)
    {
        // Modify the current situation
        newSituation = interpretCommitment(selectedCommitment);
        newSituation.cause = selectedCommitment;
        Blackboard.setCurrentSituation(newSituation);

        showAction(selectedCommitment);

        checkMilestones();
        beliefs = Blackboard.getCurrentSituation();
        if(!currentSituationIsNotTheGoalState())
            agentHasFinished = true;
        //end if
        return beliefs;
    }//end executeCapabilities




    // The agent waits
    // giving time to other agents to participate
    private void justWait()
    {
        try
        {
            sleep(10);
        }//end try
        catch(Exception anException)
        {}//end catch
    }//end justWait


    // Save the rules of the knowledge base of the agent
    // into a txt file.
    private void saveKnowledgeBase(Vector<Rule> knowledgeBase)
    {
        int i;
        int j;
        Rule aRule;
        Condition aCondition;
        Commitment aCommitment;
        SequentialFile ruleFile;
        //-----

        ruleFile = new SequentialFile(ruleFileRoute,
                ruleFileName, ruleFileSuffix);
        ruleFile.create();
        i = 0;
        while(i<knowledgeBase.size())
        {
            aRule = knowledgeBase.get(i);
            ruleFile.writeInt(i+1);
            //if(i == 0)
            //ruleFile.writeString("");
            //end if
            j = 0;
            while(j < aRule.conditions.size())
            {
                aCondition = aRule.conditions.get(j);
                ruleFile.writeString(aCondition.conditionPredicate);
                j = j + 1;
            }//end while
            ruleFile.writeString("endOfConditions");

            j = 0;
            while(j < aRule.commitments.size())
            {
                aCommitment = aRule.commitments.get(j);
                ruleFile.writeString(aCommitment.predicate);
                j = j + 1;
            }//end while
            ruleFile.writeString("endOfRule");
            i = i + 1;
            //if(i<knowledgeBase.size())
            //ruleFile.writeString("");
            //end if
        }//end while
    }//end saveKnowledgeBase



    private void updateBestGame(Vector<Situation> currentGame)
    {
        int i,j,k;
        int[][] puzzle;
        SequentialFile bestGameFile;
        int numberOflines;
        int numberOfSituations;
        //

        bestGameFile = new SequentialFile("/Users/2924/Desktop/datos",
                "bestGameFile","txt");
        bestGameFile.open();

        numberOflines = bestGameFile.getNumberOfLines();
        numberOfSituations = (numberOflines-1)/16;
        bestGameFile=null;
        if((currentGame.size() < numberOfSituations) || (numberOflines == 1))
        {
            bestGameFile = new SequentialFile("/Users/2924/Desktop/datos",
                    "bestGameFile","txt");
            bestGameFile.create();

            k =0;
            while(k < currentGame.size())
            {
                puzzle = currentGame.get(k).puzzle;
                i=0;
                while(i < 4)
                {
                    j=0;
                    while(j < 4)
                    {
                        bestGameFile.writeInt(puzzle[i][j]);
                        j = j + 1;
                    }//end while
                    i = i + 1;
                }//end while
                k = k + 1;
            }//end while
            bestGameFile.writeString("eof");
            bestGameFile.writeString("number of moves:" + (k+1));
        }//end if
    }//end updateBestGame
    ///////////////////////////////////////////////////////////////////////////////////////////


    // Basic UDLAgent performance
    // based on the algorithm of an intelligent agent
    // of AOP (Agent Oriented Programming)
    public void run()
    {
        knowledgeBase = loadKnowledgeBase();
        bestGame = loadBestGame();
        do
        {
            // no other thread can run during this process
            synchronized(this)
            {
                informationMessages = readInformationMessages();
                requestMessages = readRequestMessages();
                beliefs = beliefRevision();
                if (currentSituationIsNotTheGoalState())
                {
                    commitments = establishCommitments(beliefs, knowledgeBase);
                    System.out.println("######## commitments de " + id +" @@@@@@@@@@@@");
                    System.out.println(commitments);
                    if(commitments.size()==0)
                        continue;
                    //end
                    if(!hasPendingCommitments(commitments))
                    {
                        commitmentCandidates = setCommitmentCandidates(commitments);
                        selectedCommitment = selectCommitment(commitmentCandidates);
                    }//end if
                    else
                    {
                        selectedCommitment = commitments.get(0);
                        commitments.remove(0);
                    }//end else
                    beliefs = executeCapabilities(beliefs, selectedCommitment);
                    showAction(selectedCommitment);
                    knowledgeBase = learn(knowledgeBase, selectedCommitment);
                }//end if
                else
                    agentHasFinished  = true;
                //end else
                notifyAll();
            }//end synchronized
            justWait();
        }//end while
        while ((!agentHasToStop) && (!agentHasFinished));

        // the agent updates its knowledge
        //saveKnowledgeBase(knowledgeBase);

        // the agent will remember its best result
        //updateBestGame(Blackboard.history);

    }//end method run


}//end class UDLAgent


