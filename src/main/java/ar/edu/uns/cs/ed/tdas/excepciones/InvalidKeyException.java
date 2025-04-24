package ar.edu.uns.cs.ed.tdas.excepciones;

public class InvalidKeyException extends RuntimeException{
	private static final long serialVersionUID= 1L;

	public InvalidKeyException(String s) {
		super(s);
	}
}
