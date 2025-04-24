package ar.edu.uns.cs.ed.tdas.excepciones;

public class InvalidPositionException extends RuntimeException {
	private static final long serialVersionUID= 1L;

	public InvalidPositionException(String msg) {
		super(msg);
	}
}
