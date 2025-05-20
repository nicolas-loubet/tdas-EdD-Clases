package ar.edu.uns.cs.ed.tdas.tdamapeo;

import ar.edu.uns.cs.ed.tdas.Entry;
import ar.edu.uns.cs.ed.tdas.excepciones.InvalidKeyException;
import ar.edu.uns.cs.ed.tdas.tdalista.ListaDoblementeEnlazada;
import ar.edu.uns.cs.ed.tdas.tdalista.PositionList;

public class MapConHashCerrado<K,V> implements Map<K,V> {
	int N;
	Entrada<K,V> entries[];
	int size;
	
	private final Entrada<K,V> DISPONIBLE= new Entrada<K,V>();

	@SuppressWarnings("unchecked")
	public MapConHashCerrado(int N) { /// O(N)
		this.N= N;
		size= 0;
		entries= new Entrada[N];
		for(int i= 0; i < N; i++)
			entries[i]= null;
	}

	public MapConHashCerrado() { /// O(N)
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
	private void rehash() { /// O(n+N)
		int N_viejo= N;
		Entrada<K,V> lista_anterior[]= entries;
		
		N= proximoPrimo(N*2+1);
		entries= new Entrada[N];
		size= 0;
		for(int i= 0; i < N; i++)
			entries[i]= null;
		
		for(int i= 0; i < N_viejo; i++)
			if(lista_anterior[i] != null && !lista_anterior[i].equals(DISPONIBLE))
				put(lista_anterior[i].getKey(),lista_anterior[i].getValue());
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
		if(key == null) throw new InvalidKeyException("La key no puede ser nula");
		for(int i= hash(key); entries[i] != null; i= (i+1)%N) {
			if(entries[i].getKey().equals(key))
				return entries[i].getValue();
		}
		return null;
	}

	@Override
	public V put(K key, V value) { /// O(1)
		if(key == null) throw new InvalidKeyException("La key no puede ser nula");
		int i_disponible= -1;
		int i;
		for(i= hash(key); entries[i] != null; i= (i+1)%N) {
			if(entries[i].equals(DISPONIBLE)) {
				if(i_disponible == -1)
					i_disponible= i;
			} else if(entries[i].getKey().equals(key)) {
				V anterior= entries[i_disponible].getValue();
				entries[i_disponible].setValue(value);
				return anterior;
			}
		}
		if(i_disponible == -1)
			i_disponible= i;
		entries[i]= new Entrada<K,V>(key,value);
		size++;
		if(((float) size)/N > 0.5) rehash();
		return null;
	}

	@Override
	public V remove(K key) { /// O(1)
		if(key == null) throw new InvalidKeyException("La key no puede ser nula");
		for(int i= hash(key); entries[i] != null; i= (i+1)%N) {
			if(!entries[i].equals(DISPONIBLE) && entries[i].getKey().equals(key)) {
				V output= entries[i].getValue();
				entries[i]= DISPONIBLE;
				size--;
				return output;
			}
		}
		return null;
	}

	@Override
	public Iterable<K> keys() { /// O(N+n)
		PositionList<K> keys= new ListaDoblementeEnlazada<K>();
		for(int i= 0; i < N; i++)
			if(entries[i] != null && !entries[i].equals(DISPONIBLE))
				keys.addLast(entries[i].getKey());
		return keys;
	}

	@Override
	public Iterable<V> values() { /// O(N+n)
		PositionList<V> values= new ListaDoblementeEnlazada<V>();
		for(int i= 0; i < N; i++)
			if(entries[i] != null && !entries[i].equals(DISPONIBLE))
				values.addLast(entries[i].getValue());
		return values;
	}

	@Override
	public Iterable<Entry<K,V>> entries() { /// O(N+n)
		PositionList<Entry<K,V>> entradas= new ListaDoblementeEnlazada<Entry<K,V>>();
		for(int i= 0; i < N; i++)
			if(entries[i] != null && !entries[i].equals(DISPONIBLE))
				entradas.addLast(entries[i]);
		return entradas;
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
