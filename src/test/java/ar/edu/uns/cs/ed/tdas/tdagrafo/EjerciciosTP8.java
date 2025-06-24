package ar.edu.uns.cs.ed.tdas.tdagrafo;

import java.util.Iterator;

import ar.edu.uns.cs.ed.tdas.excepciones.InvalidOperationException;
import ar.edu.uns.cs.ed.tdas.tdacola.ColaSimplementeEnlazada;
import ar.edu.uns.cs.ed.tdas.tdacola.Queue;
import ar.edu.uns.cs.ed.tdas.tdalista.ListaDoblementeEnlazada;
import ar.edu.uns.cs.ed.tdas.tdalista.PositionList;
import ar.edu.uns.cs.ed.tdas.tdamapeo.Map;
import ar.edu.uns.cs.ed.tdas.tdamapeo.MapConHashAbierto;

public class EjerciciosTP8 {
	public static void main(String[] args) {
		ej3();
		ej4();
		ej5();
		ej6();
		ej7();
	}

	private static void ej3() {
		Graph<Character,Integer> g= new GrafoConListaAdyacentes<Character,Integer>();
		Vertex<Character> a= g.insertVertex('A');
		Vertex<Character> b= g.insertVertex('B');
		Vertex<Character> c= g.insertVertex('C');
		Vertex<Character> d= g.insertVertex('D');
		Vertex<Character> e= g.insertVertex('E');
		//g.insertVertex('F');
		g.insertEdge(a,b,1);
		g.insertEdge(a,d,2);
		g.insertEdge(a,e,3);
		g.insertEdge(b,c,4);
		g.insertEdge(b,d,5);
		g.insertEdge(c,d,6);
		g.insertEdge(c,e,7);
		System.out.println(esConexo(g));
	}
	private static <V,E> boolean esConexo(Graph<V,E> g) {
		Map<Vertex<V>,Boolean> mapa= new MapConHashAbierto<Vertex<V>,Boolean>();
		if(!g.vertices().iterator().hasNext()) return true; //Un grafo vacío es conexo
		for(Vertex<V> p: g.vertices())
			mapa.put(p,false);
		
		DFS_recorrer(g, g.vertices().iterator().next(), mapa);
		
		for(Vertex<V> p: g.vertices())
			if(!mapa.get(p))
				return false;
		return true;
	}
	private static <V,E> void DFS_recorrer(Graph<V,E> g, Vertex<V> v, Map<Vertex<V>,Boolean> mapa) {
		mapa.put(v,true);
		System.out.println(v.element());
		Iterable<Edge<E>> adyacentes= g.incidentEdges(v); // si es DGraph: emergentEdges()
		for(Edge<E> e: adyacentes) {
			Vertex<V> w= g.opposite(v,e);
			if(!mapa.get(w))
				DFS_recorrer(g,w,mapa);
		}
	}
	
	private static void ej4() {
		Graph<Character,Integer> g= new GrafoConListaAdyacentes<Character,Integer>();
		Vertex<Character> a= g.insertVertex('A');
		Vertex<Character> b= g.insertVertex('B');
		Vertex<Character> c= g.insertVertex('C');
		Vertex<Character> d= g.insertVertex('D');
		Vertex<Character> e= g.insertVertex('E');
		g.insertEdge(a,b,1);
		g.insertEdge(a,d,2);
		g.insertEdge(a,e,3);
		g.insertEdge(b,c,4);
		g.insertEdge(b,d,5);
		g.insertEdge(c,d,6);
		g.insertEdge(c,e,7);
		System.out.println(menorDistanciaBFS(g,a,c));
	}
	private static <V,E> int menorDistanciaBFS(Graph<V,E> g, Vertex<V> s, Vertex<V> t) {
		Map<Vertex<V>,Boolean> mapa= new MapConHashAbierto<Vertex<V>,Boolean>();
		for(Vertex<V> v: g.vertices())
			mapa.put(v,false);
		Map<Vertex<V>,Vertex<V>> previo= new MapConHashAbierto<Vertex<V>,Vertex<V>>();
		if(!BFS_search(g,s,t,mapa,previo)) throw new InvalidOperationException("No había un camino, no era conexo");
		
		int contador_miembros= 0;
		Vertex<V> anterior= t;
		while(anterior != s) {
			contador_miembros++;
			//System.out.println(anterior+"<--"+previo.get(anterior));
			anterior= previo.get(anterior);
		}
		return contador_miembros;
	}
	private static <V,E> boolean BFS_search(Graph<V,E> g, Vertex<V> s, Vertex<V> t, Map<Vertex<V>,Boolean> mapa, Map<Vertex<V>,Vertex<V>> previo) {
		Queue<Vertex<V>> cola= new ColaSimplementeEnlazada<Vertex<V>>();
		cola.enqueue(s);
		mapa.put(s,true);
		while(!cola.isEmpty()) {
			Vertex<V> x= cola.dequeue();
			if(x == t) return true;
			for(Edge<E> arco: g.incidentEdges(x)) {
				Vertex<V> v= g.opposite(x,arco);
				if(mapa.get(v)) continue;
				cola.enqueue(v);
				mapa.put(v,true);
				previo.put(v,x);
			}
		}
		return false;
	}
	
