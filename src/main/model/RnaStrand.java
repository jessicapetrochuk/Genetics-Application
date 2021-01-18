package model;

import exceptions.InvalidDnaInputException;
import org.json.JSONObject;

/** Represents a strand of RNA */
public class RnaStrand extends Strand {
    //REQUIRES: The length of the strand must be divisible by 3
    //EFFECTS: Create a new RNA strand
    public RnaStrand(String s) {
        super(s, Category.RNASTRANDS);
    }

    //REQUIRES: String must consist of As, Ts, Cs, and Gs only
    //MODIFIES: this
    //EFFECTS: adds given sequence to the end of the DNA strand
    public void addToStrand(String s) throws InvalidDnaInputException {
        if (s.matches("[AUCG]+")) {
            strandSequence = strandSequence + s;
        } else {
            throw new InvalidDnaInputException();
        }
    }

    //REQUIRES: Position of strand is within length of strand
    //          Change must be A, T, C, or G
    //MODIFIES: this
    //EFFECTS: Changes the nucleotide at position pos to change
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

    //REQUIRES: Not empty RnaStrand
    //          RNA strand comprised of only As, Us, Cs, and Gs
    //EFFECTS: translates RNA to a protein
    public Protein transcribeStrand() {
        Protein p = new Protein();

        for (int i = 0; i < strandSequence.length(); i = i + 3) {
            int lastIndex = i + 3;

            String codon = strandSequence.substring(i, lastIndex);
            Codon enumCodon = Codon.valueOf(codon);
            AminoAcid a = CodonToAmino.map.get(enumCodon);

            p.addAminoAcid(a);
        }

        return p;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("sequence", strandSequence);
        return json;
    }
}