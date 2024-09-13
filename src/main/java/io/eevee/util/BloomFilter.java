package io.eevee.util;

import java.util.Iterator;
import java.util.NoSuchElementException;


/**
 * Bloom Filter implementation
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
    private boolean[] array;
    private int numHashFunctions;
    private double falsePositiveRate;

    private static int DEFAULT_SIZE;
    private static int DEFAULT_NUM_HASH_FUNCTIONS;
    private static double DEFAULT_FALSE_POSITIVE_RATE;

    BloomFilter() {
        array = new boolean[DEFAULT_SIZE];
    }

    BloomFilter(double expectedFalsePositiveRate, int numElements) {
        array = new boolean[arraySize(expectedFalsePositiveRate, numElements)];
    }

    // Visible and strictly available for testing
    int capacity() {
        return array.length;
    }

    /**
     * Inserts markers indicating the inclusion of the element in the Bloom Filter
     *
     * <p>Complexity: O(1).
     *
     * @param element element to be appended
     */
    public void add(E element) {
        int hash = element.hashCode() % array.length;
        array[hash] = true;
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
        int hash = element.hashCode() % array.length;
        return array[hash] == true;
    }

    private int arraySize(double expectedFalsePositiveRate, int numElements) {
        double numerator = - numElements * Math.log(expectedFalsePositiveRate);
        double denominator = Math.pow(Math.log(2), 2);
        return (int) Math.round(numerator / denominator);
    }
}
