package com.lab2.trabgb.collections.impl;

import com.lab2.trabgb.collections.Queue;
import com.lab2.trabgb.collections.exceptions.UnderflowException;

public class LinkedQueue<E> implements Queue<E> {
	protected Node<E> first, last;
	protected int numElements;

	public LinkedQueue() {
		first = last = null;
		numElements = 0;
	}

	public boolean isEmpty() {
		return numElements == 0;
	}

	public boolean isFull() {
		// uma fila com aloca��o din�mica nunca estar� cheia!
		return false;
	}

	public int numElements() {
		return numElements;
	}	

	public void enqueue(E element) {
		Node<E> newNode = new Node<E>(element);
		if (isEmpty())
			first = last = newNode;
		else {
			last.setNext(newNode);
			last = newNode;
		}
		numElements++;
	}

	public E dequeue() throws UnderflowException {
		if (isEmpty())
			throw new UnderflowException();
		E element = first.getElement();
		if (first == last)
			first = last = null;
		else
			first = first.getNext();

		numElements--;
		return element;
	}

	public E front() throws UnderflowException {
		if (isEmpty())
			throw new UnderflowException();
		return first.getElement();
	}

	public E back() {
		if (isEmpty())
			throw new UnderflowException();
		return last.getElement();
	}
	
  public String toString() {
	  if (isEmpty())
		  return "[Empty]";
	  else {
		  String s = "[" + first.getElement();
		  Node<E> cur = first.getNext();
		  while (cur != null) {
			  s += ", " + cur.getElement();
			  cur = cur.getNext();
		  }
		  return s + "]";
	  }
  }
}	