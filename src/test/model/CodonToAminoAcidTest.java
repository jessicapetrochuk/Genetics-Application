package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CodonToAminoAcidTest {
    @Test
    public void testMapLength() {
        assertEquals(64, CodonToAmino.map.size());
    }

    @Test
    public void testCodonKeys() {
        for (Codon c: Codon.values()) {
            assertTrue(CodonToAmino.map.containsKey(c));
        }
    }

    @Test
    public void testCodonValues() {
        for (AminoAcid a: AminoAcid.values()) {
            assertTrue(CodonToAmino.map.containsValue(a));
        }
    }
 }
