package model;

import exceptions.InvalidDnaInputException;
import org.json.JSONObject;
import persistence.Writable;

/**Represents a single DNA strand*/
public class DnaStrand extends Strand {
    //EFFECTS: Create a new DNA strand
    //         A DNA strand is a sequence of As, Ts, Cs, and Gs
    public DnaStrand(String s) {
        super(s, Category.DNASTRANDS);
    }

    //REQUIRES: String must consist of As, Ts, Cs, and Gs only
    //MODIFIES: this
    //EFFECTS: adds given sequence to the end of the DNA strand
    public void addToStrand(String s) throws InvalidDnaInputException {
        if (s.matches("[ATCG]+")) {
            strandSequence = strandSequence + s;
        } else {
            throw new InvalidDnaInputException();
        }
    }

    //REQUIRES: Position of change is within length of strand
    //          Change must be A, T, C, or G
    //MODIFIES: this
    //EFFECTS: Changes the nucleotide at position pos to given change
    public void mutateStrand(Integer pos, String change) {
        String mutatedStrand = null;
        char carChange = change.charAt(0);

        for (int i = 0; i < strandSequence.length(); i++) {
            char c = strandSequence.charAt(i);

            if (i == pos) {
                char[] strandChar = strandSequence.toCharArray();
                strandChar[i] = carChange;
                mutatedStrand = String.valueOf(strandChar);
                break;
            }
        }

        strandSequence = mutatedStrand;
    }

    //REQUIRES: Not empty DNA Strand
    //          DNA strand comprised of only As, Ts, Cs, and Gs
    //EFFECTS: translates DNA strand to a RNA strand
    public String translateStrand() {
        String translatedStrand = "";

        for (int i = 0; i < strandSequence.length(); i++) {
            char c = strandSequence.charAt(i);

            if (c == 'A') {
                translatedStrand = translatedStrand + 'U';
            } else if (c == 'T') {
                translatedStrand = translatedStrand + 'A';
            } else if (c == 'G') {
                translatedStrand = translatedStrand + 'C';
            } else {
                translatedStrand = translatedStrand + 'G';
            }
        }

        return translatedStrand;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("sequence", strandSequence);
        return json;
    }
}
