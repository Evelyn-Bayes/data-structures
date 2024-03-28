package io.eevee.util;

import java.util.NoSuchElementException;

/**
 * Double linked list implementation of the {@code List} interface.
 *
 * <p>Complexity:
 * <ul>
 *   <li>Space - O(n)
 *   <li>Access - O(n)
 *   <li>Insert - O(1)
 *   <li>Remove - O(1)
 * </ul>
 * 
 * <p>In the above complexity breakdown a method like {@code remove(int index)} 
 * would constitute an 'access' operation to locate the element and a 'remove'
 * to delete it from the list.
 *
 * @see List
 * @param <E> the type of the elements stored in this collection
 */
public class DoubleLinkedList<E> implements List<E> {
    private Node<E> head;
    private Node<E> tail;
    private int size;

    DoubleLinkedList() {
        head = null;
        tail = null;
        size = 0;
    }

    /**
     * Inserts the specified element at the specified position in the list.
     *
     * <p>Complexity: O(n).
     * @param index index at which the specified element is to be inserted
     * @param e element to be inserted
     * @throws IndexOutOfBoundsException index specified is negative or greater then the length of the list
     */
    @Override
    public void add(int index, E e) throws IndexOutOfBoundsException {
        if (index < 0 || index > size ) throw new IndexOutOfBoundsException();
        if (size == 0) {
            head = new Node(e, null, null);
            tail = head;
        } else if (size == 1 && index == 0) {
            head = new Node(e, head, null);
            tail = head.next;
        } else if (size == index) {
            tail.next = new Node(e, null, tail);
            tail = tail.next;
        } else {
            Node<E> node = head;
            for(int i = 0; i < (index - 1); i++) {
                node = node.next;
            }
            node.next = new Node(e, node.next, node);
        }
        size++;
    }

    /**
     * Appends the specified element to the beginning of the list.
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
     * Appends the specified element to the beginning of the list.
     *
     * <p>Complexity: O(1).
     *
     * @param e element to be appended
     */
    public void addFirst(E e) {
        head = new Node<E>(e, head, null);
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
     * @param e element to be appended
     */
    public void addLast(E e) {
        if (size == 0) {
            head = new Node(e, null, null);
            tail = head;
        } else {
            tail.next = new Node(e, null, tail);
            tail = tail.next;
        }
        size++;
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
     * Returns the element at the specified position in the list.
     *
     * <p>Complexity: O(n).
     *
     * @param index index of element to be retrieved
     * @return the element at the specified index
     * @throws IndexOutOfBoundsException index specified is negative or greater then the length of the list
     */
    @Override
    public E get(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= size ) throw new IndexOutOfBoundsException();
        if (index == (size - 1)) return tail.e;
        Node<E> node = head;
        for(int i = 0; i < index; i++) {
            node = node.next;
        }
        return node.e;
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
        return head.e;
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
        return tail.e;
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
        if (size == 1) {
            clear();
            return;
        }
        if (index == (size - 1)) {
            removeLast();
            return;
        }
        Node<E> node = head;
        for(int i = 0; i < (index - 1); i++) {
            node = node.next;
        }
        node.next = node.next.next;
        size--;
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
        if (size == 1) {
            clear();
        } else {
            head = head.next;
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
    public void removeLast() throws NoSuchElementException {
        if (size == 0) throw new NoSuchElementException();
        if (size == 1) {
            clear();
        } else {
            Node<E> previous = null;
            Node<E> node = head;
            while(node.next != null) {
                previous = node;
                node = node.next;
            }
            previous.next = null;
            tail = previous;
            size--;
        }
    }

    /**
     * Overwrites the element at the specified position in the list.
     *
     * <p>Complexity: O(n).
     *
     * @param index index of the element to be overwritten
     * @param e element to be set
     * @throws IndexOutOfBoundsException index specified is negative or greater then the length of the list
     */
    @Override
    public void set(int index, E e) throws IndexOutOfBoundsException {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
        Node<E> node = head;
        for(int i = 0; i < index; i++) {
            node = node.next;
        }
        node.e = e;
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

    private class Node<E> {
        E e;
        Node<E> next;
        Node<E> previous;

        Node(E e, Node<E> next, Node<E> previous) {
            this.e = e;
            this.next = next;
            this.previous = previous;
        }
    }
}