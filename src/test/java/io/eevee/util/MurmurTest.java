package io.eevee.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.lang.IndexOutOfBoundsException;
import java.lang.Integer;
import java.nio.charset.Charset;
import java.util.NoSuchElementException;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;

/**
 * Unit test for the Murmur class.
 */
public class MurmurTest {

    // Known test results taken from https://github.com/apache/kafka/blob/trunk/streams/src/test/java/org/apache/kafka/streams/state/internals/Murmur3Test.java
    @Test
    public void testHashMethod() {
        assertEquals(896581614, Murmur.hash("21".getBytes(), 123));
        assertEquals(-328928243, Murmur.hash("foobar".getBytes(), 123));
        assertEquals(-1479816207, Murmur.hash("a-little-bit-long-string".getBytes(), 123));
        assertEquals(-153232333, Murmur.hash("a-little-bit-longer-string".getBytes(), 123));
        assertEquals(13417721, Murmur.hash("lkjh234lh9fiuh90y23oiuhsafujhadof229phr9h19h89h8".getBytes(), 123));
        assertEquals(461137560, Murmur.hash(new byte[]{'a', 'b', 'c'}, 123));
    }
}
