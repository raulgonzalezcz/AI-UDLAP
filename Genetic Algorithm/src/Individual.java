/**
 *
 * @author Gerardo Ayala.
 * April 2014.
 *
 */

import java.util.Collections;

public class Individual
{
    public Genome chromosome;
    public double fitnessValue;
    //----------------------------

    // Constructs an individual
    public Individual()
    {
        int i;
        //-----------
        chromosome = new Genome();
        fitnessValue = 0;
        i = 0;
        while(i < Genome.length)
        {
            chromosome.add(null);
            i = i + 1;
        }//end while
    }//end constructor


    // defines every gene of the individual
    public void defineIndividual(Genome aGenome)
    {
        int geneIndex;
        //-------------

        geneIndex = 0;
        while(geneIndex < Genome.length)
        {
            setGene(geneIndex, aGenome.get(geneIndex));
            geneIndex = geneIndex + 1;
        }//end while
        Collections.shuffle(chromosome);
    }//end generateIndividual



    // Gets a gene from the individual
    public Gene getGene(int aPosition)
    {
        return chromosome.get(aPosition);
    }//end getGene



    // Sets a gene in a certain position within an individual
    public void setGene(int aPosition, Gene aGene)
    {
        chromosome.set(aPosition, aGene);
    }//end setGene



    // Get number of genes in our individual
    public int individualSize()
    {
        return chromosome.size();
    }//end individualSize



    // Check if the individual contains a gene
    public boolean containsGene(Gene aGene)
    {
        return chromosome.contains(aGene);
    }//end containsGene




    // representation of the Individual
    public String toString()
    {
        String geneString;
        int i;
        //-----------

        geneString = "[";
        i = 0;
        while(i < individualSize())
        {
            geneString += getGene(i)+", ";
            i = i + 1;
        }//end while
        geneString = geneString.substring(0, geneString.length()-2);
        return geneString + "]";
    }//end toString

}//end class Individual
