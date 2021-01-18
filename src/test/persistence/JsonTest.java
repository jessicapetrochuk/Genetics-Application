package persistence;

import model.DnaStrand;
import model.RnaStrand;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkDnaStrand(String sequence, DnaStrand strand) {
        assertEquals(sequence, strand.getStrandSequence());
    }

    protected void checkRnaStrand(String sequence, RnaStrand strand) {
        assertEquals(sequence, strand.getStrandSequence());
    }
}
