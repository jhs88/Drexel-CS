package maze;

import maze.ui.MazeViewer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MazeGameDriver {

    final static String path = System.getProperty("user.dir") + "/large.maze";

    public static Maze loadMaze(final String path, MazeFactory mazeFactory) throws IOException {
        return mazeFactory.loadMaze(path);
    }

    public static void main(String[] args) throws IOException {
        Maze maze = null;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Select an option");
        System.out.println("1) Red\n2) Blue");
        String input = br.readLine();

        if(input.equals("1")){
            maze = loadMaze(path, new RedMazeFactory());
            MazeViewer viewer = new MazeViewer(maze);
            viewer.run();
        }else if(input.equals("2")){
            maze = loadMaze(path, new BlueMazeFactory());
            MazeViewer viewer = new MazeViewer(maze);
            viewer.run();
        }else{
            System.out.println("Not a valid option number");
        }

    }
}
