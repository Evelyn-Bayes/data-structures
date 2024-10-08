package io.eevee.util;
/**
 * Base interface for all set data structures.
 *
 * <p>Specifications:
 * <ul>
 *     <li>Arbitrary index based access - false
 *     <li>Enforces natural ordering - unspecified
 *     <li>Allows duplicates - false
 *     <li>Allows nulls - false
 * </ul>
 *
 * @see Collection
 * @param <E> the type of the elements stored in this collection
 */ 
public interface Set<E> extends Collection<E> {}
