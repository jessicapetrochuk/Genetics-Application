package model;

import org.json.JSONObject;
import persistence.Writable;

/** Represents a strand of nucleotides */
public abstract class Strand implements Writable {
    protected String strandSequence;
    private Category category;

    //EFFECTS: Creates a new strand
    public Strand(String s, Category category) {
        strandSequence = s;
        this.category = category;
    }

    //EFFECTS: returns the strand's category
    public Category getCategory() {
        return category;
    }


    //EFFECTS: returns the DNA strand as a string
    public String getStrandSequence() {
        return strandSequence;
    }

    //MODIFIES: this
    //EFFECTS: Changes the nucleotide at position pos to change
    public abstract void mutateStrand(Integer pos, String change);

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("strand", strandSequence);
        return json;
    }
}
