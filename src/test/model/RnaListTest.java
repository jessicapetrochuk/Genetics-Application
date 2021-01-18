package model;

import exceptions.EmptyStrandException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RnaListTest {
    public RnaList list;
    public RnaStrand r1;
    public RnaStrand r2;
    public RnaStrand r3;


    @BeforeEach
    public void setUp() {
        list = new RnaList();
        r1 = new RnaStrand("AAACCCGGG");
        r2 = new RnaStrand("CCCUUUGGG");
        r3 = new RnaStrand("AAACCCUUU");

        try{
            list.addStrandToList(r1);
            list.addStrandToList(r2);
            list.addStrandToList(r3);
        } catch (EmptyStrandException e){
            fail("Exception should not have been thrown");
        }

    }

    @Test
    public void addToListValidTest() {
        assertEquals(3, list.length());

        RnaStrand r4 = new RnaStrand("AAACCC");
        try {
            list.addStrandToList(r4);
        } catch (EmptyStrandException e) {
            fail("Exception should not have been thrown");
        }

        assertEquals(4, list.length());
    }

    @Test
    public void addToListInvalidTest() {
        assertEquals(3, list.length());

        RnaStrand r4 = null;
        try {
            list.addStrandToList(r4);
            fail("EmptyStrandException should have been thrown");
        } catch (EmptyStrandException e) {
            //expected
        }

        assertEquals(3, list.length());
    }

    @Test
    public void printStrandsTest() {
        assertEquals("\t0: AAACCCGGG\t1: CCCUUUGGG\t2: AAACCCUUU", list.printStrandsInList());
    }

    @Test
    public void selectStrandAtIndex0Test() {
        assertEquals(r1, list.selectStrandAtIndex(0));
    }

    @Test
    public void selectStrandAtIndexMaxLengthTest() {
        assertEquals(r3, list.selectStrandAtIndex(2));
    }

    @Test
    public void selectStrandAtIndexMiddleTest() {
        assertEquals(r2, list.selectStrandAtIndex(1));
    }
}
