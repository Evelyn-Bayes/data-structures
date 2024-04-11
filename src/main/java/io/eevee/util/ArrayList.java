package io.eevee.util;

import java.util.NoSuchElementException;

import static java.lang.Math.floorMod;

/**
 * Array list implementation of the {@code List} interface.
 *
 * <p>Complexity:
 * <ul>
 *   <li>Space - O(n)
 *   <li>Access - O(1)
 *   <li>Insert - O(n)
 *   <li>Remove - O(n)
 * </ul>
 *
 * @see List
 * @param <E> the type of the elements stored in this collection
 */
public class ArrayList<E> implements List<E> {
    private int head;
    private int size;
    private int capacity;
    private Object[] array;

    private static final int DEFAULT_CAPACITY = 10;

    ArrayList() {
        head = 0;
        size = 0;
        capacity = DEFAULT_CAPACITY;
        array = new Object[capacity];
    }

    /**
     * Inserts the element at the specific index.
     *
     * <p>Complexity: O(n).
     *
     * @param index index at which the specified element is to be inserted
     * @param e element to be inserted
     * @throws IndexOutOfBoundsException index specified is negative or greater then the length of the list
     */
    @Override
    public void add(int index, E e) throws IndexOutOfBoundsException { //TODO: I actually only need to shift one half of the list
        if (size == capacity) grow();
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        } else if (index == 0) {
            addFirst(e);
        } else if (index == size) {
            addLast(e);
        } else {
            Object[] tmp = new Object[capacity];
            for (int i = 0; i < index; i++) {
                tmp[i] = array[calculateAdjustedIndex(i)];
            }
            tmp[index] = e;
            for (int i = index; i < size; i++) {
                tmp[i+1] = array[calculateAdjustedIndex(i)];
            }
            array = tmp;
            head = 0;
            size++;
        }
    }

    /**
     * Inserts the element at the beginning of the list.
     *
     * <p>Complexity: O(1).
     *
     * @param e element to be appended
     */
    @Override
    public void add(E e) {
        addFirst(e);
    }

    /**
     * Inserts the element at the beginning of the list.
     *
     * <p>Complexity: O(1).
     *
     * @param e element to be appended
     */
    public void addFirst(E e) {
        if (size == capacity) grow();
        head = floorMod(head - 1, capacity);
        array[head] = e;
        size++;
    }

    /**
     * Inserts the element at the end of the list.
     *
     * <p>Complexity: O(1).
     *
     * @param e element to be appended
     */
    public void addLast(E e) {
        if (size == capacity) grow();
        array[size] = e;
        size++;
    }

    private int calculateAdjustedIndex(int index) {
        return floorMod((head + index), capacity);
    }

    /**
     * Empties the list..
     *
     * <p>Complexity: O(1).
     */
    @Override
    public void clear() {
        head = 0;
        size = 0;
        capacity = DEFAULT_CAPACITY;
        array = new Object[capacity];
    }

    /**
     * Returns the element at the specified position in the list.
     *
     * <p>Complexity: O(1).
     *
     * @param index index of element to be retrieved
     * @return the element at the specified index
     * @throws IndexOutOfBoundsException index specified is negative or greater then the length of the list
     */
    @Override
    public E get(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= size ) throw new IndexOutOfBoundsException();
        return (E) array[calculateAdjustedIndex(index)];
    }

    /**
     * Returns the first element in the list.
     *
     * <p>Complexity: O(1).
     *
     * @return the first element in the list
     * @throws NoSuchElementException method executed on empty list
     */
    public E getFirst() throws NoSuchElementException {
        if (size == 0) throw new NoSuchElementException();
        return (E) array[calculateAdjustedIndex(0)];
    }

    /**
     * Returns the last element in the list.
     *
     * <p>Complexity: O(1).
     *
     * @return the last element in the list
     * @throws NoSuchElementException method executed on empty list
     */
    public E getLast() throws NoSuchElementException {
        if (size == 0) throw new NoSuchElementException();
        return (E) array[calculateAdjustedIndex(size - 1)];
    }

    private void grow() {
        Object[] tmp = new Object[capacity * 2];
        for (int i = 0; i < size; i++) {
            tmp[i] = array[calculateAdjustedIndex(i)];
        }
        head = 0;
        capacity = capacity * 2;
        array = tmp;
    }

    /**
     * Removes the element at the specified position in the list.
     *
     * <p>Complexity: O(n).
     *
     * @param index index of the element to be removed
     * @throws IndexOutOfBoundsException index specified is negative, greater then the length of the list or method executed on empty list
     */
    @Override
    public void remove(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
        if (index == 0) {
            removeFirst();
        } else if (index == (size - 1)) {
            removeLast();
        } else {
            Object[] tmp = new Object[capacity];
            for (int i = 0; i < index; i++) {
                tmp[i] = array[calculateAdjustedIndex(i)];
            }
            tmp[index] = null;
            for (int i = (index + 1); i < size; i++) {
                tmp[i-1] = array[calculateAdjustedIndex(i)];
            }
            array = tmp;
            head = 0;
            size--;
        }
    }

    /**
     * Removes the first element in the list.
     *
     * <p>Complexity: O(1).
     * 
     * @throws NoSuchElementException method executed on empty list
     */
    public void removeFirst() throws NoSuchElementException {
        if (size == 0) throw new NoSuchElementException();
        array[calculateAdjustedIndex(0)] = null;
        head = calculateAdjustedIndex(1);
        size--;
    }

    /**
     * Removes the last element in the list.
     *
     * <p>Complexity: O(1).
     *
     * @throws NoSuchElementException method executed on empty list
     */
    public void removeLast() throws NoSuchElementException {
        if (size == 0) throw new NoSuchElementException();
        array[calculateAdjustedIndex(size - 1)] = null;
        size--;
    }

    /**
     * Replaces the element at the specified position in the list.
     *
     * <p>Complexity: O(1).
     * 
     * @param index index of the element to be overwritten
     * @param e element to be set
     * @throws IndexOutOfBoundsException index specified is negative or greater then the length of the list
     */
    @Override
    public void set(int index, E e) throws IndexOutOfBoundsException {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
        array[calculateAdjustedIndex(index)] = e;
    }

    /**
     * Returns the size of the list.
     *
     * <p>Complexity: O(1).
     *
     * @return size of the list
     */
    @Override
    public int size() {
        return size;
    }
}
