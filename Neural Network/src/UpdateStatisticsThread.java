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

public class UpdateStatisticsThread implements Runnable
{
    long tries;
    double lastError;
    double bestError;
    View view;
    //


    public UpdateStatisticsThread(View aView)
    {
        view = aView;
    }//end constructor


    public void run()
    {
        view.triesResultLabel.setText(""+ tries);
        view.lastErrorResultLabel.setText(""+ lastError);
        view.bestErrorResultLabel.setText(""+ bestError);
    }//end run
}//end class