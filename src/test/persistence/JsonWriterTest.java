package persistence;

import exceptions.EmptyStrandException;
import model.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class JsonWriterTest extends JsonTest {

    @Test
    void invalidFileTest() {
        try {
            StrandList sl = new StrandList("My strands");
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOExcpetion should have been thrown");
        } catch (IOException e) {
            //pass
        }

    }

    @Test
    void emptyStrandListTest() {
        try {
            StrandList sl = new StrandList("My strands");
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyStrands.json");
            writer.open();
            writer.write(sl);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyStrands.json");
            sl = reader.read();
            assertEquals("My strands", sl.getName());
            assertEquals(0, sl.numDnaStrands());
            assertEquals(0, sl.numRnaStrands());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void generalStrandListTest() {
        try {
            StrandList sl = new StrandList("My strands");
            try {
                sl.addStrand(new DnaStrand("TTTAAA"));
            } catch (EmptyStrandException e) {
                fail("Exception should not have been thrown");
            }
            try {
                sl.addStrand(new RnaStrand("UUUAAA"));
            } catch (EmptyStrandException e) {
                fail("Exception should not have been thrown");
            }
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralStrands.json");
            writer.open();
            writer.write(sl);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralStrands.json");
            sl = reader.read();
            assertEquals("My strands", sl.getName());
            DnaList dnaList = sl.getDnaList();
            RnaList rnaList = sl.getRnaList();
            assertEquals(1, dnaList.length());
            assertEquals(1, rnaList.length());

            checkDnaStrand("TTTAAA", dnaList.selectStrandAtIndex(0));
            checkRnaStrand("UUUAAA", rnaList.selectStrandAtIndex(0));
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
