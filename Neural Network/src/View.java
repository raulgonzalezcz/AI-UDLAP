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
import java.awt.Container;
import java.awt.Font;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;


public class View extends JFrame
{

    // The downsample width for the application.
    static final int DOWNSAMPLE_WIDTH = 5;
    // The down sample height for the application.
    static final int DOWNSAMPLE_HEIGHT = 7;
    //The drawPanel component for the user to draw into.
    DrawPanel drawPanel;
    // The down sample component to display the drawing downsampled.
    SamplePanel samplePanel;

    Container contentPane;
    //
    JButton downSampleButton;
    JButton addButton;
    JButton clearButton;
    JButton recognizeButton;
    JButton deleteButton;
    JButton loadButton;
    JButton saveButton;
    JButton trainButton;
    //
    DefaultListModel letterListModel;
    JList lettersList;
    JScrollPane scrollPane;
    //
    JLabel lettersKnownLabel;
    JLabel triesLabel;
    JLabel lastErrorLabel;
    JLabel bestErrorLabel;
    JLabel triesResultLabel;
    JLabel lastErrorResultLabel;
    JLabel bestErrorResultLabel;
    JLabel trainingResultsLabel;
    JLabel drawLettersHereLabel;
    //
    Color color;
    //


    public View(String aTitle)
    {
        super(aTitle);

        setTitle(aTitle);
        setSize(405,382);
        setLayout(null);
        color = new Color(153, 255, 204);
        contentPane = getContentPane();
        contentPane.setBackground(color);

        drawPanel = new DrawPanel();
        drawPanel.setBounds(168,25,200,128);
        add(drawPanel);

        samplePanel = new SamplePanel(DOWNSAMPLE_WIDTH,DOWNSAMPLE_HEIGHT);
        samplePanel.setBounds(307,210,65,70);
        drawPanel.setSamplePanel(samplePanel);
        add(samplePanel);

        lettersList = new JList();
        lettersList.setBounds(0,0,126,129);
        letterListModel = new DefaultListModel();
        lettersList.setModel(letterListModel);

        downSampleButton = new JButton("Down Sample");
        downSampleButton.setBounds(252,180,120,24);
        downSampleButton.setActionCommand("Down Sample");
        add(downSampleButton);

        addButton = new JButton("Add");
        addButton.setBounds(168,156,84,24);
        addButton.setActionCommand("Add");
        add(addButton);

        clearButton = new JButton("Clear");
        clearButton.setBounds(168,180,84,24);
        clearButton.setActionCommand("Clear");
        add(clearButton);

        recognizeButton = new JButton("Recognize");
        recognizeButton.setBounds(252,156,120,24);
        recognizeButton.setActionCommand("Recognize");
        add(recognizeButton);

        deleteButton = new JButton("Delete");
        deleteButton.setBounds(12,156,144,24);
        deleteButton.setActionCommand("Delete");
        add(deleteButton);

        loadButton = new JButton("Load");
        loadButton.setBounds(12,180,72,24);
        loadButton.setActionCommand("Load");
        add(loadButton);

        saveButton = new JButton("Save");
        saveButton.setBounds(84,180,72,24);
        saveButton.setActionCommand("Save");
        add(saveButton);

        trainButton = new JButton("Begin Training");
        trainButton.setBounds(12,204,144,24);
        trainButton.setActionCommand("Begin Training");
        add(trainButton);

        scrollPane = new JScrollPane();
        scrollPane.setBounds(12,24,144,132);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setOpaque(true);
        add(scrollPane);
        scrollPane.getViewport().add(lettersList);

        lettersKnownLabel = new JLabel("Letters Known");
        lettersKnownLabel.setBounds(12,12,94,12);
        add(lettersKnownLabel);

        drawLettersHereLabel = new JLabel("Draw Letters Here");
        drawLettersHereLabel.setBounds(204,12,144,12);
        add(drawLettersHereLabel);

        triesLabel = new JLabel("Tries");
        triesLabel.setBounds(12,264,72,24);
        add(triesLabel);

        lastErrorLabel = new JLabel("Last Error:");
        lastErrorLabel.setBounds(12,288,72,24);
        add(lastErrorLabel);

        bestErrorLabel = new JLabel("Best Error:");
        bestErrorLabel.setBounds(12,312,72,24);
        add(bestErrorLabel);

        trainingResultsLabel = new JLabel("Training Results");
        trainingResultsLabel.setBounds(12,240,120,24);
        trainingResultsLabel.setHorizontalTextPosition(SwingConstants.CENTER);
        trainingResultsLabel.setHorizontalAlignment(SwingConstants.CENTER);
        trainingResultsLabel.setFont(new Font("Dialog", Font.BOLD, 14));
        add(trainingResultsLabel);

        triesResultLabel = new JLabel("0");
        triesResultLabel.setBounds(96,264,72,24);
        add(triesResultLabel);

        lastErrorResultLabel = new JLabel("0");
        lastErrorResultLabel.setBounds(96,288,72,24);
        add(lastErrorResultLabel);

        bestErrorResultLabel = new JLabel("0");
        bestErrorResultLabel.setBounds(96,312,72,24);
        add(bestErrorResultLabel);

    }//end constructor

    public void setActionListener(Controller controller)
    {
        downSampleButton.addActionListener(controller);
        clearButton.addActionListener(controller);
        addButton.addActionListener(controller);
        deleteButton.addActionListener(controller);
        loadButton.addActionListener(controller);
        saveButton.addActionListener(controller);
        trainButton.addActionListener(controller);
        recognizeButton.addActionListener(controller);
        lettersList.addListSelectionListener(controller);
    }//end setActionListener


    public void inicia()
    {
        setVisible(true);
    }//end inicia

}//end class

