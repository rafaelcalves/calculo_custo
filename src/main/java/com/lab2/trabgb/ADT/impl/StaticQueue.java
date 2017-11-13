package com.lab2.trabgb.ADT.impl;

import com.lab2.trabgb.ADT.Queue;
import com.lab2.trabgb.ADT.exceptions.OverflowException;
import com.lab2.trabgb.ADT.exceptions.UnderflowException;

public class StaticQueue<E> implements Queue<E> {
	protected int first, last;
	protected E elements[];
		
	@SuppressWarnings("unchecked")
	public StaticQueue(int maxSize) {
		elements = (E[]) new Object[maxSize];
		first = last = -1;
	}
	
	public boolean isEmpty() {
		return first == -1;
	}

	public boolean isFull() {
		return first == (last + 1) % elements.length;
	}
	
	public int numElements() {
	   if (isEmpty())
		   return 0;
       else {
    	   int n = elements.length;
    	   return ((n + last - first) % n) + 1;
       }
	}
	
	public E front() throws UnderflowException {
		if (isEmpty())
			throw new UnderflowException();
		return elements[first];
	}
	
	public E back() throws UnderflowException {
		if (isEmpty())
			throw new UnderflowException();
		return elements[last];
	}
	
	public void enqueue(E element) throws OverflowException {
		if (isFull())
			throw new OverflowException();
		else {
		    if (last == -1)
		    	first = last = 0;
		    else
		    	last = (last + 1) % elements.length;
		    elements[last] = element;
		}
	}
	
	public E dequeue() throws UnderflowException {
		if (isEmpty())
			throw new UnderflowException();
		E element = elements[first];
		elements[first] = null; 
		if (first == last)
			first = last = -1;
		else
			first = (first + 1) % elements.length;
		return element;
	}
	
	public String toString() {
	     if (isEmpty())
	    	 return "[Empty]";		
	     else {
	    	 String s = "[" + elements[first];
	    	 int n = numElements();
	    	 for (int i = 1; i < n; i++) {
	    		 int k = (first + i) % elements.length;
	    		 s += ", " + elements[k];	    		 
	    	 }
		     return s + "]";
	     }
	}	
}