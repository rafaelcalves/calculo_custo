package com.lab2.trabgb.collections.impl;

import com.lab2.trabgb.collections.List;
import com.lab2.trabgb.collections.exceptions.OverflowException;

import java.util.Iterator;

public class StaticList<E> implements List<E>, Iterable<E> {
	protected E[] elements;
	protected int numElements;

	public StaticList(int maxSize) {
		elements = (E[])new Object[maxSize];
		numElements = 0;
	}

	public int numElements() {
		return numElements;
	}

	public boolean isEmpty() {
		return numElements == 0;
	}

	public boolean isFull() {
		return numElements == elements.length;
	}

	public void insert(E element, int pos) {
		// verifica se hÃ¡ espaÃ§Â�o na lista
		if (isFull())
			throw new OverflowException();

		// verifica se a posiÃ§Ã£o Ã© vÃ¡lida
		if (pos < 0  ||  pos > numElements)
			throw new IndexOutOfBoundsException("Ã�ndice invÃ¡lido!");
		
		// desloca para a direita os elementos necessÃ¡rios,
		// abrindo espaÂ�o para o novo
		for (int i = numElements-1; i >= pos; i--)
			elements[i+1] = elements[i];
		
		// armazena o novo elemento e ajusta o total
		elements[pos] = element;
		numElements++;
	}

	public E remove(int pos) {
		// verifica se a posiÂ�Ã§Ã£o Ã© vÃ¡lida
		if (pos < 0  ||  pos >= numElements)
			throw new IndexOutOfBoundsException("Ã�ndice invÃ¡lido!");
		
		// guarda uma referÂ�Ãªncia temporÃ¡ria ao elemento removido
		E element = elements[pos];
		
		// desloca para a esquerda os elementos necessÃ¡rios,
		// sobrescrevendo a posiÂ�Ã§Ã£o do que estÃ¡ sendo removido
		for (int i = pos; i < numElements-1; i++)
			elements[i] = elements[i+1];
		
		// define para null a posiÂ�Ã§Ã£o antes ocupada pelo Ãºltimo,
		// para que a coleta de lixo possa atuar, e ajusta o total
		elements[numElements-1] = null;
		numElements--;

		return element;
	}

	public E get(int pos) {
		// verifica se a posiÂ�Ã§Ã£o Ã© vÃ¡lida
		if (pos < 0  ||  pos >= numElements)
			throw new IndexOutOfBoundsException("Ã�ndice invÃ¡lido!");

		return elements[pos];
	}

	public int search(E element) {
		for (int i = 0; i < numElements; i++)
			if (element.equals(elements[i]))
				return i;
		
		// se chegar atÃ© aqui, Ã© porque nÃ£o encontrou
		return -1;
	}
	
	public String toString() {
		String s = "";
		for (int i = 0; i < numElements; i++)
			s += elements[i] + " ";
		return s;
	}
	
	public boolean remove(E element) {
		/*
		 *  Pode ser otimizado, porÃ©m, a forma abaixo Ã© didÃ¡tica e usa
		 *  apenas as operaÃ§Ãµes bÃ¡sicas da classe
		 */
		int pos = search(element);
		if (pos > -1) {
			remove(pos);
			return true;
		}
		return false;
	}

	public void reverse() {
		/*
		 *  Divide-se o array ao meio para trocar os extremos
		 *  atÃ© chegar no meio do array. Ex.: numElements / 2 
		 */
		for (int i = 0; i < numElements / 2; i++) {
			E temp = elements[numElements - 1 - i];
			elements[numElements - 1 - i] = elements[i];
			elements[i] = temp;
		}
	}

	public void add(E element) {
		/*
		 *  Observe que a validaÃ§Ã£o/consistÃªncia ocorre no mÃ©todo "insert"
		 *  Neste caso, o append acrescenta sempre na prÃ³xima posiÃ§Ã£o vÃ¡lida
		 */
		insert(element, numElements);
	}
	
