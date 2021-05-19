package maze;

/** Exception for invalid {@link Maze} inputs.
* @author Andrei Hutu
* @version 29th April 2021
*/
public class InvalidMazeException extends Exception{
	/** 
	*	Constructs a new exception with null as its detail message.
	*/
	public InvalidMazeException(){}

	/** 
	*	Constructs a new exception with the specified detail message.
	*	@param mess the detail message
	*/
	public InvalidMazeException(String mess){
		super(mess);
	}

	/** 
	*	Constructs a new exception with the specified detail message and cause.
	*	@param mess the detail message
	*	@param cause the cause
	*/
	public InvalidMazeException(String mess, Throwable cause){
		super(mess, cause);
	}
}