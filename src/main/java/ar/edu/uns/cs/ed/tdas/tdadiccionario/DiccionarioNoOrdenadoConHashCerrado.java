package ar.edu.uns.cs.ed.tdas.tdadiccionario;

import ar.edu.uns.cs.ed.tdas.Entry;
import ar.edu.uns.cs.ed.tdas.excepciones.InvalidEntryException;
import ar.edu.uns.cs.ed.tdas.excepciones.InvalidKeyException;
import ar.edu.uns.cs.ed.tdas.tdalista.ListaDoblementeEnlazada;
import ar.edu.uns.cs.ed.tdas.tdalista.PositionList;
import ar.edu.uns.cs.ed.tdas.tdamapeo.Entrada;

public class DiccionarioNoOrdenadoConHashCerrado<K,V> implements Dictionary<K,V> {
	int size;
	int N;
	Entry<K,V> entries[];

	private final Entrada<K,V> DISPONIBLE= new Entrada<K,V>();

	@SuppressWarnings("unchecked")
	public DiccionarioNoOrdenadoConHashCerrado(int N) { /// O(N)
		this.N= N;
		size= 0;
		entries= new Entrada[N];
		for(int i= 0; i < N; i++)
			entries[i]= null;
	}

	public DiccionarioNoOrdenadoConHashCerrado() { /// O(N)
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
		int N_viejo= N;
		Entry<K,V> lista_anterior[]= entries;
		
		N= proximoPrimo(N*2+1);
		entries= new Entrada[N];
		size= 0;
		for(int i= 0; i < N; i++)
			entries[i]= null;
		
		for(int i= 0; i < N_viejo; i++)
			if(lista_anterior[i] != null && !lista_anterior[i].equals(DISPONIBLE))
				insert(lista_anterior[i].getKey(),lista_anterior[i].getValue());
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
		if(key == null) throw new InvalidKeyException("La key no puede ser nula");
		for(int i= hash(key); entries[i] != null; i++) {
			if(entries[i] == DISPONIBLE) continue;
			if(entries[i].getKey().equals(key))
				return entries[i];
			if(i == N-1) i= -1;
		}
		return null;
	}

	@Override
	public Iterable<Entry<K,V>> findAll(K key) { /// O(1)
		if(key == null) throw new InvalidKeyException("La key no puede ser nula");
		PositionList<Entry<K,V>> l= new ListaDoblementeEnlazada<Entry<K,V>>();
		for(int i= hash(key); entries[i] != null; i++) {
			if(entries[i] == DISPONIBLE) continue;
			if(entries[i].getKey().equals(key))
				l.addLast(entries[i]);
			if(i == N-1) i= -1;
		}
		return l;
	}

	@Override
	public Entry<K,V> insert(K key, V value) { /// O(1)
		if(key == null) throw new InvalidKeyException("La key no puede ser nula");
		Entry<K,V> e= new Entrada<K,V>(key,value);
		int i;
		for(i= hash(key); entries[i] != null; i++) {
			if(entries[i] == DISPONIBLE) break;
			if(i == N-1) i= -1;
		}
		entries[i]= e;
		size++;
		if(((float) size)/N > 0.5) rehash();
		return e;
	}

	@Override
	public Entry<K,V> remove(Entry<K,V> e) { /// O(1)
		if(e == null) throw new InvalidEntryException("Entrada nula no admitida");
		for(int i= hash(e.getKey()); entries[i] != null; i++) {
			if(entries[i] == e) {
				entries[i]= DISPONIBLE;
				size--;
				return e;
			}
		}
		throw new InvalidEntryException("La entrada no se encontró en el diccionario");
	}

	@Override
	public Iterable<Entry<K,V>> entries() { /// O(N+n)
		PositionList<Entry<K,V>> l= new ListaDoblementeEnlazada<Entry<K,V>>();
		for(int i= 0; i < N; i++) {
			if(entries[i] == null) continue;
			if(entries[i] == DISPONIBLE) continue;
			l.addLast(entries[i]);
		}
		return l;
	}

	public Iterable<Entry<K,V>> eliminarTodas(K c,V v) { /// O(N+n)
		PositionList<Entry<K,V>> borrados= new ListaDoblementeEnlazada<Entry<K,V>>();
		for(Entry<K,V> e: entries())
			if(e.getKey().equals(c) && e.getValue().equals(v))
				borrados.addLast(remove(e));
		return borrados;
	}

}
