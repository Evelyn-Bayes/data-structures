package io.eevee.util;
/**
 * Base interface for list data structures
 *
 * @param <E> the type of the elements stored in this collection
 */ 
public interface List<E> {

    /**
     * Appends the specified element at the beginning of the list
     *
     * @param e element to be appended
     */
    void add(E e);

    /**
     * Inserts the specified element at the specified position in the list.
     *
     * @param index index at which the specified element is to be inserted
     * @param element element to be inserted
     */
    void add(int index, E element);

    /**
     * Empties the list.
     */
    void clear();

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

    /**
     * Returns the size of the list.
     *
     * @return size of the list
     */
    int size();
}