	private static void ej5() {
		Graph<Character,Integer> g= new GrafoConListaAdyacentes<Character,Integer>();
		Vertex<Character> a= g.insertVertex('A');
		Vertex<Character> b= g.insertVertex('B');
		Vertex<Character> c= g.insertVertex('C');
		Vertex<Character> d= g.insertVertex('D');
		Vertex<Character> e= g.insertVertex('E');
		g.insertEdge(a,b,1);
		g.insertEdge(a,d,2);
		g.insertEdge(a,e,3);
		g.insertEdge(b,c,4);
		g.insertEdge(b,d,5);
		g.insertEdge(c,d,6);
		g.insertEdge(c,e,7);
		System.out.println(existeUnCamino_DFS(g,a,c));
	}
	private static <V,E> PositionList<Vertex<V>> existeUnCamino_DFS(Graph<V,E> g, Vertex<V> s, Vertex<V> t) {
		PositionList<Vertex<V>> camino= new ListaDoblementeEnlazada<Vertex<V>>();
		Map<Vertex<V>,Boolean> mapa= new MapConHashAbierto<Vertex<V>,Boolean>();
		for(Vertex<V> v: g.vertices())
			mapa.put(v,false);
		if(!buscarCamino_DFS(g,s,t,mapa,camino)) return new ListaDoblementeEnlazada<Vertex<V>>();
		return camino;
	}
	private static <V,E> boolean buscarCamino_DFS(Graph<V,E> g, Vertex<V> s, Vertex<V> t, Map<Vertex<V>,Boolean> mapa, PositionList<Vertex<V>> camino) {
		mapa.put(s,true);
		camino.addLast(s);
		if(s == t) return true;
		Iterable<Edge<E>> adyacentes= g.incidentEdges(s); // si es DGraph: emergentEdges()
		for(Edge<E> e: adyacentes) {
			Vertex<V> w= g.opposite(s,e);
			if(!mapa.get(w)) {
				boolean encontre= buscarCamino_DFS(g,w,t,mapa,camino);
				if(encontre) return true;
			}
		}
		camino.remove(camino.last());
		return false;
	}
	
