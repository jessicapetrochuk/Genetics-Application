package model;

import exceptions.EmptyStrandException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StrandListTest {
    private StrandList strandList;

    @BeforeEach
    public void setUp() {
        strandList = new StrandList("Test List");
    }

    @Test
    public void addDNAStrandToListValidTest() {
        DnaStrand dnaStrand = new DnaStrand("AAACCC");
        try {
            strandList.addStrand(dnaStrand);
        } catch (EmptyStrandException e) {
            fail("Exception should not have been thrown");
        }

        assertEquals(1, strandList.getDnaList().length());
        assertEquals(0, strandList.getRnaList().length());
        assertEquals(dnaStrand.strandSequence, strandList.getDnaList().selectStrandAtIndex(0).strandSequence);
    }

    @Test
    public void addDNAStrandToListInvalidTest() {
        DnaStrand dnaStrand = null;
        try {
            strandList.addStrand(dnaStrand);
            fail("EmptyStrandException should be thrown");
        } catch (EmptyStrandException e) {
            //expected
        }

        assertEquals(0, strandList.getDnaList().length());
        assertEquals(0, strandList.getRnaList().length());
    }

    @Test
    public void addRNAStrandToListValidTest() {
        RnaStrand rnaStrand = new RnaStrand("AAACCC");

        try {
            strandList.addStrand(rnaStrand);
        } catch (EmptyStrandException e) {
            fail("Exception should not have been thrown");
        }

        assertEquals(0, strandList.getDnaList().length());
        assertEquals(1, strandList.getRnaList().length());
        assertEquals(rnaStrand.strandSequence, strandList.getRnaList().selectStrandAtIndex(0).strandSequence);
    }

    @Test
    public void addRNAStrandToListInvalidTest() {
        RnaStrand rnaStrand = null;
        try {
            strandList.addStrand(rnaStrand);
            fail("EmptyStrandExcpetion should have been thrown");
        } catch (EmptyStrandException e) {
            //expected
        }
        assertEquals(0, strandList.getDnaList().length());
        assertEquals(0, strandList.getRnaList().length());
    }
}
