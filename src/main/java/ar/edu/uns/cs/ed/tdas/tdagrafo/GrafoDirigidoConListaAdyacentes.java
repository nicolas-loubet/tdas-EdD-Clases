package ar.edu.uns.cs.ed.tdas.tdagrafo;

import ar.edu.uns.cs.ed.tdas.Position;
import ar.edu.uns.cs.ed.tdas.excepciones.InvalidEdgeException;
import ar.edu.uns.cs.ed.tdas.excepciones.InvalidVertexException;
import ar.edu.uns.cs.ed.tdas.tdalista.ListaDoblementeEnlazada;
import ar.edu.uns.cs.ed.tdas.tdalista.PositionList;

public class GrafoDirigidoConListaAdyacentes<V,E> implements GraphD<V,E> {
	@SuppressWarnings("hiding")
	protected class Vertice<V,E> implements Vertex<V> {
		private V elem;
		private PositionList<Arco<V,E>> incidentes;
		private PositionList<Arco<V,E>> emergentes;
		private Position<Vertice<V,E>> posicion_nodos;
		
		public V element() { return elem; }
		
		public Vertice(V elem) {
			this.elem= elem;
			incidentes= new ListaDoblementeEnlazada<Arco<V,E>>();
			emergentes= new ListaDoblementeEnlazada<Arco<V,E>>();
		}

		public void setRotulo(V elem) { this.elem= elem; }
		public PositionList<Arco<V,E>> getIncidentes() { return incidentes; }
		public PositionList<Arco<V,E>> getEmergentes() { return emergentes; }
		public void setIncidentes(PositionList<Arco<V,E>> a) { incidentes= a; }
		public void setEmergentes(PositionList<Arco<V,E>> a) { emergentes= a; }
		public void setPosicionEnNodos(Position<Vertice<V,E>> p) { posicion_nodos= p; }
		public Position<Vertice<V,E>> getPosicionEnNodos() { return posicion_nodos; }

		@Override
		public String toString() {
			return "Vertex{"+elem.toString()+"}";
		}
	}
	
	@SuppressWarnings("hiding")
	protected class Arco<V,E> implements Edge<E> {
		private E elem;
		private Vertice<V,E> cola,punta;
		private Position<Arco<V,E>> posicion_arco;
		private Position<Arco<V,E>> posicion_cola, posicion_punta;

		public E element() { return elem; }
		
		public Arco(E elem, Vertice<V,E> cola, Vertice<V,E> punta) {
			this.elem= elem;
			this.cola= cola;
			this.punta= punta;
		}

		public void setRotulo(E elem) { this.elem= elem; }
		public void setCola(Vertice<V,E> v) { cola= v; }
		public void setPunta(Vertice<V,E> v) { punta= v; }
		public Vertice<V,E> getCola() { return cola; }
		public Vertice<V,E> getPunta() { return punta; }
		public void setPosicionArco(Position<Arco<V,E>> p) { posicion_arco= p; }
		public Position<Arco<V,E>> getPosicionArco() { return posicion_arco; }
		public void setPosicionCola(Position<Arco<V,E>> p) { posicion_cola= p; }
		public void setPosicionPunta(Position<Arco<V,E>> p) { posicion_punta= p; }
		public Position<Arco<V,E>> getPosicionCola() { return posicion_cola; }
		public Position<Arco<V,E>> getPosicionPunta() { return posicion_punta; }
	}
	
	protected PositionList<Vertice<V,E>> nodos;
	protected PositionList<Arco<V,E>> arcos;
	
	public GrafoDirigidoConListaAdyacentes() {
		nodos= new ListaDoblementeEnlazada<Vertice<V,E>>();
		arcos= new ListaDoblementeEnlazada<Arco<V,E>>();
	}
	
	@Override
	public Iterable<Vertex<V>> vertices() { /// O(N_V)
		PositionList<Vertex<V>> lista= new ListaDoblementeEnlazada<Vertex<V>>();
		for(Vertex<V> v: nodos)
			lista.addLast(v);
		return lista;
	}

	@Override
	public Iterable<Edge<E>> edges() { /// O(N_E)
		PositionList<Edge<E>> lista= new ListaDoblementeEnlazada<Edge<E>>();
		for(Arco<V,E> v: arcos)
			lista.addLast((Edge<E>) v);
		return lista;
	}

	@SuppressWarnings("unchecked")
	protected Vertice<V,E> checkVertice(Vertex<V> v) { /// O(1)
		try {
			if(v == null) throw new InvalidVertexException("Vértice nulo no admitido");
			if(v.element() == null) throw new InvalidVertexException("Rótulo de vértice nulo no admitido");
			return (Vertice<V,E>) v;
		} catch(ClassCastException e) {
			throw new InvalidVertexException("El vértice no pertenecía al tipo de grafo");
		}
	}
	@SuppressWarnings("unchecked")
	protected Arco<V,E> checkArco(Edge<E> e) { /// O(1)
		try {
			if(e == null) throw new InvalidEdgeException("Arco nulo no admitido");
			if(e.element() == null) throw new InvalidEdgeException("Rótulo de arco nulo no admitido");
			return (Arco<V,E>) e;
		} catch(ClassCastException ex) {
			throw new InvalidEdgeException("El arco no pertenecía al tipo de grafo");
		}
	}

