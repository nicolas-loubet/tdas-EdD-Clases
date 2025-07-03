package ar.edu.uns.cs.ed.tdas.tdacolaconprioridad;

import java.util.Random;

import ar.edu.uns.cs.ed.tdas.Entry;
import ar.edu.uns.cs.ed.tdas.tdadiccionario.DiccionarioNoOrdenadoConHashAbierto;
import ar.edu.uns.cs.ed.tdas.tdadiccionario.Dictionary;

public class TP9 {

	public static void main(String[] args) {
		ej2();
		ej3();
	}
	
	private static void ej2() {
		Integer[] numeros= new Integer[10];
        
        Random random= new Random();
        for(int i= 0; i < numeros.length; i++)
            numeros[i]= random.nextInt(100);
        
        heapSort(numeros,10);
        
        for(int num: numeros)
            System.out.print(num + " ");
        System.out.println("");
	}
	private static <K extends Comparable<K>> void heapSort(K[] arr, int N) { // O(N log(N)) si usa heap
		PriorityQueue<K,Integer> cola= new ColaConPrioridadConHeap<K,Integer>(new Comparador<K>());
		for(int i= 0; i < N; i++) // N*
			cola.insert(arr[i],0); // log(N)
		for(int i= 0; i < N; i++) // Lo mismo que antes, no aumenta el orden
			arr[i]= cola.removeMin().getKey();
	} // Si no tuviera heap, insertar es de orden N, remover también (o marcar borrado = 1).
	//   Por lo tanto, sería ordenamiento O(N^2)

	private static void ej3() {
		Dictionary<Character,Integer> d= new DiccionarioNoOrdenadoConHashAbierto<Character,Integer>();
		d.insert('A',10);
		d.insert('B',15);
		d.insert('C',17);
		d.insert('A',1);
		d.insert('D',8);
		d.insert('E',20);
		d.insert('A',25);
		
		int[] arreglo= valOrdenados(d);
		
		for(int i: arreglo)
			System.out.print(i + " ");
		System.out.println("");
	}
	private static int[] valOrdenados(Dictionary<Character,Integer> d) { /// O(N log(N))
		int cuenta_valores= 0;
		PriorityQueue<Integer,Character> cola= new ColaConPrioridadConHeap<Integer,Character>(new Comparador<Integer>());
		for(Entry<Character,Integer> entrada: d.entries()) {
			cola.insert(entrada.getValue(),entrada.getKey());
			cuenta_valores++;
		}
		int[] arr= new int[cuenta_valores];
		for(int i= 0; i < cuenta_valores; i++) {
			arr[cuenta_valores-i-1]= cola.removeMin().getKey();
		}
		return arr;
	}
}
