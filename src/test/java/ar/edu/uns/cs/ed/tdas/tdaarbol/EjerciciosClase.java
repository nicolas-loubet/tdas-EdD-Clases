package ar.edu.uns.cs.ed.tdas.tdaarbol;

import ar.edu.uns.cs.ed.tdas.Position;
import ar.edu.uns.cs.ed.tdas.excepciones.InvalidOperationException;
import ar.edu.uns.cs.ed.tdas.tdalista.ListaDoblementeEnlazada;
import ar.edu.uns.cs.ed.tdas.tdalista.PositionList;
import ar.edu.uns.cs.ed.tdas.tdamapeo.Map;
import ar.edu.uns.cs.ed.tdas.tdamapeo.MapConHashAbierto;

public class EjerciciosClase {

	public static void main(String[] args) {
		ej6();
	}

	private static void prueba() {
		ArbolConLista<Integer> T= new ArbolConLista<Integer>();
		Position<Integer> h1, h2, h3, h4, h5;
		h1 = h2 = h3 = h4 = h5 = null;
		T.createRoot(1);
		h1 = T.addFirstChild(T.root(), 2);
		h1 = T.addAfter(T.root(), h1, 3);
		h2 = T.addAfter(T.root(), h1, 6);
		h3 = T.addAfter(T.root(), h2, 9);
		T.addFirstChild(h1, 4);
		T.addLastChild(h1, 5);
		h4 = T.addFirstChild(h2, 7);
		T.addFirstChild(h4, 8);
		T.addFirstChild(h3, 10);
		h5 = T.addLastChild(h3, 11);
		T.addFirstChild(h5, 12);
		
		for(Position<Integer> i: T.positions())
			System.out.print(i.element()+" ");
		System.out.println();
	}

	private static void ej2() {
		ArbolConLista<Integer> T= new ArbolConLista<Integer>();
		Position<Integer> h1, h2, h3, h4, h5;
		h1 = h2 = h3 = h4 = h5 = null;
		T.createRoot(1);
		h1 = T.addFirstChild(T.root(), 2);
		h1 = T.addAfter(T.root(), h1, 3);
		h2 = T.addAfter(T.root(), h1, 6);
		h3 = T.addAfter(T.root(), h2, 9);
		T.addFirstChild(h1, 4);
		T.addLastChild(h1, 5);
		h4 = T.addFirstChild(h2, 7);
		T.addFirstChild(h4, 8);
		T.addFirstChild(h3, 10);
		h5 = T.addLastChild(h3, 11);
		h5 = T.addFirstChild(h5, 12);
		
		System.out.println(T);
		T.eliminarUltimoHijo(h3);
		System.out.println(T);
	}
	
	private static void ej3() {
		ArbolConLista<Character> T= new ArbolConLista<Character>();
		T.createRoot('A');
		Position<Character> p_A= T.addLastChild(T.root(),'A');
		T.addLastChild(T.root(),'B');
		Position<Character> p_C= T.addLastChild(T.root(),'C');
		T.addLastChild(p_A,'A');
		T.addLastChild(p_A,'B');
		T.addLastChild(p_C,'A');
		System.out.println(T);
		System.out.println(cantidadRepeticiones(T));
	}
	
	public static Map<Character,Integer> cantidadRepeticiones(Tree<Character> t) {
		Map<Character,Integer> mapa= new MapConHashAbierto<Character,Integer>();
		for(char c: t) {
			if(mapa.get(c) == null)
				mapa.put(c,0);
			mapa.put(c,mapa.get(c)+1);
		}
		return mapa;
	}
	
