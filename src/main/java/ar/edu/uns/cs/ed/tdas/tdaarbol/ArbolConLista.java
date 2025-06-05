package ar.edu.uns.cs.ed.tdas.tdaarbol;

import java.util.Iterator;

import ar.edu.uns.cs.ed.tdas.Position;
import ar.edu.uns.cs.ed.tdas.excepciones.BoundaryViolationException;
import ar.edu.uns.cs.ed.tdas.excepciones.EmptyTreeException;
import ar.edu.uns.cs.ed.tdas.excepciones.InvalidOperationException;
import ar.edu.uns.cs.ed.tdas.excepciones.InvalidPositionException;
import ar.edu.uns.cs.ed.tdas.tdacola.ColaSimplementeEnlazada;
import ar.edu.uns.cs.ed.tdas.tdacola.Queue;
import ar.edu.uns.cs.ed.tdas.tdalista.ListaDoblementeEnlazada;
import ar.edu.uns.cs.ed.tdas.tdalista.PositionList;
import ar.edu.uns.cs.ed.tdas.tdamapeo.Map;
import ar.edu.uns.cs.ed.tdas.tdamapeo.MapConHashAbierto;

public class ArbolConLista<E> implements Tree<E> {
	protected int size;
	protected NodoConLista<E> root;
	
	@SuppressWarnings("hiding")
	private class NodoConLista<E> implements Position<E> {
		private E elem;
		NodoConLista<E> padre;
		PositionList<Position<E>> hijos;
		
		public NodoConLista(E e) {
			elem= e;
			hijos= new ListaDoblementeEnlazada<Position<E>>();
			padre= null;
		}
		
		@Override
		public E element() { return elem; }
		
		public void setElement(E e) { elem= e; }
		public PositionList<Position<E>> getChildren() { return hijos; }
		public NodoConLista<E> getParent() { return padre; }
		public void setParent(NodoConLista<E> n) { padre= n; }
		
		public void delete() {
			elem= null;
			padre= null;
			hijos= null;
		}
		
		@Override
		public String toString() {
			return "P{"+elem+"}";
		}
	}
	
	private NodoConLista<E> checkPosition(Position<E> v) { /// O(1)
		try {
			if(isEmpty()) throw new InvalidPositionException("Arbol vacío, no hay posiciones válidas");
			if(v == null) throw new InvalidPositionException("Posición nula no admitida");
			if(v.element() == null) throw new InvalidPositionException("Elemento nulo no admitido");
			return (NodoConLista<E>) v;
		} catch(ClassCastException e) {
			throw new InvalidPositionException("Posición de otro TDA");
		}
	}
	
	
	public ArbolConLista() { /// O(1)
		size= 0;
		root= null;
	}

	@Override
	public int size() { /// O(1)
		return size;
	}

	@Override
	public boolean isEmpty() { /// O(1)
		return size() == 0;
	}

	@Override
	public Position<E> root() { /// O(1)
		if(isEmpty()) throw new EmptyTreeException("No hay root si el árbol está vacío");
		return root;
	}

	@Override
	public boolean isRoot(Position<E> v) { /// O(1)
		if(isEmpty()) throw new InvalidPositionException("Si el árbol está vacío no hay de otra que la posición fue borrada o es de otro TDA");
		return root == checkPosition(v);
	}

	@Override
	public void createRoot(E e) { /// O(1)
		if(!isEmpty()) throw new InvalidOperationException("El árbol ya tiene un root");
		size++;
		root= new NodoConLista<E>(e);
	}

	@Override
	public boolean isInternal(Position<E> v) { /// O(1)
		return !isExternal(v);
	}

	@Override
	public boolean isExternal(Position<E> v) { /// O(1)
		return checkPosition(v).getChildren().isEmpty();
	}

	@Override
	public E replace(Position<E> v, E e) { /// O(1)
		NodoConLista<E> n= checkPosition(v);
		E borrado= n.element();
		n.setElement(e);
		return borrado;
	}

	@Override
	public Position<E> parent(Position<E> v) { /// O(1)
		if(isRoot(v)) throw new BoundaryViolationException("No hay padre del root");
		return checkPosition(v).getParent();
	}

	@Override
	public Iterable<Position<E>> children(Position<E> v) { /// O(1)
		return checkPosition(v).getChildren();
	}

	@Override
	public Position<E> addFirstChild(Position<E> p, E e) { /// O(1)
		NodoConLista<E> padre= checkPosition(p);
		NodoConLista<E> nuevo= new NodoConLista<E>(e);
		nuevo.setParent(padre);
		padre.getChildren().addFirst(nuevo);
		size++;
		return nuevo;
	}

