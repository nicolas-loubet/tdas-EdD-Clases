package ar.edu.uns.cs.ed.tdas.tdalista;

import java.util.Iterator;

import ar.edu.uns.cs.ed.tdas.Position;

public class Tester {
	
	public static void ejercicio2() {
		ListaDoblementeEnlazada<Integer> pl= new ListaDoblementeEnlazada<Integer>();
		pl.addLast(1);
		pl.addLast(2);
		pl.addLast(3);
		pl.addLast(4);
		pl.addLast(5);
		
		pl.ejercicioTP4_2(8,9);
		System.out.println(pl);
	}

	private static <E> boolean buscarSiEsta(E e1, PositionList<E> l) {
		for(E e: l)
			if(e.equals(e1))
				return true;
		return false;
	}

	private static <E> int contarApariciones(E e1, PositionList<E> l) {
		int contador= 0;
		for(E e: l)
			contador+= e.equals(e1) ? 1:0;
		return contador;
	}

	private static <E> boolean contarSiApareceNVeces(E x, int N, PositionList<E> l) {
		int contador= 0;
		for(E e: l)
			contador+= (e == x) ? 1:0;
		return contador >= N;
	}
	
	public static void ejercicio3() {
		ListaDoblementeEnlazada<Integer> pl= new ListaDoblementeEnlazada<Integer>();
		pl.addLast(1);
		pl.addLast(2);
		pl.addLast(4);
		pl.addLast(3);
		pl.addLast(4);
		pl.addLast(5);
		
		System.out.println(pl);
		System.out.println(buscarSiEsta(4,pl));
		System.out.println(contarApariciones(4,pl));
		System.out.println(contarSiApareceNVeces(4,2,pl));
	}
	
	private static <E> PositionList<E> duplicarCadaElemento(PositionList<E> lista) {
		PositionList<E> nueva= new ListaDoblementeEnlazada<E>();
		for(E e: lista) {
			nueva.addLast(e);
			nueva.addLast(e);
		}
		return nueva;
	}
	
	public static void ejercicio4() {
		ListaDoblementeEnlazada<Integer> pl= new ListaDoblementeEnlazada<Integer>();
		pl.addLast(1);
		pl.addLast(2);
		pl.addLast(3);
		pl.addLast(4);
		pl.addLast(5);
		
		System.out.println(duplicarCadaElemento(pl));
	}
	
	private static <E> Iterable<E> eliminarRepeticiones(PositionList<E> l1, PositionList<E> l2) {
		PositionList<E> repeticiones= new ListaDoblementeEnlazada<E>();
		
		PositionList<Position<E>> posicionesL2= new ListaDoblementeEnlazada<>();
	    for(Position<E> p: l2.positions())
	        posicionesL2.addLast(p);
	    
	    for(Position<E> pe2: posicionesL2)
	        for(E e1: l1)
	            if(pe2.element().equals(e1)) {
	                repeticiones.addLast(l2.remove(pe2));
	                break; //Sale del bucle sobre l1, sigue con el siguiente pe2
	            }
		return repeticiones;
	}
	
	public static void ejercicio5() {
		PositionList<Integer> pl1= new ListaDoblementeEnlazada<Integer>();
		pl1.addLast(1);
		pl1.addLast(2);
		pl1.addLast(3);
		pl1.addLast(4);
		pl1.addLast(5);
		pl1.addLast(6);
		pl1.addLast(7);
		pl1.addLast(8);

		PositionList<Integer> pl2= new ListaDoblementeEnlazada<Integer>();
		pl2.addLast(1);
		pl2.addLast(2);
		pl2.addLast(3);
		pl2.addLast(4);
		pl2.addLast(5);
		pl2.addLast(6);
		pl2.addLast(7);
		pl2.addLast(8);
		
		System.out.println("Antes: l1 = " + pl1);
		
	    Iterable<Integer> eliminados= eliminarRepeticiones(pl2,pl1);
	    
	    System.out.print("Elementos eliminados: [");
	    StringBuilder sb= new StringBuilder();
	    for(Integer e : eliminados)
	        sb.append(e).append(", ");
	    if(sb.length() > 0)
	        sb.setLength(sb.length()-2);
	    System.out.println(sb + "]");
	    System.out.println("Después: l1 = " + pl1);
	}
	
	private static <E> PositionList<E> intercalar(PositionList<E> l1, PositionList<E> l2) {
		PositionList<E> res= new ListaDoblementeEnlazada<E>();
		
		Iterator<E> it1= l1.iterator();
		Iterator<E> it2= l2.iterator();
		
		while(it1.hasNext() || it2.hasNext()) {
			if(it1.hasNext()) res.addLast(it1.next());
			if(it2.hasNext()) res.addLast(it2.next());
		}
		
		return res;
	}
	
