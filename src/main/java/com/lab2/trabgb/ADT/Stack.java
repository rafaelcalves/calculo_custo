package com.lab2.trabgb;

public interface Stack<E> {
	public boolean isEmpty();
	public boolean isFull();
	public int numElements();
	public void push(E element) throws OverflowException;
	public E pop() throws UnderflowException;
	public E top() throws UnderflowException;
}
