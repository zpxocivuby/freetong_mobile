package itaf.mobile.core.exception;

/**
 * 系统异常，记录错误信息以便查看，用户看不到此类异常信息
 * 
 * @author
 * 
 */
public class AppException extends RuntimeException {

	private static final long serialVersionUID = -5587102707137969769L;

	private int statusCode = -1;

	public AppException(String msg) {
		super(msg);
	}

	public AppException(Exception cause) {
		super(cause);
	}

	public AppException(String msg, int statusCode) {
		super(msg);
		this.statusCode = statusCode;

	}

	public AppException(String msg, Exception cause) {
		super(msg, cause);
	}

	public AppException(String msg, Exception cause, int statusCode) {
		super(msg, cause);
		this.statusCode = statusCode;

	}

	public int getStatusCode() {
		return this.statusCode;
	}
}
