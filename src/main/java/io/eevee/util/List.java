package io.eevee.util;
/**
 * Base interface for list data structures.
 *
 * <p>Specifications:
 * <ul>
 *     <li>Arbitrary index base access - true
 *     <li>Enforces natural ordering - false
 *     <li>Allows duplicates - true
 *     <li>Allows nulls - true
 * </ul>
 *
 * @see Collection
 * @param <E> the type of the elements stored in this list
 */ 
public interface List<E> extends Collection<E> {

    /**
     * Inserts the specified element at the specified position in the list.
     *
     * @param index index at which the specified element is to be inserted
     * @param element element to be inserted
     */
    void add(int index, E element);

    /**
     * Returns the element at the specified position in the list.
     *
     * @param index index of element to be retrieved
     * @return the element at the specified index
     */
    E get(int index);

    /**
     * Removes the element at the specified position in the list.
     *
     * @param index index of the element to be removed
     */
    void remove(int index);

    /**
     * Overwrites the element at the specified position in the list.
     *
     * @param index index of the element to be overwritten
     * @param element element to be set
     */
    void set(int index, E element);
}
