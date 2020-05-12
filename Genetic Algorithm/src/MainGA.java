
public class MainGA

{

    public static void presentSolution(Population lastGeneration)
    {
        double theOptimalValue;
        Individual theFittest;
        //----------------------

        System.out.println("Solution:");
        theFittest = lastGeneration.getFittest();
        System.out.println(theFittest);
        theOptimalValue = Problem.fitnessFunction(theFittest);
        System.out.println("Optimal value: " + theOptimalValue);
    }//end presentSolution




    public static void main(String[] args)
    {
        Genome theGenome;
        Population lastGeneration;
        //-----------------------

        // 1. Define the genome
        theGenome = Problem.defineGenome();

        // 2. Set the Genome
        UDLAGeneticAlgorithm.setGenome(theGenome);

        // Set GA parameters
        // -------------------
        // 3. We define the size of the
        // population of candidate solutions (individuals)
        // The population size depends
        // of the nature of the problem.
        UDLAGeneticAlgorithm.setPopulationSize(500);

        // 4. We define the number of generations.
        // In each generation
        // a proportion of the population is selected
        // to brew a new generation (next generation).
        UDLAGeneticAlgorithm.setNumberOfGenerations(50);


        // 6. Set the tournament size
        // One individual is selected from a population
        // by a tournament selection method.
        // The individual winner of the tournament
        // is selected for crossover.
        // The tournemant size is the
        // number of individuals that compete.
        UDLAGeneticAlgorithm.setTournamentSize(6);

        // 5. Set the Mutation rate
        // The mutation maintains a genetic diversity
        // from one generation to the next
        // The mutation rate represents the
        // probability of mutation, which implies
        // the increment of the search space.
        // (mutation rates should be between 0.015 - 0.020)
        UDLAGeneticAlgorithm.setMutationRate(0.01);


        // 7. Set elitism.
        // If some very good individuals appear in one generation
        // it is convenient to considere them as they are
        // for the next generation.
        // This is called "elitism"
        UDLAGeneticAlgorithm.setElitism(true);

        // 8. start the evolution process
        // "run" the genetic algorithm
        lastGeneration = UDLAGeneticAlgorithm.evolution();

        // 9. Presents the best individual
        // of the last generation as the solution
        presentSolution(lastGeneration);

        // 10. Run the process several times
        // -> tunning the parameters

        // 11. Obtain the best (optimal) solution

    }//end main

}//end ProjectGeneticAlgorithms

