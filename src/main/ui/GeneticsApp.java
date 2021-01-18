package ui;

import exceptions.EmptyStrandException;
import model.*;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

/** Represents the genetics application */
public class GeneticsApp {

    private static final String JSON_STORE = "./data/strands.json";
    private Scanner input;
    private StrandList workingStrands;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private DnaStrand currentDnaStrand;
    private RnaStrand currentRnaStrand;
    private boolean appRunning;

    //EFFECTS: constructs new DNA and RNA lists
    public GeneticsApp() throws FileNotFoundException {
        System.out.println("HELLO AND WELCOME TO THE GENETICS APP");

        currentDnaStrand = new DnaStrand("");
        currentRnaStrand = new RnaStrand("");

        workingStrands = new StrandList("Jessica's strands");
        jsonReader = new JsonReader(JSON_STORE);
        jsonWriter = new JsonWriter(JSON_STORE);

        appRunning = true;
        runApp();
    }

    //MODIFIES: this
    //EFFECTSl processes user input
    private void runApp() {
        String action;
        input = new Scanner(System.in);

        while (appRunning) {
            displayMenu();
            action = input.next();
            action = action.toLowerCase();

            if (action.equals("q")) {
                appRunning = false;
                System.out.println("Thank you for using the genetics app!");
            } else {
                processMainAction(action);
            }
        }
    }

