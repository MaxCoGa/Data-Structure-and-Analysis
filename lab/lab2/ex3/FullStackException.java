
//package lab2.ex3;
/**
 * Runtime exception thrown when one tries to perform operation push
 * on a full stack.
 */
public class FullStackException extends RuntimeException {
	public FullStackException( String err ) {
		super(err);
	}


}