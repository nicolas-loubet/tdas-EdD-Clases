package ar.edu.uns.cs.ed.tdas.excepciones;

public class EmptyTreeException extends RuntimeException{
	private static final long serialVersionUID= 1L;

	public EmptyTreeException(String msg) {
		super(msg);
	}
}

