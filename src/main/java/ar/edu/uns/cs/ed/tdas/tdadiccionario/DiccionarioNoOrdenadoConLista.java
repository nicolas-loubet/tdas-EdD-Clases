package ar.edu.uns.cs.ed.tdas.tdadiccionario;

import ar.edu.uns.cs.ed.tdas.Entry;
import ar.edu.uns.cs.ed.tdas.Position;
import ar.edu.uns.cs.ed.tdas.excepciones.InvalidEntryException;
import ar.edu.uns.cs.ed.tdas.excepciones.InvalidKeyException;
import ar.edu.uns.cs.ed.tdas.tdalista.ListaDoblementeEnlazada;
import ar.edu.uns.cs.ed.tdas.tdalista.PositionList;
import ar.edu.uns.cs.ed.tdas.tdamapeo.Entrada;

public class DiccionarioNoOrdenadoConLista<K,V> implements Dictionary<K,V> {
	PositionList<Entry<K,V>> lista;
	
	public DiccionarioNoOrdenadoConLista() { /// O(1)
		lista= new ListaDoblementeEnlazada<Entry<K,V>>();
	}
	
	@Override
	public int size() { /// O(1)
		return lista.size();
	}

	@Override
	public boolean isEmpty() { /// O(1)
		return size() == 0;
	}

	@Override
	public Entry<K,V> find(K key) { /// O(n)
		if(key == null) throw new InvalidKeyException("La key no puede ser nula");
		for(Entry<K,V> e: lista)
			if(e.getKey().equals(key))
				return e;
		return null;
	}

	@Override
	public Iterable<Entry<K,V>> findAll(K key) { /// O(n)
		if(key == null) throw new InvalidKeyException("La key no puede ser nula");
		PositionList<Entry<K,V>> l= new ListaDoblementeEnlazada<Entry<K,V>>();
		for(Entry<K,V> e: lista)
			if(e.getKey().equals(key))
				l.addLast(e);
		return l;
	}

	@Override
	public Entry<K,V> insert(K key, V value) { /// O(1)
		if(key == null) throw new InvalidKeyException("La key no puede ser nula");
		Entry<K,V> e= new Entrada<K,V>(key, value);
		lista.addLast(e);
		return e;
	}

	@Override
	public Entry<K,V> remove(Entry<K,V> e) { /// O(n)
		if(e == null) throw new InvalidEntryException("Entrada nula no admitida");
		for(Position<Entry<K,V>> p: lista.positions())
			if(p.element() == e) {
				lista.remove(p);
				return e;
			}
		throw new InvalidEntryException("La entrada no se encontró en el diccionario");
	}

	@Override
	public Iterable<Entry<K,V>> entries() { /// O(1)
		return lista;
	}
	
	@Override
	public String toString() {
		StringBuilder sb= new StringBuilder();
		sb.append("{");
		boolean primero= true;
		for(Entry<K,V> e: lista) {
			if(!primero)
				sb.append(", ");
			sb.append(e);
			primero= false;
		}
		sb.append("}");
		return sb.toString();
	}
	
	public Iterable<Entry<K,V>> eliminarTodas(K c,V v) { /// O(n)
		PositionList<Entry<K,V>> borrados= new ListaDoblementeEnlazada<Entry<K,V>>();
		for(Entry<K,V> e: entries())
			if(e.getKey().equals(c) && e.getValue().equals(v))
				borrados.addLast(remove(e));
		return borrados;
	}

}
