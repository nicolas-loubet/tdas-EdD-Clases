package ar.edu.uns.cs.ed.tdas.tdamapeo;

import ar.edu.uns.cs.ed.tdas.Entry;
import ar.edu.uns.cs.ed.tdas.excepciones.InvalidKeyException;

public class Entrada<K,V> implements Entry<K,V> {
	protected K key;
	protected V value;
	
	public Entrada() {
		key= null;
		value= null;
	}
	
	public Entrada(K key, V value) {
		setKey(key);
		setValue(value);
	}

	@Override
	public K getKey() {
		return key;
	}

	@Override
	public V getValue() {
		return value;
	}

	public void setKey(K key) {
		if(key == null) throw new InvalidKeyException("La clave no puede ser nula");
		this.key= key;
	}

	public void setValue(V value) {
		this.value= value;
	}
	
	@Override
	public String toString() {
		return "("+key+";"+value+")";
	}

}
