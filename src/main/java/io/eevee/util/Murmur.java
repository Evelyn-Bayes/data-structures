package io.eevee.util;

/**
 * Implementation of the 32 bit Murmur 3 hash function
 **/
public class Murmur {

    public static final int DEFAULT_SEED = 104729;

    public static int hash(byte[] data) {
        return hash(data, DEFAULT_SEED);
    }

    public static int hash(byte[] data, int seed) {
        int hash = seed;

        final int numblocks = data.length / 4;
        for (int i = 0; i < numblocks; i++) {
            hash = mix(hash, blockToHashMaterial(data, i * 4));
        }

        int remainingBytes = data.length % 4;
        hash = partialMix(hash, partialBlockToHashMaterial(data, remainingBytes));

        hash = finalMix(hash, data.length);
        return hash;
    }

    // Translates 4 sequential bytes stored in Little-endian format into a standard size 32 bit unsigned int.
    //
    // Bitmasking with 0xff performs fast translation from a signed byte to an unsigned int
    // Bitshifting performs fast multiplication to their component of the full 32 bit int
    // Bitwise inclusive or of non overlapping bits performs fast addition of each component of the 32 bit int
    private static int blockToHashMaterial(byte[] data, int offset) {
        int hashMaterial = (data[offset] & 0xff)
            | (data[offset + 1] & 0xff) << 8
            | (data[offset + 2] & 0xff) << 16
            | (data[offset + 3] & 0xff) << 24;
        return hashMaterial;
    }

    // Operates similarly to blockToHashMaterial using a partial block of sequential bytes stored in Little-endian format.
    //
    // Switch statement with fall through so that we add "remainingBytes" number of byte segments together
    //
    // No bitmasking with 0xff occurs so negative bytes are not turned into the comparitively larger positive int hashMaterials
    // Also as we are using the bitwise exclusive or the sign of our returned hashMaterial will depend on if we have an odd number of negative bytes in the array
    private static int partialBlockToHashMaterial(byte[] data, int remainingBytes) {
        int hashMaterial = 0;
        switch (remainingBytes) {
            case 3: hashMaterial ^= data[data.length - remainingBytes + 2] << 16;
            case 2: hashMaterial ^= data[data.length - remainingBytes + 1] << 8;
            case 1: hashMaterial ^= data[data.length - remainingBytes];
        }
        return hashMaterial;
    }

    // Unfortunately, this and the following mixing functions have no justification for numbers in use and I cannot seem to find any documentation detailing this.
    private static int mix(int hash, int hashMaterial) {
        hashMaterial *= 0xcc9e2d51;
        hashMaterial = Integer.rotateLeft(hashMaterial, 15);
        hashMaterial *= 0x1b873593;
        hash ^= hashMaterial;
        hash = Integer.rotateLeft(hash, 13) * 5 + 0xe6546b64;
        return hash;
    }

    private static int partialMix(int hash, int hashMaterial) {
        hashMaterial *= 0xcc9e2d51;
        hashMaterial = Integer.rotateLeft(hashMaterial, 15);
        hashMaterial *= 0x1b873593;
        hash ^= hashMaterial;
        return hash;
    }

    private static int finalMix(int hash, int length) {
        hash ^= length;
        hash ^= (hash >>> 16);
        hash *= 0x85ebca6b;
        hash ^= (hash >>> 13);
        hash *= 0xc2b2ae35;
        hash ^= (hash >>> 16);
        return hash;
    }
}
