package persistence;

import exceptions.EmptyStrandException;
import model.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

// Represents a reader that reads all strands from JSON data file
public class JsonReader {
    private String source;

    // EFFECTS: creates a reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads strands from file and returns them;
    //          throws IOException if an error occurs when reading data
    public StrandList read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseStrandList(jsonObject);
    }

    // EFFECTS: read source file as string and return it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses strands from JSON object and returns it
    private StrandList parseStrandList(JSONObject jsonObject) {
        String name = jsonObject.getString("Name");
        StrandList s = new StrandList(name);
        addRnaStrands(s, jsonObject);
        addDnaStrands(s, jsonObject);
        return s;
    }

    // MODIFIES: s
    // EFFECTS: parses RNA strands from JSON object and adds them to workroom
    private void addRnaStrands(StrandList s, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("RNA Strands");
        for (Object json : jsonArray) {
            JSONObject nextRnaStrand = (JSONObject) json;
            addOneRnaStrand(s, nextRnaStrand);
        }
    }

    // MODIFIES: s
    // EFFECTS: parses DNA strands from JSON object and adds them to workroom
    private void addDnaStrands(StrandList s, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("DNA Strands");
        for (Object json : jsonArray) {
            JSONObject nextDnaStrand = (JSONObject) json;
            addOneDnaStrand(s, nextDnaStrand);
        }
    }

    // MODIFIES: s
    // EFFECTS: parses RNA strand from JSON object and adds it to all RNA strands
    private void addOneRnaStrand(StrandList s, JSONObject jsonObject) {
        String strandSequence = jsonObject.getString("sequence");

        RnaStrand strand = new RnaStrand(strandSequence);
        try {
            s.addStrand(strand);
        } catch (EmptyStrandException e) {
            System.out.println("Cannot input an empty strand!");
        }
    }

    // MODIFIES: s
    // EFFECTS: parses DNA strand from JSON object and adds it to all DNA strands
    private void addOneDnaStrand(StrandList s, JSONObject jsonObject) {
        String strandSequence = jsonObject.getString("sequence");

        DnaStrand strand = new DnaStrand(strandSequence);
        try {
            s.addStrand(strand);
        } catch (EmptyStrandException e) {
            System.out.println("Cannot input an empty strand!");
        }
    }
}