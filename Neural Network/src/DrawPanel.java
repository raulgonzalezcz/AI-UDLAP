

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;

/**
 * Java Neural Network Example
 * Handwriting Recognition
 * by Jeff Heaton (http://www.jeffheaton.com) 1-2002
 * -------------------------------------------------
 * This class is used to provide a small component that
 * the user can draw handwritten letters into. This class
 * also contains the routines necessary to crop and downsample
 * the written character.
 *
 * @author Jeff Heaton (http://www.jeffheaton.com)
 * @version 1.0
 */
public class DrawPanel extends JPanel
{

    /**
     * The image that the user is drawing into.
     */
    protected Image entryImage;

    /**
     * A graphics handle to the image that the
     * user is drawing into.
     */
    protected Graphics entryGraphics;

    /**
     * The last x that the user was drawing at.
     */
    protected int lastX;

    /**
     * The last y that the user was drawing at.
     */
    protected int lastY;

    /**
     * The down sample component used with this
     * component.
     */
    protected SamplePanel samplePanel;

    /**
     * Specifies the left boundary of the cropping
     * rectangle.
     */
    protected int downSampleLeft;

    /**
     * Specifies the right boundary of the cropping
     * rectangle.
     */
    protected int downSampleRight;

    /**
     * Specifies the top boundary of the cropping
     * rectangle.
     */
    protected int downSampleTop;

    /**
     * Specifies the bottom boundary of the cropping
     * rectangle.
     */
    protected int downSampleBottom;

    /**
     * The downsample ratio for x.
     */
    protected double ratioX;

    /**
     * The downsample ratio for y
     */
    protected double ratioY;

    /**
     * The pixel map of what the user has drawn.
     * Used to downsample it.
     */
    protected int pixelMap[];


    /**
     * The constructor.
     */
    DrawPanel()
    {
        lastX = -1;
        lastY = -1;
        enableEvents(AWTEvent.MOUSE_MOTION_EVENT_MASK|
                AWTEvent.MOUSE_EVENT_MASK|
                AWTEvent.COMPONENT_EVENT_MASK);
    }//end constructor


    /**
     * Setup the internal image that the user
     * draws onto.
     */
    protected void initImage()
    {
        entryImage = createImage(getWidth(),getHeight());
        entryGraphics = entryImage.getGraphics();
        entryGraphics.setColor(Color.white);
        entryGraphics.fillRect(0,0,getWidth(),getHeight());
    }//end initImage




    /**
     * Paint the drawn image and cropping box
     * (if active).
     *
     * @param graphics The graphics context
     */
    public void paint(Graphics graphics)
    {
        if ( entryImage == null )
            initImage();
        //end if
        graphics.drawImage(entryImage,0,0,this);
        graphics.setColor(Color.black);
        graphics.drawRect(0,0,getWidth(),getHeight());
        graphics.setColor(Color.red);
        graphics.drawRect(  downSampleLeft,
                            downSampleTop,
                            downSampleRight-downSampleLeft,
                            downSampleBottom-downSampleTop);
    }//end paint






    /**
     * Process messages.
     *
     * @param event The event.
     */
    protected void processMouseEvent(MouseEvent event)
    {
        if(event.getID() == MouseEvent.MOUSE_PRESSED)
        {
            lastX = event.getX();
            lastY = event.getY();
        }//end if
    }//end  processMouseEvent






    /**
     * Process messages.
     *
     * @param event The event.
     */
    protected void processMouseMotionEvent(MouseEvent event)
    {
        if(event.getID() == MouseEvent.MOUSE_DRAGGED)
        {
            entryGraphics.setColor(Color.black);
            entryGraphics.drawLine(lastX,lastY,event.getX(),event.getY());
            getGraphics().drawImage(entryImage,0,0,this);
            lastX = event.getX();
            lastY = event.getY();
        }//end if
    }//end processMouseMotionEvent





    /**
     * Set the sample control to use. The
     * sample control displays a downsampled
     * version of the character.
     *
     * @param aSamplePanel
     */
    public void setSamplePanel(SamplePanel aSamplePanel)
    {
        samplePanel = aSamplePanel;
    }//end setSamplePanel





    /**
     * Get the down sample component to be used
     * with this component.
     *
     * @return The down sample component.
     */
    public SamplePanel getSamplePanel()
    {
        return samplePanel;
    }//end getSamplePanel





    /**
     * This method is called internally to
     * see if there are any pixels in the given
     * scan line. This method is used to perform
     * autocropping.
     *
     * @param y The horizontal line to scan.
     * @return True if there were any pixels in this
     * horizontal line.
     */
    private boolean horizontalLineIsClear(int y)
    {
        int width;
        int i;
        boolean isClean;
        //
        isClean = true;
        width = entryImage.getWidth(this);
        i = 0;
        while((i < width) && isClean)
        {
            if(pixelMap[(y*width)+i] != -1)
            {
                isClean = false;
            }//end if
            i = i + 1;
        }//end while
        return isClean;
    }//end horizontalLineIsClear





