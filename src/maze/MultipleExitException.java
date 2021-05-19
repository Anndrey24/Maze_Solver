package maze;

/** Exception for {@link Maze} inputs with multiple exits.
* @author Andrei Hutu
* @version 29th April 2021
*/
public class MultipleExitException extends InvalidMazeException{
	/** 
	*	Constructs a new exception with null as its detail message.
	*/
	public MultipleExitException(){}

	/** 
	*	Constructs a new exception with the specified detail message.
	*	@param mess the detail message
	*/
	public MultipleExitException(String mess){
		super(mess);
	}

	/** 
	*	Constructs a new exception with the specified detail message and cause.
	*	@param mess the detail message
	*	@param cause the cause
	*/
	public MultipleExitException(String mess, Throwable cause){
		super(mess, cause);
	}
}