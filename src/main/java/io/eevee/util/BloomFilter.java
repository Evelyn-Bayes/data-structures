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

    BloomFilter() {
        array = new boolean[arraySize()];
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

    // TODO: Fix to calculate size based on required false position rate
    private int arraySize() {
        return 1000;
    }
}
