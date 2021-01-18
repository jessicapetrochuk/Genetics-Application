package model;

import java.util.ArrayList;
import java.util.List;

/**Represents a single protein which is a list of amino acids*/
public class Protein {
    public List<AminoAcid> protein;

    //EFFECTS: creates a new protein
    public Protein() {
        protein = new ArrayList<>();
    }

    //MODIFIES: this
    //EFFECTS: adds an amino acid to the end of the protein
    public void addAminoAcid(AminoAcid a) {
        protein.add(a);
    }

    //EFFECTS: Prints the protein as a string with the format: "AMINO ACID 1-AMINO ACID 2-..."
    public String printProtein() {
        String strProtein = "";

        for (AminoAcid a: protein) {
            strProtein = strProtein + a.name() + "-";
        }

        strProtein = strProtein.substring(0, strProtein.length() - 1);

        return strProtein;
    }

    //EFFECTS: returns the number of amino acids the protein is made of
    public Integer length() {
        return protein.size();
    }
}
