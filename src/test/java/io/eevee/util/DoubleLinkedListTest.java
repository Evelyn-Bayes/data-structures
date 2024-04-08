package io.eevee.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.lang.IndexOutOfBoundsException;
import java.lang.Integer;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit test for the DoubleLinkedList class..
 */
public class DoubleLinkedListTest {

    private DoubleLinkedList<Integer> emptyList;
    private DoubleLinkedList<Integer> singleElementList;
    private DoubleLinkedList<Integer> multiElementList;;

    @BeforeEach
    public void setup() {
        emptyList = new DoubleLinkedList<Integer>();
        
        singleElementList = new DoubleLinkedList<Integer>();
        singleElementList.addLast(0);

        multiElementList = new DoubleLinkedList<Integer>();
        multiElementList.addLast(0);
        multiElementList.addLast(1);
        multiElementList.addLast(2);
    }

    @Test
    public void testSizeMethod() {
        assertEquals(0, emptyList.size());
        assertEquals(1, singleElementList.size());
        assertEquals(3, multiElementList.size());
    }

    @Test
    public void testGetFirstMethod() {
        assertThrows(NoSuchElementException.class, () -> {emptyList.getFirst();});
        assertEquals(0, singleElementList.getFirst());
        assertEquals(0, multiElementList.getFirst());
    }

    @Test
    public void testGetLastMethod() {
        assertThrows(NoSuchElementException.class, () -> {emptyList.getLast();});
        assertEquals(0, singleElementList.getLast());
        assertEquals(2, multiElementList.getLast());
    }

    @Test
    public void testGetMethod() {
        assertEquals(0, singleElementList.get(0));
        assertEquals(0, multiElementList.get(0));
        assertEquals(1, multiElementList.get(1));
        assertEquals(2, multiElementList.get(2));
    }

    @Test
    public void testGetMethodOnBadIndex() {
        assertThrows(IndexOutOfBoundsException.class, () -> {emptyList.get(0);});
        assertThrows(IndexOutOfBoundsException.class, () -> {singleElementList.get(-1);});
        assertThrows(IndexOutOfBoundsException.class, () -> {singleElementList.get(1);});
    }

    @Test
    public void testAddFirstMethod() {
        emptyList.addFirst(3);
        assertEquals(3, emptyList.get(0));
        
        singleElementList.addFirst(3);
        assertEquals(3, singleElementList.get(0));
        assertEquals(0, singleElementList.get(1));
        
        multiElementList.addFirst(3);
        assertEquals(3, multiElementList.get(0));
        assertEquals(0, multiElementList.get(1));
        assertEquals(1, multiElementList.get(2));
        assertEquals(2, multiElementList.get(3));
    }

    @Test
    public void testAddLastMethod() {
        emptyList.addLast(3);
        assertEquals(3, emptyList.get(0));
        
        singleElementList.addLast(3);
        assertEquals(0, singleElementList.get(0));
        assertEquals(3, singleElementList.get(1));
        
        multiElementList.addLast(3);
        assertEquals(0, multiElementList.get(0));
        assertEquals(1, multiElementList.get(1));
        assertEquals(2, multiElementList.get(2));
        assertEquals(3, multiElementList.get(3));
    }


    @Test
    public void testAddMethodOnStartOfList() {
        emptyList.add(0, 3);
        assertEquals(3, emptyList.get(0));
        
        singleElementList.add(0, 3);
        assertEquals(3, singleElementList.get(0));
        assertEquals(0, singleElementList.get(1));
        
        multiElementList.add(0, 3);
        assertEquals(3, multiElementList.get(0));
        assertEquals(0, multiElementList.get(1));
        assertEquals(1, multiElementList.get(2));
        assertEquals(2, multiElementList.get(3));
    }

    @Test
    public void testAddMethodOnEndOfList() {
        singleElementList.add(1, 3);
        assertEquals(0, singleElementList.get(0));
        assertEquals(3, singleElementList.get(1));
        
        multiElementList.add(3, 3);
        assertEquals(0, multiElementList.get(0));
        assertEquals(1, multiElementList.get(1));
        assertEquals(2, multiElementList.get(2));
        assertEquals(3, multiElementList.get(3));
    }

    @Test
    public void testAddMethodOnMiddleOfList() {
        multiElementList.add(2, 3);
        assertEquals(0, multiElementList.get(0));
        assertEquals(1, multiElementList.get(1));
        assertEquals(3, multiElementList.get(2));
        assertEquals(2, multiElementList.get(3));
    }

