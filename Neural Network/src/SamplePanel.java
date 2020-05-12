
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

import javax.swing.*;
import java.awt.*;


public class SamplePanel extends JPanel
{

    /**
     * The image data.
     */
    SampleData sampleData;




    /**
     * The constructor.
     *
     * @param width The width of the downsampled image
     * @param height The height of the downsampled image
     */
    SamplePanel(int width,int height)
    {
        sampleData = new SampleData(' ',width,height);
    }//end constructor




    /**
     * Assign a new image data object.
     *
     * @param aSampleData The image data object.
     */

    void setData(SampleData aSampleData)
    {
        sampleData = aSampleData;
    }//end setData




    /**
     * The image data object.
     *
     * @return The image data object.
     */
    SampleData getData()
    {
        return sampleData;
    }//end getData



    public void paint(Graphics graphics)
    {
        int x;
        int y;
        int verticalCell;
        int horizontalCell;
        //

        if ( sampleData == null )
            return;
        //end if

        verticalCell = getHeight()/sampleData.getHeight();
        horizontalCell = getWidth()/sampleData.getWidth();
        graphics.setColor(Color.white);
        graphics.fillRect(0,0,getWidth(),getHeight());
        graphics.setColor(Color.black);

        y = 0;
        while(y < sampleData.getHeight())
        {
            graphics.drawLine(0,y*verticalCell,getWidth(),y*verticalCell);
            y = y + 1;
        }//end while

        x = 0;
        while(x < sampleData.getWidth())
        {
            graphics.drawLine(x*horizontalCell,0,x*horizontalCell,getHeight());
            x = x + 1;
        }//end while

        y = 0;
        while(y < sampleData.getHeight())
        {
            x = 0;
            while(x < sampleData.getWidth())
            {
                if ( sampleData.getData(x,y) )
                    graphics.fillRect(x*horizontalCell,y*verticalCell,horizontalCell,verticalCell);
                //end if
                x = x + 1;
            }//end while
            y = y + 1;
        }//end while

        graphics.setColor(Color.black);
        graphics.drawRect(0,0,getWidth()-1,getHeight()-1);

    }//end paint


}//end SamplePanel