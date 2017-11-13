package com.lab2.trabgb.ADT;

import com.lab2.trabgb.ADT.exceptions.OverflowException;
import com.lab2.trabgb.ADT.exceptions.UnderflowException;

public interface Queue<E> {
	  public int numElements();  
	  public boolean isEmpty();
	  public boolean isFull();
	  public E front() throws UnderflowException;
	  public E back() throws UnderflowException;
	  public void enqueue(E element) throws OverflowException;
	  public E dequeue() throws UnderflowException;  
}