package maze.routing;

import java.util.*;
import maze.*;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


/** Class which contains the logic for finding the solution to a {@link maze.Maze} object.
* @author Andrei Hutu
* @version 29th April 2021
* @see maze.Maze
*/
public class RouteFinder implements Serializable{
	/**
	*	Maze to be solved
	*/
	private Maze maze;
	/**
	*	Route of {@link maze.Tile} objects from entrance to exit
	*/
	private Stack<Tile> route;
	/**
	*	Next direction to be taken by each {@link maze.Tile} in {@link #route}
	*/
	private Stack<Integer> directions;
	/**
	*	Finish status of route - Is the maze finished?
	*/
	private boolean finished;
	/**
	*	Start status of route - Has the route finding started?
	*/
	private boolean started;

	/**
	*	Constructs new RouteFinder from specific {@link maze.Maze} object.
	*	@param mazeIn Maze to be solved
	*/
	public RouteFinder(Maze mazeIn){
		maze = mazeIn;
		route = new Stack<Tile>();
		directions = new Stack<Integer>();
		finished = false;
		started = false;
	}

	/**
	*	Returns {@link maze.Maze} object.
	*	@return Returns Maze object.
	*/
	public Maze getMaze(){
		return maze;
	}

	/**
	*	Returns current route.
	*	@return Returns current route.
	*/
	public List<Tile> getRoute(){
		List<Tile> list = new ArrayList<Tile>(route);
		return list;
	}

	/**
	*	Returns finished status of route.
	*	@return Returns finished status of route.
	*/
	public boolean isFinished(){
		return finished;
	}

	/**
	*	Load RouteFinder object from file.
	*	@param path Path to file
	*	@return Returns RouteFinder object from file.
	*	@throws java.io.FileNotFoundException if the file does not exist, is a directory rather than a regular file, or for some other reason cannot be opened for reading 
	*	@throws java.io.IOException if an I/O error occurs while reading stream header
	*	@throws java.lang.ClassNotFoundException if class of a serialized object cannot be found
	*/
	public static RouteFinder load(String path) throws FileNotFoundException, IOException, ClassNotFoundException{
		ObjectInputStream objectStream = new ObjectInputStream(new FileInputStream(path));
        return (RouteFinder)objectStream.readObject();
	}

	/**
	*	Save RouteFinder object to file.
	*	@param path Path to file
	*	@throws java.io.FileNotFoundException if the file exists but is a directory rather than a regular file, does not exist but cannot be created, or cannot be opened for any other reason
	*	@throws java.io.IOException if an I/O error occurs while writing stream header
	*/
	public void save(String path) throws FileNotFoundException, IOException{
        ObjectOutputStream objectStream = new ObjectOutputStream(new FileOutputStream(path));
        objectStream.writeObject(this);
	}

	/**
	*	Make one step in the {@link maze.Maze}. Either move forward or backward once in the route. Do nothing if maze is finished.
	*	@return Returns if maze is finished.
	*	@throws NoRouteFoundException if the maze does not have a possible solution
	*/
	public boolean step() throws NoRouteFoundException{
		// No solution
		if(route.empty() && started)
			throw new NoRouteFoundException();

		// First step
		if(route.empty() && !started){
			Tile entrance = maze.getEntrance();
			route.push(entrance);
			directions.push(0);
			entrance.setVisited(true);
			started = true;
			return false;
		}

		// Maze solved
		if(route.peek().equals(maze.getExit()))
			return true;

		// Get current tile and direction
		Tile curr_tile = route.peek();
		int curr_dir = directions.peek();
		Tile next_tile = null;

		// Cycle through every direction exactly once		
		for(int dir = curr_dir; dir<4; dir++){
			switch(dir){
				case 0:
					next_tile=maze.getAdjacentTile(curr_tile, Maze.Direction.EAST);
					break;
				case 1:
					next_tile=maze.getAdjacentTile(curr_tile, Maze.Direction.SOUTH);
					break;
				case 2:
					next_tile=maze.getAdjacentTile(curr_tile, Maze.Direction.WEST);
					break;
				case 3:
					next_tile=maze.getAdjacentTile(curr_tile, Maze.Direction.NORTH);
					break;
			}

			// Try forward move if possible
			if(next_tile != null && next_tile.isNavigable() && !next_tile.getVisited()){
				// Remember direction taken from current tile
				directions.pop();
				directions.push(dir + 1);

				// Add next tile to route
				route.push(next_tile);
				directions.push(0);
				next_tile.setVisited(true);

				//Check for solution
				if(route.peek().equals(maze.getExit())){
					finished = true;
					return true;
				}else{
					return false;
				}
			}
		}

		// Go backwards 
		route.pop();
		directions.pop();
		curr_tile.setVisited(false);
		return false;
	}

	/**
	*	Returns string representation of {@link maze.Maze} and Route.
	*	@return Returns string representation of Maze and Route.
	*/
	public String toString(){
		String output = "";
		List<List<Tile>> tiles = maze.getTiles();
		List<Tile> list = getRoute();
		int nr_rows = tiles.size();

		// Print reverse order of rows
		for(int i = nr_rows - 1; i>=0; i--){
			// Print row number
			output+= i +"  ";
			List<Tile> row = tiles.get(i);
			int nr_col = row.size();
			// Print row
			for(int j = 0; j < nr_col; j++){
				String tile_char;
				// Highlight route
				if(list.contains(row.get(j)))
					tile_char = "*";
				else
					tile_char = row.get(j).toString(); 
				output+=tile_char + " ";
			}
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