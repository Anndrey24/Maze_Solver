# Maze Solver
Maze solving GUI application / Programming 2 (2020-2021) 

## Overview
Coded in Java, using the JavaFX library.  

This application illustrates a backtracking algorithm for solving mazes, using a stack to allow for iterative stepping through the process.  

The code is fully documented using javadocs in the html-javadocs folder.

## Features
- stepping through the solving algorithm one move at a time
- one-click route finding
- load mazes from text files
- save/load current route progress

## How to compile/run the code
Only works on Linux because of the included JavaFX library

```
$ ./javac.sh src/MazeApplication.java  
$ ./java.sh MazeApplication
```
## How to create input mazes
Sample mazes are provided in the resources/mazes folder.  
An input maze is loaded from a text file. It needs to have an equal number of cells on each row, it must have a valid solution and exactly one entrance and one exit. Make sure there are no trailing whitespaces or newlines.  
Each character in the file represents one cell in the maze:
 - CORRIDOR: .
 - ENTRANCE: e
 - EXIT: x
 - WALL: #

## Screenshots

### Menu

![Menu Image](/screenshots/menu.png)

### Different Maze Sizes

![Maze Image 1](/screenshots/maze1.png)
![Maze Image 2](/screenshots/maze2.png)

### Route Finding

![Route Image 1](/screenshots/route1.png)
![Route Image 2](/screenshots/route2.png)

### Maze Text File

![Text File Image](/screenshots/text.png)
