package ar.edu.uns.cs.ed.tdas.tdagrafo;

import ar.edu.uns.cs.ed.tdas.Position;
import ar.edu.uns.cs.ed.tdas.excepciones.InvalidEdgeException;
import ar.edu.uns.cs.ed.tdas.excepciones.InvalidVertexException;
import ar.edu.uns.cs.ed.tdas.tdalista.ListaDoblementeEnlazada;
import ar.edu.uns.cs.ed.tdas.tdalista.PositionList;

public class GrafoConMatrizAdyacencias<V,E> implements Graph<V,E> {
	@SuppressWarnings("hiding")
	protected class Vertice<V> implements Vertex<V> {
		private V elem;
		private int indice_matriz;
		private Position<Vertex<V>> posicion_nodos;
		
		public V element() { return elem; }
		
		public Vertice(V elem, int indice) {
			this.elem= elem;
			indice_matriz= indice;
		}

		public void setRotulo(V elem) { this.elem= elem; }
		public int getIndice() { return indice_matriz; }
		public void setIndice(int indice) { indice_matriz= indice; }
		public void setPosicionEnNodos(Position<Vertex<V>> p) { posicion_nodos= p; }
		public Position<Vertex<V>> getPosicionEnNodos() { return posicion_nodos; }

		@Override
		public String toString() {
			return "Vertex{"+elem.toString()+"}";
		}
	}
	
	@SuppressWarnings("hiding")
	protected class Arco<V,E> implements Edge<E> {
		private E elem;
		private Vertice<V> v1,v2;
		private Position<Edge<E>> posicion_arco;

		public E element() { return elem; }
		
		public Arco(E elem, Vertice<V> v1, Vertice<V> v2) {
			this.elem= elem;
			this.v1= v1;
			this.v2= v2;
		}

		public void setRotulo(E elem) { this.elem= elem; }
		public void setV1(Vertice<V> v) { v1= v; }
		public void setV2(Vertice<V> v) { v1= v; }
		public Vertice<V> getV1() { return v1; }
		public Vertice<V> getV2() { return v2; }
		public void setPosicionArco(Position<Edge<E>> p) { posicion_arco= p; }
		public Position<Edge<E>> getPosicionArco() { return posicion_arco; }
	}
	
	protected PositionList<Vertex<V>> nodos;
	protected PositionList<Edge<E>> arcos;
	protected Edge<E>[][] matriz_adyacencia;
	protected int SIZE_MATRIZ;
	protected int cantidad_vertices;

	@SuppressWarnings("unchecked")
	public GrafoConMatrizAdyacencias(int tamaño_matriz) {
		nodos= new ListaDoblementeEnlazada<Vertex<V>>();
		arcos= new ListaDoblementeEnlazada<Edge<E>>();
		SIZE_MATRIZ= tamaño_matriz;
		matriz_adyacencia= (Edge<E>[][]) new Arco[tamaño_matriz][tamaño_matriz];
		cantidad_vertices= 0;
		for(int i= 0; i < tamaño_matriz; i++)
			for(int j= 0; j < tamaño_matriz; j++)
				matriz_adyacencia[i][j] = null;
	}
	
	public GrafoConMatrizAdyacencias() {
		this(100);
	}
	
	@Override
	public Iterable<Vertex<V>> vertices() { /// O(1)
		return nodos;
	}

	@Override
	public Iterable<Edge<E>> edges() { /// O(1)
		return arcos;
	}

