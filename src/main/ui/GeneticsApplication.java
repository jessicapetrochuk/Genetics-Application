package ui;

import exceptions.EmptyStrandException;
import model.DnaStrand;
import model.RnaStrand;
import model.StrandList;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

import static java.awt.Color.*;

/** Represent the genetics application */
public class GeneticsApplication extends JFrame implements ActionListener {
    public static final int WIDTH = 1000;
    public static final int HEIGHT = 1000;
    private static final Font SMALL_FONT = new Font("Arial", Font.PLAIN, 30);
    private static final String JSON_STORE = "./data/strands.json";

    private JPanel homeDisplay;
    private JPanel strandDisplay;
    private JPanel dnaDisplay;
    private JPanel rnaDisplay;
    private JScrollPane listScroller;
    private JTextField newDnaStrandEntry;
    private JTextField newRnaStrandEntry;
    private JList<DnaStrand> dnaList;
    private JList<RnaStrand> rnaList;
    private DefaultListModel dnaListModel;
    private DefaultListModel rnaListModel;
    private JTextField dnaMutationString;
    private JTextField dnaMutationPosition;
    private JTextField rnaMutationString;
    private JTextField rnaMutationPosition;
    private JLabel selectedDnaStrand;
    private JLabel selectedRnaStrand;
    private JLabel currentDnaTranscribedStrand;
    private JLabel currentDnaTranslatedStrand;
    private JLabel currentRnaTranslatedStrand;
    private StrandList workingStrands;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private DnaStrand currentDnaStrand;
    private RnaStrand currentRnaStrand;

    //EFFECTS: constructs the main window for the application to run in
    public GeneticsApplication() {
        super("Genetics Application");
        init();
        initializeGraphics();
        getHomeDisplay();
        getChooseStrandsDisplay();
    }

    //MODIFIES: this
    //EFFECTS: initializes variables when app initially run
    private void init() {
        currentDnaStrand = new DnaStrand("");
        currentRnaStrand = new RnaStrand("");
        workingStrands = new StrandList("Jessica's strands");
        jsonReader = new JsonReader(JSON_STORE);
        jsonWriter = new JsonWriter(JSON_STORE);
    }

    //MODIFIES: this
    //EFFECTS: draw the window where the genetics application will operate and adds options to the window
    private void initializeGraphics() {
        JPanel mainPanel = new JPanel(new CardLayout());
        mainPanel.setLayout(new CardLayout());
        add(mainPanel);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);

        homeDisplay = new JPanel(new GridLayout(3, 1, 0, 10));
        strandDisplay = new JPanel(new GridLayout(2, 1));
        dnaDisplay = new JPanel(new GridLayout(2, 1, 0, 0));
        rnaDisplay = new JPanel(new GridLayout(2, 1, 0, 0));

        mainPanel.add(homeDisplay);
        mainPanel.add(strandDisplay);
        mainPanel.add(dnaDisplay);
        mainPanel.add(rnaDisplay);

