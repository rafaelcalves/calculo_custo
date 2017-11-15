package com.lab2.trabgb.collections.impl;

import com.lab2.trabgb.collections.Stack;
import com.lab2.trabgb.collections.exceptions.OverflowException;
import com.lab2.trabgb.collections.exceptions.UnderflowException;

public class StaticStack<E> implements Stack<E> {
	// Ã�ndice do elemento no topo da pilha
	protected int top;

	// Array que armazena as referÂ�ncias para os elementos
	protected E[] elements;

	@SuppressWarnings("unchecked")
	public StaticStack(int maxSize) {
		elements = (E[]) new Object[maxSize];
		top = -1;
	}

	public boolean isEmpty() {
		return top == -1;
	}

	public boolean isFull() {
		return top == elements.length - 1;
	}

	public int numElements() {
		return top + 1;
	}
	
	public void push(E element) throws OverflowException {
		if (isFull())
			throw new OverflowException();
		elements[++top] = element;
	}

	public E pop() throws UnderflowException {
		if (isEmpty())
			throw new UnderflowException();
		E element = elements[top];
		elements[top--] = null; 
		return element;
	}

	public E top() throws UnderflowException {
		if (isEmpty())
			throw new UnderflowException();
		return elements[top];
	}

	public String toString() {
		if (isEmpty())
			return "[Empty]";
		else {
			String s = "[";
			for (int i = numElements() - 1; i >= 0; i--) {
				s += elements[i] + ",";
			}
			return s.substring(0, s.length() - 1) + "]";
		}
  }
}