package maze.visualisation;

import maze.routing.*;
import maze.*;
import java.util.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import javafx.scene.paint.Color;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;

/** Class which converts {@link maze.routing.RouteFinder} objects to {@link javafx.scene.layout.GridPane} objects.
* @author Andrei Hutu
* @version 29th April 2021
* @see maze.routing.RouteFinder 
* @see javafx.scene.layout.GridPane 
*/
public class MazeGrid{
	/**
	*	{@link maze.routing.RouteFinder} object to be represented as {@link javafx.scene.layout.GridPane}	
	*/
	private RouteFinder rf;

	/**
	*	Constructs new MazeGrid from specific file path.
	*	@param path file path to Maze .txt input
	*	@throws InvalidMazeException File at path cannot represent a valid maze.
	*/
	public MazeGrid(String path) throws InvalidMazeException{
		rf = new RouteFinder(Maze.fromTxt(path));
	}

	/**
	*	Constructs new MazeGrid from specific {@link maze.Maze} object.
	*	@param maze Maze object
	*/
	public MazeGrid(Maze maze){
		rf = new RouteFinder(maze);
	}

	/**
	*	Constructs new MazeGrid from specific {@link maze.routing.RouteFinder} object.
	*	@param rfIn RouteFinder object
	*/
	public MazeGrid(RouteFinder rfIn){
		rf = rfIn;
	}

	/**
	*	Returns finish status of {@link maze.routing.RouteFinder} object.
	*	@return Returns finish status of RouteFinder object.
	*/
	public boolean isFinished(){
		return rf.isFinished();
	}

	/**
	*	Makes one step in {@link maze.routing.RouteFinder}. Does nothing if maze is finished.
	*	@return Returns finish status of maze route.
	*	@throws maze.routing.NoRouteFoundException if the maze does not have a possible solution
	*/
	public boolean step() throws NoRouteFoundException{
		return rf.step();
	}

	/**
	*	Save RouteFinder object to file.
	*	@param path Path to file
	*	@throws java.io.FileNotFoundException if the file exists but is a directory rather than a regular file, does not exist but cannot be created, or cannot be opened for any other reason
	*	@throws java.io.IOException if an I/O error occurs while writing stream header
	*/
	public void save(String path) throws FileNotFoundException, IOException{
		rf.save(path);
	}

	/**
	*	Returns {@link javafx.scene.layout.GridPane} representation of {@link maze.routing.RouteFinder}.
	*	@return Returns GridPane representation of RouteFinder.
	*/
	public GridPane display(){
		GridPane grid = new GridPane();
		List<List<Tile>> tiles = rf.getMaze().getTiles();
		List<Tile> list = rf.getRoute();
		int nr_rows = tiles.size();
		// Row by row (up -> down)
		for(int i = nr_rows - 1; i>=0; i--){
			List<Tile> row = tiles.get(i);
			int nr_col = row.size();
			// Set tile size
			double tile_width = 725.0 / nr_col;
			double tile_height = 525.0 / nr_rows;
			double tile_size = Math.min(tile_width, tile_height);
			// Col by col (left -> right)
			for(int j = 0; j < nr_col; j++){
				// Add cell to grid
				TileCell tc = new TileCell(row.get(j), tile_size, list.contains(row.get(j)));
				grid.add(tc.getCell(), j, nr_rows - i - 1);
			}
		}
		return grid;
	}

	/** Class which converts {@link maze.Tile} objects to {@link javafx.scene.shape.Rectangle} objects.
	* @author Andrei Hutu
	* @version 29th April 2021
	* @see maze.Tile 
	* @see javafx.scene.shape.Rectangle 
	*/
	public class TileCell{
		/**
		*	{@link maze.Tile} object to be represented as {@link javafx.scene.shape.Rectangle}	
		*/
		private Tile tile;
		/**
		*	Size of square cell in pixels
		*/
		private double size;
		/**
		*	Is tile part of the route?
		*/
		private boolean isRoute;

		/**
		*	Constructs new TileCell from specific Tile.
		*	@param tileIn Tile object to be converted
		*	@param sizeIn size of square cell
		*	@param isRouteIn is Tile part of route
		*/
		public TileCell(Tile tileIn, double sizeIn, boolean isRouteIn){
			tile = tileIn;
			size = sizeIn;
			isRoute = isRouteIn;
		}
		
		/**
		*	Constructs new Rectangle from {@link maze.Tile} attribute.
		*	@return Returns new Rectangle representation of Tile object or null if Tile cannot be represented.
		*/
		public Rectangle getCell(){
			if(isRoute)
				return new Rectangle(size, size, Color.BLUE);
			Tile.Type type = tile.getType();
			switch (type) {
				case ENTRANCE:
					return new Rectangle(size, size, Color.GREEN);
				case CORRIDOR:
					return new Rectangle(size, size, Color.WHITE);
				case WALL:
					return new Rectangle(size, size, Color.BLACK);
				case EXIT:
					return new Rectangle(size, size, Color.RED);
			}
			return null;
		}  
	}
}