	@Override
	public Position<E> addLastChild(Position<E> p, E e) { /// O(1)
		NodoConLista<E> padre= checkPosition(p);
		NodoConLista<E> nuevo= new NodoConLista<E>(e);
		nuevo.setParent(padre);
		padre.getChildren().addLast(nuevo);
		size++;
		return nuevo;
	}
	
	private Position<Position<E>> buscarPosicionHermano(Position<E> p, Position<E> b) { /// O(grado)
		//No es necesario controlar hermano. Si es null no lo encuentro porque controlé la entrada al árbol
		PositionList<Position<E>> hijos= checkPosition(p).getChildren();
		for(Position<Position<E>> i: hijos.positions())
			if(i.element() == b)
				return i;
		throw new InvalidPositionException("addBefore(), rb no era hijo de p");
	}

	@Override
	public Position<E> addBefore(Position<E> p, Position<E> rb, E e) { /// O(grado)
		NodoConLista<E> padre= checkPosition(p);
		NodoConLista<E> nuevo= new NodoConLista<E>(e);
		nuevo.setParent(padre);
		padre.getChildren().addBefore(buscarPosicionHermano(p,rb),nuevo);
		size++;
		return nuevo;
	}

	@Override
	public Position<E> addAfter(Position<E> p, Position<E> lb, E e) { /// O(grado)
		NodoConLista<E> padre= checkPosition(p);
		NodoConLista<E> nuevo= new NodoConLista<E>(e);
		nuevo.setParent(padre);
		padre.getChildren().addAfter(buscarPosicionHermano(p,lb),nuevo);
		size++;
		return nuevo;
	}

	@Override
	public void removeExternalNode(Position<E> p) { /// O(hermanos)
		NodoConLista<E> n= checkPosition(p);
		if(!isExternal(p)) throw new InvalidPositionException("La posición no es una hoja");
		if(p == root) {
			root.delete();
			root= null;
			size--;
			return;
		}
		PositionList<Position<E>> lista_hijos= n.getParent().getChildren();
		for(Position<Position<E>> i: lista_hijos.positions())
			if(i.element() == p)
				lista_hijos.remove(i);
		n.delete();
		size--;
	}

	@Override
	public void removeInternalNode(Position<E> p) { /// O(grado padre + grado actual)
		if(!isInternal(p)) throw new InvalidPositionException("El nodo no era interno");
		NodoConLista<E> actual= checkPosition(p);
		if(actual == root) { /// O(1)
			if(root.getChildren().size() == 0) { // Sólo está el root
				actual.delete();
				root= null;
			} else if(root.getChildren().size() == 1) { // El root tiene un único hijo
				NodoConLista<E> hijo= checkPosition(actual.getChildren().first().element());
				hijo.setParent(null);
				root= hijo;
				actual.delete();
			} else {
				throw new InvalidPositionException("El root sólo se puede borrar si tiene uno o ningún hijo");
			}
		} else { /// O(grado padre + grado actual*2)
			// Tengo 3 generaciones: padre - actual - hijos
			NodoConLista<E> padre= actual.getParent();
			PositionList<Position<E>> hijos_de_padre= padre.getChildren();
			PositionList<Position<E>> hijos= actual.getChildren();
			// Todos los hijos de actual se unen al padre (uso addBefore para que queden ordenados donde estaba el padre)
			Position<Position<E>> pos_pos_actual= buscarPosicionHermano(padre,actual);
			for(Position<E> i: hijos) {
				NodoConLista<E> h= checkPosition(i);
				h.setParent(padre);
				hijos_de_padre.addBefore(pos_pos_actual,h);
			}
			hijos_de_padre.remove(pos_pos_actual);
			// Destruyo los punteros a los hijos (adoptados por el padre de actual)
			// Si no lo hago, quedan referencias a los hijos por fuera del padre
			for(Position<Position<E>> i: hijos.positions())
				hijos.remove(i);
			// Destruyo el nodo actual, el GC se lo llevará
			actual.delete();
		}
		size--;
	}

	@Override
	public void removeNode(Position<E> p) { /// O(grado padre + grado actual)
		if(isExternal(p)) removeExternalNode(p);
		else removeInternalNode(p);
	}

	private void preorder(ListaDoblementeEnlazada<E> lista_nodos, Position<E> p) { /// O(grado p + SUM grado hijos)
		NodoConLista<E> n= checkPosition(p);
		lista_nodos.addLast(p.element());
		for(Position<E> i: n.getChildren())
			preorder(lista_nodos,i);
	}

