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

public class TrainingSet
{

    protected int inputCount;
    protected int outputCount;
    protected double input[][];
    protected double output[][];
    protected double classify[];
    protected int trainingSetCount;



    /**
     * The constructor.
     *
     * @param numberOfInputNeurons
     * @param numberOfOutputNeurons
     */
    TrainingSet(int numberOfInputNeurons,int numberOfOutputNeurons)
    {
        inputCount = numberOfInputNeurons;
        outputCount = numberOfOutputNeurons;
        trainingSetCount = 0;
    }//end constructor



    /**
     * Get the input neuron count
     *
     * @return The input neuron count
     */
    public int getInputCount()
    {
        return inputCount;
    }//end getInputCount




    /**
     * Get the output neuron count
     *
     * @return The output neuron count
     */
    public int getOutputCount()
    {
        return outputCount;
    }//end getOutputCount


    /**
     * Get the training set data.
     *
     * @return Training set data.
     */
    public int getTrainingSetCount()
    {
        return trainingSetCount;
    }//end getTrainingSetCount




    /**
     * Get a specified input value.
     *
     * @param set The input entry.
     * @param index The index
     * @return An individual input
     * @exception java.lang.RuntimeException
     */




    /**
     * Get one of the output values.
     *
     * @param set The entry
     * @param index Which value in the entry
     * @return The output value.
     * @exception java.lang.RuntimeException
     */
    double getOutputValue(int set,int index) throws RuntimeException
    {
        if ( (set < 0) || (set >= trainingSetCount) )
            throw(new RuntimeException("Training set out of range:" + set ));
        //end if
        if ( (index < 0) || (set >= outputCount) )
            throw(new RuntimeException("Training input index out of range:" + index ));
        //end if
        return output[set][index];
    }//end getOutput



    /**
     * Get the classification.
     *
     * @param set Which entry.
     * @return The classification for the specified entry.
     * @exception java.lang.RuntimeException
     */
    double getClassification(int set) throws RuntimeException
    {
        if ( (set < 0) || (set >= trainingSetCount) )
            throw(new RuntimeException("Training set out of range:" + set ));
        //end if
        return classify[set];
    }//end getClassify



    /**
     * Get an output set.
     *
     * @param set The entry requested.
     * @return The complete output set as an array.
     * @exception java.lang.RuntimeException
     */

    double[] getOutputSet(int set) throws RuntimeException
    {
        if ( (set < 0) || (set >= trainingSetCount) )
            throw(new RuntimeException("Training set out of range:" + set ));
        //end if
        return output[set];
    }//end getOutputSet




    /**
     * Get an input set.
     *
     * @param set The entry requested.
     * @return The complete input set as an array.
     * @exception java.lang.RuntimeException
     */

    double[] getInputSet(int set) throws RuntimeException
    {
        if ( (set < 0) || (set >= trainingSetCount) )
            throw(new RuntimeException("Training set out of range:" + set ));
        //end if
        return input[set];
    }//end getInputSet




    /**
     * Set the number of entries in the training set. This method
     * also allocates space for them.
     *
     * @param aTrainingSetCount How many entries in the training set.
     */
    public void setTrainingSetCount(int aTrainingSetCount)
    {
        trainingSetCount = aTrainingSetCount;
        input = new double[trainingSetCount][inputCount];
        output = new double[trainingSetCount][outputCount];
        classify = new double[trainingSetCount];
    }//end setTrainingSetCount



    /**
     * Set one of the training set's inputs.
     *
     * @param set The entry number
     * @param index The index(which item in that set)
     * @param value The value
     * @exception java.lang.RuntimeException
     */
    void setATrainingSetInput(int set,int index,double value) throws RuntimeException
    {
        if ( (set < 0) || (set >= trainingSetCount) )
            throw(new RuntimeException("Training set out of range:" + set ));
        //end if
        if ( (index < 0) || (index >= inputCount) )
            throw(new RuntimeException("Training input index out of range:" + index ));
        //end if
        input[set][index] = value;
    }//end setATrainingSetInput


    public void showInput()
    {
        int y;
        int x;
        //
        y=0;
        while(y < trainingSetCount)
        {
            x = 0;
            while(x < inputCount)
            {
                System.out.print(input[y][x] + "  ");
                x = x + 1;
            }//end while
            System.out.println("");
            y = y + 1;
        }//end while
    }//end showInput


}//end TrainingSet