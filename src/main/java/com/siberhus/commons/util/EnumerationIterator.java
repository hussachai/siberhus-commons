package com.siberhus.commons.util;

import java.util.Arrays;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Vector;

public class EnumerationIterator {
	
    @SuppressWarnings("unchecked")
	public static Iterator iterator(final Enumeration enumeration) {
      return new Iterator() {
        public boolean hasNext() {
          return enumeration.hasMoreElements();
        }
        
        public Object next() {
          return enumeration.nextElement();
        }

        public void remove() {
          throw new UnsupportedOperationException();
        }
      };
    }
    
    @SuppressWarnings("unchecked")
	public static Enumeration enumeration(final Iterator iterator){
    	return new Enumeration(){
			@Override
			public boolean hasMoreElements() {
				return iterator.hasNext();
			}
			@Override
			public Object nextElement() {
				return iterator.next();
			}
    		
    	};
    }
    
	@SuppressWarnings("unchecked")
	public static void main (String args[]) {
      Vector<String> v = new Vector<String>(Arrays.asList(new String[]{"hello","hi"}));
      Enumeration<String> enumeration = v.elements();
      Iterator<String> itor = EnumerationIterator.iterator(enumeration);
      while (itor.hasNext()) {
        System.out.println(itor.next());
      }
    }
  }
