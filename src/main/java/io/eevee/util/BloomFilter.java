package io.eevee.util;


/**
 * Bloom Filter implementation.
 *
 * This is a probabilistic data structure which with a small memory footprint and low compute cost
 * is able to determine if an object has not been stored in the data structure with certainty or
 * has been stored with a high degree of accuracy.
 *
 * The current implementation takes byte arrays as input so any user will need to have selected
 * an appropriate and consistent approach to serialization to use it.
 *
 * <p>Complexity:
 * <ul>
 *   <li>Space - O(1)
 *   <li>Access - O(1)
 *   <li>Insert - O(1)
 *   <li>Remove - NA
 * </ul>
 */

public class BloomFilter {
    private boolean[] bitArray;
    private int numHashFunctions;

    // Rounded up to the nearest power of two given a default of 5 hash functions and targer false positive rate of 3%
    private static int MIN_BIT_ARRAY_SIZE = 8192;

    // Taken from Google's Guava implementation
    private static int DEFAULT_NUM_HASH_FUNCTIONS = 5;

    BloomFilter() {
        bitArray = new boolean[MIN_BIT_ARRAY_SIZE];
        numHashFunctions = DEFAULT_NUM_HASH_FUNCTIONS;
    }

    BloomFilter(float targetFalsePositiveRate, int targetNumberOfElements) {
        numHashFunctions = optimalNumHashFunctions(targetFalsePositiveRate);
        bitArray = new boolean[optimalBitArraySize(targetFalsePositiveRate, targetNumberOfElements, numHashFunctions)];
    }

    // Visible and strictly available for testing
    int bitSize() {
        return bitArray.length;
    }

    // Visible and strictly available for testing
    int bitsSet() {
        int count = 0;
        for (boolean b : bitArray) {
            if (b == true) count++;
        }
        return count;
    }

    // Visible and strictly available for testing
    int numHashFunctions() {
        return numHashFunctions;
    }

    /**
     * Inserts markers indicating the inclusion of the object in the Bloom Filter
     *
     * <p>Complexity: O(1).
     *
     * @param bytes byte array representation of the object to be added
     */
    public void add(byte[] bytes) {
        for (int i = 0; i < numHashFunctions; i++) {
            int hash = Murmur.hash(bytes, i * Murmur.DEFAULT_SEED);
            int index = Math.floorMod(hash, bitArray.length);
            bitArray[index] = true;
        }
    }

    /**
     * Returns false if the object is guaranteed to not exist in the Bloom Filter.
     *
     * <p>Complexity: O(1).
     *
     * @param bytes byte array representation of the object
     * @return false if the object has not been included in the bloom filter
     */
    public boolean mayContain(byte[] bytes) {
        for (int i = 0; i < numHashFunctions; i++) {
            int hash = Murmur.hash(bytes, i * Murmur.DEFAULT_SEED);
            int index = Math.floorMod(hash, bitArray.length);
            if (bitArray[index] == false) {
                return false;
            }
        }
        return true;
    }

    private int optimalBitArraySize(float targetFalsePositiveRate, int targetNumberOfElements, int numHashFunctions) {
        double requiredBitsPerInsert = - numHashFunctions / Math.log(1 - Math.pow(targetFalsePositiveRate, 1.0f / numHashFunctions));
        double requiredBits = requiredBitsPerInsert * targetNumberOfElements;
        return (int) requiredBits;
    }

    private int optimalNumHashFunctions(float targetFalsePositiveRate) {
        double optimalNumHashFunctions = - Math.log(targetFalsePositiveRate) / Math.log(2);
        if (optimalNumHashFunctions < 1) {
            return 1;
        } else if (optimalNumHashFunctions < 256) {
            // Converts the type and rounds down to the nearest int
            return (int) optimalNumHashFunctions;
        } else {
            return 256;
        }
    }
}
