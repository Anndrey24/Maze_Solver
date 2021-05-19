package maze;

import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;

/** Class for maze objects made up of {@link Tile} objects.
* @author Andrei Hutu
* @version 29th April 2021
* @see Tile 
* @see maze.routing.RouteFinder 
*/
public class Maze implements Serializable{
	/**
	*	Maze Entrance Tile
	*/
	private Tile entrance;
	/**
	*	Maze Exit Tile
	*/
	private Tile exit;
	/**
	*	2D List of Tiles which make up Maze object
	*/
	private List<List<Tile>> tiles;

	/** Class for coordinates of {@link Tile} objects in a {@link Maze}.
	*	<p>Starting from 0:</p>
	*	<ul>
	*		<li>x - left to right</li>
	*		<li>y - bottom to top</li>
	*	</ul>
	* @author Andrei Hutu
	* @version 29th April 2021
	*/
	public class Coordinate implements Serializable{
		/**
		*	x coordinate - column number
		*/
		private int x;
		/**
		*	y coordinate - row number
		*/
		private int y;

		/**
		*	Constructs a new Coordinate with specified x and y.
		*	@param xIn x coordinate - column number
		*	@param yIn y coordinate - rows number
		*/
		public Coordinate(int xIn, int yIn){
			x = xIn;
			y = yIn;
		}

		/**
		*	Returns x coordinate / column number.
		* 	@return Returns x coordinate / column number.
		*/
		public int getX(){
			return x;
		}

		/**
		*	Returns y coordinate / row number.
		* 	@return Returns y coordinate / row number.
		*/
		public int getY(){
			return y;
		}

		/**
		*	Returns String representation of Coordinate.
		* 	@return Returns String representation of Coordinate.
		*/
		public String toString(){
			return ("(" + x + ", " + y + ")");
		}
	}

	/**
	*	All moving directions
	*/
	public enum Direction{
		/**
		*	Move up / North
		*/
		NORTH,
		/**
		*	Move down / South
		*/
		SOUTH,
		/**
		*	Move right / East
		*/
		EAST,
		/**
		*	Move left / West
		*/
		WEST;
	}

	/**
	*	Constructs empty Maze.
	*/
	private Maze(){
		tiles = new ArrayList<List<Tile>>();
	}

	/**
	*	Create Maze object from .txt file.
	*	@param path Path to .txt file
	*	@return Returns Maze object from .txt file.
	*	@throws maze.InvalidMazeException File at path cannot represent a valid maze.
	*/
	public static Maze fromTxt(String path) throws InvalidMazeException{
		Maze maze = new Maze();

		try (
            BufferedReader bufferedReader = new BufferedReader(
                new FileReader(path)
            )
        ) {
            String line = bufferedReader.readLine();
            int len = line.length();
            while (line != null) {
            	ArrayList<Tile> row = new ArrayList<Tile>();
            	// Add new rows to start of list
            	maze.tiles.add(0, row);

            	// Ragged maze
                int line_len = line.length();
                if(line_len != len)
                	throw new RaggedMazeException();

                for(int i=0; i<line_len; i++){
                	// Add tile to row
                	Tile tile = Tile.fromChar(line.charAt(i));
                	row.add(tile);

                	if(tile.getType() == Tile.Type.ENTRANCE)
                		maze.setEntrance(tile);
                	else if(tile.getType() == Tile.Type.EXIT)
                		maze.setExit(tile);
                }
                // Get next line
                line = bufferedReader.readLine();
            }
        } catch (FileNotFoundException e) {
             throw new InvalidMazeException();
        } catch (IOException e) {;
             throw new InvalidMazeException();
        }

        // No entrance
        if(maze.entrance == null)
        	throw new NoEntranceException();

        // No exit
        if(maze.exit == null)
        	throw new NoExitException();

        return maze;
	}

