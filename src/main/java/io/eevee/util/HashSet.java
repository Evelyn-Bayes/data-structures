package io.eevee.util;

import java.util.Iterator;
import java.util.NoSuchElementException;


/**
 * Hash set implementation of the {@code Set} interface.
 *
 * <p>Complexity:
 * <ul>
 *   <li>Space - O(n)
 *   <li>Access - O(log(n))
 *   <li>Insert - O(1)
 *   <li>Remove - O(1)
 * </ul>
 *
 * @see Set
 * @see Iterable
 * @param <E> the type of the elements stored in this collection
 */
public class HashSet<E> implements Set<E> {
//public class HashSet<E> implements Set<E>, Iterable<E> {
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
    public void add(E element) {
        int hash = element.hashCode() % array.length;
        if (array[hash] == null) {
            array[hash] = new LinkedList<E>();
            ((LinkedList<E>) array[hash]).addFirst(element);
        } else {
            if (!((LinkedList<E>) array[hash]).contains(element)) {
                ((LinkedList<E>) array[hash]).addFirst(element);
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
        if ((array[hash] != null) && ((LinkedList<E>) array[hash]).contains(element)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Returns iterator of the set.
     */
    /*
    @Override
    public Iterator<E> iterator() {
        return new LinkedListIterator(head);
    }
    */

    /**
     * Removes the element from the set.
     *
     * <p>Complexity: O(n).
     *
     * @param element element to be removed
     */
    public void remove(E element) {
        int hash = element.hashCode() % array.length;
        if ((array[hash] != null) && ((LinkedList<E>) array[hash]).contains(element)) {
            if (((LinkedList<E>) array[hash]).size() == 1) {
                array[hash] = null;
            } else {
                ((LinkedList<E>) array[hash]).remove(element);
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

    /* 
    private class LinkedListIterator<E> implements Iterator<E> {
        private int index;
        private Node<E> node;
        private E next;

        LinkedListIterator(Node<E> head) {
            index = 0;
            node = head;
        }

        public E next() {
            index++;
            next = node.element;
            node = node.next;
            return next;
        }

        public boolean hasNext() {
            return index < size;
        }
    }
    */
}