    //REQUIRES: input is not null
    //EFFECTS: processes the users input
    private void processMainAction(String action) {
        switch (action) {
            case "d":
                createDnaStrand();
                break;
            case "r":
                createRnaStrand();
                break;
            case "sd":
            case "sr":
                displayStrands(action);
                break;
            case "s":
                saveStrands();
                break;
            case "l":
                loadStrands();
                break;
            default:
                System.out.println("Sorry, the selection was not valid please try again");
                break;
        }
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

    //EFFECTS: displays the current list of DNA strands if the action = "sd" and RNA strands if the action = "sr"
    private void displayStrands(String action) {
        System.out.println("\nHere are the strands you currently have:");
        if (action.equals("sd")) {
            System.out.println(workingStrands.getDnaList().printStrandsInList());
        } else {
            System.out.println(workingStrands.getRnaList().printStrandsInList());
        }

        System.out.println("\nWhat you like to do?");
        System.out.println("\ts -> select a strand from the list");
        System.out.println("\tm -> go back to the main menu");

        String userInput = input.next();
        if (userInput.equals("s")) {
            if (action.equals("sd")) {
                selectNewDnaStrand(action);
            } else {
                selectNewRnaStrand(action);
            }
        } else if (userInput.equals("m")) {
            runApp();
        } else {
            System.out.println("Sorry, the selection was not valid please try again");
        }
    }

    //MODIFIES: this
    //EFFECTS: selects a new DNA strand as the current working strand
    private void selectNewDnaStrand(String action) {
        System.out.println("\nWhich number strand would you like to select");
        Integer userInputInt = input.nextInt();
        if (userInputInt < workingStrands.getDnaList().length()) {
            currentDnaStrand = workingStrands.getDnaList().selectStrandAtIndex(userInputInt);
            dnaStrandActions();
        } else {
            System.out.println("Sorry that selection is not valid");
            displayStrands(action);
        }
    }

    //MODIFIES: this
    //EFFECTS: selects a new RNA strand as the current working strand
    private void selectNewRnaStrand(String action) {
        System.out.println("\nWhich number strand would you like to select");
        Integer userInputInt = input.nextInt();
        if (userInputInt < workingStrands.getRnaList().length()) {
            currentRnaStrand = workingStrands.getRnaList().selectStrandAtIndex(userInputInt);
            rnaStrandActions();
        } else {
            System.out.println("Sorry that selection is not valid");
            displayStrands(action);
        }
    }


    //MODIFIES: this
    //EFFECTS: if the input consists of only As Cs Ts and Gs and is has a length divisible by 3
    //         strand is added to the list of DNA strands
    //         else prompts user to input again
    private void createDnaStrand() {
        System.out.println("\nPlease input your DNA strand sequence with the following requirements");
        System.out.println("\tDNA strand must only consist of As, Cs, Ts, and Gs");
        System.out.println("\tDNA strand must be comprised of codons thus must be a lengths divisible by 3");

        String dnaInput = input.next();
        if (!catchDnaStrandError(dnaInput)) {
            System.out.println("Looks like you didn't follow the requirements, please input your strand again");
        } else {
            DnaStrand dnaStrand = new DnaStrand(dnaInput);

            try {
                workingStrands.addStrand(dnaStrand);
                currentDnaStrand = dnaStrand;
                dnaStrandActions();
            } catch (EmptyStrandException e) {
                System.out.println("Cannot input an empty strand!");
                runApp();
            }
        }
    }

    //MODIFIES: this
    //EFFECTS: if the input consists of only As Cs Us and Gs and is has a length divisible by 3
    //         strand is added to the list of DNA strands
    //         else prompts user to input again
    private void createRnaStrand() {
        System.out.println("\nPlease input your RNA strand sequence with the following requirements");
        System.out.println("\tDNA strand must only consist of As, Cs, Us, and Gs");
        System.out.println("\tDNA strand must be comprised of codons thus must be a lengths divisible by 3");

        String rnaInput = input.next();
        if (!catchRnaStrandError(rnaInput)) {
            System.out.println("Looks like you didn't follow the requirements, please input your strand again");
        } else {
            RnaStrand rnaStrand = new RnaStrand(rnaInput);

            try {
                workingStrands.addStrand(rnaStrand);
                currentRnaStrand = rnaStrand;
                rnaStrandActions();
            } catch (EmptyStrandException e) {
                System.out.println("Cannot input an empty strand!");
                runApp();
            }
        }
    }

    //EFFECTS: displays the possible options when a DNA strand is selected
    private void dnaStrandActions() {
        boolean workingWithDna = true;
        String action;

        while (workingWithDna) {
            System.out.println("\nWhat would you like to do?");
            System.out.println("\tt -> Translate the strand to RNA");
            System.out.println("\tm -> Mutate the DNA strand");
            System.out.println("\tq -> Go back to main menu");
            action = input.next();
            action = action.toLowerCase();

            if (action.equals("q")) {
                workingWithDna = false;
                runApp();
            } else {
                processDnaAction(action);
            }
        }
    }

    //EFFECTS: displays the possible options when a RNA strand is selected
    private void rnaStrandActions() {
        boolean workingWithRna = true;
        String action;

        while (workingWithRna) {
            System.out.println("\nWhat would you like to do?");
            System.out.println("\tt -> Transcribe the strand to a protein");
            System.out.println("\tm -> Mutate the RNA strand");
            System.out.println("\tq -> Go back to main menu");
            action = input.next();
            action = action.toLowerCase();

            if (action.equals("q")) {
                workingWithRna = false;
                runApp();
            } else {
                processRnaAction(action);
            }
        }
    }

    //MODIFIES: this
    //EFFECTS: if the user chooses to translate the strand the translated strand is printed
    //         if the user chooses to mutate the strand the strand is changed at the given position with the given
    //         nucleotide
    private void processDnaAction(String action) {
        if (action.equals("t")) {
            String translatedStrand = currentDnaStrand.translateStrand();
            System.out.println("Here is your translated strand as RNA: " + translatedStrand);
        } else if (action.equals("m")) {
            System.out.println("Please specify the position you would like to change");
            try {
                Integer position = input.nextInt();
                System.out.println("Please specify what you would like to change this position to (A, T, C, or G)");
                String mutation = input.next().toUpperCase();

                if (catchDnaMutationError(currentDnaStrand, position, mutation)) {
                    currentDnaStrand.mutateStrand(position, mutation);
                    System.out.println("Here is your new strand with the mutation: "
                            + currentDnaStrand.getStrandSequence());
                } else {
                    System.out.println("Sorry it looks like the mutation you input is not valid");
                }
            } catch (Exception e) {
                System.out.println("Sorry that was not a valid input");
                dnaStrandActions();
            }
        } else {
            System.out.println("Sorry, the selection was not valid please try again");
        }
    }

    //MODIFIES: this
    //EFFECTS: if the user chooses to transcribe the strand the protein sequence is printed
    //         if the user chooses to mutate the strand the strand is changed at the given position with the given
    //         nucleotide
    private void processRnaAction(String action) {
        if (action.equals("t")) {
            Protein transcribedStrand = currentRnaStrand.transcribeStrand();
            System.out.println("Here is your transcribed strand as a protein: " + transcribedStrand.printProtein());
        } else if (action.equals("m")) {
            System.out.println("Please specify the position you would like to change");
            try {
                Integer position = input.nextInt();
                System.out.println("Please specify what you would like to change this position to (A, U, C, or G)");
                String mutation = input.next().toUpperCase();

                if (catchRnaMutationError(currentRnaStrand, position, mutation)) {
                    currentRnaStrand.mutateStrand(position, mutation);
                    System.out.println("Here is your new strand with the mutation: "
                            + currentRnaStrand.getStrandSequence());
                } else {
                    System.out.println("Sorry it looks like the mutation you input is not valid");
                }
            } catch (Exception e) {
                System.out.println("Sorry that was not a valid input");
                rnaStrandActions();
            }

        } else {
            System.out.println("Sorry, the selection was not valid please try again");
        }
    }

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

    //EFFECTS: checks if the DNA mutation is valid
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

    //EFFECTS: displays main menu of options for user to select from
    private void displayMenu() {
        System.out.println("\nPlease select one of the following options:");
        System.out.println("\td -> input a new strand of DNA");
        System.out.println("\tr -> input a new strand of RNA");
        System.out.println("\tsd -> select from your list of DNA strands");
        System.out.println("\tsr -> select from your list of RNA strands");
        System.out.println("\ts -> save strands to file");
        System.out.println("\tl -> load strands from file");
        System.out.println("\tq -> quit");
    }
}
