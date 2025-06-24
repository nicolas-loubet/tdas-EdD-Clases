package ar.edu.uns.cs.ed.tdas.tdagrafo;

import java.util.Iterator;

import ar.edu.uns.cs.ed.tdas.tdalista.ListaDoblementeEnlazada;
import ar.edu.uns.cs.ed.tdas.tdalista.PositionList;
import ar.edu.uns.cs.ed.tdas.tdamapeo.Map;
import ar.edu.uns.cs.ed.tdas.tdamapeo.MapConHashAbierto;

public class EjerciciosClaseTeoría {

	public static void main(String[] args) {
		problema1();
		problema2();
		problema3();
	}

	
	private static void problema1() {
		Graph<Character,Integer> gr= new GrafoConListaAdyacentes<Character,Integer>();
		Vertex<Character> a= gr.insertVertex('a');
		Vertex<Character> b= gr.insertVertex('b');
		Vertex<Character> c= gr.insertVertex('c');
		Vertex<Character> d= gr.insertVertex('d');
		Vertex<Character> e= gr.insertVertex('e');
		gr.insertVertex('f');
		Vertex<Character> g= gr.insertVertex('g');
		Vertex<Character> h= gr.insertVertex('h');
		Vertex<Character> i= gr.insertVertex('i');
		Vertex<Character> j= gr.insertVertex('j');
		gr.insertEdge(a,b,1);
		gr.insertEdge(b,c,2);
		gr.insertEdge(a,c,3);
		gr.insertEdge(e,d,4);
		gr.insertEdge(g,j,5);
		gr.insertEdge(h,j,6);
		gr.insertEdge(i,j,7);
		System.out.println(partesConexasShell(gr));
	}
	private static <V,E> Map<Integer,PositionList<Vertex<V>>> partesConexasShell(Graph<V,E> g) {
		Map<Vertex<V>,Boolean> visitado= new MapConHashAbierto<Vertex<V>,Boolean>();
		for(Vertex<V> v: g.vertices())
			visitado.put(v,false);
		int parte= 0;
		Map<Integer,PositionList<Vertex<V>>> resultado= new MapConHashAbierto<Integer,PositionList<Vertex<V>>>();
		for(Vertex<V> v: g.vertices()) {
			if(visitado.get(v)) continue;
			parte++;
			PositionList<Vertex<V>> lista= new ListaDoblementeEnlazada<Vertex<V>>();
			partesConexasDFS(g,v,visitado,lista);
			resultado.put(parte,lista);
		}
		return resultado;
	}
	private static <V,E> void partesConexasDFS(Graph<V,E> g, Vertex<V> v, Map<Vertex<V>,Boolean> visitado, PositionList<Vertex<V>> lista) {
		lista.addLast(v);
		visitado.put(v,true);
		for(Edge<E> e: g.incidentEdges(v)) {
			Vertex<V> u= g.opposite(v,e);
			if(!visitado.get(u))
				partesConexasDFS(g,u,visitado,lista);
		}
	}
	