    /**
     * This method is called to determine ....
     *
     * @param x The vertical line to scan.
     * @return True if there are any pixels in the
     * specified vertical line.
     */
    private boolean verticalLineIsClear(int x)
    {
        int width;
        int height;
        int i;
        boolean isClear;
        //
        isClear = true;
        width = entryImage.getWidth(this);
        height = entryImage.getHeight(this);
        i = 0;
        while((i < height) && isClear)
        {
            if(pixelMap[(i*width)+x] != -1)
                isClear = false;
            //end if
            i = i + 1;
        }//end while
        return isClear;
    }//end verticalLineIsClear





    /**
     * This method is called to automatically
     * crop the image so that whitespace is
     * removed.
     *
     * @param width The width of the image.
     * @param height The height of the image
     */
    protected void findDownSampleBounds(int width,int height)
    {
        int y;
        int x;
        //

        // top line
        y = 0;
        while(y < height)
        {
            if ( !horizontalLineIsClear(y) )
            {
                downSampleTop = y;
                break;
            }//end if
            y = y + 1;
        }//end while



        // bottom line
        y = height - 1;
        while (y >= 0)
        {
            if ( !horizontalLineIsClear(y) )
            {
                downSampleBottom = y;
                break;
            }//end if
            y = y - 1;
        }//end while


        // left line
        x = 0;
        while(x < width)
        {
            if ( !verticalLineIsClear(x) )
            {
                downSampleLeft = x;
                break;
            }//end if
            x = x + 1;
        }//end while



        // right line
        x = width-1;
        while ( x >= 0)
        {
            if ( !verticalLineIsClear(x) )
            {
                downSampleRight = x;
                break;
            }//end if
            x = x - 1;
        }//end while

    }//end findDownSampleBounds








    /**
     * Called to downsample a quadrant of the image.
     *
     * @param x The x coordinate of the resulting
     * downsample.
     * @param y The y coordinate of the resulting
     * downsample.
     * @return Returns true if there were ANY pixels
     * in the specified quadrant.
     */
    private boolean thereArePixelsInQuadrant(int x,int y)
    {
        boolean thereWhereAnyPixels;
        int width;
        int startX;
        int startY;
        int endX;
        int endY;
        int yIndex;
        int xIndex;
        int location;
        //
        thereWhereAnyPixels = false;
        width = entryImage.getWidth(this);
        startX = (int)(downSampleLeft+(x*ratioX));
        startY = (int)(downSampleTop+(y*ratioY));
        endX = (int)(startX + ratioX);
        endY = (int)(startY + ratioY);

        yIndex = startY;
        while (yIndex <= endY)
        {
            xIndex = startX;
            while(xIndex <= endX)
            {
                location = xIndex+(yIndex*width);
                if ( pixelMap[location] != -1 )
                    thereWhereAnyPixels = true;
                //end if
                xIndex = xIndex + 1;
            }//end while
            yIndex = yIndex + 1;
        }//end while
        return thereWhereAnyPixels;
    }//end downSampleQuadrant








    /**
     * Called to downsample the image and store
     * it in the down sample component.
     */
    public void downSampleImage()
    {
        PixelGrabber pixelGrabber;
        int width;
        int height;
        SampleData sampleData;
        int x;
        int y;
        //
        width = entryImage.getWidth(this);
        height = entryImage.getHeight(this);

        pixelGrabber = new PixelGrabber(entryImage,0,0,width,height,true);

        try
        {
            pixelGrabber.grabPixels();
            pixelMap = (int[])pixelGrabber.getPixels();
            findDownSampleBounds(width,height);

            // now downsample
            sampleData = samplePanel.getData();

            ratioX = (double)(downSampleRight - downSampleLeft) / (double)sampleData.getWidth();
            ratioY = (double)(downSampleBottom - downSampleTop) / (double)sampleData.getHeight();

            y = 0;
            while(y < sampleData.getHeight())
            {
                x = 0;
                while(x < sampleData.getWidth())
                {
                    if ( thereArePixelsInQuadrant(x,y) )
                        sampleData.setData(x,y,true);
                        //end if
                    else
                        sampleData.setData(x,y,false);
                    //end else
                    x = x + 1;
                }//end while
                y = y + 1;
            }//end while

            samplePanel.repaint();
            repaint();
        }//end try 
        catch ( InterruptedException e )
        {
            // nothing yet
        }//end catch
    }//end downSampleImage





    /**
     * Called to clear the image.
     */
    public void clear()
    {
        this.entryGraphics.setColor(Color.white);
        this.entryGraphics.fillRect(0,0,getWidth(),getHeight());
        this.downSampleBottom = 0;
        this.downSampleTop = 0;
        this.downSampleLeft = 0;
        this.downSampleRight = 0;
        repaint();
    }//end clear



}//end DrawPanel