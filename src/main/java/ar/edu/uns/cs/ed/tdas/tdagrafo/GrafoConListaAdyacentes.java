package ar.edu.uns.cs.ed.tdas.tdagrafo;

import ar.edu.uns.cs.ed.tdas.Position;
import ar.edu.uns.cs.ed.tdas.excepciones.InvalidEdgeException;
import ar.edu.uns.cs.ed.tdas.excepciones.InvalidVertexException;
import ar.edu.uns.cs.ed.tdas.tdalista.ListaDoblementeEnlazada;
import ar.edu.uns.cs.ed.tdas.tdalista.PositionList;

public class GrafoConListaAdyacentes<V,E> implements Graph<V,E> {
	@SuppressWarnings("hiding")
	protected class Vertice<V,E> implements Vertex<V> {
		private V elem;
		private PositionList<Arco<V,E>> adyacentes;
		private Position<Vertice<V,E>> posicion_nodos;
		
		public V element() { return elem; }
		
		public Vertice(V elem) {
			this.elem= elem;
			adyacentes= new ListaDoblementeEnlazada<Arco<V,E>>();
		}

		public void setRotulo(V elem) { this.elem= elem; }
		public PositionList<Arco<V,E>> getAdyacentes() { return adyacentes; }
		public void setAdyacentes(PositionList<Arco<V,E>> a) { adyacentes= a; }
		public void setPosicionEnNodos(Position<Vertice<V,E>> p) { posicion_nodos= p; }
		public Position<Vertice<V,E>> getPosicionEnNodos() { return posicion_nodos; }
	}
	
	@SuppressWarnings("hiding")
	protected class Arco<V,E> implements Edge<E> {
		private E elem;
		private Vertice<V,E> v1,v2;
		private Position<Arco<V,E>> posicion_arco;
		private Position<Arco<V,E>> posicion_v1, posicion_v2;

		public E element() { return elem; }
		
		public Arco(E elem, Vertice<V,E> v1, Vertice<V,E> v2) {
			this.elem= elem;
			this.v1= v1;
			this.v2= v2;
		}

		public void setRotulo(E elem) { this.elem= elem; }
		public void setV1(Vertice<V,E> v) { v1= v; }
		public void setV2(Vertice<V,E> v) { v1= v; }
		public Vertice<V,E> getV1() { return v1; }
		public Vertice<V,E> getV2() { return v2; }
		public void setPosicionArco(Position<Arco<V,E>> p) { posicion_arco= p; }
		public Position<Arco<V,E>> getPosicionArco() { return posicion_arco; }
		public void setPosicionV1(Position<Arco<V,E>> p) { posicion_v1= p; }
		public void setPosicionV2(Position<Arco<V,E>> p) { posicion_v2= p; }
		public Position<Arco<V,E>> getPosicionV1() { return posicion_v1; }
		public Position<Arco<V,E>> getPosicionV2() { return posicion_v2; }
	}
	
	protected PositionList<Vertice<V,E>> nodos;
	protected PositionList<Arco<V,E>> arcos;
	
	public GrafoConListaAdyacentes() {
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
		for(Edge<E> e: vert.getAdyacentes())
			lista.addLast(e);
		return lista;
	}

	@Override
	public Vertex<V> opposite(Vertex<V> v, Edge<E> e) { /// O(1)
		Vertice<V,E> vertice= checkVertice(v);
		Arco<V,E> arco= checkArco(e);
		if(arco.getV1() == vertice) return arco.getV2();
		if(arco.getV2() == vertice) return arco.getV1();
		throw new InvalidEdgeException("El arco no se corresponde con el vértice estudiado");
	}

	@Override
	public Vertex<V>[] endvertices(Edge<E> e) {
		Arco<V,E> arco= checkArco(e);
		@SuppressWarnings("unchecked")
		Vertex<V>[] vertices= new Vertex[2];
		vertices[0]= arco.getV1();
		vertices[1]= arco.getV2();
		return vertices;
	}

	@Override
	public boolean areAdjacent(Vertex<V> v, Vertex<V> w) { /// O(N_E)
		Vertice<V,E> vertice_1= checkVertice(v);
		Vertice<V,E> vertice_2= checkVertice(w);
		for(Arco<V,E> a: arcos) {
			if(a.getV1() == vertice_1 && a.getV2() == vertice_2) return true;
			if(a.getV2() == vertice_1 && a.getV1() == vertice_2) return true;
		}
		return false;
	}

	@Override
	public V replace(Vertex<V> v, V x) {
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
	public Edge<E> insertEdge(Vertex<V> v, Vertex<V> w, E e) {
		Vertice<V,E> vertice_v= checkVertice(v);
		Vertice<V,E> vertice_w= checkVertice(w);
		Arco<V,E> arco= new Arco<V,E>(e,vertice_v,vertice_w);
		vertice_v.getAdyacentes().addLast(arco);
		arco.setPosicionV1(vertice_v.getAdyacentes().last());
		vertice_w.getAdyacentes().addLast(arco);
		arco.setPosicionV2(vertice_w.getAdyacentes().last());
		arcos.addLast(arco);
		arco.setPosicionArco(arcos.last());
		return arco;
	}

	@Override
	public V removeVertex(Vertex<V> v) { /// O(N_E)
		Vertice<V,E> vert= checkVertice(v);
		for(Arco<V,E> a: vert.getAdyacentes()) { /// O(N_E)
			if(a.getV1() == v)
				a.getV2().getAdyacentes().remove(a.getPosicionV2());
			if(a.getV2() == v)
				a.getV1().getAdyacentes().remove(a.getPosicionV1());
			arcos.remove(a.getPosicionArco());
		}
		vert.setAdyacentes(null); // La desengancho y después se la lleva el GC
		V borrado= vert.element();
		vert.setRotulo(null);
		nodos.remove(vert.getPosicionEnNodos());
		vert.setPosicionEnNodos(null);
		return borrado;
	}

	@Override
	public E removeEdge(Edge<E> e) { /// O(1)
		Arco<V,E> edge= checkArco(e);
		Vertice<V,E> v1= edge.getV1();
		Vertice<V,E> v2= edge.getV2();
		v1.getAdyacentes().remove(edge.getPosicionV1());
		v2.getAdyacentes().remove(edge.getPosicionV2());
		Position<Arco<V,E>> p_edge= edge.getPosicionArco();
		return arcos.remove(p_edge).element();
	}

}
