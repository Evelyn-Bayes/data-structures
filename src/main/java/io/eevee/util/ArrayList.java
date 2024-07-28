package io.eevee.util;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static java.lang.Math.floorMod;

/**
 * Array list implementation of the {@code List} interface.
 *
 * <p>Complexity:
 * <ul>
 *   <li>Space - O(n)
 *   <li>Access - O(1)
 *   <li>Insert - O(1) (Amortized)
 *   <li>Remove - O(n)
 * </ul>
 *
 * @see List
 * @param <E> the type of the elements stored in this collection
 */
public class ArrayList<E> implements List<E> {
    private int head;
    private int size;
    private Object[] array;

    private static final int DEFAULT_CAPACITY = 10;

    ArrayList() {
        head = 0;
        size = 0;
        array = new Object[DEFAULT_CAPACITY];
    }

    /**
     * Inserts the element at the specific index.
     *
     * <p>Complexity: O(1) - (Amortized)
     *
     * @param index index at which the specified element is to be inserted
     * @param element element to be inserted
     * @throws IndexOutOfBoundsException index specified is out of range ({@code index < 0 || index > size()})
     */
    @Override
    public void add(int index, E element) {
        if (index < 0 || index > size) throw new IndexOutOfBoundsException();
        if (size == array.length) grow();

        if (size == 0) {
            array[head] = element;
        } else if (index == 0) {
            head = calculateAdjustedIndex(-1);
            array[head] = element;
        } else if (index < size) {
            for (int i = (size - 1); i >= index; i--) {
                array[calculateAdjustedIndex(i+1)] = array[calculateAdjustedIndex(i)];
            }
            array[calculateAdjustedIndex(index)] = element;
        } else {
            array[calculateAdjustedIndex(size)] = element;
        }
        size++;
    }

    /**
     * Inserts the element at the end of the list.
     *
     * <p>Complexity: O(1).
     *
     * @param element element to be appended
     */
    @Override
    public void add(E element) {
        add(size, element);
    }

    /**
     * Inserts the element at the beginning of the list.
     *
     * <p>Complexity: O(1).
     *
     * @param element element to be appended
     */
    public void addFirst(E element) {
        add(0, element);
    }

    /**
     * Inserts the element at the end of the list.
     *
     * <p>Complexity: O(1).
     *
     * @param element element to be appended
     */
    public void addLast(E element) {
        add(size, element);
    }

    private int calculateAdjustedIndex(int index) {
        return floorMod((head + index), array.length);
    }

    // Visible and strictly available for testing
    int capacity() {
        return array.length;
    }

    /**
     * Empties the list.
     *
     * <p>Complexity: O(1).
     */
    @Override
    public void clear() {
        head = 0;
        size = 0;
        array = new Object[DEFAULT_CAPACITY];
    }

    /**
     * Returns true if the specified element is in the list.
     *
     * <p>Complexity: O(n).
     *
     * @param element element to search the list for
     * @return true if the specified element is in the list
     */
    @Override
    public boolean contains(E element) {
        for (int i = 0; i < size; i++) {
            if (element.equals((E) array[i])) return true;
        }
        return false;
    }

    /**
     * If required increases the capacity of the array to the specified size
     *
     * @param minCapacity minimum capacity required
     */
    public void ensureCapacity(int minCapacity) {
        if (array.length < minCapacity) {
            Object[] resizedArray = new Object[minCapacity];
            for (int i = 0; i < size; i++) {
                resizedArray[i] = array[calculateAdjustedIndex(i)];
            }
            head = 0;  
            array = resizedArray;
        }
    }

    /**
     * Returns the element at the specified position in the list.
     *
     * <p>Complexity: O(1).
     *
     * @param index index of element to be retrieved
     * @return the element at the specified index
     * @throws IndexOutOfBoundsException index specified is out of range ({@code index < 0 || index > size()})
     */
    @Override
    public E get(int index) {
        if (index < 0 || size <= index ) throw new IndexOutOfBoundsException();
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
    public E getFirst() {
        if (size == 0) throw new NoSuchElementException();
        return (E) array[head];
    }

    /**
     * Returns the last element in the list.
     *
     * <p>Complexity: O(1).
     *
     * @return the last element in the list
     * @throws NoSuchElementException method executed on empty list
     */
    public E getLast() {
        if (size == 0) throw new NoSuchElementException();
        return (E) array[calculateAdjustedIndex(size - 1)];
    }

    private void grow() {
        if (array.length > (DEFAULT_CAPACITY / 2)) {
            ensureCapacity(array.length * 2);
        } else {
            ensureCapacity(DEFAULT_CAPACITY);
        }
    }

    /**
     * Returns iterator of the LinkedList.
     */
    @Override
    public Iterator<E> iterator() {
        return new ArrayListIterator();
    }

    private class ArrayListIterator<E> implements Iterator<E> {
        private int index;

        ArrayListIterator() {
            index = 0;
        }

        public E next() {
            return (E) array[index++];
        }

        public boolean hasNext() {
            return index < size;
        }
    }

    /**
     * Trims the list to the exact size of the number of elements in the list.
     *
     * <p>Complexity: O(n).
     */
    public void trimToSize() {
        if (array.length > size){
            Object[] trimmedArray = new Object[size];
            for (int i = 0; i < size; i++) {
                trimmedArray[i] = array[calculateAdjustedIndex(i)];
            }
            head = 0;
            array = trimmedArray;
        }
    }

    /**
     * Removes the element at the specified position in the list.
     *
     * <p>Complexity: O(n).
     *
     * @param index index of the element to be removed
     * @throws IndexOutOfBoundsException index specified is out of range ({@code index < 0 || index > size()}) or if 
     * method executed on empty list
     */
    @Override
    public void remove(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
        if (index == 0) {
            array[calculateAdjustedIndex(0)] = null;
            head = calculateAdjustedIndex(1);
        } else if (index < (size - 1)) {
            for (int i = (index + 1); i < size; i++) {
                array[calculateAdjustedIndex(i - 1)] = array[calculateAdjustedIndex(i)];
            }
            array[calculateAdjustedIndex(size - 1)] = null;
        } else {
            array[calculateAdjustedIndex(size - 1)] = null;
        }
        size--;
    }

    /**
     * Removes the first instance of the element in the list if it exists.
     *
     * <p>Complexity: O(n).
     *
     * @param element element to be removed
     */
    @Override
    public void remove(E element) {
        for (int i = 0; i < size; i++) {
            if (element.equals((E) array[calculateAdjustedIndex(i)])) {
                remove(i);
                break;
            }
        }
    }

    /**
     * Removes the first element in the list.
     *
     * <p>Complexity: O(1).
     * 
     * @throws NoSuchElementException method executed on empty list
     */
    public void removeFirst() {
        if (size == 0) throw new NoSuchElementException();
        remove(0);
    }

    /**
     * Removes the last element in the list.
     *
     * <p>Complexity: O(1).
     *
     * @throws NoSuchElementException method executed on empty list
     */
    public void removeLast() {
        if (size == 0) throw new NoSuchElementException();
        remove(size - 1);
    }

    /**
     * Replaces the element at the specified position in the list.
     *
     * <p>Complexity: O(1).
     * 
     * @param index index of the element to be overwritten
     * @param element element to be set
     * @throws IndexOutOfBoundsException index specified is out of range ({@code index < 0 || index > size()}) or if 
     * method executed on empty list
     */
    @Override
    public void set(int index, E element) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
        array[calculateAdjustedIndex(index)] = element;
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
