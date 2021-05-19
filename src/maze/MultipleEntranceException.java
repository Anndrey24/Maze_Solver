package maze;

/** Exception for {@link Maze} inputs with multiple entrances.
* @author Andrei Hutu
* @version 29th April 2021
*/
public class MultipleEntranceException extends InvalidMazeException{
	/** 
	*	Constructs a new exception with null as its detail message.
	*/
	public MultipleEntranceException(){}

	/** 
	*	Constructs a new exception with the specified detail message.
	*	@param mess the detail message
	*/
	public MultipleEntranceException(String mess){
		super(mess);
	}

	/** 
	*	Constructs a new exception with the specified detail message and cause.
	*	@param mess the detail message
	*	@param cause the cause
	*/
	public MultipleEntranceException(String mess, Throwable cause){
		super(mess, cause);
	}
}