	private static PositionList<Integer> intercalarSinRepeticiones(PositionList<Integer> l1, PositionList<Integer> l2) {
		PositionList<Integer> res= new ListaDoblementeEnlazada<Integer>();
		
		Iterator<Integer> it1= l1.iterator();
		Iterator<Integer> it2= l2.iterator();
		Integer cursor1= it1.hasNext() ? it1.next() : null;
		Integer cursor2= it2.hasNext() ? it2.next() : null;
		Integer ultimo_agregado= null;

		while(cursor1 != null || cursor2 != null) {
			Integer a_agregar= null;
			if(cursor1 == null) {
				a_agregar= cursor2;
				cursor2= it2.hasNext() ? it2.next() : null;
			} else if(cursor2 == null) {
				a_agregar= cursor1;
				cursor1= it1.hasNext() ? it1.next() : null;
			} else if(cursor1.compareTo(cursor2) < 0) {
				a_agregar= cursor1;
				cursor1= it1.hasNext() ? it1.next() : null;
			} else if(cursor1.compareTo(cursor2) > 0) {
				a_agregar= cursor2;
				cursor2= it2.hasNext() ? it2.next() : null;
			} else {
	            a_agregar = cursor1;
	            cursor1= it1.hasNext() ? it1.next() : null;
	            cursor2= it2.hasNext() ? it2.next() : null;
	        }
			
			if(a_agregar != null) {
	            if(ultimo_agregado == null || !a_agregar.equals(ultimo_agregado)) {
	                res.addLast(a_agregar);
	                ultimo_agregado= a_agregar;
	            }
	        } else {
	            throw new IllegalArgumentException("Elemento null encontrado en una de las listas");
	        }
		}
		
		return res;
	}
	
	public static void ejercicio6() {
		PositionList<Integer> pl1= new ListaDoblementeEnlazada<Integer>();
		pl1.addLast(1);
		pl1.addLast(2);
		pl1.addLast(2);
		pl1.addLast(2);
		pl1.addLast(3);
		pl1.addLast(4);
		pl1.addLast(7);
		pl1.addLast(11);
		pl1.addLast(11);

		PositionList<Integer> pl2= new ListaDoblementeEnlazada<Integer>();
		pl2.addLast(3);
		pl2.addLast(5);
		pl2.addLast(6);
		pl2.addLast(8);
		pl2.addLast(11);

		System.out.println(intercalar(pl1,pl2)); //Ejercicio 6a
		System.out.println(intercalarSinRepeticiones(pl1,pl2)); //Ejercicio 6a
	}
	
	private static <E> void eliminarAparicionesL2(PositionList<E> l1, PositionList<E> l2) {
		PositionList<E> copia_l1= new ListaDoblementeEnlazada<E>();
		for(E e: l1)
			copia_l1.addLast(e);
		
		for(Position<E> p: l1.positions()) {
			if(buscarSiEsta(p.element(),l2))
				l1.remove(p);
		}
	}
	
	private static <E> PositionList<E> invertir(PositionList<E> l) {
		PositionList<E> inv= new ListaDoblementeEnlazada<E>();
		
		for(E e: l)
			inv.addFirst(e);
		return inv;
	}
	
	private static <E> void insertarAlFinal(PositionList<E> l1, PositionList<E> l2) {
		for(E e: l2)
			l1.addLast(e);
	}
	
	private static <E> void eliminar(PositionList<E> l1, PositionList<E> l2) {
		eliminarAparicionesL2(l1,l2);
		insertarAlFinal(l1,invertir(l2));
	}
	
	public static void ejercicio7() {
		PositionList<Integer> pl1= new ListaDoblementeEnlazada<Integer>();
		pl1.addLast(1);
		pl1.addLast(2);
		pl1.addLast(2);
		pl1.addLast(3);
		pl1.addLast(4);
		pl1.addLast(2);
		pl1.addLast(7);
		pl1.addLast(8);
		pl1.addLast(11);
		pl1.addLast(4);
		pl1.addLast(11);

		PositionList<Integer> pl2= new ListaDoblementeEnlazada<Integer>();
		pl2.addLast(2);
		pl2.addLast(5);
		pl2.addLast(6);
		pl2.addLast(8);
		pl2.addLast(11);
		
		eliminar(pl1,pl2);
		System.out.println(pl1);
	}
	
	public static void main(String[] args) {
		ejercicio7();
	}
}
