package io.eevee.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.lang.IndexOutOfBoundsException;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit test for the DoubleLinkedList class..
 */
public class DoubleLinkedListTest {

    private DoubleLinkedList<String> emptyTestList;
    private DoubleLinkedList<String> testList;

    @BeforeEach
    public void setup() {
        emptyTestList = new DoubleLinkedList<String>();
        
        testList = new DoubleLinkedList<String>();
        testList.addFirst("Item 3");
        testList.addFirst("Item 2");
        testList.addFirst("Item 1");
    }

    @Test
    public void testSizeMethod() {
        assertEquals(3, testList.size());
    }

    @Test
    public void testGetMethods() {
        assertEquals("Item 1", testList.getFirst());
        assertEquals("Item 3", testList.getLast());
        assertEquals("Item 2", testList.get(1));
    }

    @Test
    public void testGetMethodsOnEmptyList() {
        assertThrows(NoSuchElementException.class, () -> {emptyTestList.getFirst();});
        assertThrows(NoSuchElementException.class, () -> {emptyTestList.getLast();});
        assertThrows(IndexOutOfBoundsException.class, () -> {emptyTestList.get(-1);});
        assertThrows(IndexOutOfBoundsException.class, () -> {emptyTestList.get(0);});
        assertThrows(IndexOutOfBoundsException.class, () -> {emptyTestList.get(1);});
    }

    @Test
    public void testAddMethods() {
        testList.addFirst("Item 0");
        testList.addLast("Item 5");
        testList.add(4, "Item 4");
        assertEquals("Item 0", testList.get(0));
        assertEquals("Item 1", testList.get(1));
        assertEquals("Item 2", testList.get(2));
        assertEquals("Item 3", testList.get(3));
        assertEquals("Item 4", testList.get(4));
        assertEquals("Item 5", testList.get(5));
    }

    @Test
    public void testAddFirstMethodOnEmptyList() {
        emptyTestList.addFirst("Item 0");
        assertEquals("Item 0", emptyTestList.getFirst());
    }

    @Test
    public void testAddLastMethodOnEmptyList() {
        emptyTestList.addLast("Item 0");
        assertEquals("Item 0", emptyTestList.getFirst());
    }

    @Test
    public void testAddIndexMethodOnEmptyList() {
        emptyTestList.add(0, "Item 0");
        assertEquals("Item 0", emptyTestList.getFirst());
    }

    @Test
    public void testAddIndexMethodOnBadIndex() {
        assertThrows(IndexOutOfBoundsException.class, () -> {testList.add(-1, "Item 0");});
        assertThrows(IndexOutOfBoundsException.class, () -> {testList.add(4, "Item 4");});
    }

    @Test
    public void testRemoveMethods() {
        testList.removeFirst();
        assertEquals("Item 2", testList.get(0));
        assertEquals("Item 3", testList.get(1));
        testList.removeLast();
        assertEquals("Item 2", testList.get(0));
        testList.remove(0);
        assertThrows(IndexOutOfBoundsException.class, () -> {emptyTestList.get(0);});
    }

    @Test
    public void testRemoveIndexMethodOnBadIndex() {
        assertThrows(IndexOutOfBoundsException.class, () -> {testList.remove(3);});
    }

    @Test
    public void testRemoveMethodsOnEmptyList() {
        assertThrows(NoSuchElementException.class, () -> {emptyTestList.removeFirst();});
        assertThrows(NoSuchElementException.class, () -> {emptyTestList.removeLast();});
        assertThrows(IndexOutOfBoundsException.class, () -> {emptyTestList.remove(0);});
    }

    @Test
    public void testSetIndexMethodOnEmptyList() {
        assertThrows(IndexOutOfBoundsException.class, () -> {emptyTestList.set(0, "Item 1");});
    }

    @Test
    public void testSetIndexMethod() {
        testList.set(0, "Item 4");
        assertEquals("Item 4", testList.get(0));
        assertEquals("Item 2", testList.get(1));
        assertEquals("Item 3", testList.get(2));
    }

    @Test
    public void testSetIndexMethodOnBadIndex() {
        assertThrows(IndexOutOfBoundsException.class, () -> {testList.set(-1, "Item 0");});
        assertThrows(IndexOutOfBoundsException.class, () -> {testList.set(4, "Item 0");});
    }

    @Test
    public void testClearMethod() {
        testList.clear();
        assertEquals(0, testList.size());
        assertThrows(NoSuchElementException.class, () -> {testList.getFirst();});
        assertThrows(NoSuchElementException.class, () -> {testList.getLast();});
    }
}