	private void preorderPositions(ListaDoblementeEnlazada<Position<E>> lista_nodos, Position<E> p) { /// O(grado p + SUM grado hijos)
		NodoConLista<E> n= checkPosition(p);
		lista_nodos.addLast(p);
		for(Position<E> i: n.getChildren())
			preorderPositions(lista_nodos,i);
	}

	private void postorder(ListaDoblementeEnlazada<E> lista_nodos, Position<E> p) { /// O(grado p + SUM grado hijos)
		NodoConLista<E> n= checkPosition(p);
		for(Position<E> i: n.getChildren())
			postorder(lista_nodos,i);
		lista_nodos.addLast(p.element());
	}

	private void postorderPositions(ListaDoblementeEnlazada<Position<E>> lista_nodos, Position<E> p) { /// O(grado p + SUM grado hijos)
		NodoConLista<E> n= checkPosition(p);
		for(Position<E> i: n.getChildren())
			postorderPositions(lista_nodos,i);
		lista_nodos.addLast(p);
	}

	private void inorder(ListaDoblementeEnlazada<E> lista_nodos, Position<E> p) { /// O(grado p + SUM grado hijos)
		if(isExternal(p)) {
			lista_nodos.addLast(p.element());
			return;
		}
		
		boolean es_el_primero= true;
		for(Position<E> pos_i: checkPosition(p).getChildren()) {
			inorder(lista_nodos,pos_i);
			if(es_el_primero)
				lista_nodos.addLast(p.element());
			es_el_primero= false;
		}
	}

	private void inorderPositions(ListaDoblementeEnlazada<Position<E>> lista_nodos, Position<E> p) { /// O(grado p + SUM grado hijos)
		if(isExternal(p)) {
			lista_nodos.addLast(p);
			return;
		}
		
		boolean es_el_primero= true;
		for(Position<E> pos_i: checkPosition(p).getChildren()) {
			inorderPositions(lista_nodos,pos_i);
			if(es_el_primero)
				lista_nodos.addLast(p);
			es_el_primero= false;
		}
	}

	public Iterable<Position<E>> positions_preorder() { /// O(N)
		ListaDoblementeEnlazada<Position<E>> lista= new ListaDoblementeEnlazada<Position<E>>();
		if(isEmpty()) return lista;
		preorderPositions(lista,root);
		return lista;
	}

	public Iterator<E> iterator_preorder() { /// O(N)
		ListaDoblementeEnlazada<E> lista= new ListaDoblementeEnlazada<E>();
		if(isEmpty()) return lista.iterator();
		preorder(lista,root);
		return lista.iterator();
	}

	public Iterable<Position<E>> positions_postorder() { /// O(N)
		ListaDoblementeEnlazada<Position<E>> lista= new ListaDoblementeEnlazada<Position<E>>();
		if(isEmpty()) return lista;
		postorderPositions(lista,root);
		return lista;
	}

	public Iterator<E> iterator_postorder() { /// O(N)
		ListaDoblementeEnlazada<E> lista= new ListaDoblementeEnlazada<E>();
		if(isEmpty()) return lista.iterator();
		postorder(lista,root);
		return lista.iterator();
	}

	public Iterable<Position<E>> positions_inorder() { /// O(N)
		ListaDoblementeEnlazada<Position<E>> lista= new ListaDoblementeEnlazada<Position<E>>();
		if(isEmpty()) return lista;
		inorderPositions(lista,root);
		return lista;
	}

	public Iterator<E> iterator_inorder() { /// O(N)
		ListaDoblementeEnlazada<E> lista= new ListaDoblementeEnlazada<E>();
		if(isEmpty()) return lista.iterator();
		inorder(lista,root);
		return lista.iterator();
	}

	public Iterable<Position<E>> positions_levelorder(Position<E> level_cut) { /// O(N)
		ListaDoblementeEnlazada<Position<E>> lista= new ListaDoblementeEnlazada<Position<E>>();

		if(isEmpty()) return lista;
		
		Queue<Position<E>> cola= new ColaSimplementeEnlazada<Position<E>>();
		cola.enqueue(root);
		cola.enqueue(level_cut);
		
		while(!cola.isEmpty()) {
			Position<E> p= cola.dequeue();
			lista.addLast(p);
			if(p == level_cut) {
				if(!cola.isEmpty())
					cola.enqueue(level_cut);
				continue;
			}
			for(Position<E> hijo: children(p))
				cola.enqueue(hijo);
		}
		return lista;
	}

