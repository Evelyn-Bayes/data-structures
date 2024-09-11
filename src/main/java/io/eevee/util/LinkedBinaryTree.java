package io.eevee.util;

import static java.util.Objects.requireNonNull;

import java.util.Iterator;
import java.util.NoSuchElementException;


/**
 * Binary Search Tree implementation of the {@code Collection} interface.
 *
 * <p>Complexity:
 * <ul>
 *   <li>Space - O(n)
 *   <li>Access - O(log(n))
 *   <li>Insert - O(log(n))
 *   <li>Remove - O(log(n))
 * </ul>
 *
 * <p>If elements are non-random in their insertion then performance degrades to O(n).
 *
 * @see Collection
 * @param <E> the type of the elements stored in this collection
 */
public class LinkedBinaryTree<E extends Comparable<E>> implements Collection<E> {
    private Node<E> root;
    private int size;

    LinkedBinaryTree() {
        root = null;
        size = 0;
    }

    // Visible and strictly available for testing
    void assertInvarients() {
        assert (size == 0) ? (root == null) : (root != null);
    }

    /**
     * Inserts the element in the collection.
     *
     * <p>Complexity: O(1).
     *
     * @param element element to be appended
     * @throws NullPointerException if the specified element is null
     */
    @Override
    public void add(E element) {
        requireNonNull(element);
        Node<E> node = root;
        if (root == null) {
            root = new Node(element, null);
            size++;
        } else while (true) {
            if (isEquivalent(node.element, element)) {
                break;
            } else if (isElementOfLowerNaturalOrdering(node.element, element)) {
                if (node.leftChild == null) {
                    node.leftChild = new Node(element, node);
                    size++;
                    break;
                } else {
                    node = node.leftChild;
                }
            } else {
                if (node.rightChild == null) {
                    node.rightChild = new Node(element, node);
                    size++;
                    break;
                } else {
                    node = node.rightChild;
                }
            }
        }
    }

    // WARNING: If class E does not have natural ordering consistent with .equals() then objects of equal order but
    // different value will be ignored on an attempt to add them.
    private boolean isEquivalent(E position, E element) {
        return element.compareTo(position) == 0 ? true : false;
    }

    private boolean isElementOfLowerNaturalOrdering(E position, E element) {
        return element.compareTo(position) < 0 ? true : false;
    }

    /**
     * Empties the collection.
     *
     * <p>Complexity: O(1).
     */
    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Returns true if the specified element is in the collection.
     *
     * <p>Complexity: O(log(n)).
     *
     * @param element element to search the collection for
     * @return true if the specified element is in the collection
     * @throws NullPointerException if the specified element is null
     */
    @Override
    public boolean contains(E element) {
        requireNonNull(element);
        Node<E> node = root;
        if (size == 0) {
            return false;
        } else while (true) {
            if (isEquivalent(node.element, element)) {
                return true;
            } else if (isElementOfLowerNaturalOrdering(node.element, element)) {
                if (node.leftChild == null) {
                    return false;
                } else {
                    node = node.leftChild;
                }
            } else {
                if (node.rightChild == null) {
                    return false;
                } else {
                    node = node.rightChild;
                }
            }
        }
    }

    /**
     * Returns the element at the specified position in the collection.
     *
     * <p>Complexity: O(log(n)).
     *
     * @param index index of element to be retrieved
     * @return the element at the specified index
     * @throws IndexOutOfBoundsException index specified is negative or greater then the size of the collection
     */
    public E get(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
        Node<E> node = getLeftmostNode(root);
        for (int i = 0; i < index; i++) {
            node = getNextNode(node);
        }
        return node.element;
    }

    // Retrieves the next node based on the "Natural Order" of the elements.
    private Node<E> getNextNode(Node<E> node) {
        if (node.rightChild != null) {
            return getLeftmostNode(node.rightChild);
        } else if (node.isLeftChild()) {
            return node.parent;
        } else {
            return getBaseOfRightEdge(node).parent;
        }
    }

    private Node<E> getLeftmostNode(Node<E> node) {
        while (node.leftChild != null) {
            node = node.leftChild;
        }
        return node;
    }

    private Node<E> getBaseOfRightEdge(Node<E> node) {
        while (node.isRightChild()) {
            node = node.parent;
        }
        return node;
    }

    /**
     * Returns the element at the beginning of the collection.
     *
     * <p>Complexity: O(log(n)).
     *
     * @return the element at the beginning of the collection
     * @throws NoSuchElementException if the method is called on an empty collection
     */
    public E getFirst() {
        if (size == 0) throw new NoSuchElementException();
        Node<E> node = getLeftmostNode(root);
        return node.element;
    }

    /**
     * Returns the element at the end of the collection.
     *
     * <p>Complexity: O(log(n)).
     *
     * @return the element at the end of the collection
     * @throws NoSuchElementException if the method is called on an empty collection
     */
    public E getLast() {
        if (size == 0) throw new NoSuchElementException();
        Node<E> node = getRightmostNode(root);
        return node.element;
    }

