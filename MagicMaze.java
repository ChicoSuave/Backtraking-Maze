/* Jose Kostyun
   Dr. Steinberg
   COP3503 Fall 2022
   Programming Assignment 2
*/

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class MagicMaze {
	//Maze Attribute declaration
	char [][] maze;
	int [][] path;
	int [] visitTele;
	String mazeName;
	int rows, columns;
	
	//Default Maze Constructor
	public MagicMaze() {
		this.maze = new char[0][0];
		this.path = new int [0][0];
		this.visitTele = new int [0];
		this.mazeName = "";
		this.rows = 0;
		this.columns = 0;	
	}
	
	//Maze Constructor with parameters
	public MagicMaze(String maze, int numRows, int numColumns) {
		this.maze = fillMaze(maze, numRows, numColumns);
		this.path = new int [numRows][numColumns];
		this.visitTele = new int [10];
		this.mazeName = getfileName(maze);
		this.rows = numRows;
		this.columns = numColumns;		
	}
	
	//Method to read text file and create maze from file
	private char[][] fillMaze(String maze, int numRows, int numColumns) {
		char[][] newMaze = new char [numRows][numColumns];
		File file = new File(maze);
		
		try {
			Scanner newscan = new Scanner(file);
			int row = 0;
			while(newscan.hasNextLine()) {
				String line = newscan.nextLine();
				for(int i = 0; i < numColumns; ++i) newMaze[row][i] = line.charAt(i);				
				row++;
			}
			newscan.close();
			return newMaze;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	//Method to get file name
	private String getfileName(String maze) {
		File file = new File(maze);
		return file.getName();
	}
	
	//Initial method to solve the maze
	public boolean solveMagicMaze() {
		return solveMagicMazeR(rows - 1, 0);
	}
	
	//Recursive maze solver with backtracking
	public boolean solveMagicMazeR(int rowPosition, int columnPosition) {
		//Checks to see if Dr. Steinberg is in a valid position
		if(isValid(rowPosition, columnPosition)) {
			//Checks to see if Dr. Steinberg has found the exit in this case the X
			if(maze[rowPosition][columnPosition] == 'X') return true;
			//Checks to see if this path has been taken before as to not move in circles. 
			if(path[rowPosition][columnPosition] == 1 && !Character.isDigit(maze[rowPosition][columnPosition])) return false;
			
			//Sets path array at Dr. Steinberg current row and column position to 1 to ensure not moving in circles
			path[rowPosition][columnPosition] = 1;
			
			//checks to see if current position is a teleporter and find the location to teleport to
			if(Character.isDigit(maze[rowPosition][columnPosition]) && visitTele[Character.getNumericValue(maze[rowPosition][columnPosition])] < 2) {
				visitTele[Character.getNumericValue(maze[rowPosition][columnPosition])]++;
				int newCords[] = new int [2];
				newCords = findTeleport(rowPosition,columnPosition);
				rowPosition = newCords[0];
				columnPosition = newCords[1];
				path[rowPosition][columnPosition] = 1;
				}
			
			//Recursive calls to move around the maze
			if(solveMagicMazeR(rowPosition + 1, columnPosition)) return true;
			if(solveMagicMazeR(rowPosition, columnPosition + 1)) return true;
			if(solveMagicMazeR(rowPosition, columnPosition - 1)) return true;
			if(solveMagicMazeR(rowPosition - 1, columnPosition)) return true;
			
			//backtracks
			path[rowPosition][columnPosition] = 0;
			return false;
		}
		//Returns false if no solution can be found
		return false;
	}
	
	//"Method to find the location to be teleported to
	private int[] findTeleport(int rowPosition, int columnPosition) {
		int cords[] = new int [2];
		for (int i = 0; i < rows; ++i) {
			for(int j = 0; j < columns;++j) {
				if(i == rowPosition && j == columnPosition) continue; 
				else if(maze[rowPosition][columnPosition] == maze[i][j]) {
					cords[0] = i;
					cords[1] = j;
				}
			}
		}
		return cords;
		
	}
	
	//Method to check if position is a valid square
	private boolean isValid(int rowPosition, int columnPosition) {
		return (rowPosition >= 0 && rowPosition < rows && columnPosition >= 0 && columnPosition < columns && maze[rowPosition][columnPosition] != '@');
	}

}