	public Iterator<E> iterator_levelorder(E level_cut) { /// O(N)
		ListaDoblementeEnlazada<E> lista= new ListaDoblementeEnlazada<E>();
		
		Position<E> p_level_cut= new NodoConLista<E>(level_cut);
		if(isEmpty()) return lista.iterator();
		
		Queue<Position<E>> cola= new ColaSimplementeEnlazada<Position<E>>();
		cola.enqueue(root);
		cola.enqueue(p_level_cut);
		
		while(!cola.isEmpty()) {
			Position<E> p= cola.dequeue();
			lista.addLast(p.element());
			if(p == p_level_cut) {
				if(!cola.isEmpty())
					cola.enqueue(p_level_cut);
				continue;
			}
			for(Position<E> hijo: children(p))
				cola.enqueue(hijo);
		}
		return lista.iterator();
	}

	public Iterable<Position<E>> positions_levelorder() { /// O(N)
		ListaDoblementeEnlazada<Position<E>> lista= new ListaDoblementeEnlazada<Position<E>>();
		if(isEmpty()) return lista;
		Queue<Position<E>> cola= new ColaSimplementeEnlazada<Position<E>>();
		cola.enqueue(root);
		while(!cola.isEmpty()) {
			Position<E> p= cola.dequeue();
			lista.addLast(p);
			for(Position<E> hijo: children(p))
				cola.enqueue(hijo);
		}
		return lista;
	}

	public Iterator<E> iterator_levelorder() { /// O(N)
		ListaDoblementeEnlazada<E> lista= new ListaDoblementeEnlazada<E>();
		if(isEmpty()) return lista.iterator();
		Queue<Position<E>> cola= new ColaSimplementeEnlazada<Position<E>>();
		cola.enqueue(root);
		while(!cola.isEmpty()) {
			Position<E> p= cola.dequeue();
			lista.addLast(p.element());
			for(Position<E> hijo: children(p))
				cola.enqueue(hijo);
		}
		return lista.iterator();
	}

	@Override
	public Iterable<Position<E>> positions() { /// O(N)
		return positions_preorder();
	}

	@Override
	public Iterator<E> iterator() { /// O(N)
		return iterator_preorder();
	}

	private void toStringRec(NodoConLista<E> node, int level, StringBuilder sb) { /// O(grado + SUM hijos)
	    for(int i = 0; i < level; i++)
	        sb.append("- ");
	    
	    sb.append(node.element().toString()).append("\n");
	    for(Position<E> child: node.getChildren())
	        toStringRec(checkPosition(child), level+1, sb);
	}
	
	@Override
	public String toString() { /// O(N)
	    if(isEmpty()) return "Árbol vacío";
	    
	    StringBuilder sb= new StringBuilder();
	    toStringRec(root, 0, sb);
	    return sb.toString();
	}
	
	// Ejercicios de TP6
	
	public void eliminarUltimoHijo(Position<E> p) { // O(grado padre + grado nodo)
		NodoConLista<E> n= checkPosition(p);
		if(n == root()) throw new InvalidOperationException("La raiz no se considera último hijo");
		PositionList<Position<E>> hermanos= n.getParent().getChildren();
		if(hermanos.last().element() != p) throw new InvalidOperationException("p no es el último hijo");
		removeNode(p);
	}
	
	// Ejercicios clase 05/06/2025
	
	public int sizeSubarbol(Position<E> p) { /// O(N)
		NodoConLista<E> n= checkPosition(p);
		
		if(isExternal(n)) return 1;
		int s= 1;
		for(Position<E> hijo: n.getChildren())
			s+= sizeSubarbol(hijo);
		return s;
	}
	
	public Map<Position<E>,Integer>	mapSizeSubarboles() { /// O(N)
		Map<Position<E>,Integer> mapa= new MapConHashAbierto<Position<E>,Integer>();
		mapearYContar(root,mapa);
		return mapa;
	}
	private int mapearYContar(NodoConLista<E> nodo, Map<Position<E>,Integer> mapa) {
		if(isExternal(nodo)) {
			mapa.put(nodo,1);
			return 1;
		}
		
		int s= 1;
		for(Position<E> hijo: nodo.getChildren()) /// O(N)
			s+= mapearYContar((NodoConLista<E>) hijo,mapa);
		mapa.put(nodo,s); /// O(1)
		return s;
	}
	
	public int podarSubarbol(Position<E> p) { /// O(N)
		NodoConLista<E> n= checkPosition(p);
		if(isExternal(n)) {
			removeExternalNode(p);
			return 1;
		}
		
		int s= 1;
		for(Position<E> hijo: n.getChildren())
			s+= podarSubarbol(hijo);
		removeExternalNode(p);
		return s;
	}
}
