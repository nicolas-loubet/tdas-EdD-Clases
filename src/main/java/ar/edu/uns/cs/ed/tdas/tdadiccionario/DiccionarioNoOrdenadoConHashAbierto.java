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
	public DiccionarioNoOrdenadoConHashAbierto(int N) { /// O(N)
		this.N= N;
		size= 0;
		buckets= new DiccionarioNoOrdenadoConLista[N];
		for(int i= 0; i < N; i++)
			buckets[i]= new DiccionarioNoOrdenadoConLista<K,V>();
	}

	public DiccionarioNoOrdenadoConHashAbierto() { /// O(N)
		this(31);
	}

	private int hash(K i) { /// O(1)
		if(i == null) throw new InvalidKeyException("La key no puede ser nula");
		return i.hashCode()%N;
	}
	
	private boolean esPrimo(int v) { /// O(v)
		for(int i= 2; i < v-1; i++)
			if(v % i == 0)
				return false;
		return true;
	}
	
	private int proximoPrimo(int v) { /// O(v)
		while(true) {
			if(esPrimo(v))
				return v;
			else
				v+= 2;
		}
	}
	
	@SuppressWarnings("unchecked")
	private void rehash() { /// O(N+n)
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
	public int size() { /// O(1)
		return size;
	}

	@Override
	public boolean isEmpty() { /// O(1)
		return size() == 0;
	}

	@Override
	public Entry<K,V> find(K key) { /// O(1)
		return buckets[hash(key)].find(key);
	}

	@Override
	public Iterable<Entry<K, V>> findAll(K key) { /// O(1)
		return buckets[hash(key)].findAll(key);
	}

	@Override
	public Entry<K,V> insert(K key, V value) { /// O(1)
		if(key == null) throw new InvalidKeyException("La key no puede ser nula");
		Entry<K,V> output= buckets[hash(key)].insert(key,value);
		if(output != null)
			size++;
		if(((float) size)/N > 0.95) rehash();
		return output;
	}

	@Override
	public Entry<K,V> remove(Entry<K,V> e) { /// O(1)
		if(e == null) throw new InvalidEntryException("Entrada nula no admitida");
		Entry<K,V> output= buckets[hash(e.getKey())].remove(e);
		if(output != null)
			size--;
		return output;
	}

	@Override
	public Iterable<Entry<K, V>> entries() { /// O(N+n)
		PositionList<Entry<K,V>> entries= new ListaDoblementeEnlazada<Entry<K,V>>();
		for(int i= 0; i < N; i++)
			for(Entry<K,V> e: buckets[i].entries())
				entries.addLast(e);
		return entries;
	}

	public Iterable<Entry<K,V>> eliminarTodas(K c,V v) { /// O(N+n)
		PositionList<Entry<K,V>> borrados= new ListaDoblementeEnlazada<Entry<K,V>>();
		for(Entry<K,V> e: entries())
			if(e.getKey().equals(c) && e.getValue().equals(v))
				borrados.addLast(remove(e));
		return borrados;
	}

}
