package ar.edu.uns.cs.ed.tdas.excepciones;

public class EmptyPriorityQueueException extends RuntimeException {
	private static final long serialVersionUID= 1L;

	public EmptyPriorityQueueException(String msg) {
		super(msg);
	}
}
