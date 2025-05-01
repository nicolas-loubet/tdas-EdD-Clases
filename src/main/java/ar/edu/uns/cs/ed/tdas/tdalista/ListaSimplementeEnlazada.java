package ar.edu.uns.cs.ed.tdas.tdalista;

import java.util.Iterator;
import ar.edu.uns.cs.ed.tdas.Position;
import ar.edu.uns.cs.ed.tdas.excepciones.*;

public class ListaSimplementeEnlazada<E> implements PositionList<E> {
	
	@SuppressWarnings("hiding")
	private class Nodo<E> implements Position<E> {
		E elemento;
		Nodo<E> siguiente;
		
		public Nodo(E item, Nodo<E> sig) {
			elemento= item;
			siguiente= sig;
		}

		@Override
		public E element() { return elemento; }
		
		public Nodo<E> getSiguiente() { return siguiente; }
		public void setSiguiente(Nodo<E> sig) { siguiente= sig; }
		public void setElemento(E item) { elemento= item; }

	}
	
	protected Nodo<E> head;
	protected int size;
	
	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public Position<E> first() {
		if(isEmpty()) throw new EmptyListException("La lista está vacía, no puedes buscar un head");
		return head;
	}

	private Nodo<E> checkPosition(Position<E> p) {
		try {
			if(p == null) throw new InvalidPositionException("Posición nula");
			if(p.element() == null) throw new InvalidPositionException("p eliminada previamente");
			return (Nodo<E>) p; // Puede fallar si p es una posición que corresponde a un nodo de otro tipo de estructura de datos
		} catch(ClassCastException e) {
			throw new InvalidPositionException("p no es un nodo de lista");
		}
	}

	@Override
	public Position<E> last() {
		Nodo<E> p= checkPosition(first());
		for(int i= 1; i < size; i++)
			p= p.getSiguiente();
		return p;
	}

	@Override
	public Position<E> next(Position<E> p) {
		if(p == last()) throw new BoundaryViolationException("No se puede hacer next() al último miembro de la lista");
		return checkPosition(p).getSiguiente();
	}

	@Override
	public Position<E> prev(Position<E> p) {
		checkPosition(p);
		if(p == head) throw new BoundaryViolationException("No se puede hacer prev() al primer miembro de la lista");
		Nodo<E> aux= head;
		while(aux.getSiguiente() != p && aux.getSiguiente() != null)
			aux= aux.getSiguiente();
		if(aux.getSiguiente() == null) throw new InvalidPositionException("Posición no pertenece a la lista");
		return aux;
	}

	@Override
	public void addFirst(E element) {
		head= new Nodo<E>(element,head);
		size++;
	}

	@Override
	public void addLast(E element) {
		if(isEmpty()) 
			addFirst(element);
		else
			addAfter(last(),element);
	}

	@Override
	public void addAfter(Position<E> p, E element) {
		Nodo<E> previo= checkPosition(p);
		previo.setSiguiente(new Nodo<E>(element,previo.getSiguiente()));
		size++;
	}

	@Override
	public void addBefore(Position<E> p, E element) {
		if(p == head) {
			addFirst(element);
		} else {
			Nodo<E> previo= (Nodo<E>) prev(p);
			Nodo<E> n= new Nodo<E>(element,(Nodo<E>) p);
			previo.setSiguiente(n);
			size++;
		}
	}

	@Override
	public E remove(Position<E> p) {
		Nodo<E> actual= checkPosition(p);
		if(p == head) {
			head= actual.getSiguiente();
		} else {
			Nodo<E> previo= checkPosition(prev(p));
			previo.setSiguiente(actual.getSiguiente());
		}
		size--;

		E elemento= actual.element();
		actual.setSiguiente(null);
		actual.setElemento(null);
		return elemento;
	}

	@Override
	public E set(Position<E> p, E element) {
		Nodo<E> n= checkPosition(p);
		E original= p.element();
		n.setElemento(element);
		return original;
	}

	@Override
	public Iterator<E> iterator() {
		return new PositionListIterator<E>(this);
	}

	@Override
	public Iterable<Position<E>> positions() {
		return null;
	}

}
