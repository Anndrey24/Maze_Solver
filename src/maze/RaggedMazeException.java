package maze;

/** Exception for ragged {@link Maze} inputs.
* @author Andrei Hutu
* @version 29th April 2021
*/
public class RaggedMazeException extends InvalidMazeException{
	/** 
	*	Constructs a new exception with null as its detail message.
	*/
	public RaggedMazeException(){}

	/** 
	*	Constructs a new exception with the specified detail message.
	*	@param mess the detail message
	*/
	public RaggedMazeException(String mess){
		super(mess);
	}

	/** 
	*	Constructs a new exception with the specified detail message and cause.
	*	@param mess the detail message
	*	@param cause the cause
	*/
	public RaggedMazeException(String mess, Throwable cause){
		super(mess, cause);
	}
}