package com.siberhus.commons.util;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Converts an iterator to an enumerator.
 * @param <ElementType> Type of element being enumerated
 */
public class IteratorEnumeration<ElementType> implements Enumeration<ElementType> {

	/**
	 * Iterator being converted to enumeration.
	 */
	private Iterator<ElementType> iterator;

	/**
	 * Create an Enumeration from an Iterator.
	 *
	 * @param iterator Iterator to convert to an enumeration.
	 *
	 */
	public IteratorEnumeration(Iterator<ElementType> iterator){
		this.iterator = iterator;
	}

	/**
	 * Tests if this enumeration contains more elements.
	 *
	 * @return true if and only if this enumeration object contains at least
	 * one more element to provide; false otherwise.
	 *
	 */
	public boolean hasMoreElements(){
		return iterator.hasNext();
	}

	/**
	 * Returns the next element of this enumeration if this enumeration
	 * object has at least one more element to provide.
	 *
	 * @return the next element of this enumeration.
	 * @throws NoSuchElementException if no more elements exist.
	 *
	 */
	public ElementType nextElement() throws NoSuchElementException {
		return iterator.next();
	}
}

