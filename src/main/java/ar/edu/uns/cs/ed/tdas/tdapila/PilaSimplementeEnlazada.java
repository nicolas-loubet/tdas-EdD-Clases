package ar.edu.uns.cs.ed.tdas.tdapila;

import ar.edu.uns.cs.ed.tdas.Position;
import ar.edu.uns.cs.ed.tdas.excepciones.EmptyStackException;

public class PilaSimplementeEnlazada<E> implements Stack<E> {
	
	@SuppressWarnings("hiding")
	private class Nodo<E> implements Position<E> {
		private E elem;
		private Nodo<E> sig;
		
		public Nodo(E elem) { this.elem= elem; }
		
		@Override
		public E element() { return elem; }
		
		public Nodo<E> getSiguiente() { return sig; }
		public void setSiguiente(Nodo<E> sig) { this.sig= sig; }
		//public void setElement(E elem) { this.elem= elem; }
	}
	
	int size;
	Nodo<E> top;
	
	public PilaSimplementeEnlazada() {
		top= null;
		size= 0;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean isEmpty() {
		return size() == 0;
	}

	@Override
	public E top() {
		if(isEmpty()) throw new EmptyStackException("Pila vacía, no puede hacer top()");
		return top.element();
	}

	@Override
	public void push(E element) {
		Nodo<E> n= new Nodo<E>(element);
		n.setSiguiente(top);
		top= n;
		size++;
	}

	@Override
	public E pop() {
		E output= top();
		top= top.getSiguiente();
		size--;
		return output;
	}

}