	private static void problema2() {
		Graph<Character,Integer> gr= new GrafoConListaAdyacentes<Character,Integer>();
		Vertex<Character> a= gr.insertVertex('a');
		Vertex<Character> b= gr.insertVertex('b');
		Vertex<Character> c= gr.insertVertex('c');
		Vertex<Character> d= gr.insertVertex('d');
		Vertex<Character> e= gr.insertVertex('e');
		/*Vertex<Character> f= */gr.insertVertex('f');
		Vertex<Character> g= gr.insertVertex('g');
		Vertex<Character> h= gr.insertVertex('h');
		Vertex<Character> i= gr.insertVertex('i');
		Vertex<Character> j= gr.insertVertex('j');
		Vertex<Character> k= gr.insertVertex('k');
		Vertex<Character> l= gr.insertVertex('l');
		Vertex<Character> m= gr.insertVertex('m');
		Vertex<Character> n= gr.insertVertex('n');
		Vertex<Character> o= gr.insertVertex('o');
		gr.insertEdge(a,b,1);
		gr.insertEdge(b,c,2);
		gr.insertEdge(a,c,3);
		gr.insertEdge(e,d,4);
		gr.insertEdge(g,j,5);
		gr.insertEdge(h,j,6);
		gr.insertEdge(i,j,7);
		gr.insertEdge(j,k,8);
		gr.insertEdge(k,l,9);
		gr.insertEdge(l,m,10);
		gr.insertEdge(k,o,11);
		gr.insertEdge(m,n,12);
		System.out.println(buscarCaminoShell(gr,h,m));
	}
	private static <V,E> PositionList<Vertex<V>> buscarCaminoShell(Graph<V,E> g, Vertex<V> origen, Vertex<V> destino) {
		Map<Vertex<V>,Boolean> visitado= new MapConHashAbierto<Vertex<V>,Boolean>();
		for(Vertex<V> v: g.vertices())
			visitado.put(v,false);
		PositionList<Vertex<V>> resultado= new ListaDoblementeEnlazada<Vertex<V>>();
		if(buscarCaminoDFS(g,origen,destino,visitado,resultado))
			return resultado;
		else return null;
	}
	private static <V,E> boolean buscarCaminoDFS(Graph<V,E> g, Vertex<V> origen, Vertex<V> destino, Map<Vertex<V>,Boolean> visitado, PositionList<Vertex<V>> lista) {
		visitado.put(origen,true);
		lista.addLast(origen);
		if(origen == destino) return true;
		
		boolean encontre= false;
		Iterator<Edge<E>> it= g.incidentEdges(origen).iterator();
		while(it.hasNext() && !encontre) {
			Vertex<V> w= g.opposite(origen,it.next());
			if(!visitado.get(w))
				encontre= buscarCaminoDFS(g,w,destino,visitado,lista);
		}
		if(!encontre) lista.remove(lista.last());
		return encontre;
	}
	
	
	private static void problema3() {
		GraphD<Character,Float> gr= new GrafoDirigidoConListaAdyacentes<Character,Float>();
		Vertex<Character> a= gr.insertVertex('a');
		Vertex<Character> b= gr.insertVertex('b');
		Vertex<Character> c= gr.insertVertex('c');
		Vertex<Character> d= gr.insertVertex('d');
		Vertex<Character> e= gr.insertVertex('e');
		/*Vertex<Character> f= */gr.insertVertex('f');
		Vertex<Character> g= gr.insertVertex('g');
		Vertex<Character> h= gr.insertVertex('h');
		Vertex<Character> i= gr.insertVertex('i');
		Vertex<Character> j= gr.insertVertex('j');
		Vertex<Character> k= gr.insertVertex('k');
		Vertex<Character> l= gr.insertVertex('l');
		Vertex<Character> m= gr.insertVertex('m');
		Vertex<Character> n= gr.insertVertex('n');
		Vertex<Character> o= gr.insertVertex('o');
		gr.insertEdge(a,b,10.0f);
		gr.insertEdge(b,c,10.0f);
		gr.insertEdge(a,c,10.0f);
		gr.insertEdge(e,d,10.0f);
		gr.insertEdge(g,j,10.0f);
		gr.insertEdge(h,j,10.0f);
		gr.insertEdge(i,j,10.0f);
		gr.insertEdge(j,k,10.0f);
		gr.insertEdge(k,l,10.0f);
		gr.insertEdge(l,m,10.0f);
		gr.insertEdge(k,o,10.0f);
		gr.insertEdge(m,n,10.0f);
		System.out.println(hallarCaminoMinimoShell(gr,h,n));
	}
	public static class Camino<V> {
		protected PositionList<Vertex<V>> camino;
		protected float costo;
		
		public Camino() {
			camino= new ListaDoblementeEnlazada<Vertex<V>>();
			costo= 0.0f;
		}
		
		public PositionList<Vertex<V>> getCamino() { return camino; }
		public float getCosto() { return costo; }
		public void setCamino(PositionList<Vertex<V>> c) { camino= c; }
		public void setCosto(float c) { costo= c; }
		
		@Override
		public String toString() { return "Costo="+Float.toString(costo)+"   :   "+camino; }
		
		public PositionList<Vertex<V>> clonarLista() {
			PositionList<Vertex<V>> nuevo= new ListaDoblementeEnlazada<Vertex<V>>();
			for(Vertex<V> v: camino)
				nuevo.addLast(v);
			return nuevo;
		}
	}
	private static <V> Camino<V> hallarCaminoMinimoShell(GraphD<V,Float> g, Vertex<V> origen, Vertex<V> destino) {
		Camino<V> camino_actual= new Camino<V>();
		Camino<V> camino_minimo= new Camino<V>();
		camino_minimo.setCosto(Float.MAX_VALUE);
		
		Map<Vertex<V>,Boolean> visitado= new MapConHashAbierto<Vertex<V>,Boolean>();
		for(Vertex<V> v: g.vertices())
			visitado.put(v,false);
		
		hallarCaminoMinimo(g,origen,destino,visitado,camino_actual,camino_minimo);
		
		return camino_minimo;
	}
	private static <V> void hallarCaminoMinimo(GraphD<V,Float> g, Vertex<V> origen, Vertex<V> destino, Map<Vertex<V>,Boolean> visitado, Camino<V> camino_actual, Camino<V> camino_minimo) {
		camino_actual.getCamino().addLast(origen);
		visitado.put(origen,true);
		if(origen == destino) {
			if(camino_actual.getCosto() < camino_minimo.getCosto()) {
				camino_minimo.setCamino(camino_actual.clonarLista());
				camino_minimo.setCosto(camino_actual.getCosto());
			}
		} else {
			for(Edge<Float> e: g.succesorEdges(origen)) {
				Vertex<V> w= g.opposite(origen,e);
				if(visitado.get(w)) continue;
				camino_actual.setCosto(camino_actual.getCosto() + e.element());
				hallarCaminoMinimo(g,w,destino,visitado,camino_actual,camino_minimo);
				camino_actual.setCosto(camino_actual.getCosto() - e.element());
			}
		}
		visitado.put(origen,false);
		camino_actual.getCamino().remove(camino_actual.getCamino().last());
	}

}
