package ar.edu.uns.cs.ed.tdas.tdacola;

import ar.edu.uns.cs.ed.tdas.excepciones.EmptyQueueException;
import ar.edu.uns.cs.ed.tdas.excepciones.InvalidEntryException;

public class ColaConArregloCircular<E> implements Queue<E> {
	private E[] datos;
	private int f;
	private int r;
	private int size;
	private int MAX_SIZE;
	private int INCREMENTO_OVERFLOW;
	
	@SuppressWarnings("unchecked")
	public ColaConArregloCircular(int MAX, int INCREMENTO_OVERFLOW) {
		if(MAX <= 0 || INCREMENTO_OVERFLOW <= 0) throw new InvalidEntryException("Tamaño MAX="+MAX+" e INCREMENTO_OVERFLOW="+INCREMENTO_OVERFLOW+" no pueden ser negativos ni 0");
		size= 0;
		f= 0;
		r= 0;
		datos= (E[]) new Object[MAX];
		MAX_SIZE= MAX;
		this.INCREMENTO_OVERFLOW= INCREMENTO_OVERFLOW;
	}

	public ColaConArregloCircular(int MAX) {
		this(MAX,20);
	}

	public ColaConArregloCircular() {
		this(20);
	}

	@Override
	public String toString() {
	    StringBuilder sb= new StringBuilder("Cola (f->t): [");
	    for(int i= 0; i < size; i++) {
	        sb.append(datos[(f+i)%MAX_SIZE]);
	        if(i < size-1) sb.append(" ; ");
	    }
	    sb.append("]");
	    return sb.toString();
	}

	private void redimencionar() {
		@SuppressWarnings("unchecked")
		E[] datos_nuevos= (E[]) new Object[MAX_SIZE+INCREMENTO_OVERFLOW];
		for(int i= 0; i < MAX_SIZE; i++)
			datos_nuevos[i]= datos[(f+i)%MAX_SIZE];
		f= 0;
		r= size;
		
		MAX_SIZE+= INCREMENTO_OVERFLOW;
		datos= datos_nuevos;
	}

	@Override
	public void enqueue(E item) {
		if(size == MAX_SIZE) redimencionar();
		datos[r]= item;
		r= (r+1)%MAX_SIZE;
		size++;
	}

	@Override
	public E front() {
		if(isEmpty()) throw new EmptyQueueException("No hay elementos en la cola.");
		return datos[f];
	}

	@Override
	public E dequeue() {
		E borrado= front();
		datos[f]= null;
		f= (f+1)%MAX_SIZE;
		size--;
		return borrado;
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public int size() {
		return size;
	}

}
