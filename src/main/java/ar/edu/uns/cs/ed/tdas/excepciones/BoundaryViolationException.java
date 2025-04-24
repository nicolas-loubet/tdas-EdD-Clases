package ar.edu.uns.cs.ed.tdas.excepciones;

public class BoundaryViolationException extends RuntimeException {
	private static final long serialVersionUID= 1L;

	public BoundaryViolationException(String msg) {
		super(msg);
	}
}
