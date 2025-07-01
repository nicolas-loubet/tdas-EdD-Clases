package ar.edu.uns.cs.ed.tdas.tdamapeo;

import ar.edu.uns.cs.ed.tdas.Entry;
import ar.edu.uns.cs.ed.tdas.excepciones.InvalidKeyException;
import ar.edu.uns.cs.ed.tdas.tdalista.ListaDoblementeEnlazada;
import ar.edu.uns.cs.ed.tdas.tdalista.PositionList;

public class MapConHashAbierto<K,V> implements Map<K,V> {
	int N;
	MapConLista<K,V> buckets[];
	int size;

	@SuppressWarnings("unchecked")
	public MapConHashAbierto(int N) { /// O(n)
		this.N= N;
		size= 0;
		buckets= new MapConLista[N];
		for(int i= 0; i < N; i++)
			buckets[i]= new MapConLista<K,V>();
	}

	public MapConHashAbierto() { /// O(n)
		this(31);
	}

	private int hash(K i) { /// O(1)
		if(i == null) throw new InvalidKeyException("La key no puede ser nula");
		return Math.abs(i.hashCode()%N);
	}
	
	private boolean esPrimo(int v) { /// O(raiz(v))
		// No tengo que evaluar el 2 porque voy a trabajar siempre con impares
		for(int i= 3; i < Math.sqrt(v); i+= 2) // O(raiz(v)/2)
			if(v % i == 0)
				return false;
		return true;
	}
	
	private int proximoPrimo(int v) { /// O(raiz(v) * numero de v necesarios hasta encontrar el siguiente, acotado por PI(v))
		while(true) {
			if(esPrimo(v))
				return v;
			else
				v+= 2; //Salteo los pares
		}
	}
	
	@SuppressWarnings("unchecked")
	private void rehash() { /// O(n + N)
		Iterable<Entry<K,V>> it= entries();
		N= proximoPrimo(N*2+1);
		size= 0;
		buckets= new MapConLista[N];
		for(int i= 0; i < N; i++)
			buckets[i]= new MapConLista<K,V>();
		for(Entry<K,V> e: it)
			put(e.getKey(),e.getValue());
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
	public V get(K key) { /// O(1)
		return buckets[hash(key)].get(key);
	}

	@Override
	public V put(K key, V value) { /// O(1)
		V output= buckets[hash(key)].put(key,value);
		if(output == null)
			size++;
		if(((float) size)/N > 0.95) rehash();
		return output;
	}

	@Override
	public V remove(K key) { /// O(1)
		V output= buckets[hash(key)].remove(key);
		if(output != null)
			size--;
		return output;
	}

	@Override
	public Iterable<K> keys() { /// O(n+N)
		PositionList<K> keys= new ListaDoblementeEnlazada<K>();
		for(int i= 0; i < N; i++)
			for(K k: buckets[i].keys())
				keys.addLast(k);
		return keys;
	}

	@Override
	public Iterable<V> values() { /// O(n+N)
		PositionList<V> values= new ListaDoblementeEnlazada<V>();
		for(int i= 0; i < N; i++)
			for(V v: buckets[i].values())
				values.addLast(v);
		return values;
	}

	@Override
	public Iterable<Entry<K,V>> entries() { /// O(n+N)
		PositionList<Entry<K,V>> entries= new ListaDoblementeEnlazada<Entry<K,V>>();
		for(int i= 0; i < N; i++)
			for(Entry<K,V> e: buckets[i].entries())
				entries.addLast(e);
		return entries;
	}

	@Override
	public String toString() {
		StringBuilder sb= new StringBuilder(); sb.append("{");
		boolean primero= true;
		for(Entry<K,V> e: entries()) {
			if(!primero)
				sb.append(", ");
			sb.append(e);
			primero= false;
		}
		sb.append("}");
		return sb.toString();
	}

}
