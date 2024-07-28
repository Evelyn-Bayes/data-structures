package io.eevee.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.lang.IndexOutOfBoundsException;
import java.lang.Integer;
import java.util.NoSuchElementException;
import java.util.stream.IntStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit test for the HashSet class.
 */
public class HashSetTest {

    private HashSet<Integer> emptySet;
    private HashSet<Integer> singleElementSet;
    private HashSet<Integer> multiElementSet;;

    @BeforeEach
    public void setup() {
        emptySet = toSet(IntStream.empty().toArray());
        singleElementSet = toSet(IntStream.of(0).toArray());
        multiElementSet = toSet(IntStream.of(0,1,2).toArray());
    }

    private HashSet<Integer> toSet(int[] array) {
        HashSet<Integer> set = new HashSet<>();
        for (int i : array) {
            set.add(i);
        }
        return set;
    }

    private void compare(HashSet<Integer> set, int[] array) {
        assertEquals(array.length, set.size());
        for (int i : array) {
            assertEquals(true, set.contains(i));
        }
    }

    @Test
    public void testSizeMethod() {
        assertEquals(0, emptySet.size());
        assertEquals(1, singleElementSet.size());
        assertEquals(3, multiElementSet.size());
    }

    @Test
    public void testContainsMethod() {
        assertEquals(false, emptySet.contains(0));

        assertEquals(true, singleElementSet.contains(0));
        assertEquals(false, singleElementSet.contains(1));

        assertEquals(true, multiElementSet.contains(0));
        assertEquals(true, multiElementSet.contains(1));
        assertEquals(true, multiElementSet.contains(2));
        assertEquals(false, multiElementSet.contains(3));
    }

    @Test
    public void testAddMethodOnDuplicate() {
        singleElementSet.add(0);
        assertEquals(1, singleElementSet.size());
        
        multiElementSet.add(0);
        multiElementSet.add(1);
        multiElementSet.add(2);
        compare(multiElementSet, IntStream.of(0,1,2).toArray());
    }

    @Test
    public void testClearMethod() {
        multiElementSet.clear();
        assertEquals(0, multiElementSet.size());
        assertEquals(false, multiElementSet.contains(0));
        assertEquals(false, multiElementSet.contains(1));
        assertEquals(false, multiElementSet.contains(2));
    }

    @Test
    public void testIterable() {
        HashSet<Integer> comparisonSet = new HashSet<>();
        comparisonSet.add(0);
        comparisonSet.add(1);
        comparisonSet.add(2);
        Integer startingValue = 0;
        for (Integer element : multiElementSet) {
            comparisonSet.remove(element);
        }
        assertEquals(0, comparisonSet.size());
    }

    @Test
    public void testRemoveObjectMethodOnSingleElementSet() {
        singleElementSet.remove(0);
        compare(singleElementSet, IntStream.empty().toArray());
    }

    @Test
    public void testRemoveObjectMethodOnMultiElementSet() {
        multiElementSet.remove(1);
        compare(multiElementSet, IntStream.of(0,2).toArray());
        assertEquals(false, multiElementSet.contains(1));

        multiElementSet.remove(1);
        compare(multiElementSet, IntStream.of(0,2).toArray());
    }
}
