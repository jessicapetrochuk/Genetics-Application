package persistence;

import model.DnaList;
import model.DnaStrand;
import model.RnaList;
import model.StrandList;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonReaderTest extends JsonTest {

    @Test
    void nonExistentFileTest() {
        JsonReader reader = new JsonReader("./data/notRealFile.json");
        try {
            StrandList sl = reader.read();
            fail("IOException expected");
        } catch (IOException e){
            //pas
        }
    }

    @Test
    void emptyStrandListTest() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyStrands.json");
        try {
            StrandList sl = reader.read();
            assertEquals("Jessica's strands", sl.getName());
            assertEquals(0, sl.numDnaStrands());
            assertEquals(0, sl.numRnaStrands());
        } catch (IOException e) {
            fail("Exception should not be thrown");
        }
    }

    @Test
    void generalStrandListTest() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralStrands.json");
        try {
            StrandList sl = reader.read();
            assertEquals("Jessica's strands", sl.getName());
            DnaList dnaList = sl.getDnaList();
            RnaList rnaList = sl.getRnaList();

            assertEquals(2, dnaList.length());
            assertEquals(2, rnaList.length());

            checkDnaStrand("AAATTT", dnaList.selectStrandAtIndex(0));
            checkDnaStrand("AAACCC", dnaList.selectStrandAtIndex(1));

            checkRnaStrand("AAAUUU", rnaList.selectStrandAtIndex(0));
            checkRnaStrand("AAAGGG", rnaList.selectStrandAtIndex(1));
        } catch (IOException e) {
            fail("Exception should not be thrown");
        }
    }
}
