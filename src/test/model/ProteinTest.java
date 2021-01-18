package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ProteinTest {
    public Protein protein;

    @BeforeEach
    public void setup() {
        protein = new Protein();
    }

    @Test
    public void proteinToStringTest() {
        protein.addAminoAcid(AminoAcid.PHE);
        protein.addAminoAcid(AminoAcid.ALA);

        assertEquals("PHE-ALA", protein.printProtein());
        assertEquals(2, protein.length());

        protein.addAminoAcid(AminoAcid.PHE);
        protein.addAminoAcid(AminoAcid.ARG);

        assertEquals("PHE-ALA-PHE-ARG", protein.printProtein());
        assertEquals(4, protein.length());
    }
}
