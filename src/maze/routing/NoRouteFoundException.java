package maze.routing;

/** Exception for {@link RouteFinder} objects with no possible solution.
* @author Andrei Hutu
* @version 29th April 2021
*/
public class NoRouteFoundException extends Exception{
	/** 
	*	Constructs a new exception with null as its detail message.
	*/
	public NoRouteFoundException(){}

	/** 
	*	Constructs a new exception with the specified detail message.
	*	@param mess the detail message
	*/
	public NoRouteFoundException(String mess){
		super(mess);
	}

	/** 
	*	Constructs a new exception with the specified detail message and cause.
	*	@param mess the detail message
	*	@param cause the cause
	*/
	public NoRouteFoundException(String mess, Throwable cause){
		super(mess, cause);
	}
}