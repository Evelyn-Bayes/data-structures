package io.eevee.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.Integer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit test for the BloomFilter class.
 */
public class BloomFilterTest {

    @Test
    public void coherenceTest() {
        BloomFilter bloomFilter = new BloomFilter();
        for (int i = 0; i < 100; i++) {
            bloomFilter.add(String.valueOf(i).getBytes());
            assertTrue(bloomFilter.mayContain(String.valueOf(i).getBytes()));
        }
    }

    @Test
    public void testForKnownNumberOfCollisions() {
        BloomFilter bloomFilter = new BloomFilter(0.01f, 10000);
        for (int i = 0; i < 10000; i++) {
            bloomFilter.add(String.valueOf(i).getBytes());
        }
        assertEquals(44645, bloomFilter.bitsSet());
    }

    @Test
    public void testFalsePositiveRateIsWithinExpectedBounds() {
        BloomFilter bloomFilter = new BloomFilter(0.01f, 10000);
        for (int i = 0; i < 10000; i++) {
            bloomFilter.add(String.valueOf(i).getBytes());
        }

        int falsePositives = 0;
        for (int i = 10000; i < 20000; i++) {
            if (bloomFilter.mayContain(String.valueOf(i).getBytes()) == true) falsePositives++;
        }
        assertEquals(97, falsePositives);
        assertTrue(falsePositives < 101);
    }

    @Test
    public void testBitArrayIsOfCorrectSize() {
        BloomFilter bloomFilter = new BloomFilter(.01f, 10000);
        assertEquals(96166, bloomFilter.bitSize());
    }

    @Test
    public void testOptimalNumberOfHashFunctionsIsSelected() {
        BloomFilter bloomFilter = new BloomFilter(.01f, 10000);
        assertEquals(6, bloomFilter.numHashFunctions());
    }
}
