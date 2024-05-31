package io.eevee.util;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.lang.IndexOutOfBoundsException;
import java.lang.Integer;
import java.util.NoSuchElementException;

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
        emptySet = new HashSet<Integer>();
        
        singleElementSet = new HashSet<Integer>();
        singleElementSet.add(0);

        multiElementSet = new HashSet<Integer>();
        multiElementSet.add(0);
        multiElementSet.add(1);
        multiElementSet.add(2);
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
        assertEquals(3, multiElementSet.size());
    }

    @Test
    public void testClearMethod() {
        multiElementSet.clear();
        assertEquals(0, multiElementSet.size());
        assertEquals(false, multiElementSet.contains(0));
        assertEquals(false, multiElementSet.contains(1));
        assertEquals(false, multiElementSet.contains(2));
    }

    /* @Test
    public void testIterable() {
        HashSet comparisonSet = new HashSet<Integer>();
        comparisonSet.add(0);
        comparisonSet.add(1);
        comparisonSet.add(2);
        Integer startingValue = 0;
        for (Integer element : multiElementSet) {
            comparisonSet.remove(element);
        }
        assertEquals(0, comparisonSet.size());
    }
    */

    @Test
    public void testRemoveObjectMethodOnSingleElementSet() {
        singleElementSet.remove(0);
        assertEquals(0, singleElementSet.size());
        assertEquals(false, singleElementSet.contains(0));
    }

    @Test
    public void testRemoveObjectMethodOnMultiElementSet() {
        multiElementSet.remove(1);
        assertEquals(2, multiElementSet.size());
        assertEquals(false, multiElementSet.contains(1));

        multiElementSet.remove(1);
        assertEquals(2, multiElementSet.size());
    }
}
