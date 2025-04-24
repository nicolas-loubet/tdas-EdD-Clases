package ar.edu.uns.cs.ed.tdas.excepciones;

public class EmptyListException extends RuntimeException{
	private static final long serialVersionUID= 1L;

	public EmptyListException(String msg) {
		super(msg);
	}
}