    private Node<E> getRightmostNode(Node<E> node) {
        while (node.rightChild != null) {
            node = node.rightChild;
        }
        return node;
    }

    /**
     * Returns iterator of the LinkedBinaryTree.
     */
    @Override
    public Iterator<E> iterator() {
        return new LinkedBinaryTreeIterator();
    }

    private class LinkedBinaryTreeIterator implements Iterator<E> {
        private Node<E> node;
        private int index;

        LinkedBinaryTreeIterator() {
            node = getLeftmostNode(root);
            index = 0;
        }

        public E next() {
            index++;
            E element = node.element;
            if (index < size) {
                node = getNextNode(node);
            }
            return element;
        }

        public boolean hasNext() {
            return index < size;
        }
    }

    /**
     * Removes the element in the collection if it exists.
     *
     * <p>Complexity: O(log(n)).
     *
     * @param element element to be removed
     * @throws NullPointerException if the specified element is null
     * @throws NoSuchElementException method executed on empty collection
     */
    @Override
    public void remove(E element) {
        requireNonNull(element);
        if (size == 0) {
            throw new NoSuchElementException();
        } else if (size == 1) {
            if (isEquivalent(root.element, element)) clear();
        } else { 
            Node<E> node = root;
            while (true) {
                if (isEquivalent(node.element, element)) {
                    unlinkNode(node);
                    size--;
                    break;
                } else if (isElementOfLowerNaturalOrdering(node.element, element)) {
                    if (node.leftChild == null) {
                        break;
                    } else {
                        node = node.leftChild;
                    }
                } else {
                    if (node.rightChild == null) {
                        break;
                    } else {
                        node = node.rightChild;
                    }
                }
            }
        }
    }

    private void unlinkNode(Node<E> node) {
        if (node == root) {
            unlinkRoot();
        } else if (node.isLeftChild()) {
            node.parent.leftChild = node.leftChild;
            if (node.rightChild == null) return;
            if (node.parent.rightChild == null) {
                node.parent.rightChild = node.rightChild;
                node.rightChild.parent = node.parent.rightChild;
            } else {
                Node<E> tmp = getLeftmostNode(node.parent.rightChild);
                tmp.leftChild = node.rightChild;
                node.rightChild.parent = tmp;
            }
        } else {
            node.parent.rightChild = node.rightChild;
            if (node.rightChild == null) return;
            if (node.parent.leftChild == null) {
                node.parent.leftChild = node.leftChild;
                node.leftChild.parent = node.parent.leftChild;
            } else {
                Node<E> tmp = getRightmostNode(node.parent.leftChild);
                tmp.rightChild = node.leftChild;
                node.leftChild.parent = tmp;
            }
        }
    }

    private void unlinkRoot() {
        Node<E> oldRoot = root;
        Node<E> childToBeMoved = oldRoot.leftChild.rightChild;
        Node<E> leftEdgeOfRightChild = getLeftmostNode(oldRoot.rightChild);

        root = root.leftChild;
        root.parent = null;
        root.rightChild = oldRoot.rightChild;

        oldRoot.rightChild.parent = root;

        leftEdgeOfRightChild.leftChild = childToBeMoved;
        childToBeMoved.parent = leftEdgeOfRightChild;
    }

    /**
     * Removes the leftmost element in the collection.
     *
     * <p>Complexity: O(log(n)).
     *
     * @throws NoSuchElementException method executed on empty collection
     */
    public void removeFirst() {
        if (size == 0) {
            throw new NoSuchElementException();
        } else if (size == 1) {
            clear();
        } else if (root.leftChild == null) {
            root = root.rightChild;
            root.parent = null;
            size--;
        } else {
            getLeftmostNode(root).parent.leftChild = null;
            size--;
        }
    }

    /**
     * Removes the rightmost element in the collection.
     *
     * <p>Complexity: O(log(n)).
     *
     * @throws NoSuchElementException method executed on empty collection
     */
    public void removeLast() {
        if (size == 0) {
            throw new NoSuchElementException();
        } else if (size == 1) {
            clear();
        } else if (root.rightChild == null) {
            root = root.leftChild;
            size--;
        } else {
            getRightmostNode(root).parent.rightChild = null;
            size--;
        }
    }

    /**
     * Returns the size of the collection.
     *
     * <p>Complexity: O(1).
     *
     * @return size of the collection
     */
    @Override
    public int size() {
        return size;
    }
    
    private static class Node<E> {
        E element;
        Node<E> parent;
        Node<E> leftChild;
        Node<E> rightChild;

        Node(E element, Node<E> parent) {
            this.element = element;
            this.parent = parent;
            leftChild = null;
            rightChild = null;
        }

        private boolean isLeftChild() {
            return (this == this.parent.leftChild) ? true : false;
        }

        private boolean isRightChild() {
            return (this == this.parent.rightChild) ? true : false;
        }
    }
}
