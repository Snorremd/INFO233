/**
 * 
 */
package Exceptions;

/**
 * @author snorre
 *
 */
public class CourseFullException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6564936638979240017L;

	/**
	 * 
	 */
	public CourseFullException() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 */
	public CourseFullException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param cause
	 */
	public CourseFullException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 */
	public CourseFullException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public CourseFullException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

}