    @Test
    public void testAddIndexMethodOnBadIndex() {
        assertThrows(IndexOutOfBoundsException.class, () -> {emptyList.add(-1, 3);});
        assertThrows(IndexOutOfBoundsException.class, () -> {emptyList.add(1, 3);});
        assertThrows(IndexOutOfBoundsException.class, () -> {singleElementList.add(2, 3);});
        assertThrows(IndexOutOfBoundsException.class, () -> {multiElementList.add(4, 3);});
    }

    @Test
    public void testRemoveFirstMethod() {
        assertThrows(NoSuchElementException.class, () -> {emptyList.removeFirst();});

        singleElementList.removeFirst();
        assertThrows(IndexOutOfBoundsException.class, () -> {singleElementList.get(0);});

        multiElementList.removeFirst();
        assertEquals(1, multiElementList.get(0));
        assertEquals(2, multiElementList.get(1));
    }

    @Test
    public void testRemoveLastMethod() {
        assertThrows(NoSuchElementException.class, () -> {emptyList.removeLast();});

        singleElementList.removeLast();
        assertThrows(IndexOutOfBoundsException.class, () -> {singleElementList.get(0);});

        multiElementList.removeLast();
        assertEquals(0, multiElementList.get(0));
        assertEquals(1, multiElementList.get(1));
    }

    @Test
    public void testRemoveMethodOnStartOfList() {
        assertThrows(IndexOutOfBoundsException.class, () -> {emptyList.remove(0);});

        singleElementList.remove(0);
        assertThrows(IndexOutOfBoundsException.class, () -> {singleElementList.get(0);});

        multiElementList.remove(0);
        assertEquals(1, multiElementList.get(0));
        assertEquals(2, multiElementList.get(1));
    }

    @Test
    public void testRemoveMethodOnEndOfList() {
        multiElementList.remove(2);
        assertEquals(0, multiElementList.get(0));
        assertEquals(1, multiElementList.get(1));
    }

    @Test
    public void testRemoveMethodOnMiddleOfList() {
        multiElementList.remove(1);
        assertEquals(0, multiElementList.get(0));
        assertEquals(2, multiElementList.get(1));
    }

    @Test
    public void testRemoveMethodOnBadIndex() {
        assertThrows(IndexOutOfBoundsException.class, () -> {emptyList.remove(-1);});
        assertThrows(IndexOutOfBoundsException.class, () -> {emptyList.remove(1);});

        assertThrows(IndexOutOfBoundsException.class, () -> {singleElementList.remove(-1);});
        assertThrows(IndexOutOfBoundsException.class, () -> {singleElementList.remove(1);});

        assertThrows(IndexOutOfBoundsException.class, () -> {multiElementList.remove(-1);});
        assertThrows(IndexOutOfBoundsException.class, () -> {multiElementList.remove(3);});
    }

    @Test
    public void testSetMethodOnStartOfList() {
        assertThrows(IndexOutOfBoundsException.class, () -> {emptyList.set(0, 3);});

        singleElementList.set(0, 3);
        assertEquals(3, singleElementList.get(0));

        multiElementList.set(0, 3);
        assertEquals(3, multiElementList.get(0));
        assertEquals(1, multiElementList.get(1));
        assertEquals(2, multiElementList.get(2));
    }

    @Test
    public void testSetMethodOnEndOfList() {
        multiElementList.set(2, 3);
        assertEquals(0, multiElementList.get(0));
        assertEquals(1, multiElementList.get(1));
        assertEquals(3, multiElementList.get(2));
    }

    @Test
    public void testSetMethodOnMiddleOfList() {
        multiElementList.set(1, 3);
        assertEquals(0, multiElementList.get(0));
        assertEquals(3, multiElementList.get(1));
        assertEquals(2, multiElementList.get(2));
    }

    @Test
    public void testSetMethodOnBadIndex() {
        assertThrows(IndexOutOfBoundsException.class, () -> {emptyList.set(-1, 3);});
        assertThrows(IndexOutOfBoundsException.class, () -> {emptyList.set(1, 3);});

        assertThrows(IndexOutOfBoundsException.class, () -> {singleElementList.set(-1, 3);});
        assertThrows(IndexOutOfBoundsException.class, () -> {singleElementList.set(1, 3);});

        assertThrows(IndexOutOfBoundsException.class, () -> {multiElementList.set(-1, 3);});
        assertThrows(IndexOutOfBoundsException.class, () -> {multiElementList.set(3, 3);});
    }

    @Test
    public void testClearMethod() {
        multiElementList.clear();
        assertEquals(0, multiElementList.size());
        assertThrows(NoSuchElementException.class, () -> {multiElementList.getFirst();});
        assertThrows(NoSuchElementException.class, () -> {multiElementList.getLast();});
    }
}
