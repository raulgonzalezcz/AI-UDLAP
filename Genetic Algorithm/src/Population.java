/**
 *
 * @author Gerardo Ayala.
 * April 2016.
 *
 */

public class Population
{
    // A population holds a pool of individuals
    Individual[] poolOfIndividuals;
    //---------------------


    // constructor
    public Population(int populationSize)
    {
        poolOfIndividuals = new Individual[populationSize];
    }//end constructor



    // initializes a population
    // generating individuals
    // with the genome as pattern
    // according to the population size
    public void initialize(Genome aGenome)
    {
        int i;
        Individual newIndividual;
        //--------------
        // Loop and create individuals
        i = 0;
        while(i < populationSize())
        {
            // creates an Individual
            newIndividual = new Individual();
            // defines the individual
            newIndividual.defineIndividual(aGenome);
            // includes the individual into the
            // pool of individuals
            saveIndividual(i, newIndividual);
            i = i + 1;
        }//end while
    }//end initialize




    public void saveIndividual(int index, Individual anIndividual)
    {
        poolOfIndividuals[index] = anIndividual;
    }//end saveIndividual




    public Individual getIndividual(int index)
    {
        return poolOfIndividuals[index];
    }//end getIndividual



    // Gets the best individual in the population
    public Individual getFittest()
    {
        Individual fittest;
        int i;
        //------------
        fittest = poolOfIndividuals[0];
        // Loop through individuals to find fittest
        i = 1;
        while(i < populationSize())
        {
            // compares individuals according to
            // the value of their fitness function
            if ((Problem.fitnessFunction(fittest) >=
                    Problem.fitnessFunction(getIndividual(i))))
            {
                fittest = getIndividual(i);
            }//end if
            i = i + 1;
        }//end while
        // returns the best individual
        // according to the fitness function
        return fittest;
    }//end getFittest



    // Gets population size
    public int populationSize()
    {
        return poolOfIndividuals.length;
    }//end populationSize

}//end Population
