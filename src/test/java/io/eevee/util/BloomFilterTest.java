package io.eevee.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.Integer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit test for the BloomFilter class.
 */
public class BloomFilterTest {

    private BloomFilter<Integer> emptyBloomFilter;
    private BloomFilter<Integer> singleElementBloomFilter;

    @BeforeEach
    public void setup() {
        emptyBloomFilter = new BloomFilter<>();

        singleElementBloomFilter = new BloomFilter<>();
        singleElementBloomFilter.add(0);
    }

    /**
     * Tests to write:
     *   1. Coherence check which inserts "x" objects and validates we can find them
     *   2. Check which inserts "x" objects with "y" known collisions and tests we see this reflected in the number of bits in our array
     *   3. Check with "x" inserts our false positive rate is within the bounds we expect
     *   4. Test we build the correct size based on our expected number of inserts and false positive rate
     *   5. Test we select the correct number of hash functions
     */

    @Test
    public void testMightContainMethod() {
        assertEquals(false, emptyBloomFilter.mayContain(0));

        assertEquals(true, singleElementBloomFilter.mayContain(0));
        assertEquals(false, singleElementBloomFilter.mayContain(1));
    }
}