	/**
	*	Returns adjacent {@link Tile} in a given direction.
	*	@param tile Starting tile
	*	@param dir Direction to move in
	*	@return Returns specific Tile object or null if it doesn't exist.
	*/
	public Tile getAdjacentTile(Tile tile, Direction dir){
		// Get coordinates of input tile
		Coordinate coordIn = getTileLocation(tile);
		if(coordIn == null)
			return null;
		int x = coordIn.getX();
		int y = coordIn.getY();

		// Update coordinates based on direction
		switch(dir){
			case NORTH:
				y += 1;
				break;
			case SOUTH:
				y -= 1;
				break;
			case EAST:
				x += 1;
				break;
			case WEST:
				x -= 1;
				break;
		}

		return getTileAtLocation(new Coordinate(x, y));
	}

	/**
	*	Returns entrance {@link Tile}.
	*	@return Returns entrance Tile.
	*/
	public Tile getEntrance(){
		return entrance;
	}

	/**
	*	Returns exit {@link Tile}.
	*	@return Returns exit Tile.
	*/
	public Tile getExit(){
		return exit;
	}

	/**
	*	Returns {@link Tile} at a given {@link Coordinate}.
	*	@param coord Coordinate of Tile
	*	@return Returns specific Tile object or null if it doesn't exist.
	*/
	public Tile getTileAtLocation(Coordinate coord){
		int x = coord.getX();
		int y = coord.getY();
		try{
			return tiles.get(y).get(x);
		}catch(IndexOutOfBoundsException e){
			return null;
		}
	}

	/**
	*	Returns {@link Coordinate} of a given {@link Tile}.
	*	@param tile Tile object
	*	@return Returns Coordinate of Tile object or null if it is not in the maze.
	*/
	public Coordinate getTileLocation(Tile tile){
		int x = -1;
		int y = -1;

		// Search row by row
		for(int i=0; i<tiles.size(); i++){
			List<Tile> row = tiles.get(i);
			x = row.indexOf(tile);
			// Default indexOf() == -1
			if(x != -1){
				y = i;
				break;
			}
		}

		// Tile not found
		if(x == -1)
			return null;

		// Tile found
		return new Coordinate(x, y);
	}

	/**
	*	Returns 2D List of {@link Tile} objects that make up Maze.
	*	@return Returns 2D List of Tile objects that make up Maze.
	*/
	public List<List<Tile>> getTiles(){
		return tiles;
	}

	/**
	*	Returns if {@link Tile} object is in Maze.
	*	@param tile Tile object
	*	@return Returns if Tile object is in maze.
	*/
	private boolean inMaze(Tile tile){
		// Search row by row
		for(int i=0; i<tiles.size(); i++){
			List<Tile> row = tiles.get(i);
			if(row.contains(tile)){
				return true;
			}
		}
		return false;
	}

	/**
	*	Set entrance {@link Tile}.
	*	@param tile Tile object
	*	@throws maze.InvalidMazeException Tile is not in Maze or Maze already has an entrance.
	*/
	private void setEntrance(Tile tile) throws InvalidMazeException{
		// Check tile in maze
		if(!inMaze(tile))
			throw new IllegalArgumentException();

		// Check no entrance yet
		if(entrance != null)
			throw new MultipleEntranceException();

		entrance = tile;
	}

	/**
	*	Set exit {@link Tile}.
	*	@param tile Tile object
	*	@throws maze.InvalidMazeException Tile is not in Maze or Maze already has an exit.
	*/
	private void setExit(Tile tile) throws InvalidMazeException{
		// Check tile in maze
		if(!inMaze(tile))
			throw new IllegalArgumentException();

		// Check no exit yet
		if(exit != null)
			throw new MultipleExitException();

		exit = tile;
	}

	/**
	*	Returns string representation of Maze.
	*	@return Returns string representation of Maze.
	*/
	public String toString(){
		String output = "";
		int nr_rows = tiles.size();

		// Print reverse order of rows
		for(int i = nr_rows - 1; i>=0; i--){
			// Print row number
			output+= i +"  ";
			List<Tile> row = tiles.get(i);
			int nr_col = row.size();
			// Print row
			for(int j = 0; j < nr_col; j++)
				output+=row.get(j).toString() + " ";
			output+='\n';
			// Print col numbers
			if(i == 0){
				output+="\n   ";
				for(int j = 0; j < nr_col; j++)
					output += j + " "; 
			}
		}
		return output;
	}
}