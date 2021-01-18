package model;

import exceptions.EmptyStrandException;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

/**Represents a list of DNA strands*/
public class DnaList implements Writable {
    public List<DnaStrand> list;
    private String listName;

    //EFFECTS: create a new list of DNA strands
    public DnaList() {
        listName = "DNA List";
        list = new ArrayList<>();
    }

    //MODIFIES: this
    //EFFECTS: adds strand to list of Dna strands
    public void addStrandToList(DnaStrand d) throws EmptyStrandException {
        if (d != null) {
            list.add(d);
        } else {
            throw new EmptyStrandException();
        }
    }

    //EFFECTS: prints all DNA strands in the list with the format "index: strand"
    public String printStrandsInList() {
        String printedList = "";

        for (Strand d : list) {
            printedList = printedList + "\t" + list.indexOf(d) + ": " + d.getStrandSequence();
        }

        return printedList;
    }

    //EFFECTS: produces the length of the strand
    public Integer length() {
        return list.size();
    }

    //REQUIRES: Index must be within the length of the string
    //EFFECTS: prints the strand at given index in the list
    public DnaStrand selectStrandAtIndex(Integer index) {
        return list.get(index);
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();

        json.put("name", listName);
        json.put("DNA Strand", dnaListToJson());

        return json;
    }

    //EFFECTS: returns strands in this DNA list as a JSON array
    public JSONArray dnaListToJson() {
        JSONArray jsonArray = new JSONArray();

        for (DnaStrand d : list) {
            jsonArray.put(d.toJson());
        }

        return jsonArray;
    }
}
