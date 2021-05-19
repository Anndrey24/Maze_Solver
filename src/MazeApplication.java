import maze.*;
import maze.routing.*;
import maze.visualisation.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.text.FontWeight;
import javafx.scene.effect.DropShadow;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import java.io.File;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/** Application with GUI for a Maze Solving algorithm.
* @author Andrei Hutu
* @version 29th April 2021
* @see maze.routing.RouteFinder 
* @see maze.visualisation.MazeGrid 
*/
public class MazeApplication extends Application {

	private Button mapButton, loadButton, saveButton, exitButton, stepButton, finishButton;
	private Text title;

	/**
	*	Initializes	JavaFX components to be ready to display.
	*/
	public void init(){
		// Initialize attributes
		title = new Text("Maze Solver");
		mapButton = new Button("Load Map");
		loadButton = new Button("Load Route");
		saveButton = new Button("Save Route");
		exitButton = new Button("Exit");
		stepButton = new Button("Step");
		finishButton = new Button("Finish");

		// Styling
		Font fontTitle = Font.font("Courier New", FontWeight.BOLD, 46);
		Font fontButton = Font.font("Courier New",  24);

		DropShadow dropShadow = new DropShadow();
		dropShadow.setRadius(2.5);
		dropShadow.setOffsetX(1.0);
		dropShadow.setOffsetY(1.0);
		dropShadow.setColor(Color.color(0.4, 0.5, 0.5));

		title.setFont(fontTitle);
		title.setFill(Color.web("ff0000"));
		title.setEffect(dropShadow);

		String buttonProp = "-fx-border-color: #ffffff; -fx-border-width: 2px;-fx-background-color: #414141; -fx-text-fill: #ffffff";
		
		// Buttons config
		mapButton.setMinSize(200, 70);
		mapButton.setFont(fontButton);
		mapButton.setStyle(buttonProp);

		loadButton.setMinSize(200, 70);
		loadButton.setFont(fontButton);
		loadButton.setStyle(buttonProp);
		
		saveButton.setMinSize(200, 70);
		saveButton.setFont(fontButton);
		saveButton.setStyle(buttonProp);
		
		exitButton.setMinSize(200, 70);
		exitButton.setFont(fontButton);
		exitButton.setStyle(buttonProp);

		stepButton.setMinSize(200, 70);
		stepButton.setFont(fontButton);
		stepButton.setStyle(buttonProp);

		finishButton.setMinSize(200, 70);
		finishButton.setFont(fontButton);
		finishButton.setStyle(buttonProp);
	}

	/**
	*	Starts application and displays menu scene.
	*/
	@Override
	public void start(Stage stage){	
		Alert a = new Alert(AlertType.ERROR);

		// Button actions
		mapButton.setOnAction(e -> {
			try{
				Scene new_scene = getMaze(stage);
				changeScene(stage, new_scene, "Maze Solver");
			}catch(MultipleEntranceException err){
				a.setContentText("MultipleEntranceException:\nMaze has multiple entrances");
				a.show();
	    	}catch(MultipleExitException err){
	    		a.setContentText("MultipleExitException:\nMaze has multiple exits");
	    		a.show();
	    	}catch(NoEntranceException err){
	    		a.setContentText("NoEntranceException:\nMaze has no entrance");
	    		a.show();
	    	}catch(NoExitException err){
	    		a.setContentText("NoExitException:\nMaze has no exit");
	    		a.show();
	    	}catch(RaggedMazeException err){
	    		a.setContentText("RaggedMazeException:\nMaze is ragged");
	    		a.show();
	    	}catch(InvalidMazeException err){
	    		a.setContentText("InvalidMazeException occured here");
	    		a.show();
	    	}catch(NullPointerException err){}
		});
		
		loadButton.setOnAction(e -> {
			try{
				Scene new_scene = getRoute(stage);
				changeScene(stage, new_scene, "Maze Solver");
			}catch (FileNotFoundException err) {
            	a.setContentText("Could not read route");
            	a.show();
       		}catch (IOException | ClassNotFoundException err) {
            	a.setContentText("Problem when reading route");
            	a.show();
        	}catch(NullPointerException err){}
		});
		
		exitButton.setOnAction(e -> {stage.hide();});
		

		// Menu Scene creation
		VBox buttonBox = new VBox(20, mapButton, loadButton, exitButton);
		buttonBox.setAlignment(Pos.CENTER);
		VBox root = new VBox(50);
		root.setBackground(Background.EMPTY);
		root.setAlignment(Pos.CENTER);
		root.getChildren().addAll(title,buttonBox);
		Scene start_scene = new Scene(root, 1024, 768, Color.web("252525"));

		// Display Menu Scene
		changeScene(stage, start_scene, "Maze Solver");
	}

	/**
	*	Launches GUI Application.
	*/
	public static void main(String args[]){
		launch(args);
	}

	/**
	*	Changes displayed scene.
	*	@param stage current stage
	*	@param scene scene to be displayed
	*	@param title new title
	*/
	private void changeScene(Stage stage, Scene scene, String title){
		stage.setScene(scene);
		stage.setTitle(title);
		stage.show();
	}

