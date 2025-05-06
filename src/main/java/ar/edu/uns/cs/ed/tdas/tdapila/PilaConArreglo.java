package ar.edu.uns.cs.ed.tdas.tdapila;

import ar.edu.uns.cs.ed.tdas.excepciones.EmptyStackException;
import ar.edu.uns.cs.ed.tdas.excepciones.InvalidEntryException;

public class PilaConArreglo<E> implements Stack<E> {
	private int size;
	private E[] datos;
	private int MAX_SIZE;
	private int INCREMENTO_OVERFLOW;

	@SuppressWarnings("unchecked")
	public PilaConArreglo(int MAX, int INCREMENTO_OVERFLOW) {
		if(MAX <= 0 || INCREMENTO_OVERFLOW <= 0) throw new InvalidEntryException("Tamaño MAX="+MAX+" e INCREMENTO_OVERFLOW="+INCREMENTO_OVERFLOW+" no pueden ser negativos ni 0");
		size= 0;
		datos= (E[]) new Object[MAX];
		MAX_SIZE= MAX;
		this.INCREMENTO_OVERFLOW= INCREMENTO_OVERFLOW;
	}

	public PilaConArreglo(int MAX) {
		this(MAX,10);
	}

	public PilaConArreglo() {
		this(20);
	}

	@Override
	public String toString() {
	    StringBuilder s= new StringBuilder("Pila(t->b): [");
	    for(int i= size-1; i >= 0; i--) {
	        s.append(datos[i]);
	        if(i > 0) s.append(" ; ");
	    }
	    s.append("]");
	    return s.toString();
	}
	
	private void redimencionar() {
		@SuppressWarnings("unchecked")
		E[] datos_nuevos= (E[]) new Object[MAX_SIZE+INCREMENTO_OVERFLOW];
		for(int i= 0; i < MAX_SIZE; i++)
			datos_nuevos[i]= datos[i];
		
		MAX_SIZE+= INCREMENTO_OVERFLOW;
		datos= datos_nuevos;
	}

	@Override
	public void push(E item) {
		if(size == MAX_SIZE) redimencionar();
		
		datos[size++]= item;
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public E top() {
		if(isEmpty()) throw new EmptyStackException("No se puede hacer top a una pila vacía.");
		return datos[size-1];
	}

	@Override
	public E pop() {
		E borrado= top();
		datos[size-1]= null;
		size--;
		return borrado;
	}

	@Override
	public int size() {
		return size;
	}

}
