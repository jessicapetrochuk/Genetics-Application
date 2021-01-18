package model;

import exceptions.EmptyStrandException;
import org.json.JSONObject;
import persistence.Writable;

/** Represents a collection of all of the DNA and RNA strands */
public class StrandList implements Writable {
    private String name;
    private DnaList dnaList;
    private RnaList rnaList;

    public StrandList(String name) {
        this.name = name;
        dnaList = new DnaList();
        rnaList = new RnaList();
    }

    //EFFECTS: gets the name
    public String getName() {
        return name;
    }

    //MODIFIES: this
    //EFFECTS: if strand is a dna strand added to the dna list else added to rna list
    public void addStrand(Strand s) throws EmptyStrandException {
        if (s != null && s.getCategory() == Category.DNASTRANDS) {
            DnaStrand d = new DnaStrand(s.getStrandSequence());
            dnaList.addStrandToList(d);
        } else if (s != null && s.getCategory() == Category.RNASTRANDS) {
            RnaStrand r = new RnaStrand(s.getStrandSequence());
            rnaList.addStrandToList(r);
        } else {
            throw new EmptyStrandException();
        }
    }

    //EFFECTS: gets the dna list
    public DnaList getDnaList() {
        return dnaList;
    }

    //EFFECTS: gets the rna list
    public RnaList getRnaList() {
        return rnaList;
    }

    //EFFECTS: gets the number of DNA strands
    public int numDnaStrands() {
        return dnaList.length();
    }

    //EFFECTS: gets the number of DNA strands
    public int numRnaStrands() {
        return rnaList.length();
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("Name", name);
        json.put("DNA Strands", dnaList.dnaListToJson());
        json.put("RNA Strands", rnaList.rnaListToJson());
        return json;
    }
}
