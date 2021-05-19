package maze;

import java.io.Serializable;

/** Class for singular nodes which together make up a {@link Maze}. (i.e. tiles of a maze)
* @author Andrei Hutu
* @version 29th April 2021
* @see Maze
*/
public class Tile implements Serializable{
	/**
	*	Type of Tile
	*/
	private Type type;
	/**
	*	Has the Tile been visited or not
	*/
	private boolean visited;

	/** 
	*	All Tile types
	*/
	public enum Type {
		/**
		*	Corridor tile type
		*/
		CORRIDOR,
		/**
		*	Entrance / start tile type
		*/
		ENTRANCE,
		/**
		*	Exit / end tile type
		*/
		EXIT,
		/**
		*	Wall / non navigable tile type
		*/
		WALL;
	}

	/**
	*	Constructs a new Tile of specified type.
	*	@param typeIn type of tile
	*/
	private Tile(Type typeIn){
		type = typeIn;
		visited = false;
	}

	/**
	*	Create Tile object from char representation.
	*	@param charIn char representation of a tile
	*	<ul>
	*		<li>'.' - CORRIDOR</li>
	*		<li>'e' - ENTRANCE</li>
	*		<li>'x' - EXIT</li>
	*		<li>'#' - WALL</li>
	*	</ul>
	*	@return Returns Tile object from char representation.
	*	@throws maze.InvalidMazeException Char not among accepted values
	*/
	protected static Tile fromChar(char charIn) throws InvalidMazeException{
		switch(charIn){
			case '.':
				return new Tile(Type.CORRIDOR);
			case 'e':
				return new Tile(Type.ENTRANCE);
			case 'x':
				return new Tile(Type.EXIT);
			case '#':
				return new Tile(Type.WALL);
		}
		throw new InvalidMazeException();
	}

	/**
	*	Returns Tile type.
	*	@return Returns tile type.
	*/
	public Type getType(){
		return type;
	}

	/**
	*	Returns visited status.
	*	@return Returns visited status.
	*/
	public boolean getVisited(){
		return visited;
	}

	/**
	*	Set visited status.
	*	@param visitedIn visited status
	*/
	public void setVisited(boolean visitedIn){
		visited = visitedIn;
	}

	/**
	*	Returns if Tile is navigable.
	*	@return Returns if tile is navigable.
	*/
	public boolean isNavigable(){
		return (type != Type.WALL);
	}

	/**
	*	Returns string representation of Tile.
	*	@return Returns string representation of Tile or an empty String if Tile is not a standard type.
	*/
	public String toString(){
		switch(type){
			case CORRIDOR:
				return ".";
			case ENTRANCE:
				return "e";
			case EXIT:
				return "x";
			case WALL:
				return "#";
		}
		return "";
	}

}