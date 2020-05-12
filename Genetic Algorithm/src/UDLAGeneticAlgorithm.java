
/**
 *
 * @author Gerardo Ayala.
 * April 2014.
 *
 */
public class UDLAGeneticAlgorithm
{
    private static Genome theGenome;
    private static int populationSize;
    private static int numberOfGenerations;
    private static double mutationRate;
    private static int tournamentSize;
    private static boolean elitism;
    //--------------------------------------

    public static void setGenome(Genome aGenome)
    {
        theGenome = aGenome;
        Genome.setLength(theGenome.size());
    }//end setGenome


    public static void setPopulationSize(int aPopulationSize)
    {
        populationSize = aPopulationSize;
    }//end setPopulationSize


    public static void setNumberOfGenerations(int aNumberOfGenerations)
    {
        numberOfGenerations =  aNumberOfGenerations;
    }//end setNumberOfGenerations


    public static void setMutationRate(double aMutationRate)
    {
        mutationRate = aMutationRate;
    }//end setMutationRate


    public static void setTournamentSize(int aTournamentSize)
    {
        tournamentSize = aTournamentSize;
    }//end setTournamentSize


    public static void setElitism(boolean isElitismConsidered)
    {
        elitism = isElitismConsidered;
    }//end setElitism


    // Crossover genetic operation
    public static Individual crossover(Individual father,
                                       Individual mother)
    {
        Individual child;
        int positionX;
        int positionY;
        int i;
        int j;
        //----------------------

        // Create a new child
        // as an empty Individual
        child = new Individual();

        // Get start and end positions for father
        positionX = (int) (Math.random() *
                father.individualSize());
        positionY = (int) (Math.random() *
                father.individualSize());

        // Set father genes into the child 
        i = 0;
        while(i < child.individualSize())
        {
            // If our start position is less than the end position
            if (
                    (positionX < positionY) &&
                            (i > positionX) &&
                            (i < positionY)
                    )
                child.setGene(i, father.getGene(i));
                //end if
            else
                //  our start position is larger
                if (positionX > positionY)
                {
                    if ((i > positionX) || (i < positionY))
                        child.setGene(i, father.getGene(i));
                    //end if
                }//end if
            //end else
            i = i + 1;
        }//end while

        // Set mother genes into the child
        i = 0;
        while(i < mother.individualSize())
        {
            // If child doesn't have the gene add it
            if (!child.containsGene(mother.getGene(i)))
            {
                // Loop to find a blank position in the child's individual
                j = 0;
                while(j < child.individualSize())
                {
                    // when blank position found, add mother's gene
                    if (child.getGene(j) == null)
                    {
                        child.setGene(j, mother.getGene(i));
                        break;
                    }//end if
                    j = j + 1;
                }//end while
            }//end if
            i = i + 1;
        }//end while

        return child;
    }//end crossover



    // Mutation operation
    private static void mutation(Individual anIndividual)
    {
        int pointer;
        int position;
        Gene gene1;
        Gene gene2;
        //--------------

        // Loop through individual
        pointer = 0;
        while(pointer < anIndividual.individualSize())
        {
            // Apply mutation rate (probability)
            if(Math.random() < mutationRate)
            {
                // Get a second random position in the individual
                position = (int) (anIndividual.individualSize() *
                        Math.random());
                // Get the cities at target position
                gene1 = anIndividual.getGene(pointer);
                gene2 = anIndividual.getGene(position);

                // Swap the genes
                anIndividual.setGene(position, gene1);
                anIndividual.setGene(pointer, gene2);
            }//end if
            pointer = pointer + 1;
        }//end while
    }//end mutation



    // Selects the best Individual for crossover
    private static Individual tournamentSelection(Population aPopulation)
    {
        Population tournamentPopulation;
        int i;
        Individual fittest;
        int randomId;
        //--------------------

        // Create a tournament population
        tournamentPopulation = new Population(tournamentSize);
        // For each place in the tournament 
        // get a random individual as candidate and
        // add it into the tournament population
        i = 0;
        while(i < tournamentSize)
        {
            randomId = (int) (Math.random() * aPopulation.populationSize());
            tournamentPopulation.saveIndividual(i,
                    aPopulation.getIndividual(randomId));
            i = i + 1;
        }//end while

        // Get the fittest 
        fittest = tournamentPopulation.getFittest();
        return fittest;
    }//end tournamentSelection




    // Evolves a population over one generation
    private static Population evolvePopulation(Population aPopulation)
    {
        Population newGeneration;
        int index;
        int i;
        Individual father;
        Individual mother;
        Individual child ;
        //------------------------

        newGeneration = new Population(populationSize);
        // Keep our best individual if elitism is enabled
        // in position 0
        index = 0;
        if (elitism)
        {
            newGeneration.saveIndividual(0, aPopulation.getFittest());
            index = 1;
        }//end if

        // **** Crossover **********************
        //**************************************
        // Loop over the new population's size 
        // and create individuals
        i = index;
        while(i < newGeneration.populationSize())
        {
            // Select parents via tournament selection
            father = tournamentSelection(aPopulation);
            mother = tournamentSelection(aPopulation);
            // Crossover between winner parents
            child = crossover(father, mother);
            // Add child into the new generation
            newGeneration.saveIndividual(i, child);
            i = i + 1;
        }//end while

        // **** Mutation ***********************
        //**************************************
        // Mutate the new population a bit 
        // to add some new genetic material
        // and expand a little the search space
        i = index;
        while(i < newGeneration.populationSize())
        {
            mutation(newGeneration.getIndividual(i));
            i = i + 1;
        }//end while
        return newGeneration;
    }//end evolvePopulation




    // Constructs a first generation
    // and then a sequence of new generations
    public static Population evolution()
    {
        Population aGeneration;
        int i;
        //--------------------

        // Constructs the FIRST generation
        aGeneration = new Population(populationSize);
        // initializes the first generation
        aGeneration.initialize(theGenome);

        // According to the number of generations indicated
        // it performs the evolution (create next generations)
        i = 0;
        while (i < numberOfGenerations)
        {
            // constructs the next generation
            aGeneration = evolvePopulation(aGeneration);
            i = i + 1;
        }//end while
        // finally it returns the LAST generation
        return aGeneration;
    }// end evolution

}//end UDLAPGeneticAlgorithm