	private static void ej6() {
		Graph<Character,Float> gr= new GrafoConListaAdyacentes<Character,Float>();
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
		System.out.println(hallarCaminoMinimoShell_Dijsktra(gr,h,n));
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
	private static <V> Camino<V> hallarCaminoMinimoShell(Graph<V,Float> g, Vertex<V> origen, Vertex<V> destino) {
		Camino<V> camino_actual= new Camino<V>();
		Camino<V> camino_minimo= new Camino<V>();
		camino_minimo.setCosto(Float.POSITIVE_INFINITY);
		
		Map<Vertex<V>,Boolean> visitado= new MapConHashAbierto<Vertex<V>,Boolean>();
		for(Vertex<V> v: g.vertices())
			visitado.put(v,false);
		
		hallarCaminoMinimo(g,origen,destino,visitado,camino_actual,camino_minimo);
		
		return camino_minimo;
	}
	private static <V> void hallarCaminoMinimo(Graph<V,Float> g, Vertex<V> origen, Vertex<V> destino, Map<Vertex<V>,Boolean> visitado, Camino<V> camino_actual, Camino<V> camino_minimo) {
		camino_actual.getCamino().addLast(origen);
		visitado.put(origen,true);
		if(origen == destino) {
			if(camino_actual.getCosto() < camino_minimo.getCosto()) {
				camino_minimo.setCamino(camino_actual.clonarLista());
				camino_minimo.setCosto(camino_actual.getCosto());
			}
		} else {
			for(Edge<Float> e: g.incidentEdges(origen)) {
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
	private static <V> Camino<V> hallarCaminoMinimoShell_Dijsktra(Graph<V,Float> g, Vertex<V> origen, Vertex<V> destino) {
		GraphD<V,Float> gd= convertirADirigido(g,origen,destino);

		Iterator<Vertex<V>> it= gd.vertices().iterator();
		origen= it.next();
		destino= it.next();
		
		Map<Vertex<V>,Float> dist= new MapConHashAbierto<Vertex<V>,Float>();
		Map<Vertex<V>,Vertex<V>> prev= new MapConHashAbierto<Vertex<V>,Vertex<V>>();
		
		hallarCaminoMinimo_Dijsktra(gd,origen,dist,prev);
		
		return calcularCaminoDijsktraShell(dist,prev,origen,destino);
	}
	private static <V,E> GraphD<V,E> convertirADirigido(Graph<V,E> g, Vertex<V> origen, Vertex<V> destino) {
		GraphD<V,E> gd= new GrafoDirigidoConListaAdyacentes<V,E>();
		Map<Vertex<V>,Vertex<V>> mapeo_vertexG_vertexDG= new MapConHashAbierto<Vertex<V>,Vertex<V>>();

		mapeo_vertexG_vertexDG.put(origen,gd.insertVertex(origen.element()));
		mapeo_vertexG_vertexDG.put(destino,gd.insertVertex(destino.element()));
		
		for(Vertex<V> v: g.vertices())
			if(mapeo_vertexG_vertexDG.get(v) == null)
				mapeo_vertexG_vertexDG.put(v,gd.insertVertex(v.element()));
		for(Edge<E> e: g.edges()) {
			Vertex<V>[] vertices_G= g.endvertices(e);
			Vertex<V> v1= mapeo_vertexG_vertexDG.get(vertices_G[0]);
			Vertex<V> v2= mapeo_vertexG_vertexDG.get(vertices_G[1]);
			gd.insertEdge(v1,v2,e.element());
			gd.insertEdge(v2,v1,e.element());
		}
		return gd;
	}
	private static <V> void hallarCaminoMinimo_Dijsktra(GraphD<V,Float> g, Vertex<V> origen, Map<Vertex<V>,Float> dist, Map<Vertex<V>,Vertex<V>> prev) {
		int N_VERTICES= 0;
		Map<Vertex<V>,Boolean> visitado= new MapConHashAbierto<Vertex<V>,Boolean>();
		for(Vertex<V> v: g.vertices()) {
			dist.put(v,Float.POSITIVE_INFINITY);
			visitado.put(v,false);
			N_VERTICES++;
		}
		dist.put(origen,0f);
		
		for(int i= 0; i < N_VERTICES; i++) {
			Vertex<V> u= buscarMinimo(dist,visitado);
			if(u == null) break;
			visitado.put(u,true);
			for(Edge<Float> e: g.succesorEdges(u)) {
				Vertex<V> v= g.opposite(u,e);
				if(visitado.get(v)) continue;
				float w= e.element();
				if(dist.get(u)+w >= dist.get(v)) continue;
				dist.put(v,dist.get(u)+w);
				prev.put(v,u);
			}
		}
	}
	private static <V> Vertex<V> buscarMinimo(Map<Vertex<V>,Float> dist, Map<Vertex<V>,Boolean> visitado) {
	    Vertex<V> min= null;
	    float min_dist= Float.POSITIVE_INFINITY;
	    for(Vertex<V> v: dist.keys()) {
	        if(!visitado.get(v) && dist.get(v) < min_dist) {
	            min= v;
	            min_dist= dist.get(v);
	        }
	    }
	    return min;
	}
	private static <V> Camino<V> calcularCaminoDijsktraShell(Map<Vertex<V>,Float> dist, Map<Vertex<V>,Vertex<V>> prev, Vertex<V> origen, Vertex<V> destino) {
		Camino<V> camino= new Camino<V>();
		camino.setCosto(dist.get(destino));
		if(camino.getCosto() == Float.POSITIVE_INFINITY) return camino;
		calcularCaminoDijsktra(prev,destino,camino);
		camino.getCamino().addLast(destino);
		return camino;
	}
	private static <V> void calcularCaminoDijsktra(Map<Vertex<V>,Vertex<V>> prev, Vertex<V> destino, Camino<V> camino) {
		Vertex<V> ant= prev.get(destino);
		if(ant == null) return;
		calcularCaminoDijsktra(prev,ant,camino);
		camino.getCamino().addLast(ant);
	}

	private static void ej7() {
		Graph<Character,Integer> gr= new GrafoConListaAdyacentes<Character,Integer>();
		Vertex<Character> a= gr.insertVertex('A');
		Vertex<Character> b= gr.insertVertex('B');
		Vertex<Character> c= gr.insertVertex('C');
		Vertex<Character> d= gr.insertVertex('D');
		Vertex<Character> e= gr.insertVertex('E');
		Vertex<Character> f= gr.insertVertex('F');
		Vertex<Character> g= gr.insertVertex('G');
		Vertex<Character> h= gr.insertVertex('H');
		gr.insertEdge(a,b,1);
		gr.insertEdge(a,c,2);
		gr.insertEdge(a,d,3);
		gr.insertEdge(b,e,4);
		gr.insertEdge(e,f,5);
		gr.insertEdge(e,g,6);
		gr.insertEdge(c,d,7);
		gr.insertEdge(d,h,8);
		System.out.println(buscarNodosADistanciaMenorAK_BFS(gr,a,4));
	}
	private static <V,E> PositionList<Vertex<V>> buscarNodosADistanciaMenorAK_BFS(Graph<V,E> g, Vertex<V> s, int k) {
		PositionList<Vertex<V>> lista= new ListaDoblementeEnlazada<Vertex<V>>();
		Queue<Vertex<V>> cola= new ColaSimplementeEnlazada<Vertex<V>>();
		Map<Vertex<V>,Integer> mapa_distancia= new MapConHashAbierto<Vertex<V>,Integer>();
		for(Vertex<V> v: g.vertices())
			mapa_distancia.put(v,-1);
		
		cola.enqueue(s);
		mapa_distancia.put(s,1);
		while(!cola.isEmpty()) {
			Vertex<V> u= cola.dequeue();
			int orden_u= mapa_distancia.get(u);
			if(orden_u == k+1) break;
			lista.addLast(u);
			Iterable<Edge<E>> adyacentes= g.incidentEdges(u);
			for(Edge<E> e: adyacentes) {
				Vertex<V> w= g.opposite(u,e);
				if(mapa_distancia.get(w) == -1) {
					mapa_distancia.put(w,orden_u+1);
					cola.enqueue(w);
				}
			}
		}
		
		return lista;
	}
	
	/*
	private static <V,E> void DFSShell(Graph<V,E> g) {
		Map<Position<V>,Boolean> mapa= new MapConHashAbierto<Position<V>,Boolean>();
		for(Vertex<V> v: g.vertices())
			mapa.put(v,false);
		for(Vertex<V> v: g.vertices())
			if(!mapa.get(v))
				DFS(g,v,mapa);
	}
	private static <V,E> void DFS(Graph<V,E> g, Vertex<V> v, Map<Position<V>,Boolean> mapa) {
		System.out.println(v.element());
		mapa.put(v,true);
		Iterable<Edge<E>> adyacentes= g.incidentEdges(v); // si es DGraph: emergentEdges()
		for(Edge<E> e: adyacentes) {
			Vertex<V> w= g.opposite(v,e);
			if(!mapa.get(w))
				DFS(g,w,mapa);
		}
	}

	private static <V,E> void BFSShell(Graph<V,E> g) {
		Map<Position<V>,Boolean> mapa= new MapConHashAbierto<Position<V>,Boolean>();
		for(Vertex<V> v: g.vertices())
			mapa.put(v,false);
		for(Vertex<V> v: g.vertices())
			if(!mapa.get(v))
				BFS(g,v,mapa);
	}
	private static <V,E> void BFS(Graph<V,E> g, Vertex<V> v, Map<Position<V>,Boolean> mapa) {
		Queue<Position<V>> cola= new ColaSimplementeEnlazada<Position<V>>();
		cola.enqueue(v);
		mapa.put(v,true);
		
		while(!cola.isEmpty()) {
			Position<V> u= cola.dequeue();
			System.out.println(u.element());
			Iterable<Edge<E>> adyacentes= g.incidentEdges(v); // si es DGraph: emergentEdges()
			for(Edge<E> e: adyacentes) {
				Vertex<V> w= g.opposite(v,e);
				if(!mapa.get(w)) {
					mapa.put(w,true);
					cola.enqueue(w);
				}
			}
		}
	}*/
	

}
