/**
 * 
 */
package Exceptions;

/**
 * @author snorre
 *
 */
public class StudentAlreadyRegisteredException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8618471409945796625L;

	/**
	 * 
	 */
	public StudentAlreadyRegisteredException() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 */
	public StudentAlreadyRegisteredException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param cause
	 */
	public StudentAlreadyRegisteredException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 */
	public StudentAlreadyRegisteredException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public StudentAlreadyRegisteredException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

}
