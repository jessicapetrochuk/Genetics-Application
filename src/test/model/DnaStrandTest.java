package model;

import exceptions.InvalidDnaInputException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DnaStrandTest {
    private DnaStrand dnaStrand;

    @BeforeEach
    public void setUp() {
        dnaStrand = new DnaStrand("AAACCCGGGTTT");
    }

    @Test
    public void translateStrandTest() {
        assertEquals("UUUGGGCCCAAA", dnaStrand.translateStrand());
        dnaStrand = new DnaStrand("CTGCTA");
        assertEquals("GACGAU", dnaStrand.translateStrand());
    }

    @Test
    public void addToStrandInvalidInputTest() {
        try {
            dnaStrand.addToStrand("ZZZ");
        } catch (InvalidDnaInputException e){
            //expected
        }
    }

    @Test
    public void addToStrandValidInputTest() {
        try {
            dnaStrand.addToStrand("AAACCCGGGTTT");
            assertEquals("AAACCCGGGTTTAAACCCGGGTTT", dnaStrand.getStrandSequence());
        } catch (InvalidDnaInputException e){
            fail("Exception should not be thrown");
        }
    }

    @Test
    public void mutateStringTestA() {
        assertEquals("AAACCCGGGTTT", dnaStrand.getStrandSequence());
        dnaStrand.mutateStrand(11, "A");
        assertEquals("AAACCCGGGTTA", dnaStrand.getStrandSequence());
    }

    @Test
    public void mutateStringTestT() {
        assertEquals("AAACCCGGGTTT", dnaStrand.getStrandSequence());
        dnaStrand.mutateStrand(0, "C");
        assertEquals("CAACCCGGGTTT", dnaStrand.getStrandSequence());
    }

    @Test
    public void mutateStringTestC() {
        assertEquals("AAACCCGGGTTT", dnaStrand.getStrandSequence());
        dnaStrand.mutateStrand(5, "T");
        assertEquals("AAACCTGGGTTT", dnaStrand.getStrandSequence());
    }

    @Test
    public void mutateStringTestG() {
        assertEquals("AAACCCGGGTTT", dnaStrand.getStrandSequence());
        dnaStrand.mutateStrand(3, "G");
        assertEquals("AAAGCCGGGTTT", dnaStrand.getStrandSequence());
    }
}