package io.eevee.util;
/**
 * Base interface for set data structures
 *
 * @param <E> the type of the elements stored in this collection
 */ 
public interface Set<E> {

    /**
     * Adds the specified element to the set.
     *
     * @param element element to be added
     */
    void add(E element);

    /**
     * Empties the set.
     */
    void clear();

    /**
     * Returns true if the specified element is in the set.
     *
     * @param element element to search the set for
     * @return true if the specified element is in the set
     */
    boolean contains(E element);

    /**
     * Removes the element from the set.
     *
     * @param element element to remove
     */
    void remove(E element);

    /**
     * Returns the size of the set.
     *
     * @return size of the set
     */
    int size();
}
