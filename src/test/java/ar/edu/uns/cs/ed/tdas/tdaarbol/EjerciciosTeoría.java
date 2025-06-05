package ar.edu.uns.cs.ed.tdas.tdaarbol;

import ar.edu.uns.cs.ed.tdas.Position;

public class EjerciciosTeoría {

	public static void main(String[] args) {
		problema1();
		problema2();
		problema3();
		problema4();
		problema5();
	}

	private static void problema1() { /// O(N)
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
		
		System.out.println(T);
		System.out.println(T.sizeSubarbol(T.root()));
	}

	private static void problema2() { /// O(N)
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
		
		System.out.println(T);
		System.out.println(T.mapSizeSubarboles());
	}

	private static void problema3() { /// O(N)
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
		
		System.out.println(T);
		System.out.println(T.podarSubarbol(h2));
		System.out.println(T);
	}

	private static void problema4() { /// O(N)
		ArbolConLista<Integer> T= new ArbolConLista<Integer>();
		Position<Integer> h1, h2, h3, h4, h5;
		h1 = h2 = h3 = h4 = h5 = null;
		T.createRoot(3);
		h1 = T.addFirstChild(T.root(), 2);
		h1 = T.addAfter(T.root(), h1, 3);
		h2 = T.addAfter(T.root(), h1, 3);
		h3 = T.addAfter(T.root(), h2, 9);
		T.addFirstChild(h1, 4);
		T.addLastChild(h1, 5);
		h4 = T.addFirstChild(h2, 7);
		T.addFirstChild(h4, 8);
		T.addFirstChild(h3, 10);
		h5 = T.addLastChild(h3, 3);
		T.addFirstChild(h5, 12);
		
		System.out.println(T);
		cambiarRotulos(T,3,100);
		System.out.println(T);
	}
	private static <E> void cambiarRotulos(Tree<E> T, E e, E f) {
		for(Position<E> p: T.positions())
			if(p.element().equals(e))
				T.replace(p,f);
	}

	private static void problema5() { /// O(N)
		ArbolConLista<Integer> T= new ArbolConLista<Integer>();
		Position<Integer> h1, h2, h3, h4, h5;
		h1 = h2 = h3 = h4 = h5 = null;
		T.createRoot(3);
		h1 = T.addFirstChild(T.root(), 2);
		h1 = T.addAfter(T.root(), h1, 3);
		h2 = T.addAfter(T.root(), h1, 3);
		h3 = T.addAfter(T.root(), h2, 9);
		T.addFirstChild(h1, 4);
		T.addLastChild(h1, 5);
		h4 = T.addFirstChild(h2, 7);
		T.addFirstChild(h4, 8);
		T.addFirstChild(h3, 10);
		h5 = T.addLastChild(h3, 3);
		T.addFirstChild(h5, 12);
		
		System.out.println(T);
		insertarMasivo(100, 1, T);
		System.out.println(T);
	}
	public static <E> void insertarMasivo(E e, int x, Tree<E> A) {
		insertarMasivoRecursivo(e, x, A, A.root());
	}
	private static <E> void insertarMasivoRecursivo(E e, int x_i, Tree<E> A, Position<E> pos) {
		if(x_i == 0) {
			A.addFirstChild(pos,e);
			return;
		}
		
		for(Position<E> hijo: A.children(pos)) /// O(grado)
			insertarMasivoRecursivo(e, x_i-1, A, hijo);
	}

}
