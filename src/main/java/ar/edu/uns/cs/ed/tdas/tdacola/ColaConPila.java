package ar.edu.uns.cs.ed.tdas.tdacola;

import ar.edu.uns.cs.ed.tdas.excepciones.EmptyQueueException;
import ar.edu.uns.cs.ed.tdas.excepciones.EmptyStackException;
import ar.edu.uns.cs.ed.tdas.tdapila.*;

public class ColaConPila<E> implements Queue<E> {
	private Stack<E> pila;

	public ColaConPila() {
		pila= new PilaConArreglo<E>();
	}

	@Override
	public String toString() {
	    Stack<E> pila_nueva= new PilaConArreglo<E>();

	    StringBuilder sb= new StringBuilder("Cola (h->t): [");
	    while(!pila.isEmpty()) {
	    	E item= pila.pop();
	        sb.append(item);
	        if(pila.size() > 0) sb.append(" ; ");
	        pila_nueva.push(item);
	    }
	    sb.append("]");
	    
	    pila= pila_nueva;
	    InvertirPila();
	    
	    return sb.toString();
	}

	@Override
	public int size() {
		return pila.size();
	}

	@Override
	public boolean isEmpty() {
		return size() == 0;
	}

	@Override
	public E front() {
		try {
			return pila.top();
		} catch(EmptyStackException e) {
			throw new EmptyQueueException("Cola vacía");
		}
	}
	
	private void InvertirPila() {
		Stack<E> pila_nueva= new PilaConArreglo<E>();
		while(!pila.isEmpty())
			pila_nueva.push(pila.pop());
		pila= pila_nueva;
	}

	@Override
	public void enqueue(E item) {
		InvertirPila();
		pila.push(item);
		InvertirPila();
	}

	@Override
	public E dequeue() {
		try {
			return pila.pop();
		} catch(EmptyStackException e) {
			throw new EmptyQueueException("Cola vacía");
		}
	}

}
