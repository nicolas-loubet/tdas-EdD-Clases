package ar.edu.uns.cs.ed.tdas.tdaarbolbinario;

import java.util.Iterator;

import ar.edu.uns.cs.ed.tdas.Position;
import ar.edu.uns.cs.ed.tdas.excepciones.BoundaryViolationException;
import ar.edu.uns.cs.ed.tdas.excepciones.EmptyTreeException;
import ar.edu.uns.cs.ed.tdas.excepciones.InvalidOperationException;
import ar.edu.uns.cs.ed.tdas.excepciones.InvalidPositionException;
import ar.edu.uns.cs.ed.tdas.tdalista.ListaDoblementeEnlazada;
import ar.edu.uns.cs.ed.tdas.tdalista.PositionList;

public class ArbolBinarioGT<E> implements BinaryGTTree<E> {
	@SuppressWarnings("hiding")
	protected class NodeLR<E> implements Position<E> {
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
	
	public ArbolBinarioGT() { /// O(1)
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
		if(isEmpty()) throw new InvalidPositionException("Si el árbol está vacío no hay de otra que la posición fue borrada o es de otro TDA");
		return root == checkPosition(v);
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
		if(isEmpty()) return lista;
		NodeLR<E> n= checkPosition(v);
		if(n.getLeft() != null) lista.addLast(n.getLeft());
		if(n.getRight() != null) lista.addLast(n.getRight());
		return lista;
	}

	@Override
	public boolean hasLeft(Position<E> v) { /// O(1)
		return !(checkPosition(v).getLeft() == null);
	}

	@Override
	public boolean hasRight(Position<E> v) { /// O(1)
		return !(checkPosition(v).getRight() == null);
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
		if(!(padre.getLeft() == null)) throw new InvalidOperationException("Ya había un hijo izquierdo");
		NodeLR<E> n= new NodeLR<E>(r,padre);
		padre.setLeft(n);
		size++;
		return n;
	}

	@Override
	public Position<E> addRight(Position<E> v, E r) { /// O(1)
		NodeLR<E> padre= checkPosition(v);
		if(!(padre.getRight() == null)) throw new InvalidOperationException("Ya había un hijo de ultra derecha");
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

}
