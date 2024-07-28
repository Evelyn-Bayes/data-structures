package io.eevee.util;

import java.util.Iterator;
import java.util.NoSuchElementException;


/**
 * Hash set implementation of the {@code Collection} interface.
 *
 * <p>Complexity:
 * <ul>
 *   <li>Space - O(n)
 *   <li>Access - O(log(n))
 *   <li>Insert - O(1)
 *   <li>Remove - O(1)
 * </ul>
 *
 * @see Collection
 * @see Iterable
 * @param <E> the type of the elements stored in this collection
 */
public class HashSet<E> implements Collection<E> {
    private Object[] array;
    private int size;

    private static final int DEFAULT_CAPACITY = 10;

    HashSet() {
        array = new Object[DEFAULT_CAPACITY];
        size = 0;
    }

    /**
     * Inserts the element into set.
     *
     * <p>Complexity: O(1).
     *
     * @param element element to be appended
     */
    @Override
    public void add(E element) {
        int hash = element.hashCode() % array.length;
        if (array[hash] == null) {
            array[hash] = new LinkedList<E>();
            ((List<E>) array[hash]).add(element);
        } else {
            if (!((List<E>) array[hash]).contains(element)) {
                ((List<E>) array[hash]).add(element);
            } else {
                return;
            }
        }
        size++;
    }

    /**
     * Empties the set.
     *
     * <p>Complexity: O(1).
     */
    @Override
    public void clear() {
        array = new Object[DEFAULT_CAPACITY];
        size = 0;
    }

    /**
     * Returns true if the specified element is in the set.
     *
     * <p>Complexity: O(n).
     *
     * @param element element to search the set for
     * @return true if the specified element is in the set
     */
    @Override
    public boolean contains(E element) {
        int hash = element.hashCode() % array.length;
        if ((array[hash] != null) && ((List<E>) array[hash]).contains(element)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Returns iterator of the set.
     */
    @Override
    public Iterator<E> iterator() {
        return new HashSetIterator();
    }

    private class HashSetIterator implements Iterator<E> {
        private int elementNum;
        private int index;
        private Iterator<E> iterator;

        HashSetIterator() {
            elementNum = 0;
            index = 0;
            iterator = null;
        }

        public E next() {
            elementNum++;
            while (iterator == null || iterator.hasNext() == false) {
                iterator = ((Collection) array[index]).iterator();
                index++;
            }
            return iterator.next();
        }

        public boolean hasNext() {
            return elementNum < size;
        }
    }

    /**
     * Removes the element from the set.
     *
     * <p>Complexity: O(n).
     *
     * @param element element to be removed
     */
    @Override
    public void remove(E element) {
        int hash = element.hashCode() % array.length;
        if ((array[hash] != null) && ((List<E>) array[hash]).contains(element)) {
            if (((List<E>) array[hash]).size() == 1) {
                array[hash] = null;
            } else {
                ((List<E>) array[hash]).remove(element);
            }
            size--;
        }
    }

    /**
     * Returns the size of the set.
     *
     * <p>Complexity: O(1).
     *
     * @return size of the set
     */
    @Override
    public int size() {
        return size;
    }
}
