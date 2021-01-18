package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AminoAcidTest {

    @Test
    public void testAminoAcid() {
        for(AminoAcid a: AminoAcid.values()) {
            assertNotNull(a);
        }
    }
}
