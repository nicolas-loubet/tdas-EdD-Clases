package ar.edu.uns.cs.ed.tdas.tdalista;

import java.util.Iterator;
import java.util.NoSuchElementException;

import ar.edu.uns.cs.ed.tdas.Position;

public class PositionListIterator<E> implements Iterator<E> {
	protected PositionList<E> lista;
	protected Position<E> cursor;
	
	public PositionListIterator(PositionList<E> lista) {
		this.lista= lista;
		cursor= lista.isEmpty() ? null : lista.first();
	}
	
	@Override
	public boolean hasNext() {
		return (cursor != null);
	}

	@Override
	public E next() {
		if(cursor == null) throw new NoSuchElementException("No hay más elementos en la lista.");
		E element= cursor.element();
		cursor= (cursor == lista.last()) ? null : lista.next(cursor);
		return element;
	}
	
	@Override
	public void remove() {
	    throw new UnsupportedOperationException("Remove no está soportado");
	}
}
