
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


import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStream;
import java.io.PrintStream;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;


public class Controller implements ActionListener, ListSelectionListener, Runnable
{

    // The downsample width for the application.
    static final int DOWNSAMPLE_WIDTH = 5;
    // The down sample height for the application.
    static final int DOWNSAMPLE_HEIGHT = 7;
    // The neural network.
    KohonenNetwork neuralNetwork;
    // The background thread used for training.
    Thread trainingBakgroundThread;
    //
    View view;
    //




    public Controller(View aView)
    {
        view = aView;
        trainingBakgroundThread = null;
    }//end constructor



    void updateStatistics(final long trial,final double theLastError,final double theBestError)
    {
        UpdateStatisticsThread updateStatisticsThread;
        //
        if ((((trial % 100) != 0) || (trial == 10)) &&
                !neuralNetwork.hasToAbortLearning )
            return;
        //end if

        if(neuralNetwork.hasToAbortLearning)
        {
            trainingBakgroundThread = null;
            view.trainButton.setText("Begin Training");
            JOptionPane.showMessageDialog(view,
                    "Training has completed.","Training",
                    JOptionPane.PLAIN_MESSAGE);
        }//end if
        updateStatisticsThread = new UpdateStatisticsThread(view);
        updateStatisticsThread.tries = trial;
        updateStatisticsThread.lastError = theLastError;
        updateStatisticsThread.bestError = theBestError;
        try
        {
            SwingUtilities.invokeAndWait(updateStatisticsThread);
        }//end try
        catch ( Exception e )
        {
            JOptionPane.showMessageDialog(view,"Error: " + e,"Training",
                    JOptionPane.ERROR_MESSAGE);
        }//end catch
    }//end updateStats





    void lettersValueChanged(ListSelectionEvent event)
    {
        SampleData selected;
        //
        if(view.lettersList.getSelectedIndex() == -1)
            return;
        //end if
        selected = (SampleData)view.letterListModel.getElementAt(view.lettersList.getSelectedIndex());
        view.samplePanel.setData((SampleData)selected.clone());
        view.samplePanel.repaint();
        view.drawPanel.clear();
    }//end letters_valueChanged



    public void valueChanged(ListSelectionEvent event)
    {
        Object object;
        //
        object = event.getSource();
        if ( object == view.lettersList )
            lettersValueChanged(event);
        //end if
    }//end valueChanged






    // ACTIONS PERFORMED /////////////////////////////////////////////

    void downSampleActionPerformed(ActionEvent event)
    {
        view.drawPanel.downSampleImage();
    }//end downSample_actionPerformed



    void clearActionPerformed(ActionEvent event)
    {
        view.drawPanel.clear();
        view.samplePanel.getData().clear();
        view.samplePanel.repaint();
    }//end clearActionPerformed




