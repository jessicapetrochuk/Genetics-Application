package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CodonTest {

    @Test
    public void testCodon() {
        for(Codon c: Codon.values()) {
            assertNotNull(c);
        }
    }
}