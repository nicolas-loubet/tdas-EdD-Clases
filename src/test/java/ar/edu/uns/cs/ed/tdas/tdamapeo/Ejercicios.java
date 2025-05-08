package ar.edu.uns.cs.ed.tdas.tdamapeo;

import ar.edu.uns.cs.ed.tdas.Entry;
import ar.edu.uns.cs.ed.tdas.tdadiccionario.DiccionarioNoOrdenadoConLista;
import ar.edu.uns.cs.ed.tdas.tdadiccionario.Dictionary;
import ar.edu.uns.cs.ed.tdas.tdalista.ListaDoblementeEnlazada;
import ar.edu.uns.cs.ed.tdas.tdalista.PositionList;

public class Ejercicios {

	public static void main(String[] args) {
		ejercicio1();
	}

	private static void ejercicio1() {
		Map<Integer,Integer> M1= new MapConLista<Integer,Integer>();
		Map<Integer,Integer> M2= new MapConLista<Integer,Integer>();
		M1.put(1,5);
		M2.put(29306,1);
		M1.put(2,5);
		M1.put(3,5);
		M1.put(29303,7);
		M1.put(4,5);
		M1.put(5,5);
		M1.put(6,5);
		M1.put(1,6);
		M1.put(2,6);
		M1.put(29308,8);
		M1.put(3,6);
		M2.put(7,5);
		M2.put(29308,4);
		M2.put(8,5);
		M2.put(9,5);
		M2.put(10,5);
		M2.put(29306,1);
		M2.put(11,5);
		M2.put(29303,8);
		M2.put(12,5);
		M2.put(13,5);
		M2.put(14,5);
		M2.put(15,5);

		System.out.println("a:"+buscarDiscrepancias(M1,M2));
		System.out.println("b:"+revisarKeysM1ContenidasM2(M1,M2));
		
		Dictionary<Integer,Character> D= new DiccionarioNoOrdenadoConLista<>();
		D.insert(1,'a');
		D.insert(2,'b');
		D.insert(3,'a');
		D.insert(2,'c');
		D.insert(1,'d');
		D.insert(4,'b');
		
		System.out.println("c:"+eliminarRepeticionesDiccionario(D));
		
		PositionList<Character> texto= new ListaDoblementeEnlazada<Character>();
		texto.addLast('a');
		texto.addLast('b');
		texto.addLast('a');
		texto.addLast('c');
		texto.addLast('d');
		texto.addLast('b');
		
		System.out.println("d:"+contarApariciones(texto));
	}

	private static <K,V> PositionList<Entry<K,V>> buscarDiscrepancias(Map<K,V> m1, Map<K,V> m2) {
		PositionList<Entry<K,V>> L= new ListaDoblementeEnlazada<Entry<K,V>>();
		for(Entry<K,V> e1: m1.entries())
			for(Entry<K,V> e2: m2.entries())
				if(e1.getKey().equals(e2.getKey()) && !e1.getValue().equals(e2.getValue())) {
					L.addLast(e1);
					L.addLast(e2);
				}
		return L;
	}

	private static <K,V> boolean revisarKeysM1ContenidasM2(Map<K,V> m1, Map<K,V> m2) {
		for(K k1: m1.keys()) {
			boolean encontrada= false;
			for(K k2: m2.keys())
				if(k1.equals(k2)) {
					encontrada= true;
					break;
				}
			if(!encontrada)
				return false;
		}
		return true;
	}

	private static <K,V> Dictionary<K,V> eliminarRepeticionesDiccionario(Dictionary<K,V> d) {
		Map<K,V> mapa= new MapConLista<K,V>();
		for(Entry<K,V> e: d.entries())
			mapa.put(e.getKey(),e.getValue());
		
		Dictionary<K,V> output= new DiccionarioNoOrdenadoConLista<K,V>();
		for(Entry<K,V> e: mapa.entries())
			output.insert(e.getKey(),e.getValue());
		return output;
	}

	private static <T> Map<T,Integer> contarApariciones(PositionList<T> lista) {
		Map<T,Integer> mapa= new MapConLista<T,Integer>();
		
		for(T i: lista) {
			Integer cuenta= mapa.get(i);
			cuenta= cuenta == null ? 0 : cuenta;
			mapa.put(i, cuenta+1);
		}
		
		return mapa;
	}

}
