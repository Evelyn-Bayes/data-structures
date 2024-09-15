package io.eevee.util;

import java.util.Iterator;
import java.util.NoSuchElementException;


/**
 * Bloom Filter implementation
 *
 * This is a probabilistic data structure which, with a small memory footprint and low compute cost
 * is able to determine if an object has not been stored in the data structure with certainty or
 * has been stored with a high degree of accuracy.
 *
 * <p>Complexity:
 * <ul>
 *   <li>Space - O(1)
 *   <li>Access - O(1)
 *   <li>Insert - O(1)
 *   <li>Remove - NA
 * </ul>
 *
 * @param <E> the type of the elements with which we are applying tests to
 */
public class BloomFilter<E> {
    private boolean[] bitArray;
    private int numHashFunctions;
    private float targetFalsePositiveRate;

    // Rounded up to the nearest power of two given our other defaults and an expected number of elements of 1000
    private static int MIN_BIT_ARRAY_SIZE = 8192;

    // Taken from Google's Guava implementation
    private static int DEFAULT_NUM_HASH_FUNCTIONS = 5;
    private static float DEFAULT_FALSE_POSITIVE_RATE = 0.03f;

    BloomFilter() {
        bitArray = new boolean[MIN_BIT_ARRAY_SIZE];
        numHashFunctions = DEFAULT_NUM_HASH_FUNCTIONS;
        targetFalsePositiveRate = DEFAULT_FALSE_POSITIVE_RATE;
    }

    BloomFilter(float targetFalsePositiveRate, int targetNumberOfElements) {
        numHashFunctions = calculateNumberOfHashFunctions(targetFalsePositiveRate);
        bitArray = new boolean[calculateArraySize(targetFalsePositiveRate, targetNumberOfElements, numHashFunctions)];
        this.targetFalsePositiveRate = targetFalsePositiveRate;
    }

    // Visible and strictly available for testing
    int capacity() {
        return bitArray.length;
    }

    /**
     * Inserts markers indicating the inclusion of the element in the Bloom Filter
     *
     * <p>Complexity: O(1).
     *
     * @param element element to be appended
     */
    public void add(E element) {
        for (int i = 0; i < numHashFunctions; i++) {
            byte[] bytes = hashCodeToByteArray(element.hashCode());
            int hash = Murmur.hash(bytes, i * Murmur.DEFAULT_SEED);
            int index = Math.floorMod(hash, bitArray.length);
            bitArray[index] = true;
        }
    }

    private byte[] hashCodeToByteArray(int value) {
        return new byte[] {
            (byte) (value >> 24),
            (byte) (value >> 16),
            (byte) (value >> 8),
            (byte) value};
    }

    /**
     * Returns false if the element is guaranteed to not exist in the Bloom Filter.
     *
     * <p>Complexity: O(1).
     *
     * @param element element to test for inclusion
     * @return false if the element has not been included in the bloom filter
     */
    public boolean mayContain(E element) {
        for (int i = 0; i < numHashFunctions; i++) {
            byte[] bytes = hashCodeToByteArray(element.hashCode());
            int hash = Murmur.hash(bytes, i * Murmur.DEFAULT_SEED);
            int index = Math.floorMod(hash, bitArray.length);
            if (bitArray[index] == false) {
                return false;
            }
        }
        return true;
    }

    private int calculateArraySize(float targetFalsePositiveRate, int targetNumberOfElements, int numHashFunctions) {
        int numerator = - numHashFunctions * targetNumberOfElements;
        double denominator = Math.log(1 - Math.pow(targetFalsePositiveRate, 1.0f / numHashFunctions));
        return (int) Math.floor(numerator / denominator);
    }

    private int calculateNumberOfHashFunctions(float targetFalsePositiveRate) {
        int numHashFunctions = (int) Math.floor(Math.log(targetFalsePositiveRate) / Math.log(2));
        if (numHashFunctions == 0) {
            return 1;
        } else if (numHashFunctions > 256) {
            return 256;
        } else {
            return numHashFunctions;
        }
    }
}
