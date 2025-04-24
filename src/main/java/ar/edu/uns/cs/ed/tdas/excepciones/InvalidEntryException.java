package ar.edu.uns.cs.ed.tdas.excepciones;

public class InvalidEntryException extends RuntimeException {
	private static final long serialVersionUID= 1L;

	public InvalidEntryException (String s) {
		super(s);
	}
}
