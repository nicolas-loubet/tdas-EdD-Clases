package ar.edu.uns.cs.ed.tdas.tdacolaconprioridad;

import java.util.Random;

public class PruebasClaseTeoría {

	public static void main(String[] args) {
		Integer[] numeros= new Integer[10];
        
        Random random= new Random();
        for(int i= 0; i < numeros.length; i++)
            numeros[i]= random.nextInt(100);
        
        heapSort(numeros,10);
        
        System.out.println("Array ordenado:");
        for(int num: numeros)
            System.out.print(num + " ");
	}
	private static <K extends Comparable<K>> void heapSort(K[] arr, int N) { // O(N log(N))
		PriorityQueue<K,Integer> cola= new ColaConPrioridadConHeap<K,Integer>(new Comparador<K>());
		for(int i= 0; i < N; i++)
			cola.insert(arr[i],0);
		for(int i= 0; i < N; i++)
			arr[i]= cola.removeMin().getKey();
	}

}