	public int remove(int fromIndex, int toIndex) {
		/*
		 * Os Ã­ndices precisam ser vÃ¡lidos (fromIndex <= toIndex)
		 * Os Ã­ndices precisam ser inferiores ao numElements
		 * O Ã­ndice inicial nÃ£o pode ser negativo. Consequentemente, o 
		 * o Ã­ndice final tambÃ©m serÃ¡ >= 0 por causa da primeira clÃ¡usula! 
		 */
		if (fromIndex > toIndex || 
			fromIndex >= numElements || toIndex >= numElements ||
			fromIndex < 0) {
			throw new IndexOutOfBoundsException("Ã�ndice invÃ¡lido!");
		}
		for (int i = toIndex; i >= fromIndex; i--) {
			remove(i);
		}
		return toIndex - fromIndex + 1; 
	}

	public List<E> split(int pos) {
		/*
		 * Verifica se a posiÃ§Ã£o Ã© vÃ¡lida
		 * Observe que aqui Ã© utilizado ">=", ao invÃ©s do utilizado no mÃ©todo insert acima
		 */
		if (pos < 0 || pos >= numElements) {
			throw new IndexOutOfBoundsException("Ã�ndice invÃ¡lido!");
		}
		// Cria a lista somente do tamanho necessÃ¡rio
		List<E> aux = new StaticList<>(numElements - pos);
		for (int i = numElements - 1; i >= pos; i--) {
			aux.insert(remove(i), 0);
		}
		return aux;
	}

	public void insert(List<E> lista, int pos) {
		/*
		 *  Verifica se a posiÃ§Ã£o Ã© vÃ¡lida
		 *  pos > numElements: a prÃ³xima posiÃ§Ã£o livre Ã© vÃ¡lida e,
		 *  portanto, Ã© verificado apenas se a posiÃ§Ã£o Ã© maior que numElements
		 */
		if (pos < 0 || pos > numElements) {
			throw new IndexOutOfBoundsException("Ã�ndice invÃ¡lido!");
		}
		/*
		 *  Verifica se hÃ¡ capacidade suficiente
		 *  A ideia Ã© manter a capacidade fixa da lista!
		 */
		if (lista.numElements() >  elements.length - numElements) {
			throw new OverflowException();
		}
		for (int i = 0; i < lista.numElements(); i++) {
			insert(lista.get(i), pos + i);
		}
	}
	
	public void addAll(List<E> l) {
		/*
		 *  Verifica se hÃ¡ capacidade suficiente
		 *  A ideia Ã© manter a capacidade fixa da lista!
		 */
		if (l.numElements() > elements.length - numElements()) {
			throw new OverflowException();
		}
		for (int i = 0; i < l.numElements(); i++) {
			add(l.get(i));
		}
	}
	
	public List<E> subList(int fromIndex, int toIndex) {
		if (fromIndex > toIndex || 
			fromIndex >= numElements || toIndex >= numElements ||
			toIndex < 0) {
			throw new IndexOutOfBoundsException("Ã�ndice invÃ¡lido!");
		}
		// Cria a lista somente com o tamanho necessÃ¡rio (inclusive)
		StaticList<E> aux = new StaticList<>(toIndex - fromIndex + 1);
		for (int i = fromIndex; i <= toIndex; i++) {
			aux.add(get(i));
		}
		return aux; 
	}

	@Override
	public boolean equals(Object obj) {
		StaticList other = (StaticList) obj;
		// NÃºmero de elementos diferentes
		if (numElements() != other.numElements()) {
			return false;
		}
		// Compara os elementos 
		// Assumindo que nÃ£o hÃ¡ valor null
		for (int i = 0; i < numElements(); i++) {
			if (!get(i).equals(other.get(i))) {
				return false;
			}
		}
		/*
		 * HÃ¡ o utilitÃ¡rio Arrays.equals que tem esse papel
		if (!Arrays.equals(elements, other.elements))
			return false;
		*/
		return true;
	}

	@Override
	public Iterator<E> iterator() {
		return new Iterator<E>() {
			private int current = 0;
			
			@Override
			public boolean hasNext() {
				return current < numElements;
			}
			@Override
			public E next() {
				if (!hasNext())
					throw new RuntimeException("List is empty");
				return elements[current++];
			}
		};
	}
}