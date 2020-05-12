


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

public class SampleData implements Comparable,Cloneable
{

    /**
     * The downsampled data as a grid of booleans.
     */
    protected boolean grid[][];

    /**
     * The letter.
     */
    protected char letter;




    /**
     * The constructor
     *
     * @param aLetter
     * @param width
     * @param height
     */
    public SampleData(char aLetter,int width,int height)
    {
        grid = new boolean[width][height];
        letter = aLetter;
    }//end constructor




    /**
     * Set one pixel of sample data.
     *
     * @param x
     * @param y
     * @param value
     */
    public void setData(int x,int y,boolean value)
    {
        grid[x][y] = value;
    }//end setData




    /**
     * Set the letter that this sample represents.
     *
     * @param aLetter
     */
    public void setLetter(char aLetter)
    {
        letter = aLetter;
    }//end setLetter





    /**
     * Get the height of the down sampled image.
     *
     * @return The height of the downsampled image.
     */
    public int getHeight()
    {
        return grid[0].length;
    }//end getHeight




    /**
     * Get the width of the downsampled image.
     *
     * @return The width of the downsampled image
     */
    public int getWidth()
    {
        return grid.length;
    }//end getWidth




    /**
     * Get the letter that this sample represents.
     *
     * @return The letter that this sample represents.
     */
    public char getLetter()
    {
        return letter;
    }//end getLetter




    /**
     * Get a pixel from the sample.
     *
     * @param x The x coordinate
     * @param y The y coordinate
     * @return The requested pixel
     */
    public boolean getData(int x,int y)
    {
        return grid[x][y];
    }//end getData




    /**
     * Clear the downsampled image
     */
    public void clear()
    {
        int i;
        int j;
        //
        i = 0;
        while(i < grid.length)
        {
            j = 0;
            while(j < grid[0].length)
            {
                grid[i][j]=false;
                j = j + 1;
            }//end while
            i = i + 1;
        }//end while

    }//end clear




    /**
     * Compare this sample to another, used for sorting.
     *
     * @param anObject
     * @return Same as String.compareTo
     */
    public int compareTo(Object anObject)
    {
        SampleData sampleData;
        //
        sampleData = (SampleData)anObject;
        if (this.getLetter() > sampleData.getLetter())
            return 1;
        //end if
        else
            return -1;
        //end else
    }//end compareTo



    /**
     * Create a copy of this sample
     *
     * @return A copy of this sample
     */
    public Object clone()
    {
        SampleData sampleData;
        int y;
        int x;
        //
        sampleData = new SampleData(letter,getWidth(),getHeight());
        y = 0;
        while(y < getHeight())
        {
            x = 0;
            while(x < getWidth())
            {
                sampleData.setData(x,y,getData(x,y));
                x = x + 1;
            }//end while
            y = y + 1;
        }//end while

        return sampleData;
    }//ene clone



    /**
     * Convert this sample to a string.
     *
     * @return Just returns the letter that this sample is assigned to.
     */
    public String toString()
    {
        return ""+letter;
    }//end toString


}//end SampleData