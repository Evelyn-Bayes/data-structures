package io.eevee.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.lang.IndexOutOfBoundsException;
import java.lang.Integer;
import java.util.NoSuchElementException;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;

/**
 * Unit test for the Murmur class.
 */
public class MurmurTest {

    @Test
    public void testHashMethod() {
        assertEquals(-965378730, Murmur.hash("".getBytes()));
        assertEquals(-1848669458, Murmur.hash("1".getBytes()));
        assertEquals(1666508496, Murmur.hash("1234".getBytes()));
        assertEquals(1964441507, Murmur.hash("12345".getBytes()));
        assertEquals(-808470058, Murmur.hash("123456".getBytes()));
        assertEquals(-1383198686, Murmur.hash("1234567".getBytes()));
        assertEquals(-80084596, Murmur.hash("A-fairly-large-string".getBytes()));
    }
}