    void addActionPerformed(ActionEvent event)
    {
        int i;
        String letter;
        SampleData sampleData;
        Comparable letterModel;
        //
        letter = JOptionPane.showInputDialog("Please enter a letter you would like to assign this sample to.");
        if(letter == null)
            return;
        //end if
        if(letter.length() > 1)
        {
            JOptionPane.showMessageDialog(view,
                    "Please enter only a single letter.","Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }//end if
        view.drawPanel.downSampleImage();
        sampleData = (SampleData)view.samplePanel.getData().clone();
        sampleData.setLetter(letter.charAt(0));

        i = 0;
        while(i < view.letterListModel.size())
        {
            letterModel = (Comparable)view.letterListModel.getElementAt(i);
            if ( letterModel.equals(letter) )
            {
                JOptionPane.showMessageDialog(view,
                        "That letter is already defined, delete it first!","Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }//end if
            if ( letterModel.compareTo(sampleData)>0 )
            {
                view.letterListModel.add(i,sampleData);
                return;
            }//end if
            i = i + 1;
        }//end while

        view.letterListModel.add(view.letterListModel.size(),sampleData);
        view.lettersList.setSelectedIndex(i);
        view.drawPanel.clear();
        view.samplePanel.repaint();

    }//end addActionPerformed




    void delActionPerformed(ActionEvent event)
    {
        int i;
        //
        i = view.lettersList.getSelectedIndex();
        if(i == -1)
        {
            JOptionPane.showMessageDialog(view,
                    "Please select a letter to delete.","Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }//end if
        view.letterListModel.remove(i);
    }//end delActionPerformed




    void loadActionPerformed(ActionEvent event)
    {
        FileReader fileReader;
        BufferedReader bufferedReader;
        SampleData sampleData;
        int index;
        String line;
        int i;
        int x;
        int y;
        //
        try
        {
            fileReader = new FileReader( new File("C:/Users/2924/Desktop/datos/sample.txt") );
            bufferedReader = new BufferedReader(fileReader);
            i=0;
            view.letterListModel.clear();

            line = bufferedReader.readLine();
            while(line != null)
            {
                sampleData = new  SampleData(line.charAt(0),DOWNSAMPLE_WIDTH,DOWNSAMPLE_HEIGHT);

                view.letterListModel.add(i++,sampleData);
                index = 2;
                y = 0;
                while(y < sampleData.getHeight())
                {
                    x = 0;
                    while(x < sampleData.getWidth())
                    {
                        sampleData.setData(x,y,line.charAt(index++) == '1');
                        x = x + 1;
                    }//end while
                    y = y + 1;
                }//end while
                line = bufferedReader.readLine();
            }//end while

            bufferedReader.close();
            fileReader.close();
            clearActionPerformed(null);
            JOptionPane.showMessageDialog(view,
                    "Loaded from 'sample.dat'.","Training",
                    JOptionPane.PLAIN_MESSAGE);
        }//end try
        catch ( Exception e )
        {
            JOptionPane.showMessageDialog(view,
                    "Error: " + e,"Training",
                    JOptionPane.ERROR_MESSAGE);
        }//end catch

    }//end loadActionPerformed







    void saveActionPerformed(ActionEvent event)
    {
        OutputStream outputStream;
        PrintStream printStream;
        SampleData sampleData;
        int i;
        int x;
        int y;
        //
        try
        {
            outputStream = new FileOutputStream("C:/Users/2924/Desktop/datos/sample.txt",false );
            printStream = new PrintStream(outputStream);

            i = 0;
            while(i < view.letterListModel.size())
            {
                sampleData = (SampleData)view.letterListModel.elementAt(i);
                printStream.print( sampleData.getLetter() + ":" );

                y = 0;
                while(y < sampleData.getHeight())
                {
                    x = 0;
                    while(x < sampleData.getWidth())
                    {
                        printStream.print( sampleData.getData(x,y)?"1":"0" );
                        x = x + 1;
                    }//end while
                    y = y + 1;
                }//end while

                printStream.println("");
                i = i + 1;
            }//end while

            printStream.close();
            outputStream.close();
            clearActionPerformed(null);
            JOptionPane.showMessageDialog(view,
                    "Saved to 'sample.dat'.",
                    "Training",
                    JOptionPane.PLAIN_MESSAGE);

        }//end try
        catch ( Exception e )
        {
            JOptionPane.showMessageDialog(view,"Error: " +
                            e,"Training",
                    JOptionPane.ERROR_MESSAGE);
        }//end catch

    }//end saveActionPerformed







    // TRAINING ///////////////////////////////////////////////////////////////////////////////////


    public void showTrainingSet(TrainingSet trainingSet)
    {
        int i;
        int j;
        //

        System.out.println("--- TRAINING SET - INPUT ---");
        i = 0;
        while( i < trainingSet.trainingSetCount)
        {
            System.out.print("Set# " + i + ": [");
            j = 0;
            while( j < trainingSet.inputCount)
            {
                System.out.print(trainingSet.input[i][j] + "   ");
                j = j + 1;
            }//end while
            System.out.println("]");
            i = i + 1;
        }//end while
        System.out.println("---");

        System.out.println("--- TRAINING SET - OUTPUT ---");
        i = 0;
        while( i < trainingSet.trainingSetCount)
        {
            System.out.print("Set# " + i + ": [");
            j = 0;
            while( j < trainingSet.outputCount)
            {
                System.out.print(trainingSet.output[i][j] + "   ");
                j = j + 1;
            }//end while
            System.out.println("]");
            i = i + 1;
        }//end while
        System.out.println("---");

        System.out.println("--- TRAINING SET - CLASSIFY ---");
        System.out.print("[");
        i = 0;
        while( i < trainingSet.trainingSetCount)
        {
            System.out.print(trainingSet.classify[i]+ "  ");
            i = i + 1;
        }//end while
        System.out.println("]");
        System.out.println("---");

    }//end showTrainingSet


    /**
     * Run method for the trainingBakgroundThread
     */
    public void run()
    {
        int numberOfInputNeurons;
        int numberOfOutputNeurons;
        TrainingSet trainingSet;
        int letterListIndex;
        int index;
        SampleData sampleData;
        int x;
        int y;
        double value;
        //

        try
        {
            // 1.- Define the number of input and output neurons
            numberOfInputNeurons = DOWNSAMPLE_HEIGHT * DOWNSAMPLE_WIDTH;
            numberOfOutputNeurons = view.letterListModel.size();

            System.out.println("Number of Input Neurons: "+numberOfInputNeurons );
            System.out.println("Number of Output Neurons: "+numberOfOutputNeurons );

            // 2.- Construct the training set
            trainingSet = new TrainingSet(numberOfInputNeurons,numberOfOutputNeurons);
            trainingSet.setTrainingSetCount(numberOfOutputNeurons);

            // 3.- Initialize the training set
            letterListIndex = 0;
            while(letterListIndex < view.letterListModel.size())
            {
                index = 0;
                // determines the sample data based on the letter indexed by letterListIndex
                sampleData = (SampleData)view.letterListModel.getElementAt(letterListIndex);

                showSampleData(sampleData);

                y = 0;
                while(y < sampleData.getHeight())
                {
                    x = 0;
                    while(x < sampleData.getWidth())
                    {
                        if(sampleData.getData(x,y))
                        {
                            value = 0.5;
                        }//end if
                        else
                        {
                            value = -0.5;
                        }//end else
                        // assign the corresponding value (0.5 or -0.5) to the training set at the index
                        trainingSet.setATrainingSetInput(letterListIndex,index,value);
                        index = index + 1;
                        x = x + 1;
                    }//end while
                    y = y + 1;
                }//end while

                // next letter for training
                letterListIndex = letterListIndex + 1;
            }//end while

            showTrainingSet(trainingSet);

            // 4.- Construct the Kohonen neural network
            neuralNetwork = new KohonenNetwork(numberOfInputNeurons,numberOfOutputNeurons,this);

            // 5.- Assign the training set to the Kohonen neural network
            neuralNetwork.setTrainingSet(trainingSet);

            // 6.- Start the neural network learning process with the assigned training set
            neuralNetwork.learn();
        }//end try
        catch ( Exception e )
        {
            JOptionPane.showMessageDialog(view,"Error: " + e,
                    "Training",
                    JOptionPane.ERROR_MESSAGE);
        }//end catch
    }//end run



    void trainActionPerformed(ActionEvent event)
    {
        if (trainingBakgroundThread == null)
        {
            view.trainButton.setText("Stop Training");
            view.trainButton.repaint();
            trainingBakgroundThread = new Thread(this);
            // starts the training background thread
            trainingBakgroundThread.start();
        }//end if
        else
        {
            neuralNetwork.hasToAbortLearning = true;
        }//end else
    }//end trainActionPerformed




    // RECOGNITION ///////////////////////////////////////////////////////////////////////////////////


    public void showSampleData(SampleData sample)
    {
        int y;
        int x;
        //
        System.out.println("-------");
        y = 0;
        while(y < DOWNSAMPLE_HEIGHT)
        {
            System.out.print("|");
            x = 0;
            while(x < DOWNSAMPLE_WIDTH)
            {
                if(sample.getData(x,y))
                    System.out.print("@");
                else
                    System.out.print(" ");
                x = x + 1;
            }//end while
            System.out.println("|");
            y = y + 1;
        }//end while
        System.out.println("-------");
    }//end showInputPattern


    public void showInputPattern(double[] inputPattern)
    {
        int i;
        //
        System.out.print("[");
        i = 0;
        while(i < DOWNSAMPLE_HEIGHT*DOWNSAMPLE_WIDTH)
        {
            System.out.print(inputPattern[i] + " ");
            i = i + 1;
        }//end while
        System.out.println("]");
    }//end showInputPattern


    public void showSelfOrganizingMap(char[] selfOrganizingMap)
    {
        int i;
        //
        System.out.println("--- Self Organizing Map ---");
        System.out.print("[  ");
        i = 0;
        while(i < selfOrganizingMap.length)
        {
            System.out.print(selfOrganizingMap[i] + "  ");
            i = i + 1;
        }//end while
        System.out.println("]");
    }//end showSelfOrganizingMap


    // SOM : Self Organizing Map.
    // It is called â€œSelf-Organizingâ€? because no supervision is required.
    char[] getSelfOrganizingMap()
    {
        char selfOrganizingMap[];
        double normalizationFactor[];
        double syntheticLastInput[];
        int i;
        int index;
        int x;
        int y;
        int winningNeuronId;
        double inputPattern[];
        SampleData sampleData;
        //
        System.out.println("********************************");
        System.out.println("Constructing Self Organizing Map");
        System.out.println("********************************");
        // 1.- Constructs the empty map as a vector or characters
        selfOrganizingMap = new char[view.letterListModel.size()];

        normalizationFactor = new double[1];
        syntheticLastInput = new double[1];

        // 2.- Initializes the map with '?'
        i = 0;
        while(i < selfOrganizingMap.length)
        {
            selfOrganizingMap[i]='?';
            i = i + 1;
        }//end while

        // 3.- Defines the input pattern (assign 0.5 or -0.5)
        i = 0;
        while(i<view.letterListModel.size())
        {
            inputPattern = new double[DOWNSAMPLE_WIDTH * DOWNSAMPLE_HEIGHT];
            index =0;
            sampleData = (SampleData)view.letterListModel.getElementAt(i);

            y = 0;
            while(y < sampleData.getHeight())
            {
                x = 0;
                while(x < sampleData.getWidth())
                {
                    if(sampleData.getData(x,y))
                    {
                        inputPattern[index] = 0.5;
                    }//end if
                    else
                    {
                        inputPattern[index] = -0.5;
                    }//end else
                    index = index + 1;
                    x = x + 1;
                }//end while
                y = y + 1;
            }//end while

            // 4.- Get the winining neuron based on the input pattern
            winningNeuronId = neuralNetwork.getWinningNeuronId(inputPattern,
                    normalizationFactor,syntheticLastInput);
            // 5.- Assign the corresponding letter of the sample data
            // into the map at the winning neuron index
            selfOrganizingMap[winningNeuronId] = sampleData.getLetter();
            i = i + 1;
        }//end while

        return selfOrganizingMap;
    }//end getSelfOrganizingMap





    void recognizeActionPerformed(ActionEvent event)
    {
        double inputPattern[];
        double normalizationFactor[];
        double syntheticLastInput[];
        int winningNeuronId;
        char selfOrganizingMap[];
        SampleData patternToRecognize;
        int index;
        int x;
        int y;
        //

        // 1.- Check if the neural network exists (has been trained)
        if (neuralNetwork == null)
        {
            JOptionPane.showMessageDialog(view,
                    "I need to be trained first!","Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }//end if

        // 2.- Get sample data to recognize
        view.drawPanel.downSampleImage();
        patternToRecognize = view.samplePanel.getData();

        System.out.println("--- DATA TO RECOGNIZE ---");
        showSampleData(patternToRecognize);

        // 3.- Construct the corresponding inputPattern
        inputPattern = new double[DOWNSAMPLE_WIDTH * DOWNSAMPLE_HEIGHT];
        index = 0;

        // 4.- Define the input pattern
        y = 0;
        while(y < patternToRecognize.getHeight())
        {
            x = 0;
            while(x < patternToRecognize.getWidth())
            {
                if(patternToRecognize.getData(x,y))
                {
                    inputPattern[index] = 0.5;
                }//end if
                else
                {
                    inputPattern[index] = -0.5;
                }//end else
                index = index + 1;
                x = x + 1;
            }//end while
            y = y + 1;
        }//end while

        System.out.println("--- PATTERN TO RECOGNIZE ---");
        showInputPattern(inputPattern);

        // 5.- Construct the normalization factor
        normalizationFactor = new double[1];

        // 6.- Construct the synthetic last input
        syntheticLastInput = new double[1];

        // 7.- Get the winner neuron id
        winningNeuronId = neuralNetwork.getWinningNeuronId(inputPattern,
                normalizationFactor, syntheticLastInput);

        System.out.println("Winning neuron id (Index in Self Organizong Map):" + winningNeuronId);
        // 8.- Construct and get the self organizing map
        selfOrganizingMap = getSelfOrganizingMap();

        showSelfOrganizingMap(selfOrganizingMap);

        // 9.- Present as result the winning neuron from the self organizing map
        System.out.println("RECOGNITION RESULT: "+ selfOrganizingMap[winningNeuronId]);
        JOptionPane.showMessageDialog(view,
                "  " + selfOrganizingMap[winningNeuronId] + "   (Neuron #" + winningNeuronId + " fired)",
                "That Letter Is",
                JOptionPane.PLAIN_MESSAGE);
        clearActionPerformed(null);

    }//end recognizeActionPerformed

    // actionPerformed /////////////////////////////////////////////////////////////////////////////////


    public void actionPerformed(java.awt.event.ActionEvent event)
    {
        Object object;
        //

        object = event.getSource();

        if ( object == view.downSampleButton )
            downSampleActionPerformed(event);
        else if ( object == view.clearButton )
            clearActionPerformed(event);
        else if ( object == view.addButton )
            addActionPerformed(event);
        else if ( object == view.deleteButton )
            delActionPerformed(event);
        else if ( object == view.loadButton )
            loadActionPerformed(event);
        else if ( object == view.saveButton )
            saveActionPerformed(event);
        else if ( object == view.trainButton )
            trainActionPerformed(event);
        else if ( object == view.recognizeButton )
            recognizeActionPerformed(event);
    }//end actionPerformed


}//end class