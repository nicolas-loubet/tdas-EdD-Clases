package ar.edu.uns.cs.ed.tdas.tdaarbolbinario;

import java.util.Iterator;

import ar.edu.uns.cs.ed.tdas.Position;
import ar.edu.uns.cs.ed.tdas.excepciones.*;
import ar.edu.uns.cs.ed.tdas.tdadiccionario.DiccionarioNoOrdenadoConHashAbierto;
import ar.edu.uns.cs.ed.tdas.tdadiccionario.Dictionary;
import ar.edu.uns.cs.ed.tdas.tdalista.ListaDoblementeEnlazada;
import ar.edu.uns.cs.ed.tdas.tdalista.PositionList;

public class ArbolBinarioNoGT<E> implements BinaryTree<E> {
	@SuppressWarnings("hiding")
	private class NodeLR<E> implements Position<E> {
		private E elem;
		private NodeLR<E> left;
		private NodeLR<E> right;
		private NodeLR<E> parent;
		
		public NodeLR(E e, NodeLR<E> p, NodeLR<E> l, NodeLR<E> r) {
			elem= e;
			parent= p;
			left= l;
			right= r;
		}
		
		public NodeLR(E e, NodeLR<E> p) {
			this(e,p,null,null);
		}
		
		public NodeLR(E e) {
			this(e,null);
		}
		
		@Override
		public E element() { return elem; }
		
		public void setElement(E e) { elem= e; }
		public NodeLR<E> getParent() { return parent; }
		public void setParent(NodeLR<E> p) { parent= p; }
		public NodeLR<E> getLeft() { return left; }
		public NodeLR<E> getRight() { return right; }
		public void setLeft(NodeLR<E> l) { left= l; }
		public void setRight(NodeLR<E> r) { right= r; }
		
		public E delete() {
			E e= elem;
			elem= null;
			parent= null;
			left= null;
			right= null;
			return e;
		}
	}
	
	protected NodeLR<E> root;
	protected int size;
	
