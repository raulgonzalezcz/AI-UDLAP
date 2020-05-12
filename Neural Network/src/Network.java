

import java.util.*;

/**
 * Gerardo Ayala, 2017.
 *
 * Based on the version by Jeff Heaton
 * (http://www.jeffheaton.com) 1-2002
 * http://www2.sys-con.com/ITSG/virtualcd/Java/archives/0705/heaton/index.html
 * Java Neural Network Example
 * Handwriting Recognition
 * -------------------------------------------------
 */

abstract public class Network
{
    //The value to consider a neuron on
    public final static double NEURON_ON_THRESHOLD = 0.9;
    //The value to consider a neuron off
    public final static double NEURON_OFF_THRESHOLD = 0.1;
    // Output neuron activations
    protected double outputNeuronActivations[];
    // Mean square error of the network
    protected double meanSquareError;
    // Number of input neurons
    protected int numberOfInputNeurons;
    // Number of output neurons
    protected int numberOfOutputNeurons;
    // Random number generator
    protected Random random = new Random(System.currentTimeMillis());

    //////////////////////////////////////////////////////////

    // Called to learn from training sets.
    abstract public void learn () throws RuntimeException;


    ///////////////////////////////////////////////////////////





    // Calculate the length of a vector.
    static double getVectorLength(double vector[])
    {
        double length;
        int i;
        //
        length = 0.0;
        i = 0;
        while(i < vector.length)
        {
            length += vector[i] * vector[i];
            i = i + 1;
        }//end while

        return length;
    }//end vectorLength




    // Called to calculate a dot product.
    double dotProduct(double vectorA[] , double vectorB[])
    {
        int i;
        int k;
        int m;
        double result;
        //
        result = 0.0;
        k = vectorA.length / 4;
        m = vectorA.length % 4;

        i = 0;
        while ( (k--) > 0 )
        {
            result += vectorA[i] * vectorB[i];
            result += vectorA[i+1] * vectorB[i+1];
            result += vectorA[i+2] * vectorB[i+2];
            result += vectorA[i+3] * vectorB[i+3];
            i+=4;
        }//end while

        while ( (m--) > 0 )
        {
            result += vectorA[i] * vectorB[i];
            i = i + 1;
        }//end while
        return result;
    }//end dotProduct





    // Called to randomize weights.
    void randomizeWeights(double weight[][])
    {
        double randomNumber;
        int temp;
        int i;
        int j;
        //
        // SQRT(12)=3.464...
        temp = (int)(3.464101615 / (2. * Math.random() )) ;

        i = 0;
        while(i < weight.length)
        {
            j = 0;
            while(j < weight[0].length)
            {
                randomNumber =  (double) random.nextInt(Integer.MAX_VALUE) +
                        (double) random.nextInt(Integer.MAX_VALUE) -
                        (double) random.nextInt(Integer.MAX_VALUE) -
                        (double) random.nextInt(Integer.MAX_VALUE);
                weight[i][j] = temp * randomNumber ;
                j = j + 1;
            }//end while
            i = i + 1;
        }//end while

    }//end randomizeWeights


}//end class