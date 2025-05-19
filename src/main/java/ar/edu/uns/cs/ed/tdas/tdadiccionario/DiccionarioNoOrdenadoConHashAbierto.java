package ar.edu.uns.cs.ed.tdas.tdadiccionario;

import ar.edu.uns.cs.ed.tdas.Entry;
import ar.edu.uns.cs.ed.tdas.excepciones.InvalidEntryException;
import ar.edu.uns.cs.ed.tdas.excepciones.InvalidKeyException;
import ar.edu.uns.cs.ed.tdas.tdalista.ListaDoblementeEnlazada;
import ar.edu.uns.cs.ed.tdas.tdalista.PositionList;

public class DiccionarioNoOrdenadoConHashAbierto<K,V> implements Dictionary<K,V> {
	int N;
	DiccionarioNoOrdenadoConLista<K,V> buckets[];
	int size;

	@SuppressWarnings("unchecked")
	public DiccionarioNoOrdenadoConHashAbierto(int N) {
		this.N= N;
		size= 0;
		buckets= new DiccionarioNoOrdenadoConLista[N];
		for(int i= 0; i < N; i++)
			buckets[i]= new DiccionarioNoOrdenadoConLista<K,V>();
	}

	public DiccionarioNoOrdenadoConHashAbierto() {
		this(31);
	}

	private int hash(K i) {
		if(i == null) throw new InvalidKeyException("La key no puede ser nula");
		return i.hashCode()%N;
	}
	
	private boolean esPrimo(int v) {
		for(int i= 2; i < v-1; i++)
			if(v % i == 0)
				return false;
		return true;
	}
	
	private int proximoPrimo(int v) {
		while(true) {
			if(esPrimo(v))
				return v;
			else
				v+= 2;
		}
	}
	
	@SuppressWarnings("unchecked")
	private void rehash() {
		Iterable<Entry<K,V>> it= entries();
		N= proximoPrimo(N*2+1);
		size= 0;
		buckets= new DiccionarioNoOrdenadoConLista[N];
		for(int i= 0; i < N; i++)
			buckets[i]= new DiccionarioNoOrdenadoConLista<K,V>();
		for(Entry<K,V> e: it)
			insert(e.getKey(),e.getValue());
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
	public Entry<K,V> find(K key) {
		return buckets[hash(key)].find(key);
	}

	@Override
	public Iterable<Entry<K, V>> findAll(K key) {
		return buckets[hash(key)].findAll(key);
	}

	@Override
	public Entry<K,V> insert(K key, V value) {
		if(key == null) throw new InvalidKeyException("La key no puede ser nula");
		Entry<K,V> output= buckets[hash(key)].insert(key,value);
		if(output != null)
			size++;
		if(((float) size)/N > 0.95) rehash();
		return output;
	}

	@Override
	public Entry<K,V> remove(Entry<K,V> e) {
		if(e == null) throw new InvalidEntryException("Entrada nula no admitida");
		Entry<K,V> output= buckets[hash(e.getKey())].remove(e);
		if(output != null)
			size--;
		return output;
	}

	@Override
	public Iterable<Entry<K, V>> entries() {
		PositionList<Entry<K,V>> entries= new ListaDoblementeEnlazada<Entry<K,V>>();
		for(int i= 0; i < N; i++)
			for(Entry<K,V> e: buckets[i].entries())
				entries.addLast(e);
		return entries;
	}

}
