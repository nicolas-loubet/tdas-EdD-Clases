package ar.edu.uns.cs.ed.tdas.tdacolaconprioridad;

import java.util.Comparator;

import ar.edu.uns.cs.ed.tdas.Entry;
import ar.edu.uns.cs.ed.tdas.excepciones.*;

public class ColaConPrioridadConHeap<K,V> implements PriorityQueue<K,V> {
	@SuppressWarnings("hiding")
	private class Entrada<K,V> implements Entry<K,V> {
		private K clave;
		private V valor;
		
		public Entrada(K key, V value) { clave= key; valor= value; }
		
		public K getKey() { return clave; }
		public V getValue() { return valor; }
		//public void setKey(K key) { clave= key; }
		//public void setValue(V value) { valor= value; }
		
		public String toString() {
			return "{" + getKey() + ":" + getValue() + "}";
		}
	}
	
	protected Entrada<K,V> [] lista_entradas;
	protected Comparator<K> comparador;
	protected int size;
	protected int MAX_SIZE;
	
	@SuppressWarnings("unchecked")
	private void resize() { /// O(N)
		Entrada<K,V> [] anterior= lista_entradas;
		MAX_SIZE*= 2;
		lista_entradas= (Entrada<K,V> []) new Entrada[MAX_SIZE];
		for(int i= 1; i <= size; i++)
			lista_entradas[i]= anterior[i];
	}
	
	@SuppressWarnings("unchecked")
	public ColaConPrioridadConHeap(int max_size, Comparator<K> comp) { /// O(1)
		lista_entradas= (Entrada<K,V> []) new Entrada[max_size];
		this.comparador= comp;
		MAX_SIZE= max_size;
		size= 0;
	}
	public ColaConPrioridadConHeap(Comparator<K> comp) { /// O(1)
		this(100,comp);
	}

	@Override
	public int size() { /// O(1)
		return size;
	}

	@Override
	public boolean isEmpty() { /// O(1)
		return size() == 0;
	}

	@Override
	public Entry<K,V> min() { /// O(1)
		if(isEmpty())
			throw new EmptyPriorityQueueException("No puede hacer min() si la cola está vacía");
		return lista_entradas[1];
	}

	@Override
	public Entry<K,V> insert(K key, V value) { /// O(h) ~ O(log(N))
		if(key == null) throw new InvalidKeyException("Clave nula no admitida");
		if(value == null) throw new InvalidEntryException("Valor nulo no admitida");
		Entrada<K,V> entrada= new Entrada<K,V>(key, value);
		lista_entradas[++size]= entrada;
		if(size == MAX_SIZE-1) resize();
		
		int i= size;
		while(i > 1) {
			Entrada<K,V> actual= lista_entradas[i];
			Entrada<K,V> padre= lista_entradas[i/2];
			if(comparador.compare(actual.getKey(),padre.getKey()) < 0) {
				Entrada<K,V> aux= lista_entradas[i];
				lista_entradas[i]= lista_entradas[i/2];
				lista_entradas[i/2] = aux;
				i/= 2;
			} else break;
		}
		return entrada;
	}
	
	private Entry<K,V> borrarRoot() {
		Entry<K,V> borrar= min();
		lista_entradas[1]= null;
		size= 0;
		return borrar;
	}
	
	private Entry<K,V> borrarUltimoYPonerEnRoot() {
		Entry<K,V> root= min();
		lista_entradas[1]= lista_entradas[size];
		lista_entradas[size--]= null;
		return root;
	}
	
	private void swapPosiciones(int p1, int p2) {
		Entrada<K,V> aux= lista_entradas[p1];
		lista_entradas[p1]= lista_entradas[p2];
		lista_entradas[p2]= aux;
	}
	
	private void reubicarNuevoRootRecursivamente(int pos_actual) {
		int hijo_izq= pos_actual*2;
		int hijo_der= pos_actual*2+1;
		if(hijo_izq > size) return;
		
		int hijo_minimo= ((hijo_der > size) || // Si no hay derecho, devuelvo true (el izq)
				(comparador.compare(lista_entradas[hijo_izq].getKey(), // Si el menor es izq devuelvo true
						            lista_entradas[hijo_der].getKey()) < 0)) ? hijo_izq : hijo_der;
		
		if(comparador.compare(lista_entradas[pos_actual].getKey(),
							  lista_entradas[hijo_minimo].getKey()) > 0) {
			swapPosiciones(pos_actual,hijo_minimo);
			reubicarNuevoRootRecursivamente(hijo_minimo);
		}
	}

	@Override
	public Entry<K,V> removeMin() { /// O(h) ~ O(log(N))
		if(size == 1) { return borrarRoot(); }
		Entry<K,V> minimo_actual= borrarUltimoYPonerEnRoot();
		reubicarNuevoRootRecursivamente(1);
		return minimo_actual;
	}

}
