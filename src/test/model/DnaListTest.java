package model;

import exceptions.EmptyStrandException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class DnaListTest {
    public DnaList list;
    public DnaStrand d1;
    public DnaStrand d2;
    public DnaStrand d3;

    @BeforeEach
    public void setUp() {
        list = new DnaList();
        d1 = new DnaStrand("AAACCCGGG");
        d2 = new DnaStrand("CCCTTTGGG");
        d3 = new DnaStrand("AAACCCTTT");

        try {
            list.addStrandToList(d1);
            list.addStrandToList(d2);
            list.addStrandToList(d3);
        } catch (EmptyStrandException e) {
            fail("Exception should not be thrown");
        }
    }

    @Test
    public void addToListValidTest() {
        assertEquals(3, list.length());

        DnaStrand d4 = new DnaStrand("AAACCC");

        try {
            list.addStrandToList(d4);
        } catch (EmptyStrandException e) {
            fail("Exception should not be thrown");
        }

        assertEquals(4, list.length());
    }

    @Test
    public void addToListInvalidTest() {
        assertEquals(3, list.length());

        DnaStrand d4 = null;
        try {
            list.addStrandToList(d4);
            fail("EmptyStrandException should have been thrown");
        } catch (EmptyStrandException e) {
            //expected
        }

        assertEquals(3, list.length());
    }

    @Test
    public void printStrandsTest() {
        assertEquals("\t0: AAACCCGGG\t1: CCCTTTGGG\t2: AAACCCTTT", list.printStrandsInList());
    }

    @Test
    public void selectStrandAtIndex0Test() {
        assertEquals(d1, list.selectStrandAtIndex(0));
    }

    @Test
    public void selectStrandAtIndexMaxLengthTest() {
        assertEquals(d3, list.selectStrandAtIndex(2));
    }

    @Test
    public void selectStrandAtIndexMiddleTest() {
        assertEquals(d2, list.selectStrandAtIndex(1));
    }
}