	private static void ej4() {
		Tree<String> T= new ArbolConLista<String>();
		Position<String> h1, h2, h3, h4, h5;
		h1 = h2 = h3 = h4 = h5 = null;
		T.createRoot("s1");
		h1 = T.addFirstChild(T.root(), "s2");
		h1 = T.addAfter(T.root(), h1, "s3");
		h2 = T.addAfter(T.root(), h1, "s6");
		h3 = T.addAfter(T.root(), h2, "s9");
		T.addFirstChild(h1, "s4");
		T.addLastChild(h1, "s5");
		h4 = T.addFirstChild(h2, "s7");
		T.addFirstChild(h4, "s8");
		T.addFirstChild(h3, "s10");
		h5 = T.addLastChild(h3, "s11");
		h5 = T.addFirstChild(h5, "s12");
		
		System.out.println(T);
		for(Position<String> p: posicionesDeAparicion(T,"s8"))
			System.out.println(p);
	}

	public static <E> Iterable<Position<E>> posicionesDeAparicion(Tree<E> a, E s) {
		PositionList<Position<E>> lista= new ListaDoblementeEnlazada<Position<E>>();
		
		revisarSiEncuentroS_postorder(lista,a,a.root(),s);
		
		return lista;
	}

	private static <E> void revisarSiEncuentroS_postorder(PositionList<Position<E>> lista, Tree<E> a, Position<E> pos, E s) {
		for(Position<E> i: a.children(pos))
			revisarSiEncuentroS_postorder(lista,a,i,s);
		if(pos.element().equals(s))
			lista.addLast(pos);
	}

	private static void ej5() {
		ArbolConLista<Integer> T= new ArbolConLista<Integer>();
		Position<Integer> h1, h2, h3, h4, h5;
		h1 = h2 = h3 = h4 = h5 = null;
		T.createRoot(1);
		h1 = T.addFirstChild(T.root(), 2);
		h1 = T.addAfter(T.root(), h1, 5);
		h2 = T.addAfter(T.root(), h1, 6);
		h3 = T.addAfter(T.root(), h2, 9);
		T.addFirstChild(h1, 4);
		T.addLastChild(h1, 5);
		h4 = T.addFirstChild(h2, 5);
		T.addFirstChild(h4, 5);
		T.addFirstChild(h3, 10);
		h5 = T.addLastChild(h3, 11);
		T.addFirstChild(h5, 12);
		
		System.out.println(T);
		System.out.println(borrarTodasLasOcurrencias(T,5));
		System.out.println(T);
	}

	private static <E> int borrarTodasLasOcurrencias(Tree<E> t, E value) { // O(N * grado_root)
		int cuenta_eliminados= 0;
		for(Position<E> pos: t.positions()) { // N repeticiones
			if(pos.element().equals(value)) {
				if(pos == t.root()) {
					// Esta parte se podría reemplazar con un try-catch
					int cuenta_hijos= 0;
					for(Position<E> hijo: t.children(pos)) // Grado de root repeticiones
						cuenta_hijos++;
					if(cuenta_hijos >= 2) throw new InvalidOperationException("No puedo borrar el root con más de un hijo");
				}
				t.removeNode(pos);
				cuenta_eliminados++;
			}
		}
		return cuenta_eliminados;
	}
	
	private static void ej6() {
		ArbolConLista<Integer> T= new ArbolConLista<Integer>();
		Position<Integer> h1, h2, h3, h4, h5;
		h1 = h2 = h3 = h4 = h5 = null;
		T.createRoot(1);
		h1 = T.addFirstChild(T.root(), 2);
		h1 = T.addAfter(T.root(), h1, 3);
		h2 = T.addAfter(T.root(), h1, 6);
		h3 = T.addAfter(T.root(), h2, 9);
		T.addFirstChild(h1, 4);
		T.addLastChild(h1, 5);
		h4 = T.addFirstChild(h2, 7);
		T.addFirstChild(h4, 8);
		T.addFirstChild(h3, 10);
		h5 = T.addLastChild(h3, 11);
		h5 = T.addFirstChild(h5, 12);
		
		System.out.println(T);
		System.out.println(buscarNoExhaustivo(T,15));
	}
	
	private static <E> boolean buscarNoExhaustivo(Tree<E> t, E value) { // O(N) peor caso
		for(E i: t) // N repeticiones
			if(i.equals(value))
				return true;
		return false;
	}

}
