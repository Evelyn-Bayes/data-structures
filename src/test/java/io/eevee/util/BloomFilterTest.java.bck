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
    private BloomFilter<Integer> multiElementBloomFilter;

    @BeforeEach
    public void setup() {
        emptyBloomFilter = new BloomFilter<>();

        singleElementBloomFilter = new BloomFilter<>();
        singleElementBloomFilter.add(0);
    }

    @Test
    public void testMightContainMethod() {
        assertEquals(false, emptyBloomFilter.mayContain(0));

        assertEquals(true, singleElementBloomFilter.mayContain(0));
        assertEquals(false, singleElementBloomFilter.mayContain(1));
    }
}