        homeDisplay.setVisible(true);
    }

    //MODIFIES: this
    //EFFECTS: displays the home display on the current JPanel
    private void getHomeDisplay() {
        JLabel welcomeLabel = new JLabel("Welcome to the Genetics App!");
        JButton loadFileButton = new JButton("Load Strands From File");
        JButton newFileButton = new JButton("New File");

        setUpButton(loadFileButton);
        setUpButton(newFileButton);

        homeDisplay.add(welcomeLabel);
        homeDisplay.add(loadFileButton);
        homeDisplay.add(newFileButton);

        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.PLAIN, 50));

        loadFileButton.setActionCommand("loadFile");
        newFileButton.setActionCommand("newFile");
    }

    //MODIFIES: this
    //EFFECTS: displays the choose strand display on the current JPanel
    private void getChooseStrandsDisplay() {
        JButton dnaStrandsButton = new JButton("DNA Strands");
        JButton rnaStrandsButton = new JButton("RNA Strands");

        setUpButton(dnaStrandsButton);
        setUpButton(rnaStrandsButton);

        strandDisplay.add(dnaStrandsButton);
        strandDisplay.add(rnaStrandsButton);

        dnaStrandsButton.setActionCommand("dnaStrands");
        rnaStrandsButton.setActionCommand("rnaStrands");
    }

    /** DNA STRAND DISPLAY **/

    //MODIFIES: this
    //EFFECTS: displays the DNA strands display on the current JPanel
    private void getDnaStrandsDisplay() {
        dnaDisplay.add(dnaStrandsDisplayPanel());
        dnaDisplay.add(dnaStrandsOptionsPanel());
    }

    /** top half of display **/
    //MODIFIES: this
    //EFFECTS: adds the title to the DNA strands panel as well as a list of all of the strands
    private JPanel dnaStrandsDisplayPanel() {
        JPanel displayPanel = new JPanel(new FlowLayout());

        displayPanel.add(configureTitle("DNA STRANDS"));
        JLabel subTitleLabelPart1 = new JLabel("Use the options below to interact with the following strands");
        subTitleLabelPart1.setFont(SMALL_FONT);
        displayPanel.add(subTitleLabelPart1);
        displayPanel.setBackground(white);

        displayPanel.add(configureSpace());
        displayPanel.add(configureDnaListPanel());
        displayPanel.add(configureAddDnaStrandPanel());

        return displayPanel;
    }

    //EFFECTS: constructs a scrollable list of the DNA strands currently available
    private JScrollPane configureDnaListPanel() {
        dnaListModel = new DefaultListModel<>();
        dnaList = new JList(dnaListModel);
        dnaList.setFont(SMALL_FONT);

        listScroller = new JScrollPane(dnaList);
        listScroller.setBorder(null);
        listScroller.setPreferredSize(new Dimension(1000, 230));
        for (DnaStrand d: workingStrands.getDnaList().list) {
            dnaListModel.addElement(d.getStrandSequence());
        }

        return listScroller;
    }

    //EFFECTS: constructs a text field to add a new DNA strand. If the DNA strand is a set of 3 A, T, C, or Gs then
    //         when the add strand button is selected the strand is added to the list of DNA strands.
    private JPanel configureAddDnaStrandPanel() {
        JPanel addStrandPanel = new JPanel(new FlowLayout());

        JLabel addDnaStrandLabel = new JLabel("Add a new DNA strand, remember must be sets of 3 A, T, C, and "
                + "Gs: ");

        newDnaStrandEntry = new JTextField();
        newDnaStrandEntry.setFont(SMALL_FONT);
        addDnaStrandLabel.setForeground(black);
        newDnaStrandEntry.setPreferredSize(new Dimension(600, 50));
        addDnaStrandLabel.setFont(SMALL_FONT);

        addStrandPanel.add(addDnaStrandLabel);
        addStrandPanel.add(newDnaStrandEntry);
        addStrandPanel.add(configureSubmitDnaStrandButton());
        addStrandPanel.setPreferredSize(new Dimension(1000, 100));
        addStrandPanel.setBackground(WHITE);

        return addStrandPanel;
    }

    //EFFECTS: creates a button that adds an DNA strand to the DNA strand list if the input is valid
    private JButton configureSubmitDnaStrandButton() {
        JButton submitStrandButton = new JButton("add strand");
        setUpButton(submitStrandButton);
        submitStrandButton.setActionCommand("submitDNAStrand");
        submitStrandButton.setFont(SMALL_FONT);
        submitStrandButton.setPreferredSize(new Dimension(300, 50));
        return submitStrandButton;
    }

    /** bottom half of display **/
    //MODIFIES: this
    //EFFECTS: adds all of the options necessary for interacting with the DNA strands
    private JPanel dnaStrandsOptionsPanel() {
        JPanel buttonsPanel = new JPanel(new GridLayout(9, 1));

        buttonsPanel.add(constructCurrentDnaStrandButton());
        buttonsPanel.add(configureSpace());
        buttonsPanel.add(currentDnaStrandPanel());
        buttonsPanel.add(transcribeDnaStrandPanel());
        buttonsPanel.add(translateDnaStrandPanel());
        buttonsPanel.add(configureDnaMutateStrandPanel());
        buttonsPanel.add(configureSpace());
        buttonsPanel.add(configureBackButton());
        buttonsPanel.add(configureSaveButton());

        return buttonsPanel;
    }

    //EFFECTS: creates a new button to get the current strand selected
    private JButton constructCurrentDnaStrandButton() {
        JButton getCurrentStrandButton = new JButton("GET THE CURRENT STRAND");
        getCurrentStrandButton.addActionListener(this);
        getCurrentStrandButton.setActionCommand("getDnaStrand");
        setUpButton(getCurrentStrandButton);
        getCurrentStrandButton.setPreferredSize(new Dimension(1000, 50));
        return getCurrentStrandButton;
    }

    //EFFECTS: displays the currently selected DNA strand when the get current strand button is selected
    private JPanel currentDnaStrandPanel() {
        JPanel currentStrandPanel = new JPanel(new BorderLayout());
        JLabel currentStrandPanelTitle = new JLabel("Currently selected DNA Strand: ");
        currentStrandPanelTitle.setFont(SMALL_FONT);
        currentStrandPanel.add(currentStrandPanelTitle, BorderLayout.LINE_START);
        selectedDnaStrand = new JLabel("");
        selectedDnaStrand.setFont(SMALL_FONT);
        currentStrandPanel.add(selectedDnaStrand, BorderLayout.CENTER);
        return currentStrandPanel;
    }

    //EFFECTS: displays the transcribed DNA strand of the current strand when the get current strand button is selected
    private JPanel transcribeDnaStrandPanel() {
        JPanel transcribedStrandPanel = new JPanel(new BorderLayout());
        JLabel transcribedStrandPanelTitle = new JLabel("Corresponding RNA Strand: ");
        transcribedStrandPanelTitle.setFont(SMALL_FONT);
        transcribedStrandPanel.add(transcribedStrandPanelTitle, BorderLayout.LINE_START);
        currentDnaTranscribedStrand = new JLabel("");
        currentDnaTranscribedStrand.setFont(SMALL_FONT);
        transcribedStrandPanel.add(currentDnaTranscribedStrand, BorderLayout.CENTER);
        return transcribedStrandPanel;
    }

    //EFFECTS: displays the translated DNA strand of the current strand when the get current strand button is selected
    private JPanel translateDnaStrandPanel() {
        JPanel translatedStrandPanel = new JPanel(new BorderLayout());
        JLabel translatedStrandPanelTitle = new JLabel("Corresponding Amino Acid Sequence: ");
        translatedStrandPanelTitle.setFont(SMALL_FONT);
        translatedStrandPanel.add(translatedStrandPanelTitle, BorderLayout.LINE_START);
        currentDnaTranslatedStrand = new JLabel("");
        currentDnaTranslatedStrand.setFont(SMALL_FONT);
        translatedStrandPanel.add(currentDnaTranslatedStrand, BorderLayout.CENTER);
        return translatedStrandPanel;
    }

    //EFFECTS: constructs a mutation display that allows a user to input a position to mutate and a nucleotide to change
    //         this position to. If both are valid the strand is mutated when the user selects the mutate button
    private JPanel configureDnaMutateStrandPanel() {
        JPanel mutateStrandPanel = new JPanel(new BorderLayout());
        JPanel mutateStrandTextPanel = new JPanel(new FlowLayout());

        JLabel mutationLabelPartOne = new JLabel("Mutate the selected strand at position");
        mutationLabelPartOne.setFont(SMALL_FONT);

        JLabel mutationLabelPartTwo = new JLabel("with");
        mutationLabelPartTwo.setFont(SMALL_FONT);

        dnaMutationPosition = new JTextField();
        dnaMutationPosition.setPreferredSize(new Dimension(50, 50));
        dnaMutationPosition.setFont(SMALL_FONT);

        dnaMutationString = new JTextField();
        dnaMutationString.setPreferredSize(new Dimension(50, 50));
        dnaMutationString.setFont(SMALL_FONT);

        mutateStrandTextPanel.add(mutationLabelPartOne);
        mutateStrandTextPanel.add(dnaMutationPosition);
        mutateStrandTextPanel.add(mutationLabelPartTwo);
        mutateStrandTextPanel.add(dnaMutationString);

        JButton mutateButton = new JButton("Mutate!");
        setUpButton(mutateButton);
        mutateButton.setActionCommand("mutateDna");
        mutateButton.setFont(SMALL_FONT);

        mutateStrandPanel.add(mutateStrandTextPanel, BorderLayout.LINE_START);
        mutateStrandPanel.add(mutateButton);

        return mutateStrandPanel;
    }

    /** RNA STRAND DISPLAY **/

    //MODIFIES: this
    //EFFECTS: displays the RNA strands display on the current JPanel
    private void configureRnaDisplay() {
        rnaDisplay.add(rnaStrandsDisplayPanel());
        rnaDisplay.add(rnaStrandsOptionsPanel());
    }

    /** top half of display **/
    //MODIFIES: this
    //EFFECTS: adds the title to the RNA strands panel as well as a list of all of the strands
    private JPanel rnaStrandsDisplayPanel() {
        JPanel displayPanel = new JPanel(new FlowLayout());

        displayPanel.add(configureTitle("RNA STRANDS"));
        JLabel subTitleLabelPart1 = new JLabel("Use the options below to interact with the following strands");
        subTitleLabelPart1.setFont(SMALL_FONT);
        displayPanel.add(subTitleLabelPart1);
        displayPanel.setBackground(white);

        displayPanel.add(configureSpace());
        displayPanel.add(configureRnaListPanel());
        displayPanel.add(configureAddRnaStrandPanel());

        return displayPanel;
    }

    //EFFECTS: constructs a scrollable list of the DNA strands currently available
    private JScrollPane configureRnaListPanel() {
        rnaListModel = new DefaultListModel<>();
        rnaList = new JList(rnaListModel);
        rnaList.setFont(SMALL_FONT);

        listScroller = new JScrollPane(rnaList);
        listScroller.setBorder(null);
        listScroller.setPreferredSize(new Dimension(1000, 230));
        for (RnaStrand r: workingStrands.getRnaList().list) {
            rnaListModel.addElement(r.getStrandSequence());
        }

        return listScroller;
    }

    //EFFECTS: constructs a text field to add a new DNA strand. If the DNA strand is a set of 3 A, T, C, or Gs then
    //         when the add strand button is selected the strand is added to the list of DNA strands.
    private JPanel configureAddRnaStrandPanel() {
        JPanel addStrandPanel = new JPanel(new FlowLayout());

        JLabel addRnaStrandLabel = new JLabel("Add a new RNA strand, remember must be sets of 3 A, U, C, and "
                + "Gs: ");

        newRnaStrandEntry = new JTextField();
        newRnaStrandEntry.setFont(SMALL_FONT);
        addRnaStrandLabel.setForeground(black);
        newRnaStrandEntry.setPreferredSize(new Dimension(600, 50));
        addRnaStrandLabel.setFont(SMALL_FONT);

        addStrandPanel.add(addRnaStrandLabel);
        addStrandPanel.add(newRnaStrandEntry);
        addStrandPanel.add(configureSubmitRnaStrandButton());
        addStrandPanel.setPreferredSize(new Dimension(1000, 100));
        addStrandPanel.setBackground(WHITE);

        return addStrandPanel;
    }

    //EFFECTS: creates a button that adds an RNA strand to the RNA strand list if the input is valid
    private JButton configureSubmitRnaStrandButton() {
        JButton submitStrandButton = new JButton("add strand");
        setUpButton(submitStrandButton);
        submitStrandButton.setActionCommand("submitRNAStrand");
        submitStrandButton.setFont(SMALL_FONT);
        submitStrandButton.setPreferredSize(new Dimension(300, 50));
        return submitStrandButton;
    }

    /** bottom half of display **/
    //MODIFIES: this
    //EFFECTS: adds all of the options necessary for interacting with the DNA strands
    private JPanel rnaStrandsOptionsPanel() {
        JPanel buttonsPanel = new JPanel(new GridLayout(8,1));

        buttonsPanel.add(constructCurrentRnaStrandButton());
        buttonsPanel.add(configureSpace());
        buttonsPanel.add(currentRnaStrandPanel());
        buttonsPanel.add(translateRnaStrandPanel());
        buttonsPanel.add(configureRnaMutateStrandPanel());
        buttonsPanel.add(configureSpace());
        buttonsPanel.add(configureBackButton());
        buttonsPanel.add(configureSaveButton());

        return buttonsPanel;
    }

    //EFFECTS: creates a new button to get the current start selected
    private JButton constructCurrentRnaStrandButton() {
        JButton getCurrentStrandButton = new JButton("GET THE CURRENT STRAND");
        getCurrentStrandButton.addActionListener(this);
        getCurrentStrandButton.setActionCommand("getRnaStrand");
        setUpButton(getCurrentStrandButton);
        getCurrentStrandButton.setPreferredSize(new Dimension(1000, 50));
        return getCurrentStrandButton;
    }

    //EFFECTS: displays the currently selected DNA strand when the get current strand button is selected
    private JPanel currentRnaStrandPanel() {
        JPanel currentStrandPanel = new JPanel(new BorderLayout());
        JLabel currentStrandPanelTitle = new JLabel("Currently selected RNA Strand: ");
        currentStrandPanelTitle.setFont(SMALL_FONT);
        currentStrandPanel.add(currentStrandPanelTitle, BorderLayout.LINE_START);
        selectedRnaStrand = new JLabel("");
        selectedRnaStrand.setFont(SMALL_FONT);
        currentStrandPanel.add(selectedRnaStrand, BorderLayout.CENTER);
        return currentStrandPanel;
    }

    //EFFECTS: displays the translated RNA strand of the current strand when the get current strand button is selected
    private JPanel translateRnaStrandPanel() {
        JPanel translatedStrandPanel = new JPanel(new BorderLayout());
        JLabel translatedStrandPanelTitle = new JLabel("Corresponding Amino Acid Sequence: ");
        translatedStrandPanelTitle.setFont(SMALL_FONT);
        translatedStrandPanel.add(translatedStrandPanelTitle, BorderLayout.LINE_START);
        currentRnaTranslatedStrand = new JLabel("");
        currentRnaTranslatedStrand.setFont(SMALL_FONT);
        translatedStrandPanel.add(currentRnaTranslatedStrand, BorderLayout.CENTER);
        return translatedStrandPanel;
    }

    //EFFECTS: constructs a mutation display that allows a user to input a position to mutate and a nucleotide to change
    //         this position to. If both are valid the strand is mutated when the user selects the mutate button
    private JPanel configureRnaMutateStrandPanel() {
        JPanel mutateStrandPanel = new JPanel(new BorderLayout());
        JPanel mutateStrandTextPanel = new JPanel(new FlowLayout());

        JLabel mutationLabelPartOne = new JLabel("Mutate the selected strand at position");
        mutationLabelPartOne.setFont(SMALL_FONT);

        JLabel mutationLabelPartTwo = new JLabel("with");
        mutationLabelPartTwo.setFont(SMALL_FONT);

        rnaMutationPosition = new JTextField();
        rnaMutationPosition.setPreferredSize(new Dimension(50, 50));
        rnaMutationPosition.setFont(SMALL_FONT);

        rnaMutationString = new JTextField();
        rnaMutationString.setPreferredSize(new Dimension(50, 50));
        rnaMutationString.setFont(SMALL_FONT);

        mutateStrandTextPanel.add(mutationLabelPartOne);
        mutateStrandTextPanel.add(rnaMutationPosition);
        mutateStrandTextPanel.add(mutationLabelPartTwo);
        mutateStrandTextPanel.add(rnaMutationString);

        JButton mutateButton = new JButton("Mutate!");
        setUpButton(mutateButton);
        mutateButton.setActionCommand("mutateRna");
        mutateButton.setFont(SMALL_FONT);

        mutateStrandPanel.add(mutateStrandTextPanel, BorderLayout.LINE_START);
        mutateStrandPanel.add(mutateButton);

        return mutateStrandPanel;
    }

    @Override
    //MODIFIES: this
    //EFFECTS: processes the buttons pressed in the application
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("dnaStrands") || e.getActionCommand().equals("rnaStrands")
                || e.getActionCommand().equals("newFile") || e.getActionCommand().equals("loadFile")) {
            switchDisplay(e.getActionCommand());
        } else if (e.getActionCommand().equals("back")) {
            goBack();
        } else if (e.getActionCommand().equals("save")) {
            saveStrands();
        } else if (e.getActionCommand().equals("submitDNAStrand")) {
            submitDnaStrand();
        } else if (e.getActionCommand().equals("submitRNAStrand")) {
            submitRnaStrand();
        } else if (e.getActionCommand().equals("getDnaStrand")) {
            getSelectedDnaStrand();
        } else if (e.getActionCommand().equals("getRnaStrand")) {
            getSelectedRnaStrand();
        } else if (e.getActionCommand().equals("mutateDna")) {
            mutateDna();
        } else if (e.getActionCommand().equals("mutateRna")) {
            mutateRna();
        }
    }

    //MODIFIES: this
    //EFFECTS: if the user input string and position is valid, mutates the selected DNA strand at the given position
    //         with the given nucleotide
    private void mutateDna() {
        if (dnaList.getSelectedValue() != null) {
            try {
                Integer mutationPosition = Integer.parseInt(dnaMutationPosition.getText());
                String mutationString = String.valueOf(dnaMutationString.getText().toUpperCase());
                String dnaStrandString = String.valueOf(dnaList.getSelectedValue());
                DnaStrand dnaStrand = new DnaStrand(dnaStrandString);

                if (catchDnaMutationError(dnaStrand, mutationPosition, mutationString)) {
                    dnaStrand.mutateStrand(mutationPosition, mutationString);
                    dnaListModel.setElementAt(dnaStrand.getStrandSequence(), dnaList.getSelectedIndex());
                    selectedDnaStrand.setText(String.valueOf(dnaList.getSelectedValue()));
                    RnaStrand rnaStrand = new RnaStrand(dnaStrand.translateStrand());
                    currentDnaTranscribedStrand.setText(dnaStrand.translateStrand());
                    currentDnaTranslatedStrand.setText(rnaStrand.transcribeStrand().printProtein());
                    currentDnaStrand.mutateStrand(mutationPosition, mutationString);
                }
                dnaMutationPosition.setText("");
                dnaMutationString.setText("");
            } catch (NumberFormatException numberFormatException) {
                dnaMutationPosition.setText("");
                dnaMutationString.setText("");
            }
        }
    }

    //MODIFIES: this
    //EFFECTS: if the user input string and position is valid, mutates the selected DNA strand at the given position
    //         with the given nucleotide
    private void mutateRna() {
        if (rnaList.getSelectedValue() != null) {
            try {
                Integer mutationPosition = Integer.parseInt(rnaMutationPosition.getText());
                String mutationString = String.valueOf(rnaMutationString.getText().toUpperCase());
                String rnaStrandString = String.valueOf(rnaList.getSelectedValue());
                RnaStrand rnaStrand = new RnaStrand(rnaStrandString);

                if (catchRnaMutationError(rnaStrand, mutationPosition, mutationString)) {
                    rnaStrand.mutateStrand(mutationPosition, mutationString);
                    rnaListModel.setElementAt(rnaStrand.getStrandSequence(), rnaList.getSelectedIndex());
                    selectedRnaStrand.setText(String.valueOf(rnaList.getSelectedValue()));
                    currentRnaTranslatedStrand.setText(rnaStrand.transcribeStrand().printProtein());
                    currentRnaStrand.mutateStrand(mutationPosition, mutationString);
                }
                rnaMutationPosition.setText("");
                rnaMutationString.setText("");
            } catch (NumberFormatException numberFormatException) {
                rnaMutationPosition.setText("");
                rnaMutationString.setText("");
            }
        }
    }

    //MODIFIES: this
    //EFFECTS: changes the current RNA strand displayed to the one the user has selected as well as the translated
    //         strand
    private void getSelectedRnaStrand() {
        if (rnaList.getSelectedValue() != null) {
            for (RnaStrand r: workingStrands.getRnaList().list) {
                if (r.getStrandSequence() == String.valueOf(rnaList.getSelectedValue())) {
                    currentRnaStrand = r;
                }
            }
            selectedRnaStrand.setText(String.valueOf(rnaList.getSelectedValue()));
            String rnaStrandString = String.valueOf(rnaList.getSelectedValue());
            RnaStrand rnaStrand = new RnaStrand(rnaStrandString);
            currentRnaTranslatedStrand.setText(rnaStrand.transcribeStrand().printProtein());
        }
    }

    //MODIFIES: this
    //EFFECTS: changes the current DNA strand displayed to the one the user has selected as well as the transcribed
    //         strand and the translated strand
    private void getSelectedDnaStrand() {
        if (dnaList.getSelectedValue() != null) {
            for (DnaStrand d: workingStrands.getDnaList().list) {
                if (d.getStrandSequence() == String.valueOf(dnaList.getSelectedValue())) {
                    currentDnaStrand = d;
                }
            }
            selectedDnaStrand.setText(String.valueOf(dnaList.getSelectedValue()));
            String dnaStrandString = String.valueOf(dnaList.getSelectedValue());
            DnaStrand dnaStrand = new DnaStrand(dnaStrandString);
            RnaStrand rnaStrand = new RnaStrand(dnaStrand.translateStrand());
            currentDnaTranscribedStrand.setText(dnaStrand.translateStrand());
            currentDnaTranslatedStrand.setText(rnaStrand.transcribeStrand().printProtein());
        }
    }

    //MODIFIES: this
    //EFFECTS: if the RNA input is valid RNA strand is added to the working strands and is added to the displayed list
    //         of RNA strands
    private void submitRnaStrand() {
        RnaStrand newStrand = new RnaStrand(newRnaStrandEntry.getText().toUpperCase());
        try {
            if (catchRnaStrandError(newStrand.getStrandSequence())) {
                workingStrands.addStrand(newStrand);
                rnaListModel.addElement(newStrand.getStrandSequence().toUpperCase());
            }
        } catch (EmptyStrandException emptyStrandException) {
            emptyStrandException.printStackTrace();
        }
        newRnaStrandEntry.setText("");
    }

    //MODIFIES: this
    //EFFECTS: if the DNA input is valid DNA strand is added to the working strands and is added to the displayed list
    //         of DNA strands
    private void submitDnaStrand() {
        DnaStrand newStrand = new DnaStrand(newDnaStrandEntry.getText().toUpperCase());
        try {
            if (catchDnaStrandError(newStrand.getStrandSequence())) {
                workingStrands.addStrand(newStrand);
                dnaListModel.addElement(newStrand.getStrandSequence().toUpperCase());
            }
        } catch (EmptyStrandException emptyStrandException) {
            emptyStrandException.printStackTrace();
        }
        newDnaStrandEntry.setText("");
    }

    //EFFECTS: changes the display from DNA or RNA display to strand display
    private void goBack() {
        dnaDisplay.setVisible(false);
        rnaDisplay.setVisible(false);
        strandDisplay.setVisible(true);
    }

    //EFFECTS: changes the display to DNA or RNA display from strand display
    private void switchDisplay(String actionCommand) {
        if (actionCommand.equals("dnaStrands")) {
            strandDisplay.setVisible(false);
            dnaDisplay.setVisible(true);
        } else if (actionCommand.equals("rnaStrands")) {
            strandDisplay.setVisible(false);
            rnaDisplay.setVisible(true);
        } else if (actionCommand.equals("newFile")) {
            switchToStrandDisplay();
        } else if (actionCommand.equals("loadFile")) {
            loadStrands();
            switchToStrandDisplay();
        }
    }

    //EFFECTS: changes the display to strand display from home display
    private void switchToStrandDisplay() {
        homeDisplay.setVisible(false);
        strandDisplay.setVisible(true);
        configureRnaDisplay();
        getDnaStrandsDisplay();
    }

    //EFFECTS: sets up a title
    private JLabel configureTitle(String title) {
        JLabel dnaListLabel = new JLabel(title);
        dnaListLabel.setFont(new Font("Arial", Font.PLAIN, 40));
        dnaListLabel.setHorizontalAlignment(SwingConstants.CENTER);
        return dnaListLabel;
    }

    //EFFECTS: creates a back button
    private JButton configureBackButton() {
        JButton backButton = new JButton("back");
        setUpButton(backButton);
        backButton.setActionCommand("back");
        backButton.setPreferredSize(new Dimension(300, 50));
        return backButton;
    }

    //EFFECTS: creates a save button
    private JButton configureSaveButton() {
        JButton saveButton = new JButton("save");
        setUpButton(saveButton);
        saveButton.setActionCommand("save");
        saveButton.setPreferredSize(new Dimension(300, 50));
        return saveButton;
    }

    //EFFECTS: creates a space
    private JLabel configureSpace() {
        JLabel space = new JLabel("");
        space.setPreferredSize(new Dimension(1000, 30));
        return space;
    }

    //MODIFIES: this
    //EFFECTS: creates the designs for the buttons in the application
    private void setUpButton(JButton button) {
        button.setForeground(Color.BLACK);
        button.setBackground(Color.WHITE);
        button.setFont(new Font("Arial", Font.PLAIN, 40));
        button.addActionListener(this);
    }

    //MODIFIES: this
    //EFFECTS: loads strands from file
    private void loadStrands() {
        try {
            workingStrands = jsonReader.read();
            System.out.println("Loaded " + workingStrands.getName() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read file: " + JSON_STORE);
        }
    }

    //EFFECTS: saves strands to file
    private void saveStrands() {
        try {
            jsonWriter.open();
            jsonWriter.write(workingStrands);
            jsonWriter.close();
            System.out.println("Saved " + workingStrands.getName() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file:" + JSON_STORE);
        }
    }

    /** CHECK FOR INCORRECT INPUTS **/
    //EFFECTS: checks if the DNA input is valid
    private boolean catchDnaStrandError(String dnaInput) {
        if (dnaInput.matches("[ATCG]+")) {
            return dnaInput.length() % 3 == 0;
        } else {
            return false;
        }
    }

    //EFFECTS: checks if the RNA input is valid
    private boolean catchRnaStrandError(String dnaInput) {
        if (dnaInput.matches("[AUCG]+")) {
            return dnaInput.length() % 3 == 0;
        } else {
            return false;
        }
    }

    //EFFECTS: returns true if the DNA mutation is valid
    private boolean catchDnaMutationError(DnaStrand d, Integer p, String c) {
        if (p >= d.getStrandSequence().length()) {
            return false;
        } else if (c.length() > 1) {
            return false;
        } else {
            return c.matches("[ATCG]+");
        }
    }

    //EFFECTS: checks if the RNA mutation is valid
    private boolean catchRnaMutationError(RnaStrand r, Integer p, String c) {
        if (p >= r.getStrandSequence().length()) {
            return false;
        } else if (c.length() > 1) {
            return false;
        } else {
            return c.matches("[AUCG]+");
        }
    }

    //EFFECTS: runs application
    public static void main(String[] args) {
        new GeneticsApplication();
    }
}
