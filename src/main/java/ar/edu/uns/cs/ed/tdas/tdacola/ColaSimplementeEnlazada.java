package ar.edu.uns.cs.ed.tdas.tdacola;

import ar.edu.uns.cs.ed.tdas.Position;
import ar.edu.uns.cs.ed.tdas.excepciones.EmptyQueueException;

public class ColaSimplementeEnlazada<E> implements Queue<E> {
	
	@SuppressWarnings("hiding")
	private class Nodo<E> implements Position<E> {
		private E elem;
		private Nodo<E> sig;
		
		public Nodo(E elem) {
			this.elem= elem;
			sig= null;
		}
		
		@Override
		public E element() {
			return elem;
		}
		
		public Nodo<E> getSiguiente() { return sig; }
		public void setSiguiente(Nodo<E> sig) { this.sig= sig; }
		//public void setElement(E elem) { this.elem= elem; }
	}
	
	private int size;
	private Nodo<E> head;
	private Nodo<E> tail;
	
	public ColaSimplementeEnlazada() {
		size= 0;
		head= null;
		tail= null;
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
	public E front() {
		if(isEmpty()) throw new EmptyQueueException("Cola vacía, no puede hacer front()");
		return head.element();
	}

	@Override
	public void enqueue(E element) {
		Nodo<E> n= new Nodo<E>(element);
		if(isEmpty()) head= n;
		else tail.setSiguiente(n);
		tail= n;
		size++;
	}

	@Override
	public E dequeue() {
		E output= front();
		Nodo<E> sig= head.getSiguiente();
		head.setSiguiente(null);
		head= sig;
		size--;
		return output;
	}

}
