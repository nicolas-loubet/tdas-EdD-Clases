package ar.edu.uns.cs.ed.tdas.tdalista;

import java.util.Iterator;
import ar.edu.uns.cs.ed.tdas.Position;
import ar.edu.uns.cs.ed.tdas.excepciones.*;

public class ListaDoblementeEnlazada<E> implements PositionList<E> {
	
	@SuppressWarnings("hiding")
	private class NodoDoble<E> implements Position<E> {
		E elemento;
		NodoDoble<E> anterior;
		NodoDoble<E> siguiente;
		
		public NodoDoble(E item, NodoDoble<E> ant, NodoDoble<E> sig) {
			elemento= item;
			siguiente= sig;
			anterior= ant;
		}

		@Override
		public E element() { return elemento; }

		public NodoDoble<E> getAnterior() { return anterior; }
		public NodoDoble<E> getSiguiente() { return siguiente; }
		public void setAnterior(NodoDoble<E> ant) { anterior= ant; }
		public void setSiguiente(NodoDoble<E> sig) { siguiente= sig; }
		public void setElemento(E item) { elemento= item; }
	}
	
	
	protected NodoDoble<E> dummy;
	protected int size;
	
	public ListaDoblementeEnlazada() {
		dummy= new NodoDoble<E>(null, null, null);
		dummy.setSiguiente(dummy);
		dummy.setAnterior(dummy);
		size= 0;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	private NodoDoble<E> checkPosition(Position<E> p) {
		if(p == null)
			throw new InvalidPositionException("Posición nula");
		if(!(p instanceof NodoDoble<?>))
	        throw new InvalidPositionException("La posición no es un nodo de esta lista");
		NodoDoble<E> node= (NodoDoble<E>) p;
	    if(node.getAnterior() == null && node.getSiguiente() == null && node.element() == null)
	        throw new InvalidPositionException("La posición fue eliminada previamente");
	    return node;
	}

	public String toString() {
		if(isEmpty()) return "[]";
	    StringBuilder sb= new StringBuilder("[");
	    for (Position<E> p= first(); p != null; p= (p == last()) ? null : next(p)) {
	        sb.append(p.element());
	        if(p != last())
	        	sb.append(", ");
	    }
	    sb.append("]");
	    return sb.toString();
	}
	
	@Override
	public Position<E> first() {
		if(isEmpty()) throw new EmptyListException("La lista está vacía, no puedes buscar un head");
		return dummy.getSiguiente();
	}

	@Override
	public Position<E> last() {
		if(isEmpty()) throw new EmptyListException("La lista está vacía, no puedes buscar un head");
		return dummy.getAnterior();
	}

	@Override
	public Position<E> next(Position<E> p) {
		NodoDoble<E> pos= checkPosition(p);
		NodoDoble<E> sig= pos.getSiguiente();
		if(sig == dummy) throw new BoundaryViolationException("No se puede hacer next() al último miembro de la lista");
		return sig;
	}

	@Override
	public Position<E> prev(Position<E> p) {
		NodoDoble<E> pos= checkPosition(p);
		NodoDoble<E> ant= pos.getAnterior();
		if(ant == dummy) throw new BoundaryViolationException("No se puede hacer prev() al primer miembro de la lista");
		return ant;
	}

	@Override
	public void addAfter(Position<E> p, E element) {
		NodoDoble<E> anterior= checkPosition(p);
		NodoDoble<E> siguiente= anterior.getSiguiente();
		NodoDoble<E> nuevo= new NodoDoble<E>(element,anterior,siguiente);
		anterior.setSiguiente(nuevo);
		siguiente.setAnterior(nuevo);
		size++;
	}

	@Override
	public void addBefore(Position<E> p, E element) {
		NodoDoble<E> siguiente= checkPosition(p);
		NodoDoble<E> anterior= siguiente.getAnterior();
		NodoDoble<E> nuevo= new NodoDoble<E>(element,anterior,siguiente);
		siguiente.setAnterior(nuevo);
		anterior.setSiguiente(nuevo);
		size++;
	}

	@Override
	public void addFirst(E element) {
		NodoDoble<E> siguiente= dummy.getSiguiente();
		NodoDoble<E> nuevo= new NodoDoble<E>(element,dummy,siguiente);
		dummy.setSiguiente(nuevo);
		siguiente.setAnterior(nuevo);
		size++;
	}

	@Override
	public void addLast(E element) {
		NodoDoble<E> anterior= dummy.getAnterior();
		NodoDoble<E> nuevo= new NodoDoble<E>(element,anterior,dummy);
		dummy.setAnterior(nuevo);
		anterior.setSiguiente(nuevo);
		size++;
	}

	@Override
	public E remove(Position<E> p) {
		NodoDoble<E> borrar= checkPosition(p);
		borrar.getAnterior().setSiguiente(borrar.getSiguiente());
		borrar.getSiguiente().setAnterior(borrar.getAnterior());
		borrar.setAnterior(null);
		borrar.setSiguiente(null);
		E element= borrar.element();
		borrar.setElemento(null);
		size--;
		return element;
	}

	@Override
	public E set(Position<E> p, E element) {
		NodoDoble<E> n= checkPosition(p);
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
		ListaDoblementeEnlazada<Position<E>> lista= new ListaDoblementeEnlazada<Position<E>>();
		NodoDoble<E> cursor= dummy.getSiguiente();
		while(cursor != dummy) {
			lista.addLast(cursor);
			cursor= cursor.getSiguiente();
		}
		return lista;
	}
	
	public void ejercicioTP4_2(E e1, E e2) {
		if(size <= 1) throw new BoundaryViolationException("No tengo suficiente cantidad de elementos para ejecutar la tarea, deben haber al menos 2.");
		addAfter(first(),e1);
		addBefore(last(),e2);
	}


}
