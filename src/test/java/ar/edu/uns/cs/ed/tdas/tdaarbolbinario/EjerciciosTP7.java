package ar.edu.uns.cs.ed.tdas.tdaarbolbinario;

import ar.edu.uns.cs.ed.tdas.Position;
import ar.edu.uns.cs.ed.tdas.excepciones.EmptyTreeException;

public class EjerciciosTP7 {

	public static void main(String[] args) {
		ej1(); //Prueba de attach
		ej2();
		ej3();
		ej4();
		ej5();
	}
	
	private static void ej1() {
		BinaryTree<Integer> T0= new ArbolBinario<Integer>();
		T0.createRoot(0);
		BinaryTree<Integer> T1= new ArbolBinario<Integer>();
		T1.createRoot(1);
		BinaryTree<Integer> T2= new ArbolBinario<Integer>();
		T2.createRoot(2);
		System.out.println(T0);
		System.out.println(T1);
		System.out.println(T2);
		T0.attach(T0.root(),T1,T2);
		System.out.println(T0);
		System.out.println(T0.size());
	}

	private static void ej2() {
		ArbolBinario<Integer> T= new ArbolBinario<Integer>();
		T.createRoot(1);
		Position<Integer> p2= T.addLeft(T.root(),2);
		Position<Integer> p3= T.addRight(T.root(),3);
		T.addLeft(p2,4);
		T.addRight(p2,5);
		T.addLeft(p3,6);
		
		System.out.println(T.listarHijosXPadre());
	}

	private static void ej3() {
		BinaryTree<Character> T= new ArbolBinario<Character>();
		T.createRoot('/');
		Position<Character> p2= T.addLeft(T.root(),'*');
		Position<Character> p3= T.addRight(T.root(),'+');
		T.addRight(p3,'2');
		Position<Character> p4= T.addLeft(p2,'+');
		T.addLeft(p4,'3');
		T.addRight(p4,'1');
		T.addRight(p2,'3');
		Position<Character> p5= T.addLeft(p3,'-');
		T.addLeft(p5,'9');
		T.addRight(p5,'5');
		
		System.out.println(T);
		System.out.println(operar(T,T.root()));
	}
	private static float operar(BinaryTree<Character> T, Position<Character> pos) {
		return T.isExternal(pos) ? Integer.parseInt(""+pos.element()) : operacion(pos.element(),operar(T,T.left(pos)),operar(T,T.right(pos)));
	}
	private static float operacion(char op, float izq, float der) {
		switch(op) {
			case '+': return izq+der;
			case '-': return izq-der;
			case '*': return izq*der;
			case '/': return izq/der;
			default: return Float.NaN;
		}
	}

	private static void ej4() {
		BinaryTree<Integer> T= new ArbolBinario<Integer>();
		T.createRoot(1);
		Position<Integer> p2= T.addLeft(T.root(),2);
		Position<Integer> p3= T.addRight(T.root(),3);
		T.addLeft(p2,4);
		T.addRight(p2,5);
		T.addLeft(p3,6);

		System.out.println(T);
		completarDerechos(-1,T);
		System.out.println(T);
	}
	private static <E> void completarDerechos(E r, BinaryTree<E> T) { /// O(N)
		if(T.isEmpty()) throw new EmptyTreeException("Árbol vacío");
		for(Position<E> p: T.positions()) // O(N) armarlo y O(N) recorrerlo [2N=N]
			if(T.hasLeft(p) && !T.hasRight(p))
				T.addRight(p,r); // O(1)
	}

	private static void ej5() {
		ArbolBinario<Character> T= new ArbolBinario<Character>();
		T.createRoot('/');
		Position<Character> p2= T.addLeft(T.root(),'*');
		Position<Character> p3= T.addRight(T.root(),'+');
		T.addRight(p3,'2');
		Position<Character> p4= T.addLeft(p2,'+');
		T.addLeft(p4,'3');
		T.addRight(p4,'1');
		T.addRight(p2,'3');
		Position<Character> p5= T.addLeft(p3,'-');
		T.addLeft(p5,'9');
		T.addRight(p5,'5');
		
		System.out.println(T);
		T.eliminarSubarbol(p5);
		System.out.println(T);
	}

}
