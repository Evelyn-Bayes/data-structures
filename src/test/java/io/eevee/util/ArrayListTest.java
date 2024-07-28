package io.eevee.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.lang.IndexOutOfBoundsException;
import java.lang.Integer;
import java.util.NoSuchElementException;
import java.util.stream.IntStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit test for the ArrayList class.
 */
public class ArrayListTest {

    private ArrayList<Integer> emptyList;
    private ArrayList<Integer> singleElementList;
    private ArrayList<Integer> multiElementList;
    private ArrayList<Integer> atCapacityList;

    @BeforeEach
    public void setup() {
        emptyList = toList(IntStream.empty().toArray());
        singleElementList = toList(IntStream.of(0).toArray());
        multiElementList = toList(IntStream.range(0, 3).toArray());
        atCapacityList = toList(IntStream.range(0, 10).toArray());
    }

    private ArrayList<Integer> toList(int[] array) {
        ArrayList<Integer> list = new ArrayList<>();
        for (int i : array) {
            list.addLast(i);
        }
        return list;
    }

    private void compare(ArrayList<Integer> list, int[] array) {
        assertEquals(array.length, list.size());
        for (int i = 0; i < array.length; i++) {
            assertEquals(array[i], list.get(i));
        }
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
        compare(emptyList, IntStream.of(3).toArray());
        
        singleElementList.addFirst(3);
        compare(singleElementList, IntStream.of(3,0).toArray());
        
        multiElementList.addFirst(3);
        compare(multiElementList, IntStream.of(3,0,1,2).toArray());
    }

    @Test
    public void testAddLastMethod() {
        emptyList.addLast(3);
        compare(emptyList, IntStream.of(3).toArray());
        
        singleElementList.addLast(3);
        compare(singleElementList, IntStream.of(0,3).toArray());
        
        multiElementList.addLast(3);
        compare(multiElementList, IntStream.of(0,1,2,3).toArray());
    }


    @Test
    public void testAddMethodOnStartOfList() {
        emptyList.add(0, 3);
        compare(emptyList, IntStream.of(3).toArray());
        
        singleElementList.add(0, 3);
        compare(singleElementList, IntStream.of(3,0).toArray());
        
        multiElementList.add(0, 3);
        compare(multiElementList, IntStream.of(3,0,1,2).toArray());
    }

    @Test
    public void testAddMethodOnEndOfList() {
        singleElementList.add(1, 3);
        compare(singleElementList, IntStream.of(0,3).toArray());
        
        multiElementList.add(3, 3);
        compare(multiElementList, IntStream.of(0,1,2,3).toArray());
    }

    @Test
    public void testAddMethodOnMiddleOfList() {
        multiElementList.add(2, 3);
        compare(multiElementList, IntStream.of(0,1,3,2).toArray());
    }

    @Test
    public void testAddIndexMethodOnBadIndex() {
        assertThrows(IndexOutOfBoundsException.class, () -> {emptyList.add(-1, 3);});
        assertThrows(IndexOutOfBoundsException.class, () -> {emptyList.add(1, 3);});

        assertThrows(IndexOutOfBoundsException.class, () -> {singleElementList.add(-1, 3);});
        assertThrows(IndexOutOfBoundsException.class, () -> {singleElementList.add(2, 3);});

        assertThrows(IndexOutOfBoundsException.class, () -> {multiElementList.add(-1, 3);});
        assertThrows(IndexOutOfBoundsException.class, () -> {multiElementList.add(4, 3);});
    }

    @Test
    public void testRemoveFirstMethod() {
        assertThrows(NoSuchElementException.class, () -> {emptyList.removeFirst();});

        singleElementList.removeFirst();
        compare(singleElementList, IntStream.empty().toArray());

        multiElementList.removeFirst();
        compare(multiElementList, IntStream.of(1,2).toArray());
    }

    @Test
    public void testRemoveLastMethod() {
        assertThrows(NoSuchElementException.class, () -> {emptyList.removeLast();});

        singleElementList.removeLast();
        compare(singleElementList, IntStream.empty().toArray());

        multiElementList.removeLast();
        compare(multiElementList, IntStream.of(0,1).toArray());
    }

