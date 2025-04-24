package ar.edu.uns.cs.ed.tdas.excepciones;

public class InvalidOperationException extends RuntimeException{
	private static final long serialVersionUID= 1L;

	public InvalidOperationException(String msg) {
		super(msg);
	}
}