	/**
	*	Gets Maze scene from .txt Maze input file.
	*	@param stage current stage
	*	@return Returns Maze scene.
	*	@throws NullPointerException if file path is left empty (i.e. FileChooser is cancelled)
	*	@throws maze.InvalidMazeException if file at path cannot represent a valid maze
	*/
	private Scene getMaze(Stage stage) throws NullPointerException, InvalidMazeException{
		// Get maze file
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Maze File");
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Text Files", "*.txt"));

		// Create MazeGrid object from file
		File selectedFile = fileChooser.showOpenDialog(stage);
		String path = selectedFile.getPath();
		MazeGrid mg = new MazeGrid(path);

		return getMazeScene(stage, mg);
		
	}

	/**
	*	Gets Maze scene from RouteFinder object input file.
	*	@param stage current stage
	*	@return Returns Maze scene.
	*	@throws NullPointerException if file path is left empty (i.e. FileChooser is cancelled)
	*	@throws java.io.FileNotFoundException if the file does not exist, is a directory rather than a regular file, or for some other reason cannot be opened for reading
	*	@throws java.io.IOException if an I/O error occurs while reading stream header
	*	@throws ClassNotFoundException if class of a serialized object cannot be found
	*/
	private Scene getRoute(Stage stage) throws NullPointerException, FileNotFoundException, IOException, ClassNotFoundException{
		// Get route file
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Route File");
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("All Files", "*.*"));

		// Create MazeGrid object from file
		File selectedFile = fileChooser.showOpenDialog(stage);
		String path = selectedFile.getPath();
		MazeGrid mg = new MazeGrid(RouteFinder.load(path));

		return getMazeScene(stage, mg);
	}

	/**
	*	Saves current state of {@link maze.routing.RouteFinder} object to file.
	*	@param stage current stage
	*	@param mg MazeGrid object containing RouteFinder
	*	@throws NullPointerException if file path is left empty (i.e. FileChooser is cancelled)
	*	@throws java.io.FileNotFoundException if the file exists but is a directory rather than a regular file, does not exist but cannot be created, or cannot be opened for any other reason
	*	@throws java.io.IOException if an I/O error occurs while writing stream header
	*/
	private void saveRoute(Stage stage, MazeGrid mg) throws NullPointerException, FileNotFoundException, IOException{
		// Create output file
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Save Route File");
        File file = fileChooser.showSaveDialog(stage);
        String path = file.getPath();

        // Save route to file
        mg.save(path);
	}

	/**
	*	Sets Maze scene according to the state of {@link maze.routing.RouteFinder}.
	*	@param stage current stage
	*	@param mg MazeGrid object containing RouteFinder
	*	@return Returns Maze scene.
	*/
	private Scene getMazeScene(Stage stage, MazeGrid mg){
		// Get current state of MazeGrid
		GridPane mazeGrid = mg.display();
		
		Alert a = new Alert(AlertType.NONE);

		// Button actions
		stepButton.setOnAction(e -> {
			try{
				if(mg.isFinished()){
					a.setAlertType(AlertType.INFORMATION);
					a.setContentText("Maze already finished!");
					a.show();
				}else{
					mg.step();
					Scene new_scene = getMazeScene(stage, mg);
					changeScene(stage, new_scene, "Maze Solver");
				}
				
			}catch(NoRouteFoundException err){
				a.setAlertType(AlertType.ERROR);
				a.setContentText("NoRouteFoundException:\nMaze has no solution");
				a.show();
			}
		});
		finishButton.setOnAction(e -> {
			try{
				if(mg.isFinished()){
					a.setAlertType(AlertType.INFORMATION);
					a.setContentText("Maze already finished!");
					a.show();
				}else{
					while(!mg.isFinished())
						mg.step();
					Scene new_scene = getMazeScene(stage, mg);
					changeScene(stage, new_scene, "Maze Solver");
				}
				
			}catch(NoRouteFoundException err){
				a.setAlertType(AlertType.ERROR);
				a.setContentText("NoRouteFoundException:\nMaze has no solution");
				a.show();
			}
		});
		saveButton.setOnAction(e -> {
			try{
				saveRoute(stage, mg);
			}catch (FileNotFoundException err) {
            	a.setAlertType(AlertType.ERROR);
				a.setContentText("Could not save route");
				a.show();
       		}catch (IOException err) {
            	a.setAlertType(AlertType.ERROR);
				a.setContentText("Problem when saving route");
				a.show();
        	}catch(NullPointerException err){}
		});
		 
		// Maze Scene creation
		VBox sideBox = new VBox(25, mapButton, loadButton, saveButton, exitButton);
		HBox content = new HBox(40, mazeGrid, sideBox);
		HBox controlButtons = new HBox(30, stepButton, finishButton);
		VBox root = new VBox(30, title, content, controlButtons);
		root.setAlignment(Pos.CENTER);
		root.setBackground(Background.EMPTY);
		content.setAlignment(Pos.CENTER);
		controlButtons.setAlignment(Pos.CENTER_LEFT);
		controlButtons.setPadding(new Insets(10, 10, 0, 30));
		Scene maze_scene = new Scene(root, 1024,768, Color.web("252525"));
		
		return maze_scene;
	}

}
