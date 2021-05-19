package maze;

/** Exception for {@link Maze} inputs with no entrances.
* @author Andrei Hutu
* @version 29th April 2021
*/
public class NoEntranceException extends InvalidMazeException{
	/** 
	*	Constructs a new exception with null as its detail message.
	*/
	public NoEntranceException(){}

	/** 
	*	Constructs a new exception with the specified detail message.
	*	@param mess the detail message
	*/
	public NoEntranceException(String mess){
		super(mess);
	}

	/** 
	*	Constructs a new exception with the specified detail message and cause.
	*	@param mess the detail message
	*	@param cause the cause
	*/
	public NoEntranceException(String mess, Throwable cause){
		super(mess, cause);
	}
}