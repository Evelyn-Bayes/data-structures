package io.eevee.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.lang.IndexOutOfBoundsException;
import java.lang.Integer;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit test for the LinkedBinaryTree class.
 */
public class LinkedBinaryTreeTest {

    private final int MAGIC_NUMBER_NOT_IN_DEFAULT_TREES = 777;

    private LinkedBinaryTree<Integer> emptyTree;
    private LinkedBinaryTree<Integer> singleElementTree;
    private LinkedBinaryTree<Integer> multiElementTree;

    @BeforeEach
    public void setup() {
        emptyTree = new LinkedBinaryTree<Integer>();
        
        singleElementTree = new LinkedBinaryTree<Integer>();
        singleElementTree.add(0);

        multiElementTree = new LinkedBinaryTree<Integer>();
        multiElementTree.add(0);
        multiElementTree.add(-2);
        multiElementTree.add(2);
        multiElementTree.add(-3);
        multiElementTree.add(-1);
        multiElementTree.add(3);
        multiElementTree.add(1);
    }

    @Test
    public void testSizeMethod() {
        assertEquals(0, emptyTree.size());
        assertEquals(1, singleElementTree.size());
        assertEquals(7, multiElementTree.size());
    }

    @Test
    public void testGetFirstMethod() {
        assertThrows(NoSuchElementException.class, () -> {emptyTree.getFirst();});
        assertEquals(0, singleElementTree.getFirst());
        assertEquals(-3, multiElementTree.getFirst());
    }

    @Test
    public void testGetLastMethod() {
        assertThrows(NoSuchElementException.class, () -> {emptyTree.getLast();});
        assertEquals(0, singleElementTree.getLast());
        assertEquals(3, multiElementTree.getLast());
    }

    @Test
    public void testGetMethod() {
        assertEquals(0, singleElementTree.get(0));
        assertEquals(-3, multiElementTree.get(0));
        assertEquals(-2, multiElementTree.get(1));
        assertEquals(-1, multiElementTree.get(2));
        assertEquals(0, multiElementTree.get(3));
        assertEquals(1, multiElementTree.get(4));
        assertEquals(2, multiElementTree.get(5));
        assertEquals(3, multiElementTree.get(6));
    }

    @Test
    public void testGetMethodOnBadIndex() {
        assertThrows(IndexOutOfBoundsException.class, () -> {emptyTree.get(0);});
        assertThrows(IndexOutOfBoundsException.class, () -> {singleElementTree.get(-1);});
        assertThrows(IndexOutOfBoundsException.class, () -> {singleElementTree.get(1);});
    }

    @Test
    public void testClearMethod() {
        multiElementTree.clear();
        multiElementTree.assertInvarients();
        assertEquals(0, multiElementTree.size());
        assertThrows(IndexOutOfBoundsException.class, () -> {multiElementTree.get(0);});
    }

    @Test
    public void testIterable() {
        Integer startingValue = -3;
        for (Integer element : multiElementTree) {
            assertEquals(startingValue, element);
            startingValue++;
        }
    }

    @Test
    public void testContains() {
        assertEquals(false, emptyTree.contains(0));

        assertEquals(true, singleElementTree.contains(0));
        assertEquals(false, singleElementTree.contains(1));

        assertEquals(true, multiElementTree.contains(-3));
        assertEquals(true, multiElementTree.contains(-2));
        assertEquals(true, multiElementTree.contains(-1));
        assertEquals(true, multiElementTree.contains(0));
        assertEquals(true, multiElementTree.contains(0));
        assertEquals(true, multiElementTree.contains(1));
        assertEquals(true, multiElementTree.contains(2));
        assertEquals(true, multiElementTree.contains(3));
        assertEquals(false, multiElementTree.contains(4));
    }

    @Test
    public void testRemoveObjectMethodLeftEdge() {
        assertThrows(NoSuchElementException.class, () -> {emptyTree.remove(Integer.valueOf(0));});

        singleElementTree.remove(Integer.valueOf(0));
        singleElementTree.assertInvarients();
        assertThrows(IndexOutOfBoundsException.class, () -> {singleElementTree.get(0);});

        multiElementTree.remove(Integer.valueOf(-3));
        multiElementTree.assertInvarients();
        assertEquals(-2, multiElementTree.get(0));
        assertEquals(-1, multiElementTree.get(1));
        assertEquals(0, multiElementTree.get(2));
        assertEquals(1, multiElementTree.get(3));
        assertEquals(2, multiElementTree.get(4));
        assertEquals(3, multiElementTree.get(5));
    }

    @Test
    public void testRemoveObjectMethodOnRightEdge() {
        multiElementTree.remove(Integer.valueOf(3));
        multiElementTree.assertInvarients();
        assertEquals(-3, multiElementTree.get(0));
        assertEquals(-2, multiElementTree.get(1));
        assertEquals(-1, multiElementTree.get(2));
        assertEquals(0, multiElementTree.get(3));
        assertEquals(1, multiElementTree.get(4));
        assertEquals(2, multiElementTree.get(5));
        assertThrows(IndexOutOfBoundsException.class, () -> {multiElementTree.get(6);});
    }

    @Test
    public void testRemoveObjectMethodOnMiddle() {
        multiElementTree.remove(Integer.valueOf(1));
        assertEquals(-3, multiElementTree.get(0));
        assertEquals(-2, multiElementTree.get(1));
        assertEquals(-1, multiElementTree.get(2));
        assertEquals(0, multiElementTree.get(3));
        assertEquals(2, multiElementTree.get(4));
        assertEquals(3, multiElementTree.get(5));
        assertThrows(IndexOutOfBoundsException.class, () -> {multiElementTree.get(6);});
    }

    @Test
    public void testRemoveObjectMethodOnRoot() {
        multiElementTree.remove(Integer.valueOf(0));
        assertEquals(-3, multiElementTree.get(0));
        assertEquals(-2, multiElementTree.get(1));
        assertEquals(-1, multiElementTree.get(2));
        assertEquals(1, multiElementTree.get(3));
        assertEquals(2, multiElementTree.get(4));
        assertEquals(3, multiElementTree.get(5));
        assertThrows(IndexOutOfBoundsException.class, () -> {multiElementTree.get(6);});
    }

    @Test
    public void testRemoveObjectMethodOnInvalidObject() {
        multiElementTree.remove(Integer.valueOf(4));
        assertEquals(-3, multiElementTree.get(0));
        assertEquals(-2, multiElementTree.get(1));
        assertEquals(-1, multiElementTree.get(2));
        assertEquals(0, multiElementTree.get(3));
        assertEquals(1, multiElementTree.get(4));
        assertEquals(2, multiElementTree.get(5));
        assertEquals(3, multiElementTree.get(6));
    }
}
