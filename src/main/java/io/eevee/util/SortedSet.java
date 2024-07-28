package io.eevee.util;
/**
 * Base interface for all sorted set data structures.
 *
 * <p>Specifications:
 * <ul>
 *     <li>Arbitrary index based access - false
 *     <li>Enforces natural ordering - true
 *     <li>Allows duplicates - false
 *     <li>Allows nulls - false
 * </ul>
 *
 * @see Set
 * @see Collection
 * @param <E> the type of the elements stored in this collection
 */ 
public interface SortedSet<E> extends Set<E> {}