	@Override
	public Iterable<Edge<E>> incidentEdges(Vertex<V> v) { /// O(deg(V))
		PositionList<Edge<E>> lista= new ListaDoblementeEnlazada<Edge<E>>();
		Vertice<V,E> vert= checkVertice(v);
		for(Edge<E> e: vert.getIncidentes())
			lista.addLast(e);
		return lista;
	}

	@Override
	public Iterable<Edge<E>> succesorEdges(Vertex<V> v) {
		PositionList<Edge<E>> lista= new ListaDoblementeEnlazada<Edge<E>>();
		Vertice<V,E> vert= checkVertice(v);
		for(Edge<E> e: vert.getEmergentes())
			lista.addLast(e);
		return lista;
	}

	@Override
	public Vertex<V> opposite(Vertex<V> v, Edge<E> e) { /// O(1)
		Vertice<V,E> vertice= checkVertice(v);
		Arco<V,E> arco= checkArco(e);
		if(arco.getCola() == vertice) return arco.getPunta();
		if(arco.getPunta() == vertice) return arco.getCola();
		throw new InvalidEdgeException("El arco no se corresponde con el vértice estudiado");
	}

	@Override
	public Vertex<V>[] endvertices(Edge<E> e) { /// O(1)
		Arco<V,E> arco= checkArco(e);
		@SuppressWarnings("unchecked")
		Vertex<V>[] vertices= new Vertex[2];
		vertices[0]= arco.getCola();
		vertices[1]= arco.getPunta();
		return vertices;
	}

	@Override
	public boolean areAdjacent(Vertex<V> v, Vertex<V> w) { /// O(N_E)
		Vertice<V,E> vertice_1= checkVertice(v);
		Vertice<V,E> vertice_2= checkVertice(w);
		for(Arco<V,E> a: arcos) {
			if(a.getCola() == vertice_1 && a.getPunta() == vertice_2) return true;
			if(a.getPunta() == vertice_1 && a.getCola() == vertice_2) return true;
		}
		return false;
	}

	@Override
	public V replace(Vertex<V> v, V x) { /// O(1)
		Vertice<V,E> vert= checkVertice(v);
		V borrado= vert.element();
		vert.setRotulo(x);
		return borrado;
	}

	@Override
	public Vertex<V> insertVertex(V x) { /// O(1)
		Vertice<V,E> v= new Vertice<V,E>(x);
		nodos.addLast(v);
		v.setPosicionEnNodos(nodos.last());
		return v;
	}

	@Override
	public Edge<E> insertEdge(Vertex<V> v, Vertex<V> w, E e) { /// O(1)
		Vertice<V,E> vertice_v= checkVertice(v);
		Vertice<V,E> vertice_w= checkVertice(w);
		Arco<V,E> arco= new Arco<V,E>(e,vertice_v,vertice_w);
		vertice_v.getEmergentes().addLast(arco);
		arco.setPosicionCola(vertice_v.getEmergentes().last());
		vertice_w.getIncidentes().addLast(arco);
		arco.setPosicionPunta(vertice_w.getIncidentes().last());
		arcos.addLast(arco);
		arco.setPosicionArco(arcos.last());
		return arco;
	}

	@Override
	public V removeVertex(Vertex<V> v) { /// O(N_E)
		Vertice<V,E> vert= checkVertice(v);
		for(Arco<V,E> a: vert.getEmergentes()) { /// O(N_E)
			if(a.getCola() == v)
				a.getPunta().getEmergentes().remove(a.getPosicionPunta());
			if(a.getPunta() == v)
				a.getCola().getEmergentes().remove(a.getPosicionCola());
			arcos.remove(a.getPosicionArco());
		}
		vert.setEmergentes(null); // La desengancho y después se la lleva el GC
		
		for(Arco<V,E> a: vert.getIncidentes()) { /// O(N_E)
			if(a.getCola() == v)
				a.getPunta().getIncidentes().remove(a.getPosicionPunta());
			if(a.getPunta() == v)
				a.getCola().getIncidentes().remove(a.getPosicionCola());
			arcos.remove(a.getPosicionArco());
		}
		vert.setIncidentes(null); // La desengancho y después se la lleva el GC
		
		V borrado= vert.element();
		vert.setRotulo(null);
		nodos.remove(vert.getPosicionEnNodos());
		vert.setPosicionEnNodos(null);
		return borrado;
	}

	@Override
	public E removeEdge(Edge<E> e) { /// O(1)
		Arco<V,E> edge= checkArco(e);
		Vertice<V,E> v1= edge.getCola();
		Vertice<V,E> v2= edge.getPunta();
		v1.getIncidentes().remove(edge.getPosicionCola());
		v2.getIncidentes().remove(edge.getPosicionPunta());
		v1.getEmergentes().remove(edge.getPosicionCola());
		v2.getEmergentes().remove(edge.getPosicionPunta());
		Position<Arco<V,E>> p_edge= edge.getPosicionArco();
		return arcos.remove(p_edge).element();
	}

}
