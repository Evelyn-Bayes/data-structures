package io.eevee.util;
/**
 * Base interface for all data structures.
 *
 * <p>Specifications:
 * <ul>
 *     <li>Arbitrary index based access - unspecified
 *     <li>Enforces natural ordering - unspecified
 *     <li>Allows duplicates - unspecified
 *     <li>Allows nulls - unspecified
 * </ul>
 *
 * @param <E> the type of the elements stored in this collection
 */ 
public interface Collection<E> extends Iterable<E> {

    /**
     * Adds the specified element to the collection.
     *
     * @param element element to be appended
     */
    void add(E element);

    /**
     * Empties the collection.
     */
    void clear();

    /**
     * Returns true if the specified element is in the collection.
     *
     * @param element element to search the collection for
     * @return true if the specified element is in the collection
     */
    boolean contains(E element);

    /**
     * Removes the first instance of the element in the collection if it exists.
     *
     * @param element element to be removed
     */
    void remove(E element);

    /**
     * Returns the size of the collection.
     *
     * @return size of the collection
     */
    int size();
}