	public ArbolBinarioNoGT() { /// O(1)
		root= null;
		size= 0;
	}

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if(isEmpty()) return "Árbol vacío";
        toStringRec(root, 0, sb);
        return sb.toString();
    }
    private void toStringRec(NodeLR<E> node, int level, StringBuilder sb) {
        if(node == null) return;
        for(int i= 0; i < level; i++) sb.append("  ");
        sb.append(node.element()).append("\n");
        toStringRec(node.getLeft(), level+1, sb);
        toStringRec(node.getRight(), level+1, sb);
    }
    
	@Override
	public int size() { /// O(1)
		return size;
	}

	@Override
	public boolean isEmpty() { /// O(1)
		return size() == 0;
	}
	
	private NodeLR<E> checkPosition(Position<E> v) { /// O(1)
		try {
			if(isEmpty()) throw new InvalidPositionException("Arbol vacío, no hay posiciones válidas");
			if(v == null) throw new InvalidPositionException("Posición nula no admitida");
			if(v.element() == null) throw new InvalidPositionException("Elemento nulo no admitido");
			return (NodeLR<E>) v;
		} catch(ClassCastException e) {
			throw new InvalidPositionException("Posición de otro TDA");
		}
	}

	@Override
	public E replace(Position<E> v, E e) { /// O(1)
		NodeLR<E> n= checkPosition(v);
		E borrado= v.element();
		n.setElement(e);
		return borrado;
	}

	@Override
	public Position<E> root() { /// O(1)
		if(isEmpty()) throw new EmptyTreeException("No hay root si el árbol está vacío");
		return root;
	}

	@Override
	public boolean isRoot(Position<E> v) { /// O(1)
		return root == checkPosition(v);
	}

	@Override
	public void createRoot(E e) { /// O(1)
		if(!isEmpty()) throw new InvalidOperationException("El árbol ya tiene un root");
		size= 1;
		root= new NodeLR<E>(e);
	}

	@Override
	public boolean isInternal(Position<E> v) { /// O(1)
		return hasLeft(v) || hasRight(v);
	}

	@Override
	public boolean isExternal(Position<E> v) { /// O(1)
		return !isInternal(v);
	}

	@Override
	public Position<E> parent(Position<E> v) { /// O(1)
		if(isRoot(v)) throw new BoundaryViolationException("No hay padre del root");
		return checkPosition(v).getParent();
	}

	@Override
	public Iterable<Position<E>> children(Position<E> v) { /// O(1)
		PositionList<Position<E>> lista= new ListaDoblementeEnlazada<Position<E>>();
		NodeLR<E> n= checkPosition(v);
		if(n.getLeft() != null) lista.addLast(n.getLeft());
		if(n.getRight() != null) lista.addLast(n.getRight());
		return lista;
	}

	@Override
	public Position<E> addFirstChild(Position<E> p, E e) { /// O(1)
		NodeLR<E> padre= checkPosition(p);
		NodeLR<E> nuevo= new NodeLR<E>(e, padre);
		if(padre.getLeft() == null)
			padre.setLeft(nuevo);
		else if(padre.getRight() == null) {
			padre.setRight(padre.getLeft());
			padre.setLeft(nuevo);
		} else throw new InvalidOperationException("El árbol estaba lleno al llamar FirstChild");
		size++;
		return nuevo;
	}

	@Override
	public Position<E> addLastChild(Position<E> p, E e) { /// O(1)
		NodeLR<E> padre= checkPosition(p);
		NodeLR<E> nuevo= new NodeLR<E>(e, padre);
		if(padre.getRight() == null)
			padre.setRight(nuevo);
		else if(padre.getLeft() == null) {
			padre.setLeft(padre.getRight());
			padre.setRight(nuevo);
		} else throw new InvalidOperationException("El árbol estaba lleno al llamar FirstChild");
		size++;
		return nuevo;
	}

	@Override
	public Position<E> addBefore(Position<E> p, Position<E> rb, E e) { /// O(grado)
		NodeLR<E> padre= checkPosition(p);
		NodeLR<E> nuevo= new NodeLR<E>(e,padre);
		if(padre.getLeft() == null && padre.getRight() == rb) {
			padre.setLeft(nuevo);
			size++;
		} if(padre.getRight() == null && padre.getLeft() == rb) {
			padre.setRight(padre.getLeft());
			padre.setLeft(nuevo);
			size++;
		} else {
			throw new BoundaryViolationException("Sólo puedo hacer addBefore si el hermano es hijo único hasta el momento");
		}
		return nuevo;
	}

	@Override
	public Position<E> addAfter(Position<E> p, Position<E> lb, E e) { /// O(grado)
		NodeLR<E> padre= checkPosition(p);
		NodeLR<E> nuevo= new NodeLR<E>(e,padre);
		if(padre.getLeft() == lb && padre.getRight() == null) {
			padre.setRight(nuevo);
			size++;
		} if(padre.getRight() == lb && padre.getLeft() == null) {
			padre.setLeft(padre.getRight());
			padre.setRight(nuevo);
			size++;
		} else {
			throw new BoundaryViolationException("Sólo puedo hacer addAfter si el hermano es hijo único hasta el momento");
		}
		return nuevo;
	}

	@Override
	public void removeExternalNode(Position<E> p) { /// O(1)
		NodeLR<E> n= checkPosition(p);
		if(!isExternal(p)) throw new InvalidPositionException("La posición no es una hoja");
		if(p == root) {
			root.delete();
			root= null;
		} else {
			NodeLR<E> padre= n.getParent();
			if(padre.getLeft() == n)
				padre.setLeft(null);
			else
				padre.setRight(null);
			n.delete();
		}
		size--;
	}

	@Override
	public void removeInternalNode(Position<E> p) { /// O(1)
		if(!isInternal(p)) throw new InvalidPositionException("El nodo no era interno");
		NodeLR<E> actual= checkPosition(p);
		if(actual == root) { /// O(1)
			if(size == 1) { // Sólo está el root
				actual.delete();
				root= null;
			} else if(root.getLeft() == null && root.getRight() != null) { // El root tiene un único hijo
				NodeLR<E> hijo= root.getRight();
				hijo.setParent(null);
				root= hijo;
				actual.delete();
			} else if(root.getLeft() != null && root.getRight() == null) { // El root tiene un único hijo
				NodeLR<E> hijo= root.getLeft();
				hijo.setParent(null);
				root= hijo;
				actual.delete();
			} else {
				throw new InvalidPositionException("El root sólo se puede borrar si tiene uno o ningún hijo");
			}
		} else {
			NodeLR<E> abuelo= actual.getParent();
			if(actual.getLeft() == null && actual.getRight() != null) {
				NodeLR<E> nieto= actual.getRight();
				nieto.setParent(abuelo);
				if(abuelo.getLeft() == actual)
					abuelo.setLeft(nieto);
				else
					abuelo.setRight(nieto);
				actual.delete();
			} else if(actual.getLeft() != null && actual.getRight() == null) {
				NodeLR<E> nieto= actual.getLeft();
				nieto.setParent(abuelo);
				if(abuelo.getLeft() == actual)
					abuelo.setLeft(nieto);
				else
					abuelo.setRight(nieto);
				actual.delete();
			} else {
				throw new InvalidPositionException("Un nodo interno sólo se puede borrar si tiene uno o ningún hijo");
			}
		}
		size--;
	}

	@Override
	public void removeNode(Position<E> p) { /// O(1)
		if(isExternal(p)) removeExternalNode(p);
		else removeInternalNode(p);
	}

	private void preorder(PositionList<E> lista_nodos, Position<E> p) { /// O(N)
		NodeLR<E> n= checkPosition(p);
		lista_nodos.addLast(p.element());
		if(n.getLeft() != null) preorder(lista_nodos,n.getLeft());
		if(n.getRight() != null) preorder(lista_nodos,n.getRight());
	}
	private void preorderPositions(PositionList<Position<E>> lista_nodos, Position<E> p) { /// O(N)
		NodeLR<E> n= checkPosition(p);
		lista_nodos.addLast(p);
		if(n.getLeft() != null) preorderPositions(lista_nodos,n.getLeft());
		if(n.getRight() != null) preorderPositions(lista_nodos,n.getRight());
	}
	public Iterable<Position<E>> positions_preorder() { /// O(N)
		PositionList<Position<E>> lista= new ListaDoblementeEnlazada<Position<E>>();
		if(isEmpty()) return lista;
		preorderPositions(lista,root);
		return lista;
	}
	public Iterator<E> iterator_preorder() { /// O(N)
		PositionList<E> lista= new ListaDoblementeEnlazada<E>();
		if(isEmpty()) return lista.iterator();
		preorder(lista,root);
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

	@Override
	public boolean hasLeft(Position<E> v) { /// O(1)
		return checkPosition(v).getLeft() != null;
	}

	@Override
	public boolean hasRight(Position<E> v) { /// O(1)
		return checkPosition(v).getRight() != null;
	}

	@Override
	public Position<E> left(Position<E> v) { /// O(1)
		NodeLR<E> n= checkPosition(v);
		if(n.getLeft() == null) throw new BoundaryViolationException("No hay hijo izquierdo");
		return n.getLeft();
	}

	@Override
	public Position<E> right(Position<E> v) { /// O(1)
		NodeLR<E> n= checkPosition(v);
		if(n.getRight() == null) throw new BoundaryViolationException("No hay hijo derecho");
		return n.getRight();
	}

	@Override
	public Position<E> addLeft(Position<E> v, E r) { /// O(1)
		NodeLR<E> padre= checkPosition(v);
		if(padre.getLeft() != null) throw new InvalidOperationException("Ya había un hijo izquierdo");
		NodeLR<E> n= new NodeLR<E>(r,padre);
		padre.setLeft(n);
		size++;
		return n;
	}

	@Override
	public Position<E> addRight(Position<E> v, E r) { /// O(1)
		NodeLR<E> padre= checkPosition(v);
		if(padre.getRight() != null) throw new InvalidOperationException("Ya había un hijo de ultra derecha");
		NodeLR<E> n= new NodeLR<E>(r,padre);
		padre.setRight(n);
		size++;
		return n;
	}

	@Override
	public void attach(Position<E> r, BinaryTree<E> T1, BinaryTree<E> T2) {
		NodeLR<E> padre= checkPosition(r);
		if(isInternal(padre)) throw new InvalidPositionException("El nodo tiene un hijo");
		if(!T1.isEmpty()) {
			NodeLR<E> hijo_izq= checkPosition(T1.root());
			padre.setLeft(hijo_izq);
			hijo_izq.setParent(padre);
			size+= T1.size();
		}
		if(!T2.isEmpty()) {
			NodeLR<E> hijo_der= checkPosition(T2.root());
			padre.setRight(hijo_der);
			hijo_der.setParent(padre);
			size+= T2.size();
		}
	}
	
	
	// Ejercicios TP 7
	
	public Dictionary<E,E> listarHijosXPadre() {
		Dictionary<E,E> d= new DiccionarioNoOrdenadoConHashAbierto<E,E>();
		if(isEmpty()) return d;
		agregarADiccionarioRecursivamente(d,root);
		return d;
	}
	private void agregarADiccionarioRecursivamente(Dictionary<E,E> d, NodeLR<E> pos) {
		if(pos.getLeft() == null && pos.getRight() == null) return;
		if(pos.getLeft() != null) d.insert(pos.element(), pos.getLeft().element());
		if(pos.getRight() != null) d.insert(pos.element(), pos.getRight().element());
		if(pos.getLeft() != null) agregarADiccionarioRecursivamente(d,pos.getLeft());
		if(pos.getRight() != null) agregarADiccionarioRecursivamente(d,pos.getRight());
	}
	
	public void eliminarSubarbol(Position<E> p) {
		NodeLR<E> n= checkPosition(p);
		NodeLR<E> padre= n.getParent();
		if(padre.getLeft() == n) padre.setLeft(null);
		else padre.setRight(null);
		n.setParent(null);
		size-= contarElementos(p);
	}
	private int contarElementos(Position<E> p) {
		NodeLR<E> n= checkPosition(p);
		if(n.getLeft() == null && n.getRight() == null) return 1;
		if(n.getLeft() == null) return contarElementos(n.getRight());
		if(n.getRight() == null) return contarElementos(n.getLeft());
		return contarElementos(n.getLeft())+contarElementos(n.getRight());
	}

}
