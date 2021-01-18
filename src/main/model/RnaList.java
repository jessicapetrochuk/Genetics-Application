package model;

import exceptions.EmptyStrandException;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

/** Represents a list of RNA strands */
public class RnaList implements Writable {
    private String listName;
    public List<RnaStrand> list;

    //EFFECTS: create a new list of DNA strands
    public RnaList() {
        listName = "RNA List";
        list = new ArrayList<>();
    }

    //EFFECTS: returns the name of the list
    public String getName() {
        return listName;
    }

    //MODIFIES: this
    //EFFECTS: adds strand to list of Dna strands
    public void addStrandToList(RnaStrand r) throws EmptyStrandException {
        if (r != null) {
            list.add(r);
        } else {
            throw new EmptyStrandException();
        }
    }

    //EFFECTS: prints all RNA strands in the list with the format: "index: strand"
    public String printStrandsInList() {
        String printedList = "";

        for (RnaStrand r: list) {
            printedList = printedList + "\t" + list.indexOf(r) + ": " + r.getStrandSequence();
        }

        return printedList;
    }

    //EFFECTS: returns the length of the list
    public Integer length() {
        return list.size();
    }

    //REQUIRES: Index must be within the length of the string
    //EFFECTS: prints the strand at given index in the list
    public RnaStrand selectStrandAtIndex(Integer index) {
        return list.get(index);
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();

        json.put("name", listName);
        json.put("RNA Strand", rnaListToJson());

        return json;
    }

    //EFFECTS: returns strands in this RNA list as a JSON array
    public JSONArray rnaListToJson() {
        JSONArray jsonArray = new JSONArray();

        for (RnaStrand r : list) {
            jsonArray.put(r.toJson());
        }
        
        return jsonArray;
    }
}
