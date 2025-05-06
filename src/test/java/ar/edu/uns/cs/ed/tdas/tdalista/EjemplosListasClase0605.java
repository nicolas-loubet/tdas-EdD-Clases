package ar.edu.uns.cs.ed.tdas.tdalista;

import java.util.Iterator;

import ar.edu.uns.cs.ed.tdas.Position;

public class EjemplosListasClase0605 {

	public static void main(String[] args) {
		PositionList<Integer> lista1= new ListaDoblementeEnlazada<Integer>();
		PositionList<Integer> lista2= new ListaDoblementeEnlazada<Integer>();
		lista1.addLast(1);
		lista1.addLast(2);
		lista1.addLast(3);
		lista1.addLast(4);
		lista1.addLast(5);
		lista1.addLast(6);
		lista1.addLast(7);
		lista1.addLast(8);
		lista1.addLast(9);
		lista1.addLast(10);

		lista2.addLast(4);
		lista2.addLast(5);
		lista2.addLast(6);
		
		//Ejercicio1
		System.out.println("Ej1_si:"+contarAparicionesE(4,lista1));
		System.out.println("Ej1_ci:"+contarAparicionesEConIterador(4,lista1));
		
		//Ejercicio2
		System.out.println("Ej2_si:"+verSiApareceE(4,lista1));
		System.out.println("Ej2_ci:"+verSiApareceEConIterador(4,lista1));
		
		//Ejercicio3
		System.out.println("Ej3_si:"+verSiSonIguales(lista1,lista1));
		System.out.println("Ej3_ci:"+verSiSonIgualesConIterador(lista1,lista1));
		System.out.println("Ej3_ci:"+verSiSonIgualesConIteradorYForEach(lista1,lista1));
		
		//Ejercicio4
		System.out.println("Ej4_ci:"+verSiEstaContenida(lista1,lista2));
		
		//Ejercicio5
		ListaDoblementeEnlazada<Integer> l1_lde= (ListaDoblementeEnlazada<Integer>) lista1;
		System.out.println("Ej5_si:"+l1_lde.clone());
		System.out.println("Ej5_ci:"+l1_lde.cloneConIterador());
		
		//Ejercicio6
		ListaDoblementeEnlazada<Integer> l1_lde_clonado= l1_lde.clone();
		l1_lde_clonado.med(4);
		System.out.println("Ej6_si:"+l1_lde_clonado);

		//Ejercicio7
		l1_lde_clonado= l1_lde.clone();
		l1_lde_clonado.atp(4);
		System.out.println("Ej7_si:"+l1_lde_clonado);
		l1_lde_clonado= l1_lde.clone();
		l1_lde_clonado.atpConIterador(4);
		System.out.println("Ej7_ci:"+l1_lde_clonado);
	}
	
	//Ejercicio 1

	private static <T> int contarAparicionesE(T e, PositionList<T> lin) {
		if(lin.isEmpty()) return 0;
		int contador= 0;
		
		Position<T> p= lin.first();
		while(p != null) {
			if(p.element().equals(e))
				contador++;
			p= p == lin.last() ? null : lin.next(p);
		}
		return contador;
	}

	private static <T> int contarAparicionesEConIterador(T e, PositionList<T> lin) {
		int contador= 0;
		
		for(T i: lin)
			if(i.equals(e))
				contador++;
		return contador;
	}
	
	//Ejercicio 2

	private static <T> boolean verSiApareceE(T e, PositionList<T> lin) {
		if(lin.isEmpty()) return false;
		
		Position<T> p= lin.first();
		while(p != null) {
			if(p.element().equals(e))
				return true;
			p= p == lin.last() ? null : lin.next(p);
		}
		return false;
	}

	private static <T> boolean verSiApareceEConIterador(T e, PositionList<T> lin) {
		for(T i: lin)
			if(i.equals(e))
				return true;
		return false;
	}
	
	//Ejercicio 3

	private static <T> boolean verSiSonIguales(PositionList<T> lin,PositionList<T> lan) {
		if(lin.isEmpty() && lan.isEmpty()) return true;
		if(lin.size() != lan.size()) return false;

		Position<T> p1= lin.first();
		Position<T> p2= lan.first();
		while(p1 != null) {
			if(!p1.element().equals(p2.element()))
				return false;
			p1= p1 == lin.last() ? null : lin.next(p1);
			p2= p2 == lan.last() ? null : lan.next(p2);
		}
		return true;
	}

	private static <T> boolean verSiSonIgualesConIterador(PositionList<T> lin,PositionList<T> lan) {
		Iterator<T> it1= lin.iterator();
		Iterator<T> it2= lan.iterator();
		while(it1.hasNext() && it2.hasNext())
			if(!it1.next().equals(it2.next()))
				return false;
		return (!it1.hasNext() && !it2.hasNext());
	}

	private static <T> boolean verSiSonIgualesConIteradorYForEach(PositionList<T> lin,PositionList<T> lan) {
		Iterator<T> it2= lan.iterator();
		for(T i: lin) {
			if(!it2.hasNext()) return false;
			if(!i.equals(it2.next()))
				return false;
		}
		return !it2.hasNext();
	}
	
	//Ejercicio 4

	private static <T> boolean verSiEstaContenida(PositionList<T> lin, PositionList<T> lan) {
		if(lan.isEmpty()) return true; //JAJA
		if(lin.isEmpty()) return false;
		
		Iterator<T> it1= lin.iterator();
		
		while(it1.hasNext()) {
			T e1= it1.next();
			Iterator<T> it2= lan.iterator();
			while(it2.hasNext()) {
				T e2= it2.next();
				if(!e1.equals(e2))
					break;
				if(!it2.hasNext()) {
					return true;
				} else {
					if(!it1.hasNext()) return false;
					e1= it1.next();
				}
			}
		}
		return false;
	}

	//Restantes, copiar en ListaDoblementeEnlazada
	//Diseñados en alto nivel, casi para usar desde afuera
	/*
	
	public ListaDoblementeEnlazada<E> clone() { //Clase 06-05
		ListaDoblementeEnlazada<E> nueva= new ListaDoblementeEnlazada<E>();
		
		if(isEmpty()) return nueva;
		
		Position<E> p= first();
		for(int i= 0; i < size()-1; i++) {
			nueva.addLast(p.element());
			p= next(p);
		}
		nueva.addLast(p.element());
		return nueva;
	}

	public ListaDoblementeEnlazada<E> cloneConIterador() { //Clase 06-05
		ListaDoblementeEnlazada<E> nueva= new ListaDoblementeEnlazada<E>();
		
		if(isEmpty()) return nueva;
		
		for(E i: this)
			nueva.addLast(i);
		return nueva;
	}

	public void med(E e) { //Clase 06-05
		if(isEmpty()) return;
		
		Position<E> p= first();
		while(p != null) {
			Position<E> p_sig= p == last() ? null : next(p);
			if(p.element().equals(e))
				remove(p);
			p= p_sig;
		}
	}

	public void atp(E e) { //Clase 06-05
		if(isEmpty()) return;
		
		Position<E> p= first();
		while(p != null) {
			if(!p.element().equals(e))
				addBefore(p,e);
			p= p == last() ? null : next(p);
		}
	}

	public void atpConIterador(E e) { //Clase 06-05
		for(Position<E> p: positions())
			if(!p.element().equals(e))
				addBefore(p,e);
	}

	 */
	

}
