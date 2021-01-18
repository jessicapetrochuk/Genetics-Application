package model;

import exceptions.InvalidDnaInputException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RnaStrandTest {
    public RnaStrand rnaStrand;

    @BeforeEach
    public void setUp(){
        rnaStrand = new RnaStrand("UUUCCCGGGAAA");
    }

    @Test
    public void addToStrandInvalidInputTest() {
        try {
            rnaStrand.addToStrand("ZZZ");
        } catch (InvalidDnaInputException e){
            //expected
        }
    }

    @Test
    public void addToStrandValidInputTest() {
        try {
            rnaStrand.addToStrand("AAACCCGGGUUU");
            assertEquals("UUUCCCGGGAAAAAACCCGGGUUU", rnaStrand.getStrandSequence());
        } catch (InvalidDnaInputException e){
            fail("Exception should not be thrown");
        }
    }

    @Test
    public void transcribeRnaTest(){
        Protein transcribedStrandExpected = new Protein();
        transcribedStrandExpected.addAminoAcid(AminoAcid.PHE);
        transcribedStrandExpected.addAminoAcid(AminoAcid.PRO);
        transcribedStrandExpected.addAminoAcid(AminoAcid.GLY);
        transcribedStrandExpected.addAminoAcid(AminoAcid.LYS);

        Protein transcribedStrandActual = rnaStrand.transcribeStrand();

        assertEquals(4, transcribedStrandActual.length());
        assertEquals(transcribedStrandExpected.printProtein(), transcribedStrandActual.printProtein());
    }

    @Test
    public void mutateStringTestA() {
        assertEquals("UUUCCCGGGAAA", rnaStrand.getStrandSequence());
        rnaStrand.mutateStrand(0, "A");
        assertEquals("AUUCCCGGGAAA", rnaStrand.getStrandSequence());
    }

    @Test
    public void mutateStringTestT() {
        assertEquals("UUUCCCGGGAAA", rnaStrand.getStrandSequence());
        rnaStrand.mutateStrand(11, "C");
        assertEquals("UUUCCCGGGAAC", rnaStrand.getStrandSequence());
    }

    @Test
    public void mutateStringTestC() {
        assertEquals("UUUCCCGGGAAA", rnaStrand.getStrandSequence());
        rnaStrand.mutateStrand(5, "U");
        assertEquals("UUUCCUGGGAAA", rnaStrand.getStrandSequence());
    }

    @Test
    public void mutateStringTestG() {
        assertEquals("UUUCCCGGGAAA", rnaStrand.getStrandSequence());
        rnaStrand.mutateStrand(3, "G");
        assertEquals("UUUGCCGGGAAA", rnaStrand.getStrandSequence());
    }
}
