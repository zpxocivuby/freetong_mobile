package itaf.mobile.core.exception;

/**
 * 抛给用户的异常，用户能够看到
 * 
 * @author
 * 
 */
public class ClientException extends RuntimeException {

	private static final long serialVersionUID = -9074418485650985276L;

	public ClientException(String msg) {
		super(msg);
	}

	public ClientException(Exception cause) {
		super(cause);
	}

	public ClientException(String msg, Exception cause) {
		super(msg, cause);
	}

}
