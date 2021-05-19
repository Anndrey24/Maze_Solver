package maze;

/** Exception for {@link Maze} inputs with no exits.
* @author Andrei Hutu
* @version 29th April 2021
*/
public class NoExitException extends InvalidMazeException{
	/** 
	*	Constructs a new exception with null as its detail message.
	*/
	public NoExitException(){}

	/** 
	*	Constructs a new exception with the specified detail message.
	*	@param mess the detail message
	*/
	public NoExitException(String mess){
		super(mess);
	}

	/** 
	*	Constructs a new exception with the specified detail message and cause.
	*	@param mess the detail message
	*	@param cause the cause
	*/
	public NoExitException(String mess, Throwable cause){
		super(mess, cause);
	}
}