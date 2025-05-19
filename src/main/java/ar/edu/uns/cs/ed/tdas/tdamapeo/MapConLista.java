package ar.edu.uns.cs.ed.tdas.tdamapeo;

import ar.edu.uns.cs.ed.tdas.Entry;
import ar.edu.uns.cs.ed.tdas.Position;
import ar.edu.uns.cs.ed.tdas.excepciones.InvalidKeyException;
import ar.edu.uns.cs.ed.tdas.tdalista.ListaDoblementeEnlazada;
import ar.edu.uns.cs.ed.tdas.tdalista.PositionList;

public class MapConLista<K,V> implements Map<K,V> {
	PositionList<Entrada<K,V>> lista;
	
	public MapConLista() {
		lista= new ListaDoblementeEnlazada<Entrada<K,V>>();
	}

	@Override
	public int size() {
		return lista.size();
	}

	@Override
	public boolean isEmpty() {
		return size() == 0;
	}

	@Override
	public V get(K key) {
		if(key == null) throw new InvalidKeyException("La key no puede ser nula");
		for(Entrada<K,V> e: lista)
			if(e.getKey().equals(key))
				return e.getValue();
		return null;
	}

	@Override
	public V put(K key, V value) {
		if(key == null) throw new InvalidKeyException("La key no puede ser nula");
		for(Entrada<K,V> e: lista)
			if(e.getKey().equals(key)) {
				V anterior= e.getValue();
				e.setValue(value);
				return anterior;
			}
		
		lista.addLast(new Entrada<K,V>(key,value));
		return null;
	}

	@Override
	public V remove(K key) {
		if(key == null) throw new InvalidKeyException("La key no puede ser nula");
		for(Position<Entrada<K,V>> p: lista.positions()) {
			if(p.element().getKey().equals(key)) {
				V anterior= p.element().getValue();
				lista.remove(p);
				return anterior;
			}
		}
		return null;
	}

	@Override
	public Iterable<K> keys() {
		PositionList<K> keys= new ListaDoblementeEnlazada<K>();
		for(Entrada<K,V> e: lista)
			keys.addLast(e.getKey());
		return keys;
	}

	@Override
	public Iterable<V> values() {
		PositionList<V> values= new ListaDoblementeEnlazada<V>();
		for(Entrada<K,V> e: lista)
			values.addLast(e.getValue());
		return values;
	}

	@Override
	public Iterable<Entry<K,V>> entries() {
		PositionList<Entry<K,V>> entradas= new ListaDoblementeEnlazada<Entry<K,V>>();
		for(Entrada<K,V> e: lista)
			entradas.addLast(e);
		return entradas;
	}

	@Override
	public String toString() {
		StringBuilder sb= new StringBuilder(); sb.append("{");
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

}
