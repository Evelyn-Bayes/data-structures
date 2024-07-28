package io.eevee.util;

import java.util.Iterator;
import java.util.NoSuchElementException;


/**
 * Double linked list implementation of the {@code List} interface.
 *
 * <p>Complexity:
 * <ul>
 *   <li>Space - O(n)
 *   <li>Access - O(n)
 *   <li>Insert - O(n)
 *   <li>Remove - O(n)
 * </ul>
 * 
 * <p>Certain operations such as adding or removing elements from the head and tail of the list can be performed in
 * constant time.
 *
 * @see List
 * @param <E> the type of the elements stored in this collection
 */
public class LinkedList<E> implements List<E>, Iterable<E> {
    private Node<E> head;
    private Node<E> tail;
    int size;

    LinkedList() {
        head = null;
        tail = null;
        size = 0;
    }
    
    // Visible and strictly available for testing
    void assertInvarients() {
        assert (size == 0)
            ? (head == null && tail == null)
            : (head.previous == null && tail.next == null);
    }

    /**
     * Inserts the specified element at the specified position in the list.
     *
     * <p>Complexity: O(n).
     * @param index index at which the specified element is to be inserted
     * @param element element to be inserted
     * @throws IndexOutOfBoundsException index specified is negative or greater then the length of the list
     */
    @Override
    public void add(int index, E element) {
        if (index < 0 || index > size ) {
            throw new IndexOutOfBoundsException();
        } else if (index == 0) {
            addFirst(element);
        } else if (index < size) {
            Node<E> node = getNode(index - 1);
            node.next = new Node(element, node.next, node);
            size++;
        } else {
            addLast(element);
        }
    }

    /**
     * Appends the specified element to the beginning of the list.
     *
     * <p>Complexity: O(1).
     *
     * @param element element to be appended
     */
    @Override
    public void add(E element) {
        addFirst(element);
    }

    /**
     * Appends the specified element to the beginning of the list.
     *
     * <p>Complexity: O(1).
     *
     * @param element element to be appended
     */
    public void addFirst(E element) {
        head = new Node<E>(element, head, null);
        if (size == 0) {
            tail = head;
        }
        size++;
    }

    /**
     * Appends the specified element to the end of the list.
     *
     * <p>Complexity: O(1).
     *
     * @param element element to be appended
     */
    public void addLast(E element) {
        if (size == 0) {
            addFirst(element);
        } else {
            tail.next = new Node(element, null, tail);
            tail = tail.next;
            size++;
        }
    }

    /**
     * Empties the list.
     *
     * <p>Complexity: O(1).
     */
    @Override
    public void clear() {
        head = null;
        tail = null;
        size = 0;
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
        Node<E> node = head;
        for (int i = 0; i < size; i++) {
            if (node.element.equals(element)) return true;
            node = node.next;
        }
        return false;
    }

    /**
     * Returns the element at the specified position in the list.
     *
     * <p>Complexity: O(n).
     *
     * @param index index of element to be retrieved
     * @return the element at the specified index
     * @throws IndexOutOfBoundsException index specified is negative or greater then the length of the list
     */
    @Override
    public E get(int index) {
        if (index < 0 || index >= size ) {
            throw new IndexOutOfBoundsException();
        } else if (index < (size - 1)) {
            Node<E> node = getNode(index);
            return node.element;
        } else {
            return tail.element;
        }
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
        return head.element;
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
        return tail.element;
    }

    private Node<E> getNode(int index) {
        Node<E> node = head;
        for(int i = 0; i < index; i++) {
            node = node.next;
        }
        return node;
    }

    /**
     * Returns iterator of the LinkedList.
     */
    @Override
    public Iterator<E> iterator() {
        return new LinkedListIterator();
    }

    private class LinkedListIterator implements Iterator<E> {
        private int index;
        private Node<E> node;
        private E next;

        LinkedListIterator() {
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

    /**
     * Removes the element at the specified position in the list.
     *
     * <p>Complexity: O(n).
     *
     * @param index index of the element to be removed
     * @throws IndexOutOfBoundsException index specified is negative, greater then the length of the list or method executed on empty list
     */
    @Override
    public void remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        } else if (index == 0) {
            removeFirst();
        } else if (index == (size - 1)) {
            removeLast();
        } else {
            Node<E> node = getNode(index - 1);
            node.next = node.next.next;
            node.next.previous = node;
            size--;
        }
    }

    /**
     * Removes the first instance of the element in the list if it exists.
     *
     * <p>Complexity: O(n).
     *
     * @param element element to be removed
     * @throws NoSuchElementException method executed on empty list
     */
    @Override
    public void remove(E element) {
        if (size == 0) throw new NoSuchElementException();
        if (element.equals(head.element)) {
            removeFirst();
            return;
        }
        for (Node<E> node = head.next; node.next != null; node = node.next) {
            if (element.equals(node.element)) {
                node.previous.next = node.next;
                node.next.previous = node.previous;
                size--;
                return;
            }
        }
        if (element.equals(tail.element)) {
            removeLast();
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
        if (size == 1) {
            clear();
        } else {
            head = head.next;
            head.previous = null;
            size--;
        }
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
        if (size == 1) {
            clear();
        } else {
            tail = tail.previous;
            tail.next = null;
            size--;
        }
    }

    /**
     * Overwrites the element at the specified position in the list.
     *
     * <p>Complexity: O(n).
     *
     * @param index index of the element to be overwritten
     * @param element element to be set
     * @throws IndexOutOfBoundsException index specified is negative or greater then the length of the list
     */
    @Override
    public void set(int index, E element) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
        Node<E> node = getNode(index);
        node.element = element;
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

    private static class Node<E> {
        E element;
        Node<E> next;
        Node<E> previous;

        Node(E element, Node<E> next, Node<E> previous) {
            this.element = element;
            this.next = next;
            this.previous = previous;
        }
    }
}