	protected Vertice<V> checkVertice(Vertex<V> v) { /// O(1)
		try {
			if(v == null) throw new InvalidVertexException("Vértice nulo no admitido");
			if(v.element() == null) throw new InvalidVertexException("Rótulo de vértice nulo no admitido");
			return (Vertice<V>) v;
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
	
	@SuppressWarnings("unchecked")
	protected void resize() { /// O(N_V^2)
		int size_anterior= SIZE_MATRIZ;
		Edge<E>[][] matriz_anterior= matriz_adyacencia;
		SIZE_MATRIZ*= 2;
		
		matriz_adyacencia= (Edge<E>[][]) new Arco[SIZE_MATRIZ][SIZE_MATRIZ];
		for(int i= 0; i < size_anterior; i++)
			for(int j= 0; j < size_anterior; j++)
				matriz_adyacencia[i][j]= matriz_anterior[i][j];

		for(int i= size_anterior; i < SIZE_MATRIZ; i++)
			for(int j= 0; j < SIZE_MATRIZ; j++)
				matriz_adyacencia[i][j]= null;
		for(int i= 0; i < size_anterior; i++)
			for(int j= size_anterior; j < SIZE_MATRIZ; j++)
				matriz_adyacencia[i][j]= null;
	}

	@Override
	public Iterable<Edge<E>> incidentEdges(Vertex<V> v) { /// O(N_V)
		Vertice<V> nodo= checkVertice(v);
		int i= nodo.getIndice();
		PositionList<Edge<E>> lista= new ListaDoblementeEnlazada<Edge<E>>();
		for(int j= 0; j < cantidad_vertices; j++)
			if(matriz_adyacencia[i][j] != null)
				lista.addLast(matriz_adyacencia[i][j]);
		return lista;
	}

	@Override
	public Vertex<V> opposite(Vertex<V> v, Edge<E> e) { /// O(1)
		Arco<V,E> arco= checkArco(e);
		if(arco.getV1() == v) return arco.getV2();
		if(arco.getV2() == v) return arco.getV1();
		throw new InvalidEdgeException("El arco no se corresponde con el vértice estudiado");
	}

	@Override
	public Vertex<V>[] endvertices(Edge<E> e) { /// O(1)
		Arco<V,E> arco= checkArco(e);
		@SuppressWarnings("unchecked")
		Vertex<V>[] vertices= new Vertex[2];
		vertices[0]= arco.getV1();
		vertices[1]= arco.getV2();
		return vertices;
	}

	@Override
	public boolean areAdjacent(Vertex<V> v, Vertex<V> w) { /// O(N_E)
		Vertice<V> vertice_1= checkVertice(v);
		Vertice<V> vertice_2= checkVertice(w);
		int i= vertice_1.getIndice();
		int j= vertice_2.getIndice();
		return matriz_adyacencia[i][j] != null;
	}

	@Override
	public V replace(Vertex<V> v, V x) { /// O(1)
		Vertice<V> vert= checkVertice(v);
		V borrado= vert.element();
		vert.setRotulo(x);
		return borrado;
	}

	@Override
	public Vertex<V> insertVertex(V x) { /// O(N_V^2)
		Vertice<V> v= new Vertice<V>(x, cantidad_vertices++);
		if(cantidad_vertices == SIZE_MATRIZ) resize();
		nodos.addLast(v);
		v.setPosicionEnNodos(nodos.last());
		return v;
	}

	@Override
	public Edge<E> insertEdge(Vertex<V> v, Vertex<V> w, E e) { /// O(1)
		Vertice<V> v1= checkVertice(v);
		Vertice<V> v2= checkVertice(w);
		int fila= v1.getIndice();
		int col= v2.getIndice();
		Arco<V,E> arco= new Arco<V,E>(e, v1, v2);
		matriz_adyacencia[fila][col]= matriz_adyacencia[col][fila]= arco;
		arcos.addLast(arco);
		arco.setPosicionArco(arcos.last());
		return arco;
	}

	protected void eliminarFilaMatriz(int i) { /// O(N_V^2)
		for(int i_cursor= i+1; i_cursor < cantidad_vertices; i_cursor++) {
			for(int j= 0; j < cantidad_vertices; j++)
				matriz_adyacencia[i_cursor-1][j]= matriz_adyacencia[i_cursor][j];
		}
		for(int i_cursor= i+1; i_cursor < cantidad_vertices; i_cursor++) {
			for(int j= 0; j < cantidad_vertices-1; j++) // El último es el que voy a borrar
				matriz_adyacencia[j][i_cursor-1]= matriz_adyacencia[j][i_cursor];
		}
		for(int j= 0; j < cantidad_vertices; j++) {
			matriz_adyacencia[cantidad_vertices-1][j]= null;
			matriz_adyacencia[j][cantidad_vertices-1]= null;
		}
	}
	
	@Override
	public V removeVertex(Vertex<V> v) { /// O(N_V^2)
		Vertice<V> vert= checkVertice(v);
		int i= vert.getIndice();
		
		for(int j= 0; j < cantidad_vertices; j++)
			if(matriz_adyacencia[i][j] != null)
				arcos.remove(checkArco(matriz_adyacencia[i][j]).getPosicionArco());
		eliminarFilaMatriz(i);
		
		V borrado= vert.element();
		vert.setRotulo(null);
		nodos.remove(vert.getPosicionEnNodos());
		vert.setPosicionEnNodos(null);
		return borrado;
	}

	@Override
	public E removeEdge(Edge<E> e) { /// O(1)
		Arco<V,E> arco= checkArco(e);
		int fila= arco.getV1().getIndice();
		int col= arco.getV2().getIndice();
		matriz_adyacencia[fila][col]= null;
		matriz_adyacencia[col][fila]= null;
		arcos.remove(arco.getPosicionArco());
		return e.element();
	}

}
