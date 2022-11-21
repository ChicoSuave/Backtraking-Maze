/** Dr. Andrew Steinberg
 *  COP3503 Computer Science 2
 *  Programming Assignment 2 Driver
 *  Fall 2022
 */
 
import java.io.File;
import java.util.Scanner;

public class MagicMazeDriver 
{   
    public static void main(String args[]) throws Exception
    {   
        MagicMaze maze = new MagicMaze("C:/Users/Jose/Desktop/Eclipse/MagicMaze/src/maze2.txt",11, 15); 
        
        if(maze.solveMagicMaze())
			System.out.println(maze.mazeName + " passed!");
		else
        	System.out.println(maze.mazeName + " did not passed!");
    }
}