    @Test
    public void testRemoveMethodOnStartOfList() {
        assertThrows(IndexOutOfBoundsException.class, () -> {emptyList.remove(0);});

        singleElementList.remove(0);
        compare(singleElementList, IntStream.empty().toArray());

        multiElementList.remove(0);
        compare(multiElementList, IntStream.of(1,2).toArray());
    }

    @Test
    public void testRemoveMethodOnEndOfList() {
        multiElementList.remove(2);
        compare(multiElementList, IntStream.of(0,1).toArray());
    }

    @Test
    public void testRemoveMethodOnMiddleOfList() {
        multiElementList.remove(1);
        compare(multiElementList, IntStream.of(0,2).toArray());
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
        compare(singleElementList, IntStream.of(3).toArray());

        multiElementList.set(0, 3);
        compare(multiElementList, IntStream.of(3,1,2).toArray());
    }

    @Test
    public void testSetMethodOnEndOfList() {
        multiElementList.set(2, 3);
        compare(multiElementList, IntStream.of(0,1,3).toArray());
    }

    @Test
    public void testSetMethodOnMiddleOfList() {
        multiElementList.set(1, 3);
        compare(multiElementList, IntStream.of(0,3,2).toArray());
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
    public void testRemoveObjectMethodOnStartOfList() {
        emptyList.remove(Integer.valueOf(0));
        compare(emptyList, IntStream.empty().toArray());

        singleElementList.remove(Integer.valueOf(0));
        compare(singleElementList, IntStream.empty().toArray());

        multiElementList.remove(Integer.valueOf(0));
        compare(multiElementList, IntStream.of(1,2).toArray());
    }

    @Test
    public void testRemoveObjectMethodOnEndOfList() {
        multiElementList.remove(Integer.valueOf(2));
        compare(multiElementList, IntStream.of(0,1).toArray());
    }

    @Test
    public void testRemoveObjectMethodOnMiddleOfList() {
        multiElementList.remove(Integer.valueOf(1));
        compare(multiElementList, IntStream.of(0,2).toArray());
    }

    @Test
    public void testRemoveObjectMethodOnInvalidObject() {
        multiElementList.remove(Integer.valueOf(3));
        compare(multiElementList, IntStream.of(0,1,2).toArray());
    }

    @Test
    public void testRemoveObjectMethodHandlesOrderCorrectlyForListContainingDuplicates() {
        ArrayList<Integer> listWithDuplicates = toList(IntStream.of(0,3,1,3,2).toArray());
        listWithDuplicates.remove(Integer.valueOf(3));
        compare(listWithDuplicates, IntStream.of(0,1,3,2).toArray());
    }

    @Test
    public void testClearMethod() {
        multiElementList.clear();
        assertEquals(0, multiElementList.size());
        assertThrows(NoSuchElementException.class, () -> {multiElementList.getFirst();});
        assertThrows(NoSuchElementException.class, () -> {multiElementList.getLast();});
        assertEquals(10, multiElementList.capacity());
    }

    @Test
    public void testIterable() {
        Integer startingValue = 0;
        for (Integer node : multiElementList) {
            assertEquals(startingValue, node);
            startingValue++;
        }
    }

    @Test
    public void testListGrowsToFitNewElements() {
        assertEquals(10, atCapacityList.size());
        assertEquals(10, atCapacityList.capacity());
        atCapacityList.addLast(10);
        assertEquals(11, atCapacityList.size());
        assertEquals(20, atCapacityList.capacity());
        compare(atCapacityList, IntStream.of(0,1,2,3,4,5,6,7,8,9,10).toArray());
    }

    @Test
    public void testTrimToSize() {
        atCapacityList.addLast(10);
        atCapacityList.trimToSize();
        assertEquals(11, atCapacityList.capacity());
        compare(atCapacityList, IntStream.of(0,1,2,3,4,5,6,7,8,9,10).toArray());
    }

    @Test
    public void testContains() {
        assertEquals(false, emptyList.contains(0));
        assertEquals(true, singleElementList.contains(0));
        assertEquals(false, singleElementList.contains(1));
        assertEquals(true, multiElementList.contains(0));
        assertEquals(true, multiElementList.contains(1));
        assertEquals(true, multiElementList.contains(2));
        assertEquals(false, multiElementList.contains(3));
    }
}
